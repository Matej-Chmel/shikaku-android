package edu.mch.shikaku;
import android.view.View;
import android.widget.AdapterView;

public class OnLevelItemLongClickListener implements AdapterView.OnItemLongClickListener
{
	private final ListActivity host;

	public OnLevelItemLongClickListener(ListActivity host)
	{
		this.host = host;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
		this.host.onItemLongClicked(position);
		return true;
	}
}
