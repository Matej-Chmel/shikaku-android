package edu.mch.shikaku.activities;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import edu.mch.shikaku.R;
import edu.mch.shikaku.sound.SoundManager;
import edu.mch.shikaku.sound.Sounds;

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
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.toastError();
		}
	}

	private void toastError()
	{
		Toast.makeText(this, this.errorText, Toast.LENGTH_LONG).show();
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
}
