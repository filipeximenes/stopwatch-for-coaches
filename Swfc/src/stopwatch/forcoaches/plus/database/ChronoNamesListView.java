package stopwatch.forcoaches.plus.database;

import simultaneous.stopwatches.free.R;
import stopwatch.forcoaches.util.ExtendedListView;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

public class ChronoNamesListView extends ExtendedListView<ChronoNames>{
	
	interface StartActivityFromItemSelected2{
		void startActovityFromSelectedItem(int num, String name);
	}
	
	interface GetRaceIdFromActivity{
		long getRaceId();
	}
	
	private StartActivityFromItemSelected2 startActivity;
	private GetRaceIdFromActivity raceId;

	public ChronoNamesListView(Context context) {
		super(context);
	}
	
	public ChronoNamesListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ChronoNamesListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void init() {
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				startActivity.startActovityFromSelectedItem(adapterGetItem(pos).id, adapterGetItem(pos).name);
			}
		});
		
		setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				LayoutInflater factory = LayoutInflater.from(getContext());
	            final View textEntryView = factory.inflate(R.layout.remane_item_dialog, null);
	            
	            final EditText text = (EditText) textEntryView.findViewById(R.id.rename_item_text_tv);
	            
	            final ChronoNames item = adapterGetItem(pos);
	            
	            text.setText(item.getName());
				
				Builder builder = new Builder(getContext());
				builder.setView(textEntryView);
				builder.setTitle("Rename Item");
				builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						item.setName(text.getText().toString());
						
						StopwatchDatabase db = new StopwatchDatabase();
						db.open(getContext());
						ContentValues values = new ContentValues();
						values.put(DatabaseReferences.CHRONO_TB_RACE_ID, raceId.getRaceId());
						values.put(DatabaseReferences.CHRONO_TB_NUM, item.getId());
						values.put(DatabaseReferences.CHRONO_TB_NAME, item.getName());
						
						db.update(DatabaseReferences.CHRONO_TB, 
								values, 
								DatabaseReferences.CHRONO_TB_RACE_ID + "==" + raceId.getRaceId() + " and " +
								DatabaseReferences.CHRONO_TB_NUM + "==" + item.getId(), 
								null);
						db.close();
						adapterNotifyDataSetChanged();
					}
				});
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
				});
				
				builder.create().show();
				
				return true;
			}
		});
	}
	
	@Override
	protected View adapterGetView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		final ChronoNames item = (ChronoNames) adapterGetItem(position);
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.single_text, null);
		}
		
		TextView date = (TextView) v.findViewById(R.id.text1);
		
		date.setText(item.name);
		
		return v;
	}
	
	public void setStartActivityFromItemSelected(StartActivityFromItemSelected2 startActivity){
		this.startActivity = startActivity;
	}
	
	public void setGetRaceIdFromActivity(GetRaceIdFromActivity id){
		this.raceId = id;
	}
}
