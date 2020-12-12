package edu.mch.shikaku;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
	private Button buttonNextLevel;
	private GameView gameView;
	private LevelManager levelManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game);

		this.buttonNextLevel = this.findViewById(R.id.button_nextLevel);
		this.gameView = this.findViewById(R.id.gameView);
		this.levelManager = LevelManager.getInstance(this);

		this.levelManager.chooseLevel(this.getIntent().getIntExtra(IntentExtras.LEVEL_INDEX, 0));
		this.loadNextLevel();
	}

	private void loadNextLevel()
	{
		this.gameView.init(this, this.levelManager.nextLevel(this.gameView));
	}
	public void onButtonNextLevel(View view)
	{
		this.buttonNextLevel.setVisibility(View.GONE);
		this.loadNextLevel();
	}
	public void onGameCompleted()
	{
		this.gameView.disableControl();
		this.buttonNextLevel.setVisibility(View.VISIBLE);
	}
}
