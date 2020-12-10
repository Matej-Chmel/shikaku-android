package edu.mch.shikaku;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
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
	public void onButtonRandomLevel(View view)
	{
		Toast.makeText(this, "Random", Toast.LENGTH_SHORT).show();
	}
	public void onButtonSelectLevel(View view)
	{
		Toast.makeText(this, "Select", Toast.LENGTH_SHORT).show();
	}
}
