package edu.mch.shikaku;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game);

		LevelManager levelManager = LevelManager.getInstance(this);
		((TextView) this.findViewById(R.id.textView_levelTest)).setText(levelManager.getLevel(this.getIntent()
				.getIntExtra(IntentExtras.LEVEL_INDEX, 0)).toTextDisplay());
	}
}
