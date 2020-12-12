package edu.mch.shikaku;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.HashMap;

public class GameRectangle
{
	private final GameField[] gameFields;

	public GameRectangle(int dimX, HashMap<Integer, GameField> fieldsByPosition, int left, int top, int right, int bottom)
	{
		if (left > right)
		{
			int temp = left;
			left = right;
			right = temp;
		}

		if (top > bottom)
		{
			int temp = top;
			top = bottom;
			bottom = temp;
		}

		this.gameFields = new GameField[(right - left + 1) * (bottom - top + 1)];
		int i = 0;

		for(int y = top; y <= bottom; y++)
			for(int x = left; x <= right; x++)
				this.gameFields[i++] = fieldsByPosition.get((dimX + 1) * y + x);
	}

	public boolean doesOverlap()
	{
		boolean valueFound = false;

		for(GameField field : this.gameFields)
		{
			if (field.isOccupied())
				return true;

			if (field.value == 0)
				continue;

			if (valueFound || field.value != this.gameFields.length)
				return true;

			valueFound = true;
		}

		return false;
	}
	public void drawTo(Canvas canvas, Paint paint)
	{
		for(GameField field : this.gameFields)
			field.drawTo(canvas, paint);
	}
	public void setAsParent()
	{
		for(GameField field : this.gameFields)
			field.setParent(this);
	}
}
