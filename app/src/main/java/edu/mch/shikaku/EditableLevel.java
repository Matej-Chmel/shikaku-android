package edu.mch.shikaku;
import android.content.ContentValues;
import android.graphics.Canvas;

public class EditableLevel extends ViewableLevel implements Clickable
{
	private LevelItem levelItem;

	public EditableLevel(int[][] board, GameView gameView, LevelItem levelItem)
	{
		super(board, levelItem.dimX, levelItem.dimY, gameView, levelItem.id);
		this.levelItem = levelItem;
	}
	public EditableLevel(int dimX, int dimY, GameView gameView)
	{
		super(new int[dimX][dimY], dimX, dimY, gameView, 0);
		this.clear();
	}

	public void clear()
	{
		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
				this.board[x][y] = 0;
	}
	public LevelItem createLevelItem()
	{
		this.levelItem = new LevelItem(
				this.board,
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
	public boolean itemExists()
	{
		return this.levelItem != null;
	}
	@Override
	public void onClick(float x, float y)
	{

	}
	@Override
	public void renderTo(Canvas canvas)
	{
		this.topRenderer.renderBackground(canvas);
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
	public void update()
	{
		this.levelItem.update(this.board, this.dimX, this.dimY);
	}
}
