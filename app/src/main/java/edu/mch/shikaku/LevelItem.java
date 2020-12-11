package edu.mch.shikaku;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

public class LevelItem extends Level
{
	public Bitmap bitmap;
	private Difficulty difficulty;

	public LevelItem(int[][] board, Difficulty difficulty, int dimX, int dimY, long id)
	{
		super(board, dimX, dimY, id);
		this.difficulty = difficulty;
	}
	public LevelItem(Cursor cursor)
	{
		super(cursor);
		this.difficulty = Difficulty.fromInteger(cursor.getInt(cursor.getColumnIndex(LevelItem.DIFFICULTY)));
	}

	public ContentValues getContentValues()
	{
		ContentValues content = super.getContentValues();
		content.put(LevelItem.DIFFICULTY, this.difficulty.value);
		return content;
	}
	public Difficulty getDifficulty()
	{
		return difficulty;
	}
	public LevelEditor toLevelEditor()
	{
		int[][] newBoard = new int[this.dimX][this.dimY];

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
				newBoard[x][y] = this.getField(x, y);

		return new LevelEditor(newBoard, this);
	}
	public String toTextDisplay()
	{
		StringBuilder builder = new StringBuilder();
		builder.ensureCapacity(2 * this.dimX * this.dimY + this.dimY);

		for (int y = 0; y < this.dimY; y++)
		{
			int lastX = this.dimX - 1;

			for (int x = 0; x < lastX; x++)
			{
				builder.append(this.getField(x, y));
				builder.append(' ');
			}

			builder.append(this.getField(lastX, y));
			builder.append('\n');
		}

		return builder.toString();
	}
	public void update(int[][] board, int dimX, int dimY)
	{
		this.init(board, dimX, dimY);
		this.difficulty = Difficulty.calculate(this.board, this.dimX, this.dimY);
	}
}
