package edu.mch.shikaku;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class Database extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "edu.mch.shikaku.db";

	public Database(@Nullable Context context)
	{
		super(context, DATABASE_NAME, null, 1);
	}

	public void clearAllLevels()
	{
		this.getWritableDatabase().delete(Level.TABLE, null, null);
	}
	public void deleteLevel(Level level) throws DatabaseException
	{
		if (this.getWritableDatabase().delete(Level.TABLE,
				level.getWhereCondition(),
				level.getIdString()
		) != 1)
			throw new DatabaseException(level, "deleted");
	}
	public void insertLevel(Level level) throws DatabaseException
	{
		long id = this.getWritableDatabase().insert(Level.TABLE, null, level.getContentValues());

		if (id == -1)
			throw new DatabaseException(level, "inserted");

		level.setId(id);
	}
	public void insertLevels(Collection<? extends Level> levels) throws DatabaseException
	{
		SQLiteDatabase database = this.getWritableDatabase();
		database.beginTransaction();

		try
		{
			for (Level item : levels)
				this.insertLevel(item);

			database.setTransactionSuccessful();
		}
		finally
		{
			database.endTransaction();
		}
	}
	@Override
	public void onCreate(SQLiteDatabase db) throws SQLException
	{
		db.execSQL(String.format(
				"CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)",
				Level.TABLE,
				Level.ID,
				Level.BOARD,
				Level.BEST_TIME,
				Level.DIFFICULTY,
				Level.DIM_X,
				Level.DIM_Y
		));
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException
	{
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", Level.TABLE));
		this.onCreate(db);
	}
	public ArrayList<LevelItem> selectAllLevelsItems()
	{
		ArrayList<LevelItem> levels = new ArrayList<>();

		Cursor cursor = this.getReadableDatabase().rawQuery(String.format("SELECT * FROM %s",
				Level.TABLE
		), null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast())
		{
			levels.add(new LevelItem(cursor));
			cursor.moveToNext();
		}

		return levels;
	}
	public void updateLevel(Level level) throws DatabaseException
	{
		if (this.getWritableDatabase().update(Level.TABLE,
				level.getContentValues(),
				level.getWhereCondition(),
				level.getIdString()
		) != 1)
			throw new DatabaseException(level, "updated");
	}
}
