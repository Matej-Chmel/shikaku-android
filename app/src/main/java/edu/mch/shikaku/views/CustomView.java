package edu.mch.shikaku.views;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/*
	Abstraktní třída vlastního View.
*/
public abstract class CustomView extends View
{
	protected boolean changed = true;
	protected boolean ready = false;

	public CustomView(Context context)
	{
		super(context);
	}
	public CustomView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}
	public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
	{
		super.onSizeChanged(width, height, oldWidth, oldHeight);
		this.ready = true;
	}
	public void update()
	{
		this.changed = true;
		this.invalidate();
	}
}
