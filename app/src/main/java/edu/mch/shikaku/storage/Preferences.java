package edu.mch.shikaku.storage;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences
{
	private static final String FIRST_START = "firstStart";

	private final SharedPreferences.Editor editor;
	private final SharedPreferences preferences;

	@SuppressLint("CommitPrefEdits")
	public Preferences(Context context)
	{
		this.preferences = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE
		);
		this.editor = this.preferences.edit();
	}

	public boolean isFirstLaunch()
	{
		return !this.preferences.contains(FIRST_START);
	}
	public void onFirstLaunchSuccess()
	{
		this.editor.putBoolean(FIRST_START, false);
		this.editor.commit();
	}
}
