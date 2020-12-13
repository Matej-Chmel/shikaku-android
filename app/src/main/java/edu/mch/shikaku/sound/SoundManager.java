package edu.mch.shikaku.sound;
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import edu.mch.shikaku.storage.AssetReader;

public class SoundManager
{
	private static SoundManager instance;

	private final ArrayList<Sound> sounds;

	public static SoundManager getInstance(Context context) throws IOException
	{
		if (instance == null)
			instance = new SoundManager(context);
		return instance;
	}

	private SoundManager(Context context) throws IOException
	{
		AssetReader reader = new AssetReader(context);
		this.sounds = new ArrayList<>();

		this.sounds.add(reader.readSound("sounds/correct.mp3"));
		this.sounds.add(reader.readSound("sounds/new_best_time.mp3"));
		this.sounds.add(reader.readSound("sounds/remove.mp3"));
		this.sounds.add(reader.readSound("sounds/victory.mp3"));
		this.sounds.add(reader.readSound("sounds/wrong.mp3"));
	}

	public void playSound(int position)
	{
		this.sounds.get(position).start();
	}
}
