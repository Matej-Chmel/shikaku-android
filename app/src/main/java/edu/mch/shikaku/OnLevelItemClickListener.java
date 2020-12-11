package edu.mch.shikaku;
import android.view.View;
import android.widget.AdapterView;

public class OnLevelItemClickListener implements AdapterView.OnItemClickListener
{
	private final ListActivity host;

	public OnLevelItemClickListener(ListActivity host)
	{
		this.host = host;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		this.host.onItemClicked(position);
	}
}
