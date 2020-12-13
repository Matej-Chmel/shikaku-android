package edu.mch.shikaku.levels;
import android.content.Context;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import edu.mch.shikaku.R;
import edu.mch.shikaku.storage.AssetReader;
import edu.mch.shikaku.storage.Database;
import edu.mch.shikaku.storage.DatabaseException;
import edu.mch.shikaku.storage.NoLevelsException;
import edu.mch.shikaku.views.EditorPalette;
import edu.mch.shikaku.views.GameView;

public class LevelManager
{
	private static LevelManager instance;

	private int currentIndex;
	private final Database database;
	public final ArrayList<LevelItem> levelItems;

	public static LevelManager getInstance(Context context)
	{
		if (instance == null)
			instance = new LevelManager(context);
		return instance;
	}

	public LevelManager(Context context)
	{
		this.currentIndex = 0;
		this.database = new Database(context);
		this.levelItems = this.database.selectAllLevelsItems();
	}

	public void checkLevelsExist() throws NoLevelsException
	{
		if (this.levelItems.isEmpty())
			throw new NoLevelsException();
	}
	public void chooseLevel(int index)
	{
		this.currentIndex = index;
	}
	public EditableLevel getEditableLevel(GameView gameView, EditorPalette palette, int index)
	{
		return this.levelItems.get(index).toEditableLevel(gameView, palette);
	}
	public int getRandomLevelIndex() throws NoLevelsException
	{
		this.checkLevelsExist();
		return ThreadLocalRandom.current().nextInt(this.levelItems.size());
	}
	public LevelItem nextLevel()
	{
		if (this.currentIndex == this.levelItems.size())
			this.currentIndex = 0;

		return this.levelItems.get(this.currentIndex++);
	}
	public boolean resetLevels(Context context)
	{
		this.levelItems.clear();
		this.database.clearAllLevels();

		try
		{
			String[] levelsData = new AssetReader(context).readText("levels.txt").split(
					"\\r\\n[\\r\\n]+");

			for (String data : levelsData)
			{
				String[] rows = data.split("\\r\\n");
				ArrayList<ArrayList<Integer>> rowValuesLists = new ArrayList<>();
				int maxWidth = 0;

				for (String row : rows)
				{
					ArrayList<Integer> rowValues = new ArrayList<>();
					Scanner scanner = new Scanner(row);

					while (scanner.hasNext())
						rowValues.add(scanner.nextInt());

					int width = rowValues.size();

					if (width > maxWidth)
						maxWidth = width;

					rowValuesLists.add(rowValues);
				}

				if (maxWidth == 0)
					continue;

				int[][] board = new int[maxWidth][rows.length];
				int y = 0;

				for (ArrayList<Integer> rowValues : rowValuesLists)
				{
					int x = 0;
					int length = rowValues.size();

					for (; x < length; x++)
						board[x][y] = rowValues.get(x);

					for (; x < maxWidth; x++)
						board[x][y] = 0;

					y++;
				}

				this.levelItems.add(new LevelItem(
						board,
						Difficulty.calculate(board, maxWidth, rows.length),
						maxWidth,
						rows.length,
						0
				));
			}

			this.database.insertLevels(this.levelItems);
			return true;
		}
		catch (DatabaseException e)
		{
			Toast.makeText(context, e.getMessage(context.getResources()), Toast.LENGTH_LONG).show();
		}
		catch (IOException e)
		{
			Toast.makeText(
					context,
					context.getResources().getString(R.string.exception_noStandardLevels),
					Toast.LENGTH_LONG
			).show();
		}

		return false;
	}
	public void saveLevel(EditableLevel level) throws DatabaseException
	{
		if (level.itemExists())
		{
			this.database.updateLevel(level);
			level.update();
		}
		else
		{
			this.database.insertLevel(level);
			this.levelItems.add(level.createLevelItem());
		}
	}
	public void saveLevel(LevelItem level) throws DatabaseException
	{
		this.database.updateLevel(level);
	}
}
