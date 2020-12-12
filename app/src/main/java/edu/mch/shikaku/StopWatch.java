package edu.mch.shikaku;
import android.os.Handler;
import java.util.Timer;

public class StopWatch
{
	public static final long PERIOD = 1000;

	private final Handler handler;
	private final GameActivity host;
	private Timer timer;
	private StopWatchTask task;

	public StopWatch(Handler handler, GameActivity host)
	{
		this.handler = handler;
		this.host = host;
	}

	public void start()
	{
		this.task = new StopWatchTask(this.handler, this.host);
		this.timer = new Timer();
		this.timer.schedule(this.task, 0, StopWatch.PERIOD);
	}
	public long stop()
	{
		if (this.task == null)
			return 0;

		this.task.cancel();
		this.timer.cancel();
		this.timer.purge();

		long milliseconds = this.task.getMilliseconds();

		this.task = null;
		this.timer = null;

		return milliseconds;
	}
}
