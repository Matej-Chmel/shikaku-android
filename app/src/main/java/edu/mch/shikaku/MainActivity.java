package edu.mch.shikaku;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
