package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.res.Resources;

public class DatabaseException extends Exception
{
	@SuppressLint("DefaultLocale")
	public DatabaseException(Level level, String reason)
	{
		super(String.format("Level %d could not be %s.", level.getId(), reason));
	}

	public String getMessage(Resources resources)
	{
		return String.format(
				"%s\n%s",
				resources.getString(R.string.exception_databaseError),
				super.getMessage()
		);
	}
}
