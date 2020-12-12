package edu.mch.shikaku;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
	public static final int DEFAULT_LEVEL_DIMENSION = 5;

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

		Intent intent = this.getIntent();
		int levelIndex = intent.getIntExtra(IntentExtras.LEVEL_INDEX, 0);

		if (intent.getBooleanExtra(IntentExtras.LAUNCH_EDITOR, false))
		{
			this.findViewById(R.id.editorPalette).setVisibility(View.VISIBLE);
			this.findViewById(R.id.floatingButton_saveLevel).setVisibility(View.VISIBLE);
			this.gameView.init(
					this,
					levelIndex == -1 ? new EditableLevel(GameActivity.DEFAULT_LEVEL_DIMENSION,
							GameActivity.DEFAULT_LEVEL_DIMENSION,
							this.gameView
					) : this.levelManager.getEditableLevel(this.gameView, levelIndex)
			);
		}
		else
		{
			this.levelManager.chooseLevel(levelIndex);
			this.loadNextLevel();
		}
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
	public void onFloatingButtonSaveLevel(View view)
	{
		Toast.makeText(this, "Save level", Toast.LENGTH_SHORT).show();
	}
	public void onGameCompleted()
	{
		this.gameView.disableControl();
		this.buttonNextLevel.setVisibility(View.VISIBLE);
	}
}
