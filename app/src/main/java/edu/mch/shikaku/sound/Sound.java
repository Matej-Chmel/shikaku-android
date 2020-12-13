package edu.mch.shikaku.sound;
import android.media.MediaPlayer;
import java.io.FileDescriptor;
import java.io.IOException;

public class Sound
{
	private boolean active;
	private final MediaPlayer player;

	public Sound(FileDescriptor descriptor, long start, long length) throws IOException
	{
		this.active = true;
		this.player = new MediaPlayer();
		this.player.setDataSource(descriptor, start, length);
		this.player.prepare();
		this.player.setLooping(false);
		this.setVolume(1, 1);
	}

	public boolean isActive()
	{
		return this.active;
	}
	public void setActive(boolean active)
	{
		this.active = active;
	}
	public void setVolume(float left, float right)
	{
		this.player.setVolume(left, right);
	}
	public void start()
	{
		if (this.active)
			this.player.start();
	}
}
