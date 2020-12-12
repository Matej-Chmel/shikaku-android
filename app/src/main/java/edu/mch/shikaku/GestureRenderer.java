package edu.mch.shikaku;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;

public class GestureRenderer extends Renderer
{
	private final Paint okPaint;
	private final Paint overlapPaint;

	protected GestureRenderer(Context context, int dimX, int dimY, int height, int width)
	{
		super(dimX, dimY, height, width);

		Resources resources = context.getResources();

		this.okPaint = new Paint();
		this.okPaint.setColor(resources.getColor(R.color.gesture_ok));
		this.overlapPaint = new Paint();
		this.overlapPaint.setColor(resources.getColor(R.color.gesture_overlaps));
	}

	public void draw(GameRectangle rectangle)
	{
		this.clear();
		rectangle.drawAsGesture(this.canvas, rectangle.isCorrect() ? this.okPaint : this.overlapPaint);
	}
}
