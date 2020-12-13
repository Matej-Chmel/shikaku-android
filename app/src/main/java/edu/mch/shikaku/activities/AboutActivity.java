package edu.mch.shikaku.activities;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import edu.mch.shikaku.R;

public class AboutActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_about);
	}
	public void onGithub(View view)
	{
		this.startActivity(new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://github.com/Matej-Chmel/shikaku-android")
		));
	}
}
