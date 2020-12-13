package edu.mch.shikaku.stopwatch;
import edu.mch.shikaku.activities.GameActivity;

/*
	Objekt, který se předává mezi vláknem, na kterém běží stopky
	a vláknem uživatelského rozhraní.
*/
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
