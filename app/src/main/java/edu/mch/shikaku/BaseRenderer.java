package edu.mch.shikaku;
import android.content.Context;
import android.graphics.Rect;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

public class BaseRenderer extends Renderer
{
	private final CircularPaintList paintList;

	protected BaseRenderer(Context context, HashMap<Integer, GameField> fieldsByPosition, int dimX, int dimY, int height, int width)
	{
		super(dimX, dimY, height, width);
		this.paintList = new CircularPaintList(context);

		int lengthX = (int) (this.dimX + 1);

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
			{
				int startX = (int) (x * this.moveWidth + Renderer.LINE_WIDTH);
				int startY = (int) (y * this.moveHeight + Renderer.LINE_WIDTH);

				Objects.requireNonNull(fieldsByPosition.get(lengthX * y + x))
						.setBackgroundRectangle(new Rect(
								startX,
								startY,
								startX + (int) this.tileWidth,
								startY + (int) this.tileHeight
						));
			}
	}

	public void draw(LinkedHashSet<GameRectangle> gameRectangles)
	{
		this.clear();
		this.paintList.reset();

		for(GameRectangle rectangle : gameRectangles)
			rectangle.drawTo(this.canvas, this.paintList.next());
	}
}
