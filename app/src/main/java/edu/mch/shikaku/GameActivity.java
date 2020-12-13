package edu.mch.shikaku;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
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
	private TextView textViewStopWatch;

	private EditableLevel editableLevel;
	private LevelItem levelItem;
	private StopWatch stopWatch;
	private boolean testGameInProgress;
	private String toastNewBestTimeText;
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
		this.textViewStopWatch = this.findViewById(R.id.textView_stopWatch);

		Resources resources = this.getResources();
		Intent intent = this.getIntent();
		int levelIndex = intent.getIntExtra(IntentExtras.LEVEL_INDEX, 0);

		this.stopWatch = new StopWatch(new Handler(Looper.getMainLooper()), this);
		this.toastNewBestTimeText = resources.getString(R.string.toast_newBestTime);
		this.toastSaveLevelText = resources.getString(R.string.toast_saveLevel);

		if (intent.getBooleanExtra(IntentExtras.LAUNCH_EDITOR, false))
		{
			this.editableLevel = levelIndex == -1 ? new EditableLevel(GameActivity.DEFAULT_LEVEL_DIMENSION,
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
		this.levelItem = this.levelManager.nextLevel();
		this.gameView.init(this, this.levelItem.toPlayableLevel(this.gameView));
		this.textViewStopWatch.setTextColor(Color.BLACK);
		this.stopWatch.start();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);

		if (intent != null && resultCode == IntentExtras.RESULT_CODE_FIELD_VALUE)
			this.editorPalette.setExtraNumber(intent.getIntExtra(IntentExtras.FIELD_VALUE, 0));
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
	public void onEditorPaletteSelectExtraNumber()
	{
		this.startActivityForResult(
				new Intent(this, ChooseNumberActivity.class),
				IntentExtras.RESULT_CODE_FIELD_VALUE
		);
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
		long milliseconds = this.stopWatch.stop();

		if (milliseconds != 0 && this.levelItem.update(milliseconds))
		{
			this.textViewStopWatch.setTextColor(Color.RED);
			Toast.makeText(this, this.toastNewBestTimeText, Toast.LENGTH_LONG).show();

			try
			{
				this.levelManager.saveLevel(this.levelItem);
			}
			catch (DatabaseException e)
			{
				Toast.makeText(this, e.getMessage(this.getResources()), Toast.LENGTH_LONG).show();
			}
		}

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
	@SuppressLint("DefaultLocale")
	public void onStopWatchUpdate(long milliseconds)
	{
		this.textViewStopWatch.setText(String.format("%d s", milliseconds / 1000));
	}
	private void startEditor()
	{
		this.testGameInProgress = false;

		this.buttonClearEditor.setVisibility(View.VISIBLE);
		this.editorPalette.init(this, this.switchEraser);
		this.editorPalette.setControlEnabled(true);
		this.floatingButtonBackToEditor.setVisibility(View.GONE);
		this.floatingButtonSaveLevel.setVisibility(View.VISIBLE);
		this.switchEraser.setVisibility(View.VISIBLE);
		this.textViewStopWatch.setVisibility(View.GONE);
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
