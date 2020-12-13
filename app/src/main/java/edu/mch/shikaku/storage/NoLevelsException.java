package edu.mch.shikaku.storage;
import android.content.res.Resources;
import edu.mch.shikaku.R;

/*
	Výjimka vyvolaná při neúspěšném načtení hlavolamů.
*/
public class NoLevelsException extends Exception
{
	public String getMessage(Resources resources)
	{
		return resources.getString(R.string.exception_noLevels);
	}
}
