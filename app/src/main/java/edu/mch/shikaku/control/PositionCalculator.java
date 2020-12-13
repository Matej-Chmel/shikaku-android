package edu.mch.shikaku.control;

public class PositionCalculator
{
	public static int getPosition(float coordinate, int maxValue, float moveDistance)
	{
		return Math.max(Math.min((int) (coordinate / moveDistance), maxValue), 0);
	}
}
