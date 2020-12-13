package edu.mch.shikaku.control;

/*
	Statická třída vypočítávající pozici pole v mřížce z koordinátů kliknutí nebo gesta.
*/
public class PositionCalculator
{
	public static int getPosition(float coordinate, int maxValue, float moveDistance)
	{
		return Math.max(Math.min((int) (coordinate / moveDistance), maxValue), 0);
	}

	private PositionCalculator()
	{
	}
}
