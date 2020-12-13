package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

public class EditorPalette extends CustomView implements Clickable
{
	public static final int TILES_COUNT = 9;
	public static final int LAST_TILE_INDEX = EditorPalette.TILES_COUNT - 1;

	private int chosenTileIndex = 0;
	private Integer extraNumber;
	private GameActivity host;
	private SwitchCompat switchEraser;
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
	public void init(GameActivity host, SwitchCompat switchEraser)
	{
		this.host = host;
		this.switchEraser = switchEraser;
	}

	public int getChosenNumber()
	{
		if (this.switchEraser.isChecked())
			return 0;
		if (this.chosenTileIndex == EditorPalette.LAST_TILE_INDEX)
			return this.extraNumber == null ? -1 : this.extraNumber;
		return this.chosenTileIndex + 2;
	}
	@Override
	public void onClick(float x, float y)
	{
		this.switchEraser.setChecked(false);
		this.chosenTileIndex = (int) (x / this.renderer.tileWidth);

		if (this.chosenTileIndex == EditorPalette.LAST_TILE_INDEX)
			this.host.onEditorPaletteSelectExtraNumber();

		this.update();
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (this.ready && this.changed)
		{
			this.renderer.draw(this.chosenTileIndex, this.extraNumber);
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
	public void setExtraNumber(Integer extraNumber)
	{
		this.extraNumber = extraNumber;
		this.update();
	}
}
