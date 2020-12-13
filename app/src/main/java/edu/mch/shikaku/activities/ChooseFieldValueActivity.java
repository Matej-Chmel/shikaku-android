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

public class ChooseFieldValueActivity extends AppCompatActivity
{
	private EditText editNumberFieldValue;
	private String toastFieldValueOneText;
	private String toastFieldValueTooHighText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_choose_field_value);

		Resources resources = this.getResources();

		this.editNumberFieldValue = this.findViewById(R.id.editNumber_fieldValue);
		this.toastFieldValueTooHighText = String.format(resources.getString(R.string.toast_fieldValue_tooHigh),
				Level.MAXIMUM_FIELD_VALUE
		);
		this.toastFieldValueOneText = resources.getString(R.string.toast_fieldValue_one);
	}

	public void onButtonConfirm(View view)
	{
		int value = Integer.parseInt(this.editNumberFieldValue.getText().toString());

		if (value == 1)
		{
			Toast.makeText(this, this.toastFieldValueOneText, Toast.LENGTH_SHORT).show();
			return;
		}

		if (value > Level.MAXIMUM_FIELD_VALUE)
		{
			Toast.makeText(this, this.toastFieldValueTooHighText, Toast.LENGTH_SHORT).show();
			return;
		}

		Intent intent = new Intent();
		intent.putExtra(IntentExtras.FIELD_VALUE, value);
		this.setResult(IntentExtras.RESULT_CODE_FIELD_VALUE, intent);
		this.finish();
	}
}
