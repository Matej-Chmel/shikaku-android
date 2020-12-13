package edu.mch.shikaku.activities;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import edu.mch.shikaku.R;
import edu.mch.shikaku.levels.EditableLevel;
import edu.mch.shikaku.levels.LevelItem;
import edu.mch.shikaku.levels.LevelManager;
import edu.mch.shikaku.sound.SoundManager;
import edu.mch.shikaku.sound.Sounds;
import edu.mch.shikaku.stopwatch.StopWatch;
import edu.mch.shikaku.stopwatch.StopWatchDisplayer;
import edu.mch.shikaku.storage.DatabaseException;
import edu.mch.shikaku.views.EditorPalette;
import edu.mch.shikaku.views.GameView;

public class GameActivity extends AppCompatActivity
{
	public static final int DEFAULT_LEVEL_DIMENSION = 5;

	private Button buttonClearEditor;
	private Button buttonNextLevel;
	private EditorPalette editorPalette;
	private FloatingActionButton floatingButtonBackToEditor;
	private FloatingActionButton floatingButtonSaveLevel;
	private GameView gameView;
	private SwitchCompat switchEraser;
	private TextView textViewStopWatch;

	private EditableLevel editableLevel;
	private boolean launchedFromMain;
	private LevelItem levelItem;
	private LevelManager levelManager;
	private SoundManager soundManager;
	private StopWatch stopWatch;
	private StopWatchDisplayer stopWatchDisplayer;
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

		try
		{
			this.soundManager = SoundManager.getInstance(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Toast.makeText(this,
					resources.getString(R.string.exception_soundError),
					Toast.LENGTH_LONG
			)
					.show();
		}

		this.stopWatch = new StopWatch(new Handler(Looper.getMainLooper()), this);
		this.stopWatchDisplayer = StopWatchDisplayer.getInstance(this);
		this.toastNewBestTimeText = resources.getString(R.string.toast_newBestTime);
		this.toastSaveLevelText = resources.getString(R.string.toast_saveLevel);

		if (intent.getBooleanExtra(IntentExtras.LAUNCH_EDITOR, false))
		{
			this.editableLevel = levelIndex == -1 ? new EditableLevel(GameActivity.DEFAULT_LEVEL_DIMENSION,
					GameActivity.DEFAULT_LEVEL_DIMENSION,
					this.gameView,
					this.editorPalette
			) : this.levelManager.getEditableLevel(this.gameView, this.editorPalette, levelIndex);
			this.launchedFromMain = intent.getBooleanExtra(IntentExtras.LAUNCH_EDITOR_FROM_MAIN,
					false
			);
			this.startEditor();
		}
		else
		{
			this.levelManager.chooseLevel(levelIndex);
			this.loadNextLevel();
			this.stopWatch.start();
		}
	}

	private void loadNextLevel()
	{
		this.levelItem = this.levelManager.nextLevel();
		this.gameView.init(this, this.levelItem.toPlayableLevel(this.gameView, this.soundManager));
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);

		if (intent == null)
			return;

		if (resultCode == IntentExtras.RESULT_CODE_FIELD_VALUE)
			this.editorPalette.setExtraNumber(intent.getIntExtra(IntentExtras.FIELD_VALUE, 0));
		else if (resultCode == IntentExtras.RESULT_CODE_DIMENSIONS)
		{
			int dimX = intent.getIntExtra(IntentExtras.DIM_X, 0);
			int dimY = intent.getIntExtra(IntentExtras.DIM_Y, 0);

			if (dimX == 0 || dimY == 0)
				return;

			this.gameView.resizeLevel(dimX, dimY);
		}
	}
	public void onButtonClearEditor(View view)
	{
		this.gameView.clearLevel();
	}
	public void onButtonNextLevel(View view)
	{
		this.setGameState(true);
		this.loadNextLevel();
		this.stopWatch.start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.getMenuInflater().inflate(R.menu.menu_game, menu);

		if (this.editableLevel == null || this.testGameInProgress)
			menu.findItem(R.id.menuItem_resize).setVisible(false);
		else
			menu.findItem(R.id.menuItem_restart).setVisible(false);

		return true;
	}
	public void onEditorPaletteSelectExtraNumber()
	{
		this.startActivityForResult(new Intent(this, ChooseFieldValueActivity.class),
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
			if (this.soundManager != null)
				this.soundManager.playSound(Sounds.NEW_BEST_TIME);
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

			if (this.launchedFromMain)
				this.startActivity(new Intent(this, ListActivity.class));
			this.finish();
			return;
		}

		this.setGameState(false);
	}
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.menuItem_resize)
			this.startActivityForResult(new Intent(this, ChooseDimensionsActivity.class),
					IntentExtras.RESULT_CODE_DIMENSIONS
			);
		else if (id == R.id.menuItem_restart)
		{
			this.gameView.clearLevel();
			this.setGameState(true);
			this.stopWatch.start();
		}

		return true;
	}
	public void onStopWatchUpdate(long milliseconds)
	{
		this.stopWatchDisplayer.displayTo(this.textViewStopWatch, milliseconds);
	}
	private void setGameState(boolean gameInProgress)
	{
		if (gameInProgress)
		{
			this.buttonNextLevel.setVisibility(View.GONE);
			this.gameView.setControlEnabled(true);
			this.textViewStopWatch.setTextColor(Color.BLACK);
			return;
		}

		this.buttonNextLevel.setVisibility(View.VISIBLE);
		this.gameView.setControlEnabled(false);
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
		this.invalidateOptionsMenu();
	}
	private void startTestGame()
	{
		this.testGameInProgress = true;

		this.buttonClearEditor.setVisibility(View.GONE);
		this.editorPalette.setControlEnabled(false);
		this.floatingButtonBackToEditor.setVisibility(View.VISIBLE);
		this.floatingButtonSaveLevel.setVisibility(View.GONE);
		this.switchEraser.setVisibility(View.GONE);
		this.gameView.init(this, this.editableLevel.toPlayableLevel(this.soundManager));

		this.invalidateOptionsMenu();
		Toast.makeText(this, this.toastSaveLevelText, Toast.LENGTH_LONG).show();
	}
}
