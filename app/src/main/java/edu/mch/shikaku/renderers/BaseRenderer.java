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

/*
	Vykresluje správně sestavené čtyřúhelníky.
*/
public class BaseRenderer extends LevelRenderer
{
	private Paint currentPaint;
	private final CircularPaintList paintList;

	public BaseRenderer(Context context, HashMap<Integer, GameField> fieldsByPosition, int dimX, int dimY, int height, int width)
	{
		super(dimX, dimY, height, width);
		this.paintList = new CircularPaintList(context);
		this.currentPaint = this.paintList.next();

		int lastX = (int) (this.dimX - 1);
		int lastY = (int) (this.dimY - 1);
		int lengthX = (int) (this.dimX + 1);
		int fieldHeight = (int) (this.tileHeight + LevelRenderer.LINE_WIDTH);
		int fieldWidth = (int) (this.tileWidth + LevelRenderer.LINE_WIDTH);

		for (int y = 0; y < this.dimY; y++)
			for (int x = 0; x < this.dimX; x++)
			{
				int startX = (int) (x * this.moveWidth);
				int startY = (int) (y * this.moveHeight);

				Objects.requireNonNull(fieldsByPosition.get(lengthX * y + x))
						.setBackgroundRectangle(new Rect(
								startX,
								startY,
								startX + fieldWidth + (x == lastX ? (int) LevelRenderer.LINE_WIDTH : 0),
								startY + fieldHeight + (y == lastY ? (int) LevelRenderer.LINE_WIDTH : 0)
						));
			}
	}

	public void draw(LinkedHashSet<GameRectangle> gameRectangles)
	{
		this.clear();

		for (GameRectangle rectangle : gameRectangles)
		{
			if (rectangle.drawAsParent(this.canvas, this.currentPaint))
				this.currentPaint = this.paintList.next();
		}
	}
}
