package edu.mch.shikaku;
import android.graphics.Canvas;

public abstract class ViewableLevel extends Level
{
	protected boolean controlEnabled;
	protected final GameView gameView;
	protected TopRenderer topRenderer;

	protected ViewableLevel(int[][] board, int dimX, int dimY, GameView gameView, long id)
	{
		super(board, dimX, dimY, id);
		this.controlEnabled = true;
		this.gameView = gameView;
	}

	public void draw()
	{
		this.topRenderer.draw(this);
	}
	public void onSizeChanged(int height, int width)
	{
		this.topRenderer = new TopRenderer(this.dimX, this.dimY, height, width);
	}
	public abstract void renderTo(Canvas canvas);
	public void setControlEnabled(boolean controlEnabled)
	{
		this.controlEnabled = controlEnabled;
	}
}
