package edu.mch.shikaku.sound;
import android.media.MediaPlayer;
import java.io.FileDescriptor;
import java.io.IOException;

public class Sound
{
	private final MediaPlayer player;

	public Sound(FileDescriptor descriptor, long start, long length) throws IOException
	{
		this.player = new MediaPlayer();
		this.player.setDataSource(descriptor, start, length);
		this.player.prepare();
		this.player.setLooping(false);
		this.setVolume(1, 1);
	}

	public void setVolume(float left, float right)
	{
		this.player.setVolume(left, right);
	}
	public void start()
	{
		this.player.start();
	}
}
