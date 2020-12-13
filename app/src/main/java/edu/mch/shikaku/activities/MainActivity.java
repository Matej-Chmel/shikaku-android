package edu.mch.shikaku.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.mch.shikaku.R;
import edu.mch.shikaku.levels.LevelManager;
import edu.mch.shikaku.storage.NoLevelsException;
import edu.mch.shikaku.storage.Preferences;

public class MainActivity extends AppCompatActivity
{
	private LevelManager levelManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		this.levelManager = LevelManager.getInstance(this);
		Preferences preferences = new Preferences(this);

		if (preferences.isFirstLaunch() && levelManager.resetLevels(this))
			preferences.onFirstLaunchSuccess();
	}

	public void onButtonCreateLevel(View view)
	{
		this.startGameActivity(-1, true);
	}
	public void onButtonRandomLevel(View view)
	{
		try
		{
			this.startGameActivity(this.levelManager.getRandomLevelIndex(), false);
		}
		catch (NoLevelsException e)
		{
			this.toastNoLevels(e);
		}
	}
	public void onButtonSelectLevel(View view)
	{
		try
		{
			this.levelManager.checkLevelsExist();
			this.startActivity(new Intent(this, ListActivity.class));
		}
		catch (NoLevelsException e)
		{
			this.toastNoLevels(e);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.menuItem_about)
			this.startActivity(new Intent(this, AboutActivity.class));
		else if (id == R.id.menuItem_soundTest)
			this.startActivity(new Intent(this, SoundTestActivity.class));

		return true;
	}
	private void startGameActivity(int levelIndex, boolean launchEditor)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(IntentExtras.LEVEL_INDEX, levelIndex);

		if (launchEditor)
		{
			intent.putExtra(IntentExtras.LAUNCH_EDITOR, true);
			intent.putExtra(IntentExtras.LAUNCH_EDITOR_FROM_MAIN, true);
		}

		this.startActivity(intent);
	}
	private void toastNoLevels(NoLevelsException e)
	{
		Toast.makeText(this, e.getMessage(this.getResources()), Toast.LENGTH_SHORT).show();
	}
}
