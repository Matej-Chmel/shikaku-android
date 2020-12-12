package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelHolder
{
	public static final int IMAGE_DIMENSION = 1340;

	private final ImageView imageViewpreview;
	private final TextView textViewDimensions;
	private final View view;

	public LevelHolder(View view)
	{
		this.imageViewpreview = view.findViewById(R.id.imageView_levelPreview);
		this.textViewDimensions = view.findViewById(R.id.textView_levelDimensions);
		this.view = view;
	}

	@SuppressLint("DefaultLocale")
	public void apply(Context context, LevelItem item)
	{
		Resources resources = context.getResources();

		if (item.bitmap == null)
		{
			TopRenderer renderer = new TopRenderer(
					item.getDimX(),
					item.getDimY(),
					LevelHolder.IMAGE_DIMENSION,
					LevelHolder.IMAGE_DIMENSION
			);
			renderer.renderBackground(renderer.canvas);
			renderer.draw(item);
			item.bitmap = renderer.getBitmap();
		}

		this.imageViewpreview.setImageBitmap(item.bitmap);
		this.textViewDimensions.setText(String.format("%dx%d", item.getDimX(), item.getDimY()));
		this.view.setBackgroundColor(resources.getColor(item.getDifficulty().color));
	}
}
