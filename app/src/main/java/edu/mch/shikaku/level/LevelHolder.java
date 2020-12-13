package edu.mch.shikaku.level;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import edu.mch.shikaku.R;
import edu.mch.shikaku.renderers.TopRenderer;

public class LevelHolder
{
	public static final int IMAGE_DIMENSION = 1340;

	private final ImageView imageViewPreview;
	private final TextView textViewBestTime;
	private final TextView textViewDimensions;
	private final View view;

	private final String notAttemptedText;

	public LevelHolder(View view)
	{
		this.imageViewPreview = view.findViewById(R.id.imageView_levelPreview);
		this.textViewBestTime = view.findViewById(R.id.textView_levelItem_bestTime);
		this.textViewDimensions = view.findViewById(R.id.textView_levelDimensions);
		this.view = view;

		this.notAttemptedText = view.getContext()
				.getResources()
				.getString(R.string.stopWatch_notAttempted);
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
			renderer.drawBackground();
			renderer.draw(item);
			item.bitmap = renderer.getBitmap();
		}

		this.imageViewPreview.setImageBitmap(item.bitmap);
		this.textViewDimensions.setText(String.format("%dx%d", item.getDimX(), item.getDimY()));
		this.view.setBackgroundColor(resources.getColor(item.getDifficulty().color));

		Long bestTime = item.getBestTime();
		this.textViewBestTime.setText(bestTime == null ? this.notAttemptedText : String.format(
				"%d s",
				bestTime / 1000
		));
	}
}
