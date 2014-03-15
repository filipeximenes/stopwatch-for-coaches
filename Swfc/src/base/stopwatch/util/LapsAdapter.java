package base.stopwatch.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import forcoaches.stopwatch.R;

public class LapsAdapter extends ArrayAdapter<AthleteItem.ChronoTime>{
	private boolean lapOrTotal = true;

	public LapsAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.chrono_item_lap, null);
		}
		
		TextView number = (TextView) v.findViewById(R.id.number);
		TextView time = (TextView) v.findViewById(R.id.time);
		
		final AthleteItem.ChronoTime item = getItem(position);
		
		number.setText(String.valueOf(item.number) + "|");		
		if (lapOrTotal){
			time.setText(item.timeStr);
		}else{
			time.setText(item.totalStr);
		}
		
		return v;
	}
	
	public void setLapOrTotal(boolean bool){
		lapOrTotal = bool;
	}	
}
