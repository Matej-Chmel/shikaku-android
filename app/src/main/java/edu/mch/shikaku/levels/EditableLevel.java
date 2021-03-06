package edu.mch.shikaku.levels;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.graphics.Canvas;
import edu.mch.shikaku.control.ClickDetector;
import edu.mch.shikaku.control.Clickable;
import edu.mch.shikaku.control.PositionCalculator;
import edu.mch.shikaku.sound.SoundManager;
import edu.mch.shikaku.views.EditorPalette;
import edu.mch.shikaku.views.GameView;

/*
	Objekt hlavolamu, který se používá při editaci.

	Lze měnit hodnoty polí a měnit rozměry.
	Obsahuje reference na paletu, aby správně určil vkládanou hodnotu.
	Pokud se edituje již existující hlavolam, obsahuje i referenci na položku z ListView,
	kterou může informovat o změnách.

	Tato třída vykresluje pouze mřížku s hodnotami bez čtyřúhelníků.
*/
public class EditableLevel extends ViewableLevel implements Clickable
{
	private int lastX;
	private int lastY;
	private LevelItem levelItem;
	private EditorPalette palette;

	public EditableLevel(int[][] board, GameView gameView, EditorPalette palette, LevelItem levelItem)
	{
		super(board, levelItem.dimX, levelItem.dimY, gameView, levelItem.id);
		this.init(palette);
		this.levelItem = levelItem;
	}
	public EditableLevel(int dimX, int dimY, GameView gameView, EditorPalette palette)
	{
		super(new int[dimX][dimY], dimX, dimY, gameView, 0);
		this.init(palette);
		this.clear();
	}
	private void init(EditorPalette palette)
	{
		this.palette = palette;
		this.setControlEnabled(this.controlEnabled);
	}

	public void clear()
	{
		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
				this.board[x][y] = 0;
	}
	public LevelItem createLevelItem()
	{
		this.levelItem = new LevelItem(this.board,
				Difficulty.calculate(this.board, this.dimX, this.dimY),
				this.dimX,
				this.dimY,
				this.id
		);
		return this.levelItem;
	}
	@Override
	public ContentValues getContentValues()
	{
		ContentValues content = super.getContentValues();
		content.put(Level.DIFFICULTY, Difficulty.calculate(this.board, this.dimX, this.dimY).value);
		return content;
	}
	@Override
	protected void init(int[][] board, int dimX, int dimY)
	{
		super.init(board, dimX, dimY);
		this.lastX = this.dimX - 1;
		this.lastY = this.dimY - 1;
	}
	public boolean itemExists()
	{
		return this.levelItem != null;
	}
	@Override
	public void onClick(float x, float y)
	{
		int value = this.palette.getChosenNumber();

		if (value == -1)
			return;

		this.setFieldValue(value,
				PositionCalculator.getPosition(x, this.lastX, this.topRenderer.getMoveWidth()),
				PositionCalculator.getPosition(y, this.lastY, this.topRenderer.getMoveHeight())
		);

		this.gameView.update();
	}
	@Override
	public void onSizeChanged(int height, int width)
	{
		super.onSizeChanged(height, width);
		this.setControlEnabled(this.controlEnabled);
	}
	@Override
	public void renderTo(Canvas canvas)
	{
		this.topRenderer.drawBackground(canvas);
		this.topRenderer.renderTo(canvas);
	}
	public void resize(int newDimX, int newDimY)
	{
		int[][] newBoard = new int[newDimX][newDimY];
		int minX = Math.min(this.dimX, newDimX);
		int minY = Math.min(this.dimY, newDimY);

		for (int y = 0; y < minY; y++)
			for (int x = 0; x < minX; x++)
				newBoard[x][y] = this.board[x][y];

		for (int y = minY; y < newDimY; y++)
			for (int x = minX; x < newDimX; x++)
				newBoard[x][y] = 0;

		this.init(newBoard, newDimX, newDimY);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void setControlEnabled(boolean controlEnabled)
	{
		super.setControlEnabled(controlEnabled);

		if (this.controlEnabled)
			this.gameView.setOnTouchListener(new ClickDetector(this));
	}
	private void setFieldValue(int value, int x, int y)
	{
		this.board[x][y] = value;
	}
	public PlayableLevel toPlayableLevel(SoundManager soundManager)
	{
		return new PlayableLevel(this.createBoardCopy(),
				this.dimX,
				this.dimY,
				this.gameView,
				this.id,
				soundManager
		);
	}
	public void update()
	{
		this.levelItem.update(this.board, this.dimX, this.dimY);
	}
}
