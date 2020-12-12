package edu.mch.shikaku;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GameView extends View
{
	private boolean changed = true;
	private GameActivity host;
	private ViewableLevel level;
	private boolean ready = false;

	public GameView(Context context)
	{
		super(context);
	}
	public GameView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}
	public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public void disableControl()
	{
		this.level.setControlEnabled(false);
	}
	public void init(GameActivity host, ViewableLevel level)
	{
		this.host = host;
		this.level = level;

		if (this.ready)
			this.level.onSizeChanged(this.getHeight(), this.getWidth());

		this.update();
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (this.ready && this.changed)
		{
			this.level.draw();
			this.changed = false;
		}

		this.level.renderTo(canvas);
	}
	public void onLevelCompleted()
	{
		this.host.onGameCompleted();
	}
	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
	{
		super.onSizeChanged(width, height, oldWidth, oldHeight);
		this.level.onSizeChanged(height, width);
		this.ready = true;
	}
	public void update()
	{
		this.changed = true;
		this.invalidate();
	}
}
