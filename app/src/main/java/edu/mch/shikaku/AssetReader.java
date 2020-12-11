package edu.mch.shikaku;
import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

public class AssetReader
{
	private final AssetManager manager;

	public AssetReader(Context context)
	{
		this.manager = context.getAssets();
	}
	public String readFile(String filename) throws IOException
	{
		InputStream stream = this.manager.open(filename);
		byte[] buffer = new byte[stream.available()];
		stream.read(buffer);
		stream.close();
		return new String(buffer);
	}
}
