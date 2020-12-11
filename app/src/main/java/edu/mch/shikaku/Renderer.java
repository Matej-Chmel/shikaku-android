package edu.mch.shikaku;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public abstract class Renderer
{
	public static final float LINE_WIDTH = 16;
	public static final float CENTERED_LINE_WIDTH = 2 * Renderer.LINE_WIDTH;

	protected final Bitmap bitmap;
	protected final Canvas canvas;
	protected final Paint cleanerPaint;
	protected final Paint defaultPaint;
	protected final float dimX;
	protected final float dimY;
	protected final float height;
	protected final float tileHeight;
	protected final float tileWidth;
	protected final float width;

	protected Renderer(int dimX, int dimY, int height, int width)
	{
		this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		this.canvas = new Canvas(this.bitmap);
		this.cleanerPaint = new Paint();
		this.cleanerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		this.defaultPaint = new Paint();
		this.dimX = dimX;
		this.dimY = dimY;
		this.height = height;
		this.tileHeight = (height - (dimY + 1) * Renderer.LINE_WIDTH) / dimY;
		this.tileWidth = (width - (dimX + 1) * Renderer.LINE_WIDTH) / dimX;
		this.width = width;
	}

	public Bitmap getBitmap()
	{
		return this.bitmap.copy(this.bitmap.getConfig(), true);
	}
	public void renderBackground()
	{
		this.canvas.drawColor(Color.WHITE);
	}
	public void renderTo(Canvas c)
	{
		c.drawBitmap(this.bitmap, 0, 0, this.defaultPaint);
	}
}
