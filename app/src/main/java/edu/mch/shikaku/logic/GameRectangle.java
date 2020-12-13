package edu.mch.shikaku.logic;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.HashMap;

public class GameRectangle
{
	private Paint paint;
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
		for(GameField field : this.gameFields)
			if (field.isOccupied())
				return true;

		return false;
	}
	public void drawAsGesture(Canvas canvas, Paint paint)
	{
		for(GameField field : this.gameFields)
			field.drawTo(canvas, paint);
	}
	public boolean drawAsParent(Canvas canvas, Paint paint)
	{
		boolean paintApplied = false;

		if (this.paint == null)
		{
			paintApplied = true;
			this.paint = paint;
		}

		for(GameField field : this.gameFields)
			field.drawTo(canvas, this.paint);

		return paintApplied;
	}
	public boolean isCorrect()
	{
		boolean valueFound = false;

		for(GameField field : this.gameFields)
		{
			if (field.isOccupied())
				return false;

			if (field.value == 0)
				continue;

			if (valueFound || field.value != this.gameFields.length)
				return false;

			valueFound = true;
		}

		return valueFound;
	}
	public GameRectangle popOverlapping()
	{
		return this.gameFields[0].popParent();
	}
	public void setAsParent()
	{
		for(GameField field : this.gameFields)
			field.setParent(this);
	}
	public void removeAsParent()
	{
		this.paint = null;

		for(GameField field : this.gameFields)
			field.setParent(null);
	}
}
