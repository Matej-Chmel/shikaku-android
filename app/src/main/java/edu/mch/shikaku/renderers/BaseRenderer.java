package edu.mch.shikaku.renderers;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import edu.mch.shikaku.colors.CircularPaintList;
import edu.mch.shikaku.logic.GameField;
import edu.mch.shikaku.logic.GameRectangle;

public class BaseRenderer extends LevelRenderer
{
	private Paint currentPaint;
	private final CircularPaintList paintList;

	public BaseRenderer(Context context, HashMap<Integer, GameField> fieldsByPosition, int dimX, int dimY, int height, int width)
	{
		super(dimX, dimY, height, width);
		this.paintList = new CircularPaintList(context);
		this.currentPaint = this.paintList.next();

		int lengthX = (int) (this.dimX + 1);

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
			{
				int startX = (int) (x * this.moveWidth + LevelRenderer.LINE_WIDTH);
				int startY = (int) (y * this.moveHeight + LevelRenderer.LINE_WIDTH);

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

		for(GameRectangle rectangle : gameRectangles)
		{
			if (rectangle.drawAsParent(this.canvas, this.currentPaint))
				this.currentPaint = this.paintList.next();
		}
	}
}
