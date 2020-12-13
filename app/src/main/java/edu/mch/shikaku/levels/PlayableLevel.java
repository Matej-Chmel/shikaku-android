package edu.mch.shikaku.levels;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import java.util.HashMap;
import java.util.LinkedHashSet;
import edu.mch.shikaku.control.GestureDetector;
import edu.mch.shikaku.logic.GameField;
import edu.mch.shikaku.logic.GameRectangle;
import edu.mch.shikaku.renderers.BaseRenderer;
import edu.mch.shikaku.renderers.GestureRenderer;
import edu.mch.shikaku.sound.SoundManager;
import edu.mch.shikaku.sound.Sounds;
import edu.mch.shikaku.views.GameView;

/*
	Objekt zpracovávající herní logiku
	a také určuje pořadí vykreslení jednotlivých vrstev na Canvas.

	Herní logika:
	Při vytvoření gesta se uloží pozice označeného pole v mřížce.
	Při ukončení gesta se uloží další pozice.
	Pomocí těchto dvou pozic se poté vytvoří čtyřúhelník.
	Čtyřúhelník se úspěšně uloží, pokud nepřekrývá žádný jiný čtyřúhelník,
	a zárověň překrývá právě jednu hodnotu v mřížce, která je stejná jako
	počet polí, které čtyřúhelník překrývá.

	Pokud geta začíná a končí ve stejném poli, pakse nevytváří čtyřúhelník.
	Avšak lze pomocí tohoto gesta odstranit čtyřúhelník, který se nachází na pozici gesta.

	Pořadí vykreslení:
	Nejdříve se vykreslí bílé pozadí.
	Poté všechny správně sestavené čtyřúhelníky z gameRectangles.
	Poté právě sestavovaný čtyřúhelník pomocí gesta.
	Nakonec mřížka a hodnoty polí.
*/
public class PlayableLevel extends ViewableLevel
{
	private BaseRenderer baseRenderer;
	private final HashMap<Integer, GameField> fieldsByPosition;
	private final LinkedHashSet<GameRectangle> gameRectangles;
	private boolean gestureActive;
	private int gestureEndX;
	private int gestureEndY;
	private GestureRenderer gestureRenderer;
	private int gestureStartX;
	private int gestureStartY;
	private final SoundManager soundManager;
	private final boolean soundManagerLoaded;
	private int valuesTotal;

	protected PlayableLevel(int[][] board, int dimX, int dimY, GameView gameView, long id, SoundManager soundManager)
	{
		super(board, dimX, dimY, gameView, id);
		this.fieldsByPosition = new HashMap<>();
		this.gameRectangles = new LinkedHashSet<>();
		this.gestureActive = false;
		this.soundManager = soundManager;
		this.soundManagerLoaded = this.soundManager != null;
		this.valuesTotal = 0;

		int lengthX = this.dimX + 1;

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
			{
				int value = this.getFieldValue(x, y);
				this.fieldsByPosition.put(lengthX * y + x, new GameField(value));

				if (value != 0)
					this.valuesTotal++;
			}
	}

	private GameRectangle createGameRectangle()
	{
		return new GameRectangle(this.dimX,
				this.fieldsByPosition,
				this.gestureStartX,
				this.gestureStartY,
				this.gestureEndX,
				this.gestureEndY
		);
	}
	@Override
	public void draw()
	{
		this.baseRenderer.draw(this.gameRectangles);

		if (this.gestureActive)
			this.gestureRenderer.draw(this.createGameRectangle());

		super.draw();
	}
	@SuppressLint("ClickableViewAccessibility")
	private void enabledGestures()
	{
		if (this.controlEnabled)
			this.gameView.setOnTouchListener(new GestureDetector(this.dimX,
					this.dimY,
					this,
					this.baseRenderer.getMoveHeight(),
					this.baseRenderer.getMoveWidth()
			));
	}
	public void onGestureEnd(int x, int y)
	{
		this.gestureActive = false;
		this.onGestureMove(x, y);

		GameRectangle rectangle = this.createGameRectangle();

		if (this.gestureStartX == this.gestureEndX && this.gestureStartY == this.gestureEndY)
		{
			if (rectangle.doesOverlap())
			{
				this.gameRectangles.remove(rectangle.popOverlapping());
				this.playSound(Sounds.REMOVE);
			}
			return;
		}

		if (!rectangle.isCorrect())
		{
			this.playSound(Sounds.WRONG);
			return;
		}

		rectangle.setAsParent();
		this.gameRectangles.add(rectangle);

		if (this.gameRectangles.size() == this.valuesTotal)
		{
			this.gameView.onLevelCompleted();
			this.playSound(Sounds.VICTORY);
		}
		else
			this.playSound(Sounds.CORRECT);
	}
	public void onGestureMove(int x, int y)
	{
		this.gestureEndX = x;
		this.gestureEndY = y;
	}
	public void onGestureStart(int x, int y)
	{
		this.gestureActive = true;
		this.gestureStartX = x;
		this.gestureStartY = y;
		this.onGestureMove(x, y);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onSizeChanged(int height, int width)
	{
		super.onSizeChanged(height, width);

		Context context = this.gameView.getContext();

		this.baseRenderer = new BaseRenderer(context,
				this.fieldsByPosition,
				this.dimX,
				this.dimY,
				height,
				width
		);
		this.gestureRenderer = new GestureRenderer(context, this.dimX, this.dimY, height, width);

		this.enabledGestures();
	}
	private void playSound(int position)
	{
		if (this.soundManagerLoaded)
			this.soundManager.playSound(position);
	}
	public void restart()
	{
		for (GameRectangle rectangle : this.gameRectangles)
			rectangle.removeAsParent();

		this.gameRectangles.clear();
	}
	@Override
	public void renderTo(Canvas canvas)
	{
		this.topRenderer.drawBackground(canvas);
		this.baseRenderer.renderTo(canvas);

		if (this.gestureActive)
			this.gestureRenderer.renderTo(canvas);

		this.topRenderer.renderTo(canvas);
	}
	@Override
	public void setControlEnabled(boolean controlEnabled)
	{
		super.setControlEnabled(controlEnabled);
		this.enabledGestures();
	}
	public void update()
	{
		this.gameView.update();
	}
}
