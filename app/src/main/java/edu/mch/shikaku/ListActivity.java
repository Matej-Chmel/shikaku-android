package edu.mch.shikaku;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_list);

		ListView listView = this.findViewById(R.id.listView_levels);
		listView.setAdapter(new LevelAdapter(this, LevelManager.getInstance(this).levelItems));
		listView.setOnItemClickListener(new OnLevelItemClickListener(this));
		listView.setOnItemLongClickListener(new OnLevelItemLongClickListener(this));
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
	private void startGameActivity(int position, boolean launchEditor)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(IntentExtras.LAUNCH_EDITOR, launchEditor);
		intent.putExtra(IntentExtras.LEVEL_INDEX, position);
		this.startActivity(intent);
	}
}
