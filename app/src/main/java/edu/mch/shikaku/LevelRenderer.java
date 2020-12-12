package edu.mch.shikaku;

public abstract class LevelRenderer extends Renderer
{
	public static final float LINE_WIDTH = 16;
	public static final float CENTERED_LINE_WIDTH = 2 * LevelRenderer.LINE_WIDTH;

	protected final float dimX;
	protected final float dimY;
	protected final float moveHeight;
	protected final float moveWidth;
	protected final float tileHeight;
	protected final float tileWidth;

	protected LevelRenderer(int dimX, int dimY, int height, int width)
	{
		super(height, width);
		this.dimX = dimX;
		this.dimY = dimY;
		this.tileHeight = (height - (dimY + 1) * LevelRenderer.LINE_WIDTH) / dimY;
		this.tileWidth = (width - (dimX + 1) * LevelRenderer.LINE_WIDTH) / dimX;
		this.moveHeight = LevelRenderer.LINE_WIDTH + this.tileHeight;
		this.moveWidth = LevelRenderer.LINE_WIDTH + this.tileWidth;
	}
}
