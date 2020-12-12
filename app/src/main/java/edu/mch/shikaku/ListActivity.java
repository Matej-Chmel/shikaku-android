package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
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
		Toast.makeText(this, "Create level", Toast.LENGTH_SHORT).show();
	}
	public void onItemClicked(int position)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(IntentExtras.LEVEL_INDEX, position);
		this.startActivity(intent);
	}
	@SuppressLint("DefaultLocale")
	public void onItemLongClicked(int position)
	{
		Toast.makeText(this, String.format("Long clicked %d", position), Toast.LENGTH_SHORT).show();
	}
}
