package edu.mch.shikaku.activities;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import edu.mch.shikaku.R;
import edu.mch.shikaku.sound.SoundManager;
import edu.mch.shikaku.sound.Sounds;

/*
	Aktivita s nastavením a testováním zvukových efektů.

	Pro každý zvukový efekt obsahuje tlačítko na levé straně.
	Kliknutím na něj lze efekt přehrát.
	Přepínači na pravé straně lze nastavit,
	zda-li je povoleno přehrávat daný zvuk při řešení hlavolamu.

	Nastavení zvuků se ukládá v SharedPreferences.
*/
public class SoundTestActivity extends AppCompatActivity
{
	private String errorText;
	private SoundManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_sound_test);

		this.errorText = this.getResources().getString(R.string.exception_soundError);

		try
		{
			this.manager = SoundManager.getInstance(this);

			boolean[] soundSettings = this.manager.getSoundSettingsAsArray();

			this.setToggleButtonState(R.id.toggleButton_correct, soundSettings[Sounds.CORRECT]);
			this.setToggleButtonState(R.id.toggleButton_newBestTime,
					soundSettings[Sounds.NEW_BEST_TIME]
			);
			this.setToggleButtonState(R.id.toggleButton_remove, soundSettings[Sounds.REMOVE]);
			this.setToggleButtonState(R.id.toggleButton_victory, soundSettings[Sounds.VICTORY]);
			this.setToggleButtonState(R.id.toggleButton_wrong, soundSettings[Sounds.WRONG]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.toastError();
		}
	}

	private boolean getToggleButtonState(int id)
	{
		return ((ToggleButton) this.findViewById(id)).isChecked();
	}
	private void setToggleButtonState(int id, boolean state)
	{
		((ToggleButton) this.findViewById(id)).setChecked(state);
	}
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();

		boolean[] soundSettings = new boolean[Sounds.COUNT];

		soundSettings[Sounds.CORRECT] = this.getToggleButtonState(R.id.toggleButton_correct);
		soundSettings[Sounds.NEW_BEST_TIME] = this.getToggleButtonState(R.id.toggleButton_newBestTime);
		soundSettings[Sounds.REMOVE] = this.getToggleButtonState(R.id.toggleButton_remove);
		soundSettings[Sounds.VICTORY] = this.getToggleButtonState(R.id.toggleButton_victory);
		soundSettings[Sounds.WRONG] = this.getToggleButtonState(R.id.toggleButton_wrong);

		this.manager.applySoundSettings(soundSettings);
	}
	@SuppressLint("NonConstantResourceId")
	public void onSoundButton(View view)
	{
		switch (view.getId())
		{
			case R.id.button_soundCorrect:
				this.playSound(Sounds.CORRECT);
				break;
			case R.id.button_soundNewBestTime:
				this.playSound(Sounds.NEW_BEST_TIME);
				break;
			case R.id.button_soundRemove:
				this.playSound(Sounds.REMOVE);
				break;
			case R.id.button_soundVictory:
				this.playSound(Sounds.VICTORY);
				break;
			case R.id.button_soundWrong:
				this.playSound(Sounds.WRONG);
				break;
		}
	}
	private void playSound(int position)
	{
		if (this.manager == null)
			this.toastError();
		else
			this.manager.playSound(position);
	}
	private void toastError()
	{
		Toast.makeText(this, this.errorText, Toast.LENGTH_LONG).show();
	}
}
