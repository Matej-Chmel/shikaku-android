package edu.mch.shikaku;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameField
{
	private GameRectangle parent;
	private Rect backgroundRectangle;
	public final int value;

	public GameField(int value)
	{
		this.value = value;
	}

	public void drawTo(Canvas canvas, Paint paint)
	{
		canvas.drawRect(this.backgroundRectangle, paint);
	}
	public boolean isOccupied()
	{
		return this.parent != null;
	}
	public void setBackgroundRectangle(Rect backgroundRectangle)
	{
		this.backgroundRectangle = backgroundRectangle;
	}
	public void setParent(GameRectangle parent)
	{
		this.parent = parent;
	}
	public GameRectangle popParent()
	{
		GameRectangle parent = this.parent;
		this.parent.removeAsParent();
		return parent;
	}
}
