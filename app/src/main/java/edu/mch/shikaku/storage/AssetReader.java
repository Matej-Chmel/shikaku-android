package edu.mch.shikaku.storage;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import edu.mch.shikaku.sound.Sound;

public class AssetReader
{
	private final AssetManager manager;

	public AssetReader(Context context)
	{
		this.manager = context.getAssets();
	}
	public Sound readSound(String filename) throws IOException
	{
		AssetFileDescriptor descriptor = this.manager.openFd(filename);
		Sound sound = new Sound(
				descriptor.getFileDescriptor(),
				descriptor.getStartOffset(),
				descriptor.getLength()
		);
		descriptor.close();
		return sound;
	}
	public String readText(String filename) throws IOException
	{
		InputStream stream = this.manager.open(filename);
		byte[] buffer = new byte[stream.available()];
		stream.read(buffer);
		stream.close();
		return new String(buffer);
	}
}
