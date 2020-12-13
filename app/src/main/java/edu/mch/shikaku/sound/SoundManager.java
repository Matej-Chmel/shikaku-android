package edu.mch.shikaku.sound;
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import edu.mch.shikaku.storage.AssetReader;
import edu.mch.shikaku.storage.Preferences;

/*
	Singleton sdružující všechny zvukové efekty.
	Nastavení zvukových efektů se načítání a ukládá pomocí bitových operací na celém čísle.
*/
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

		this.applySoundSettings(Preferences.getInstance(context).getSoundSettings());
	}

	public void applySoundSettings(int soundSettings)
	{
		int length = this.sounds.size();
		int weight = 1;

		for (int i = 0; i < length; i++)
		{
			this.sounds.get(i).setActive((soundSettings & weight) != 0);
			weight *= 2;
		}
	}
	public void applySoundSettings(boolean[] soundSettings)
	{
		for (int i = 0; i < soundSettings.length; i++)
			this.sounds.get(i).setActive(soundSettings[i]);
	}
	public boolean[] getSoundSettingsAsArray()
	{
		boolean[] array = new boolean[this.sounds.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = this.sounds.get(i).isActive();

		return array;
	}
	public int getSoundSettingsAsInteger()
	{
		int length = this.sounds.size();
		int value = 0;
		int weight = 1;

		for (int i = 0; i < length; i++)
		{
			if (this.sounds.get(i).isActive())
				value |= weight;
			weight *= 2;
		}

		return value;
	}
	public void playSound(int position)
	{
		this.sounds.get(position).start();
	}
}
