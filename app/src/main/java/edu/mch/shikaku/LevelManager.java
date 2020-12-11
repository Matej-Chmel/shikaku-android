package edu.mch.shikaku;
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class LevelManager
{
	private static LevelManager instance;

	private final Database database;
	private final ArrayList<LevelItem> levelItems;

	public static LevelManager getInstance(Context context)
	{
		if (instance == null)
			instance = new LevelManager(context);
		return instance;
	}

	public LevelManager(Context context)
	{
		this.database = new Database(context);
		this.levelItems = this.database.selectAllLevelsItems();
	}

	public LevelItem getLevel(int index)
	{
		return this.levelItems.get(index);
	}
	public int getRandomLevelIndex() throws NoLevelsException
	{
		if (this.levelItems.isEmpty())
			throw new NoLevelsException();
		return ThreadLocalRandom.current().nextInt(this.levelItems.size());
	}
	public void resetLevels(Context context) throws DatabaseException, IOException
	{
		this.levelItems.clear();
		this.database.clearAllLevels();

		String[] levelsData = new AssetReader(context).readFile("levels.txt").split(
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
	}
	public void saveLevel(LevelEditor editor) throws DatabaseException
	{
		if (editor.itemExists())
		{
			this.database.updateLevel(editor);
			editor.update();
		}
		else
		{
			this.database.insertLevel(editor);
			this.levelItems.add(editor.createLevelItem());
		}
	}
}