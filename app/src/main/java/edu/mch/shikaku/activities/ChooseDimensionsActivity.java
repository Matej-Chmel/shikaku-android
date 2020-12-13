package edu.mch.shikaku.activities;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.mch.shikaku.R;
import edu.mch.shikaku.levels.Level;

/*
	Aktivita, ve které lze zvolit rozměry hlavolamu při jeho editaci.
	Pokaždé spouštěna pomocí startActivityForResult.
*/
public class ChooseDimensionsActivity extends AppCompatActivity
{
	private EditText editNumberDimX;
	private EditText editNumberDimY;

	private String toastNotValidText;
	private String toastOneTotalSpaceText;
	private String toastTooHighText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_choose_dimensions);

		Resources resources = this.getResources();

		this.editNumberDimX = this.findViewById(R.id.editNumber_dimX);
		this.editNumberDimY = this.findViewById(R.id.editNumber_dimY);

		this.toastNotValidText = resources.getString(R.string.toast_dimension_notValid);
		this.toastOneTotalSpaceText = resources.getString(R.string.toast_dimension_oneTotalSpace);
		this.toastTooHighText = String.format(resources.getString(R.string.toast_dimension_tooHigh),
				Level.MAXIMUM_DIMENSION
		);
	}

	private int getNumber(EditText editNumber)
	{
		try
		{
			return Integer.parseInt(editNumber.getText().toString());
		}
		catch (NumberFormatException e)
		{
			return 0;
		}
	}
	public void onButtonConfirm(View view)
	{
		int dimX = this.getNumber(this.editNumberDimX);
		int dimY = this.getNumber(this.editNumberDimY);

		if (dimX <= 0 || dimY <= 0)
		{
			Toast.makeText(this, this.toastNotValidText, Toast.LENGTH_LONG).show();
			return;
		}
		if (dimX > Level.MAXIMUM_DIMENSION || dimY > Level.MAXIMUM_DIMENSION)
		{
			Toast.makeText(this, this.toastTooHighText, Toast.LENGTH_LONG).show();
			return;
		}
		if (dimX * dimY == 1)
		{
			Toast.makeText(this, this.toastOneTotalSpaceText, Toast.LENGTH_LONG).show();
			return;
		}

		Intent intent = new Intent();
		intent.putExtra(IntentExtras.DIM_X, dimX);
		intent.putExtra(IntentExtras.DIM_Y, dimY);
		this.setResult(IntentExtras.RESULT_CODE_DIMENSIONS, intent);
		this.finish();
	}
}
