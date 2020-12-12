package edu.mch.shikaku;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GameView extends View
{
	private boolean changed = true;
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

	public void init(ViewableLevel level)
	{
		this.level = level;
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
