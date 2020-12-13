package edu.mch.shikaku.control;
import android.view.View;
import android.widget.AdapterView;
import edu.mch.shikaku.activities.ListActivity;

/*
	Zpracovává krátká kliknutí na položku ListView.
*/
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
