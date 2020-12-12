package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class EditorPalette extends CustomView implements Clickable
{
	public static final int TILES_COUNT = 9;
	public static final int LAST_TILE_INDEX = EditorPalette.TILES_COUNT - 1;

	private int chosenTileIndex = 0;
	private EditorPaletteRenderer renderer;

	public EditorPalette(Context context)
	{
		super(context);
	}
	public EditorPalette(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}
	public EditorPalette(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public int getChosenNumber()
	{
		if (this.chosenTileIndex == EditorPalette.LAST_TILE_INDEX)
			return -1;
		return this.chosenTileIndex + 2;
	}
	@Override
	public void onClick(float x, float y)
	{
		this.chosenTileIndex = (int) (x / this.renderer.tileWidth);
		this.update();
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (this.ready && this.changed)
		{
			this.renderer.draw(this.chosenTileIndex);
			this.changed = true;
		}

		this.renderer.renderTo(canvas);
	}
	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
	{
		super.onSizeChanged(width, height, oldWidth, oldHeight);
		this.renderer = new EditorPaletteRenderer(
				this.getContext(),
				height,
				width
		);
	}
	@SuppressLint("ClickableViewAccessibility")
	public void setControlEnabled(boolean enabled)
	{
		if (enabled)
		{
			this.setVisibility(View.VISIBLE);
			this.setOnTouchListener(new ClickDetector(this));
		}
		else
		{
			this.setVisibility(View.GONE);
			this.setOnTouchListener(null);
		}
	}
}
