package edu.mch.shikaku.control;
import android.view.View;
import android.widget.AdapterView;
import edu.mch.shikaku.activities.ListActivity;

/*
	Zpracovává dlouhá kliknutní na položky ListView.
*/
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
