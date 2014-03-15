package stopwatch.forcoaches.plus.database;

import simultaneous.stopwatches.free.R;
import stopwatch.forcoaches.util.DateItemOptionsDialog;
import stopwatch.forcoaches.util.ExtendedListView;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

public class SavedDatesListView extends ExtendedListView<SavedDate>{
	
	protected StartActivityFromItemSelected startActivity;
	private GetActivity activityInt;
	
	interface StartActivityFromItemSelected{
		void startActivityFromSelectedItem(long num, String dataStr);
	}
	
	interface GetActivity{
		Activity getActivity();
	}
	
	public SavedDatesListView(Context context) {
		super(context);
	}
	
	public SavedDatesListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SavedDatesListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void init() {
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				startActivity.startActivityFromSelectedItem(adapterGetItem(pos).id, adapterGetItem(pos).date);
			}
		});
		
		setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				DateItemOptionsDialog dialog = new DateItemOptionsDialog(activityInt.getActivity(), SavedDatesListView.this, adapterGetItem(pos));
				dialog.show();
				return true;
			}
		});
	}
	
	@Override
	protected View adapterGetView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		final SavedDate item = (SavedDate) adapterGetItem(position);
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.single_text, null);
		}
		
		TextView date = (TextView) v.findViewById(R.id.text1);
		
		date.setText(item.date);	
		
		return v;
	}
	
	public void setStartActivityFromItemSelected(StartActivityFromItemSelected startActivity){
		this.startActivity = startActivity;
	}
	
	public void setActivityInt(GetActivity activityInt) {
		this.activityInt = activityInt;
	}
}
