package edu.mch.shikaku.level;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import edu.mch.shikaku.views.EditorPalette;
import edu.mch.shikaku.views.GameView;

public class LevelItem extends Level
{
	public Bitmap bitmap;
	private Long bestTime;
	private Difficulty difficulty;

	public LevelItem(int[][] board, Difficulty difficulty, int dimX, int dimY, long id)
	{
		super(board, dimX, dimY, id);
		this.difficulty = difficulty;
	}
	public LevelItem(Cursor cursor)
	{
		super(cursor);

		int bestTimeIndex = cursor.getColumnIndex(LevelItem.BEST_TIME);
		if (!cursor.isNull(bestTimeIndex))
			this.bestTime = cursor.getLong(bestTimeIndex);

		this.difficulty = Difficulty.fromInteger(cursor.getInt(cursor.getColumnIndex(LevelItem.DIFFICULTY)));
	}

	public Long getBestTime()
	{
		return this.bestTime;
	}
	public ContentValues getContentValues()
	{
		ContentValues content = super.getContentValues();
		content.put(LevelItem.BEST_TIME, this.bestTime);
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
	private void updateBitmap()
	{
		this.bitmap = null;
	}
	public boolean update(Long bestTime)
	{
		if (this.bestTime == null || bestTime < this.bestTime)
		{
			this.bestTime = bestTime;
			this.updateBitmap();
			return true;
		}

		return false;
	}
	public void update(int[][] board, int dimX, int dimY)
	{
		this.init(board, dimX, dimY);
		this.updateBitmap();
		this.bestTime = null;
		this.difficulty = Difficulty.calculate(this.board, this.dimX, this.dimY);
	}
}
