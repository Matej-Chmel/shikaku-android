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
	public EditableLevel toEditableLevel(GameView view, EditorPalette palette)
	{
		return new EditableLevel(this.createBoardCopy(), view, palette, this);
	}
	public PlayableLevel toPlayableLevel(GameView view)
	{
		return new PlayableLevel(
				this.createBoardCopy(),
				this.getDimX(),
				this.getDimY(),
				view,
				this.getId()
		);
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
				builder.append(this.getFieldValue(x, y));
				builder.append(' ');
			}

			builder.append(this.getFieldValue(lastX, y));
			builder.append('\n');
		}

		return builder.toString();
	}
	public void update(int[][] board, int dimX, int dimY)
	{
		this.init(board, dimX, dimY);
		this.bitmap = null;
		this.difficulty = Difficulty.calculate(this.board, this.dimX, this.dimY);
	}
}
