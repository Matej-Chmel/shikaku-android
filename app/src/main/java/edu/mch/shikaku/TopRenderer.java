package edu.mch.shikaku;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class TopRenderer extends Renderer
{
	private final Paint blackPaint;
	private final float moveHeight;
	private final float moveWidth;

	public TopRenderer(int dimX, int dimY, int height, int width)
	{
		super(dimX, dimY, height, width);
		this.blackPaint = new Paint(Color.BLACK);
		this.blackPaint.setStyle(Paint.Style.STROKE);
		this.blackPaint.setAntiAlias(true);
		this.moveHeight = Renderer.LINE_WIDTH + this.tileHeight;
		this.moveWidth = Renderer.LINE_WIDTH + this.tileWidth;
	}

	public void render(int[][] board)
	{
		this.blackPaint.setStrokeWidth(Renderer.LINE_WIDTH);

		for (int i = 1; i < this.dimY; i++)
		{
			float y = i * this.moveHeight;
			this.canvas.drawLine(0, y, this.width, y, this.blackPaint);
		}

		for (int i = 1; i < this.dimX; i++)
		{
			float x = i * this.moveWidth;
			this.canvas.drawLine(x, 0, x, this.height, this.blackPaint);
		}

		this.blackPaint.setStrokeWidth(Renderer.LINE_WIDTH * 2);

		this.canvas.drawRect(new Rect((int) Renderer.LINE_WIDTH,
				(int) Renderer.LINE_WIDTH,
				(int) (this.width - Renderer.LINE_WIDTH),
				(int) (this.height - Renderer.LINE_WIDTH)
		), this.blackPaint);
	}
}
