package edu.mch.shikaku.level;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import edu.mch.shikaku.R;

public class LevelAdapter extends ArrayAdapter<LevelItem>
{
	public LevelAdapter(Context context, ArrayList<LevelItem> entries)
	{
		super(context, R.layout.list_item_level, entries);
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View view, @NonNull ViewGroup parent)
	{
		LevelHolder holder;

		if (view == null)
		{
			view = ((LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_level,
					parent,
					false
			);

			holder = new LevelHolder(view);
			view.setTag(holder);
		}
		else
			holder = (LevelHolder) view.getTag();

		holder.apply(this.getContext(), this.getItem(position));
		return view;
	}
}
