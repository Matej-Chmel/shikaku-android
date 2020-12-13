package edu.mch.shikaku.levels;
import android.content.ContentValues;
import android.database.Cursor;
import androidx.annotation.NonNull;
import java.util.Scanner;

/*
	Abstraktní třída hlavolamu.

	Obsahuje ty nejzákladnější vlastnosti pro ukládání mřížky během řešení
	a ukládání objektu do databáze.
*/
public abstract class Level
{
	public static final int MAXIMUM_DIMENSION = 11;
	public static final int MAXIMUM_FIELD_VALUE = Level.MAXIMUM_DIMENSION * Level.MAXIMUM_DIMENSION;

	public static final String BOARD = "board";
	public static final String BEST_TIME = "best_time";
	public static final String DIFFICULTY = "difficulty";
	public static final String DIM_X = "dim_x";
	public static final String DIM_Y = "dim_y";
	public static final String ID = "id";
	public static final String TABLE = "levels";

	protected int[][] board;
	protected int dimX;
	protected int dimY;
	protected long id;

	protected Level(int[][] board, int dimX, int dimY, long id)
	{
		this.init(board, dimX, dimY);
		this.id = id;
	}
	protected Level(Cursor cursor)
	{
		int dimX = cursor.getInt(cursor.getColumnIndex(Level.DIM_X));
		int dimY = cursor.getInt(cursor.getColumnIndex(Level.DIM_Y));
		this.init(new int[dimX][dimY], dimX, dimY);
		this.id = cursor.getInt(cursor.getColumnIndex(Level.ID));

		String boardData = cursor.getString(cursor.getColumnIndex(Level.BOARD));
		Scanner scanner = new Scanner(boardData);

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
				this.board[x][y] = scanner.nextInt();
	}

	protected int[][] createBoardCopy()
	{
		int[][] newBoard = new int[this.dimX][this.dimY];

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
				newBoard[x][y] = this.getFieldValue(x, y);

		return newBoard;
	}
	public ContentValues getContentValues()
	{
		ContentValues content = new ContentValues();
		content.put(Level.BOARD, this.toString());
		content.put(Level.DIM_X, this.dimX);
		content.put(Level.DIM_Y, this.dimY);
		return content;
	}
	public int getDimX()
	{
		return dimX;
	}
	public int getDimY()
	{
		return dimY;
	}
	public int getFieldValue(int x, int y)
	{
		return this.board[x][y];
	}
	public long getId()
	{
		return id;
	}
	public String[] getIdString()
	{
		return new String[]{String.valueOf(this.id)};
	}
	public String getWhereCondition()
	{
		return String.format("%s = ?", Level.ID);
	}
	protected void init(int[][] board, int dimX, int dimY)
	{
		this.board = board;
		this.dimX = dimX;
		this.dimY = dimY;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	@NonNull
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.ensureCapacity(2 * this.dimX * this.dimY);

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
			{
				builder.append(this.board[x][y]);
				builder.append(' ');
			}

		return builder.toString();
	}
}
