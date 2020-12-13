package edu.mch.shikaku;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseNumberActivity extends AppCompatActivity
{
	private EditText editNumberFieldValue;
	private String toastFieldValueTooHighText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_choose_number);

		this.editNumberFieldValue = this.findViewById(R.id.editNumber_fieldValue);
		this.toastFieldValueTooHighText = String.format(this.getResources()
				.getString(R.string.toast_fieldValueTooHigh), Level.MAXIMUM_FIELD_VALUE);
	}

	public void onButtonFieldValue(View view)
	{
		int value = Integer.parseInt(this.editNumberFieldValue.getText().toString());

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
