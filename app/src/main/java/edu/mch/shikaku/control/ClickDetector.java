package edu.mch.shikaku.control;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

public class ClickDetector implements View.OnTouchListener
{
	private final Clickable clickable;

	public ClickDetector(Clickable clickable)
	{
		this.clickable = clickable;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
			this.clickable.onClick(event.getX(), event.getY());
		return true;
	}
}
