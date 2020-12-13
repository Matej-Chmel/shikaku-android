package edu.mch.shikaku.level;
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
import edu.mch.shikaku.views.GameView;

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
	private int valuesTotal;

	protected PlayableLevel(int[][] board, int dimX, int dimY, GameView gameView, long id)
	{
		super(board, dimX, dimY, gameView, id);
		this.fieldsByPosition = new HashMap<>();
		this.gameRectangles = new LinkedHashSet<>();
		this.gestureActive = false;
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
	public void onGestureEnd(int x, int y)
	{
		this.gestureActive = false;
		this.onGestureMove(x, y);

		GameRectangle rectangle = this.createGameRectangle();

		if (this.gestureStartX == this.gestureEndX && this.gestureStartY == this.gestureEndY)
		{
			if (rectangle.doesOverlap())
				this.gameRectangles.remove(rectangle.popOverlapping());
			return;
		}

		if (!rectangle.isCorrect())
			return;

		rectangle.setAsParent();
		this.gameRectangles.add(rectangle);

		if (this.gameRectangles.size() == this.valuesTotal)
			this.gameView.onLevelCompleted();
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

		if (this.controlEnabled)
			this.gameView.setOnTouchListener(new GestureDetector(
					this.dimX,
					this.dimY,
					this,
					this.baseRenderer.getMoveHeight(),
					this.baseRenderer.getMoveWidth()
			));
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
	public void update()
	{
		this.gameView.update();
	}
}
