package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class PlayableLevel extends ViewableLevel
{
	private BaseRenderer baseRenderer;
	private final HashMap<Integer, GameField> fieldsByPosition;
	private final LinkedHashSet<GameRectangle> gameRectangles;
	private boolean gestureActive;
	private int gestureEndX;
	private int gestureEndY;
	private GestureRenderer gestureRenderer;
	private int gestureStartX;
	private int gestureStartY;

	protected PlayableLevel(int[][] board, int dimX, int dimY, GameView gameView, long id)
	{
		super(board, dimX, dimY, gameView, id);
		this.fieldsByPosition = new HashMap<>();
		this.gameRectangles = new LinkedHashSet<>();
		this.gestureActive = false;

		int lenghtX = this.dimX + 1;

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
				this.fieldsByPosition.put(lenghtX * y + x, new GameField(this.getFieldValue(x, y)));
	}

	private GameRectangle createGameRectangle()
	{
		return new GameRectangle(this.dimX,
				this.fieldsByPosition,
				this.gestureStartX,
				this.gestureStartY,
				this.gestureEndX,
				this.gestureEndY
		);
	}
	@Override
	public void draw()
	{
		this.baseRenderer.draw(this.gameRectangles);

		if (this.gestureActive)
			this.gestureRenderer.draw(this.createGameRectangle());

		super.draw();
	}
	public void onGestureEnd(int x, int y)
	{
		this.gestureActive = false;
		this.onGestureMove(x, y);

		GameRectangle rectangle = this.createGameRectangle();

		if (rectangle.doesOverlap())
			return;

		rectangle.setAsParent();
		this.gameRectangles.add(rectangle);
	}
	public void onGestureMove(int x, int y)
	{
		this.gestureEndX = x;
		this.gestureEndY = y;
	}
	public void onGestureStart(int x, int y)
	{
		this.gestureActive = true;
		this.gestureStartX = x;
		this.gestureStartY = y;
		this.onGestureMove(x, y);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onSizeChanged(int height, int width)
	{
		super.onSizeChanged(height, width);

		Context context = this.gameView.getContext();

		this.baseRenderer = new BaseRenderer(context,
				this.fieldsByPosition,
				this.dimX,
				this.dimY,
				height,
				width
		);
		this.gestureRenderer = new GestureRenderer(context, this.dimX, this.dimY, height, width);

		if (this.controlEnabled)
			this.gameView.setOnTouchListener(new GestureDetector(this.dimX,
					this.dimY,
					this,
					this.baseRenderer.moveHeight,
					this.baseRenderer.moveWidth
			));
	}
	@Override
	public void renderTo(Canvas canvas)
	{
		this.topRenderer.renderBackground(canvas);
		this.baseRenderer.renderTo(canvas);
		this.gestureRenderer.renderTo(canvas);
		this.topRenderer.renderTo(canvas);
	}
	public void update()
	{
		this.gameView.update();
	}
}
