package edu.mch.shikaku;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

public abstract class Renderer
{
	protected final Bitmap bitmap;
	protected final Canvas canvas;
	protected final Paint defaultPaint;
	protected final float height;
	protected final float width;

	protected Renderer(int height, int width)
	{
		this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		this.canvas = new Canvas(this.bitmap);
		this.defaultPaint = new Paint();
		this.height = height;
		this.width = width;
	}

	protected void clear()
	{
		this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}
	public Bitmap getBitmap()
	{
		return this.bitmap.copy(this.bitmap.getConfig(), true);
	}
	public void renderBackground(Canvas canvas)
	{
		canvas.drawColor(Color.WHITE);
	}
	public void renderTo(Canvas canvas)
	{
		canvas.drawBitmap(this.bitmap, 0, 0, this.defaultPaint);
	}
}
