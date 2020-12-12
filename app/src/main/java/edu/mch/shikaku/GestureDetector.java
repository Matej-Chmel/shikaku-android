package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

public class GestureDetector implements View.OnTouchListener
{
	private final PlayableLevel host;
	private final int lastX;
	private final int lastY;
	private final float moveHeight;
	private final float moveWidth;

	public GestureDetector(int dimX, int dimY, PlayableLevel host, float moveHeight, float moveWidth)
	{
		this.host = host;
		this.lastX = dimX - 1;
		this.lastY = dimY - 1;
		this.moveHeight = moveHeight;
		this.moveWidth = moveWidth;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		int x = Math.max(Math.min((int) (event.getX() / this.moveWidth), this.lastX), 0);
		int y = Math.max(Math.min((int) (event.getY() / this.moveHeight), this.lastY), 0);

		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				this.host.onGestureStart(x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				this.host.onGestureMove(x, y);
				break;
			case MotionEvent.ACTION_UP:
				this.host.onGestureEnd(x, y);
				break;
			default:
				break;
		}

		this.host.update();
		return true;
	}
}
