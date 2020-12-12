package edu.mch.shikaku;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

	public void onButtonRandomLevel(View view)
	{
		try
		{
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra(IntentExtras.LEVEL_INDEX, this.levelManager.getRandomLevelIndex());
			this.startActivity(intent);
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
		if (item.getItemId() == R.id.menuItem_about)
			this.startActivity(new Intent(this, AboutActivity.class));

		return true;
	}
	private void toastNoLevels(NoLevelsException e)
	{
		Toast.makeText(this, e.getMessage(this.getResources()), Toast.LENGTH_SHORT).show();
	}
}
