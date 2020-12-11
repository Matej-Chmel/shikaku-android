package edu.mch.shikaku;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GameView extends View
{
	private boolean changed = true;
	private Level level;
	private boolean ready = false;
	private TopRenderer topRenderer;

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

	public void init(Level level)
	{
		this.level = level;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (this.ready && this.changed)
		{
			this.topRenderer.renderBackground();
			this.topRenderer.render(this.level);
			this.changed = false;
		}

		this.topRenderer.renderTo(canvas);
	}
	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
	{
		super.onSizeChanged(width, height, oldWidth, oldHeight);
		this.topRenderer = new TopRenderer(level.getDimX(), level.getDimY(), height, width);
		this.ready = true;
	}
}
