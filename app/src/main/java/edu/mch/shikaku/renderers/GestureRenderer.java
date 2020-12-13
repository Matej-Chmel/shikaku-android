package edu.mch.shikaku.renderers;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import edu.mch.shikaku.R;
import edu.mch.shikaku.logic.GameRectangle;

/*
	Vykresluje právě vytvářený čtyřúhelník.

	Pokud je čtyřúhelník správně sestaven, je vykreslen barvou R.color.gesture_ok,
	jinak R.color.gesture_overlaps.
*/
public class GestureRenderer extends LevelRenderer
{
	private final Paint okPaint;
	private final Paint overlapPaint;

	public GestureRenderer(Context context, int dimX, int dimY, int height, int width)
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
