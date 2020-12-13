package edu.mch.shikaku.stopwatch;
import android.os.Handler;
import java.util.TimerTask;
import edu.mch.shikaku.activities.GameActivity;

public class StopWatchTask extends TimerTask
{
	private final Handler handler;
	private final GameActivity host;
	private long milliseconds;

	public StopWatchTask(Handler handler, GameActivity host)
	{
		this.handler = handler;
		this.host = host;
		this.milliseconds = 0;
	}

	public long getMilliseconds()
	{
		return this.milliseconds;
	}
	@Override
	public void run()
	{
		this.milliseconds += StopWatch.PERIOD;
		this.handler.post(new StopWatchUpdater(this.host, this.milliseconds));
	}
}
