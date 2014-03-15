package stopwatch.forcoaches.plus.database;

import base.stopwatch.chronometer.BaseChronometer;
import simultaneous.stopwatches.free.R;
import stopwatch.forcoaches.plus.database.SavedDatesListView.StartActivityFromItemSelected;
import stopwatch.forcoaches.util.ExtendedListView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimesListView extends ExtendedListView<Long>{
	
	protected StartActivityFromItemSelected startActivity;

	public TimesListView(Context context) {
		super(context);
	}
	
	public TimesListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TimesListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected View adapterGetView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		long past = 0;
		
		if (position > 0){
			past = (Long) adapterGetItem(position-1);
		}
		
		final long item = (Long) adapterGetItem(position);
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.double_text, null);
		}
		
		TextView lap = (TextView) v.findViewById(R.id.text1);
		
		lap.setText(BaseChronometer.getTimeString(item - past));
		
		TextView total = (TextView) v.findViewById(R.id.text2);
		
		total.setText(BaseChronometer.getTimeString(item));
		
		TextView pos = (TextView) v.findViewById(R.id.text3);
		
		pos.setText(String.valueOf(position+1));
		
		return v;
	}
}
