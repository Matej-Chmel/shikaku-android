package edu.mch.shikaku.stopwatch;
import edu.mch.shikaku.activities.GameActivity;

public class StopWatchUpdater implements Runnable
{
	private final GameActivity host;
	private final long milliseconds;

	public StopWatchUpdater(GameActivity host, long milliseconds)
	{
		this.host = host;
		this.milliseconds = milliseconds;
	}

	@Override
	public void run()
	{
		this.host.onStopWatchUpdate(this.milliseconds);
	}
}
