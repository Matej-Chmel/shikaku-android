package edu.mch.shikaku.renderers;
import android.content.Context;
import android.graphics.Paint;
import edu.mch.shikaku.R;
import edu.mch.shikaku.views.EditorPalette;

/*
	Vykresluje paletu EditorPalette.
*/
public class EditorPaletteRenderer extends Renderer
{
	private final Paint blackPaint;
	private final Paint chosenPaint;
	private final int numberTilesCount;
	private final float textMoveHeight;
	private final float textMoveWidth;
	protected final float tileWidth;

	public EditorPaletteRenderer(Context context, int height, int width)
	{
		super(height, width);
		this.blackPaint = new Paint();
		this.blackPaint.setAntiAlias(true);
		this.chosenPaint = new Paint();
		this.chosenPaint.setColor(context.getResources().getColor(R.color.editorPalette_chosen));
		this.numberTilesCount = EditorPalette.TILES_COUNT - 1;
		this.tileWidth = this.width / EditorPalette.TILES_COUNT;
		this.textMoveHeight = 3 * (this.height / 4);
		this.textMoveWidth = this.tileWidth / 4;
		this.blackPaint.setTextSize(this.tileWidth / 2);
	}

	public void draw(int chosenTileIndex, Integer extraNumber)
	{
		this.drawBackground(this.canvas);

		this.canvas.drawRect(
				chosenTileIndex * this.tileWidth,
				0,
				(chosenTileIndex + 1) * this.tileWidth,
				this.height,
				this.chosenPaint
		);

		for (int i = 0; i < this.numberTilesCount; i++)
			this.canvas.drawText(
					String.valueOf(i + 2),
					i * this.tileWidth + this.textMoveWidth,
					this.textMoveHeight, this.blackPaint
			);

		this.canvas.drawText(
				extraNumber == null ? "..." : extraNumber.toString(),
				this.numberTilesCount * this.tileWidth + this.textMoveWidth,
				this.textMoveHeight,
				this.blackPaint
		);
	}
	public float getTileWidth()
	{
		return this.tileWidth;
	}
}
