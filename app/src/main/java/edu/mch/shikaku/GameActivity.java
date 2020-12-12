package edu.mch.shikaku;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GameActivity extends AppCompatActivity
{
	public static final int DEFAULT_LEVEL_DIMENSION = 5;

	private Button buttonClearEditor;
	private Button buttonNextLevel;
	private EditorPalette editorPalette;
	private FloatingActionButton floatingButtonBackToEditor;
	private FloatingActionButton floatingButtonSaveLevel;
	private GameView gameView;
	private LevelManager levelManager;
	private SwitchCompat switchEraser;

	private EditableLevel editableLevel;
	private boolean testGameInProgress;
	private String toastSaveLevelText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game);

		this.buttonClearEditor = this.findViewById(R.id.button_clearEditor);
		this.buttonNextLevel = this.findViewById(R.id.button_nextLevel);
		this.editorPalette = this.findViewById(R.id.editorPalette);
		this.floatingButtonBackToEditor = this.findViewById(R.id.floatingButton_backToEditor);
		this.floatingButtonSaveLevel = this.findViewById(R.id.floatingButton_saveLevel);
		this.gameView = this.findViewById(R.id.gameView);
		this.levelManager = LevelManager.getInstance(this);
		this.switchEraser = this.findViewById(R.id.switch_eraser);

		this.toastSaveLevelText = this.getResources().getString(R.string.toast_saveLevel);

		Intent intent = this.getIntent();
		int levelIndex = intent.getIntExtra(IntentExtras.LEVEL_INDEX, 0);

		if (intent.getBooleanExtra(IntentExtras.LAUNCH_EDITOR, false))
		{
			this.editableLevel = levelIndex == -1 ? new EditableLevel(
					GameActivity.DEFAULT_LEVEL_DIMENSION,
					GameActivity.DEFAULT_LEVEL_DIMENSION,
					this.gameView,
					this.editorPalette
			) : this.levelManager.getEditableLevel(this.gameView, this.editorPalette, levelIndex);
			this.startEditor();
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
	public void onButtonClearEditor(View view)
	{
		if (this.gameView.level instanceof EditableLevel)
		{
			((EditableLevel) this.gameView.level).clear();
			this.gameView.update();
		}
	}
	public void onButtonNextLevel(View view)
	{
		this.buttonNextLevel.setVisibility(View.GONE);
		this.loadNextLevel();
	}
	public void onFloatingButtonBackToEditor(View view)
	{
		this.startEditor();
	}
	public void onFloatingButtonSaveLevel(View view)
	{
		this.startTestGame();
	}
	public void onGameCompleted()
	{
		if (this.testGameInProgress)
		{
			try
			{
				this.levelManager.saveLevel(this.editableLevel);
			}
			catch (DatabaseException e)
			{
				Toast.makeText(this, e.getMessage(this.getResources()), Toast.LENGTH_LONG).show();
			}

			this.finish();
			return;
		}

		this.gameView.disableControl();
		this.buttonNextLevel.setVisibility(View.VISIBLE);
	}
	private void startEditor()
	{
		this.testGameInProgress = false;

		this.buttonClearEditor.setVisibility(View.VISIBLE);
		this.editorPalette.init(this.switchEraser);
		this.editorPalette.setControlEnabled(true);
		this.floatingButtonBackToEditor.setVisibility(View.GONE);
		this.floatingButtonSaveLevel.setVisibility(View.VISIBLE);
		this.switchEraser.setVisibility(View.VISIBLE);
		this.gameView.init(this, this.editableLevel);

		this.editableLevel.setControlEnabled(true);
	}
	private void startTestGame()
	{
		this.testGameInProgress = true;

		this.buttonClearEditor.setVisibility(View.GONE);
		this.editorPalette.setControlEnabled(false);
		this.floatingButtonBackToEditor.setVisibility(View.VISIBLE);
		this.floatingButtonSaveLevel.setVisibility(View.GONE);
		this.switchEraser.setVisibility(View.GONE);
		this.gameView.init(this, this.editableLevel.toPlayableLevel());

		Toast.makeText(this, this.toastSaveLevelText, Toast.LENGTH_LONG).show();
	}
}
