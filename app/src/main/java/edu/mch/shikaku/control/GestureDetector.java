package edu.mch.shikaku.control;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import edu.mch.shikaku.levels.PlayableLevel;

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
		int x = PositionCalculator.getPosition(event.getX(), this.lastX, this.moveWidth);
		int y = PositionCalculator.getPosition(event.getY(), this.lastY, this.moveHeight);

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
