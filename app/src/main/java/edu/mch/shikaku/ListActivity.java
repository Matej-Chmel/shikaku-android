package edu.mch.shikaku;
import android.content.Intent;
import android.os.Bundle;
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
	}

	public void onItemClicked(int position)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(IntentExtras.LEVEL_INDEX, position);
		this.startActivity(intent);
	}
}
