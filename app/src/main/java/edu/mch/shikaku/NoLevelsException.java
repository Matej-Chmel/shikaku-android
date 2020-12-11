package edu.mch.shikaku;
import android.content.res.Resources;

public class NoLevelsException extends Exception
{
	public String getMessage(Resources resources)
	{
		return resources.getString(R.string.exception_noLevels);
	}
}
