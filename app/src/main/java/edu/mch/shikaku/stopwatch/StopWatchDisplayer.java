package edu.mch.shikaku.stopwatch;
import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import edu.mch.shikaku.R;

public class StopWatchDisplayer
{
	private static StopWatchDisplayer instance;

	private final String notAttemptedText;

	public static StopWatchDisplayer getInstance(Context context)
	{
		if (instance == null)
			instance = new StopWatchDisplayer(context);
		return instance;
	}

	private StopWatchDisplayer(Context context)
	{
		this.notAttemptedText = context.getResources().getString(R.string.stopWatch_notAttempted);
	}

	@SuppressLint("DefaultLocale")
	public void displayTo(TextView textView, long milliseconds)
	{
		textView.setText(String.format("%.1f s", (double) milliseconds / 1000));
	}
	public void displayTo(TextView textView, Long bestTime)
	{
		if (bestTime == null)
			textView.setText(this.notAttemptedText);
		else
			this.displayTo(textView, (long) bestTime);
	}
}
