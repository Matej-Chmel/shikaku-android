package edu.mch.shikaku;
import android.content.ContentValues;

public class LevelEditor extends Level
{
	private LevelItem levelItem;

	public LevelEditor(int[][] board, LevelItem levelItem)
	{
		super(board, levelItem.dimX, levelItem.dimY, levelItem.id);
		this.levelItem = levelItem;
	}
	public LevelEditor(int dimX, int dimY)
	{
		super(new int[dimX][dimY], dimX, dimY, 0);
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
