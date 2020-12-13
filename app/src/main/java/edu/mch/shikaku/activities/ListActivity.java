package edu.mch.shikaku.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.mch.shikaku.R;
import edu.mch.shikaku.control.OnLevelItemClickListener;
import edu.mch.shikaku.control.OnLevelItemLongClickListener;
import edu.mch.shikaku.levels.LevelAdapter;
import edu.mch.shikaku.levels.LevelManager;

/*
	Aktivita zobrazující seznam všech hlavolamů.

	Kliknutím na hlavolam se spustí GameActivity v módu řešení hlavolamu.
	Dlouhým kliknutním na hlavolam se spustí editace hlavolamu.
	Kliknutím na tlačítko v pravém dolním rohu se spustí editace nového hlavolamu.
	Z menu lze resetovat databázi hlavolamů do původní podoby.
	Tj. vymazat všechny hlavolamy a načíst pouze ty, které se nacházejí v souboru levels.txt
	Seznam reaguje na změny.
*/
public class ListActivity extends AppCompatActivity
{
	private LevelAdapter levelAdapter;
	private boolean inGameActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_list);

		this.levelAdapter = new LevelAdapter(this, LevelManager.getInstance(this).levelItems);
		this.inGameActivity = false;

		ListView listView = this.findViewById(R.id.listView_levels);
		listView.setAdapter(levelAdapter);
		listView.setOnItemClickListener(new OnLevelItemClickListener(this));
		listView.setOnItemLongClickListener(new OnLevelItemLongClickListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.getMenuInflater().inflate(R.menu.menu_list, menu);
		return true;
	}
	public void onFloatingButtonCreateLevelClicked(View view)
	{
		this.startGameActivity(-1, true);
	}
	public void onItemClicked(int position)
	{
		this.startGameActivity(position, false);
	}
	public void onItemLongClicked(int position)
	{
		this.startGameActivity(position, true);
	}
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		if (item.getItemId() == R.id.menuItem_restart)
		{
			LevelManager.getInstance(this).resetLevels(this);
			this.levelAdapter.notifyDataSetChanged();
		}

		return true;
	}
	@Override
	protected void onResume()
	{
		super.onResume();

		if (this.inGameActivity)
		{
			this.levelAdapter.notifyDataSetChanged();
			this.inGameActivity = false;
		}
	}
	private void startGameActivity(int position, boolean launchEditor)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(IntentExtras.LAUNCH_EDITOR, launchEditor);
		intent.putExtra(IntentExtras.LEVEL_INDEX, position);
		this.startActivity(intent);
		this.inGameActivity = true;
	}
}
