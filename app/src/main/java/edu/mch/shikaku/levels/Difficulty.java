package edu.mch.shikaku.levels;
import java.util.EnumSet;
import java.util.HashMap;
import edu.mch.shikaku.R;

public enum Difficulty
{
	EASY(R.color.difficulty_easy, 0), NORMAL(R.color.difficulty_normal,
		1
), MEDIUM(R.color.difficulty_medium, 2), HARD(R.color.difficulty_hard,
		3
), EXTREME(R.color.difficulty_extreme, 4);

	private static final HashMap<Integer, Difficulty> difficultyByValue;

	public final int color;
	public final int value;

	static
	{
		EnumSet<Difficulty> set = EnumSet.allOf(Difficulty.class);
		difficultyByValue = new HashMap<>();

		for (Difficulty difficulty : set)
			difficultyByValue.put(difficulty.value, difficulty);
	}

	public static Difficulty calculate(int[][] board, int dimX, int dimY)
	{
		int totalSpace = dimX * dimY;

		if (totalSpace <= 9)
			return Difficulty.EASY;
		if (totalSpace <= 20)
			return Difficulty.NORMAL;

		int maxField = 0;

		for (int y = 0; y < dimY; y++)
			for (int x = 0; x < dimX; x++)
			{
				int fieldValue = board[x][y];

				if (fieldValue > maxField)
					maxField = fieldValue;
			}

		if (maxField == 0 || maxField == totalSpace)
			return Difficulty.EASY;

		float percentageTake = (float) maxField / (float) totalSpace;

		if (percentageTake < 0.05)
			return Difficulty.EXTREME;
		if (percentageTake < 0.15)
			return Difficulty.HARD;
		return Difficulty.MEDIUM;
	}
	public static Difficulty fromInteger(int value)
	{
		return Difficulty.difficultyByValue.get(value);
	}

	Difficulty(int color, int value)
	{
		this.color = color;
		this.value = value;
	}
}
