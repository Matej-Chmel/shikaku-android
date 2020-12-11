package edu.mch.shikaku;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game);

		GameView gameView = this.findViewById(R.id.gameView);
		gameView.init(LevelManager.getInstance(this)
				.getLevel(this.getIntent().getIntExtra(IntentExtras.LEVEL_INDEX, 0)));
	}
}
