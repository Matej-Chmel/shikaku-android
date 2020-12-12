package edu.mch.shikaku;
import android.graphics.Color;
import android.graphics.Paint;

public class TopRenderer extends Renderer
{
	private final Paint blackPaint;
	private final float textMoveHeight;
	private final float textMoveWidth;
	private final Paint textPaint;

	public TopRenderer(int dimX, int dimY, int height, int width)
	{
		super(dimX, dimY, height, width);
		this.blackPaint = new Paint();
		this.blackPaint.setColor(Color.BLACK);
		this.blackPaint.setStyle(Paint.Style.STROKE);
		this.textMoveHeight = 3 * (this.tileHeight / 4);
		this.textMoveWidth = 1 * (this.tileWidth / 4);
		this.textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		this.textPaint.setColor(Color.BLACK);
		this.textPaint.setStyle(Paint.Style.FILL);
		this.textPaint.setTextSize(this.tileWidth / 2);
	}

	public void draw(Level level)
	{
		if (!(level instanceof LevelItem))
			this.clear();

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

		this.blackPaint.setStrokeWidth(Renderer.CENTERED_LINE_WIDTH);
		this.canvas.drawRect(0, 0, (int) this.width, (int) this.height, this.blackPaint);

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
			{
				int value = level.getFieldValue(x, y);

				if (value == 0)
					continue;

				this.canvas.drawText(
						String.valueOf(value),
						x * this.moveWidth + this.textMoveWidth,
						y * this.moveHeight + this.textMoveHeight,
						this.textPaint
				);
			}
	}
}
