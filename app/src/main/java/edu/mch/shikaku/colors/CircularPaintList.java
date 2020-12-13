package edu.mch.shikaku.colors;
import android.content.Context;
import android.graphics.Paint;
import edu.mch.shikaku.R;

/*
	Kruhový seznam barev.
*/
public class CircularPaintList
{
	private int currentIndex;
	private final Paint[] paints;

	public CircularPaintList(Context context)
	{
		this.reset();

		int[] colors = context.getResources().getIntArray(R.array.rectangle_colors);
		this.paints = new Paint[colors.length];

		for(int i = 0; i < colors.length; i++)
		{
			Paint paint = new Paint();
			paint.setColor(colors[i]);
			this.paints[i] = paint;
		}
	}

	public Paint next()
	{
		if (this.currentIndex == this.paints.length)
			this.currentIndex = 0;

		return this.paints[this.currentIndex++];
	}
	public void reset()
	{
		this.currentIndex = 0;
	}
}
