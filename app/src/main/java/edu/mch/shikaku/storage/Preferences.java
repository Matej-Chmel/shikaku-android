package edu.mch.shikaku.storage;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import edu.mch.shikaku.sound.Sounds;

public class Preferences
{
	private static final String FIRST_START = "firstStart";
	private static final String SOUND_SETTINGS = "sound_settings";
	private static Preferences instance;

	private final SharedPreferences.Editor editor;
	private final SharedPreferences preferences;

	public static Preferences getInstance(Context context)
	{
		if (instance == null)
			instance = new Preferences(context);
		return instance;
	}

	@SuppressLint("CommitPrefEdits")
	private Preferences(Context context)
	{
		this.preferences = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE
		);
		this.editor = this.preferences.edit();
	}

	public int getDefaultSoundSettings()
	{
		return (int) (Math.pow(2, Sounds.COUNT) - 1);
	}
	public int getSoundSettings()
	{
		int defaultValue = this.getDefaultSoundSettings();

		if (this.preferences.contains(Preferences.SOUND_SETTINGS))
			return this.preferences.getInt(Preferences.SOUND_SETTINGS, defaultValue);

		this.saveSoundSettings(defaultValue);
		return defaultValue;
	}
	public boolean isFirstLaunch()
	{
		return !this.preferences.contains(Preferences.FIRST_START);
	}
	public void onFirstLaunchSuccess()
	{
		this.editor.putBoolean(Preferences.FIRST_START, false);
		this.editor.commit();
	}
	public void saveSoundSettings(int soundSettings)
	{
		this.editor.putInt(Preferences.SOUND_SETTINGS, soundSettings);
		this.editor.commit();
	}
}
