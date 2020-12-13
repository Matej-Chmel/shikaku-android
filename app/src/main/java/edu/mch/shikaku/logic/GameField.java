package edu.mch.shikaku.logic;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/*
	Objekt pole mřížky.

	Využívá se pouze při řešení hlavolamu.
	Obsahuje referenci na čtyřúhelník, který jej překrývá.
	Pomocí této reference lze určit, zda-li je pole překryto či nikoliv.
*/
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
