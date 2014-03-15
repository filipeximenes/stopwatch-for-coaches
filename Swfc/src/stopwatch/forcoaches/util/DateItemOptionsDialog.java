package stopwatch.forcoaches.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import simultaneous.stopwatches.free.R;
import stopwatch.forcoaches.plus.database.ChronoNames;
import stopwatch.forcoaches.plus.database.DatabaseReferences;
import stopwatch.forcoaches.plus.database.SavedDate;
import stopwatch.forcoaches.plus.database.SavedDatesListView;
import stopwatch.forcoaches.plus.database.StopwatchDatabase;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import base.stopwatch.chronometer.BaseChronometer;

public class DateItemOptionsDialog extends Dialog{
	private SavedDate date;
	private Activity act;
	private SavedDatesListView list;
	
	public DateItemOptionsDialog(Activity act, SavedDatesListView list, SavedDate date) {
		super(act);
		this.date = date;
		this.act = act;
		this.list = list;
		initialize();
	}
	
	private void initialize(){
		setContentView(R.layout.date_item_dialog);
		
		setTitle("Options");
		
		Button save = (Button) findViewById(R.id.date_dialog_save_bt);
		Button delete = (Button) findViewById(R.id.date_dialog_delete_bt);
		Button rename = (Button) findViewById(R.id.date_dialog_rename_bt);
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File sdCard = Environment.getExternalStorageDirectory();
				File dir = new File ((sdCard.getAbsolutePath() + "/StopwatchForCoaches/" + date.getDate().replaceAll("(/|:| )","_")));
				
				dir.mkdirs();
				
				StopwatchDatabase db = new StopwatchDatabase();
				ArrayList<ChronoNames> chronos = db.getChronoNames(act, date.getId());
				
				for (ChronoNames item: chronos){
					File file = new File(dir, item.getName().replaceAll("(/|:| )","_") + ".txt");
					
					db = new StopwatchDatabase();
					ArrayList<Long> times = db.getSavedTimes(act, date.getId(), item.getId());
					
					try {
						FileOutputStream f = new FileOutputStream(file);
						
						long past = 0;
						
						for (int i = 0; i < times.size(); i++){
							f.write((
									(i+1) + 
									"\t" + 
									BaseChronometer.getTimeString(times.get(i) - past) +
									"\t" +
									BaseChronometer.getTimeString(times.get(i)) + 
									"\n"
									).getBytes());
						}
						
						f.flush();
						f.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				dismiss();
				
				Builder builder = new Builder(act);
				builder.setTitle("Saved!");
				builder.setMessage("Saved to the 'StopwatchForCoaches' folder");
				builder.setNeutralButton("ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						dismiss();
					}
				});
				
				builder.create().show();
			}
		});
		
		rename.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				
				LayoutInflater factory = LayoutInflater.from(getContext());
	            final View textEntryView = factory.inflate(R.layout.remane_item_dialog, null);
	            
	            final EditText text = (EditText) textEntryView.findViewById(R.id.rename_item_text_tv);
	            
	            text.setText(date.getDate());
				
				Builder builder = new Builder(act);
				builder.setView(textEntryView);
				builder.setTitle("Reaname Item");
				builder.setPositiveButton("Remane", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						date.setDate(text.getText().toString());
						
						StopwatchDatabase db = new StopwatchDatabase();
						db.open(act);
						ContentValues values = new ContentValues();
						values.put(DatabaseReferences.RACES_TB_ID, date.getId());
						values.put(DatabaseReferences.RACES_TB_STRING, date.getDate());
						
						db.update(DatabaseReferences.RACES_TB, 
								values, 
								DatabaseReferences.RACES_TB_ID + "==" + date.getId(), 
								null);
						db.close();
						list.adapterNotifyDataSetChanged();
					}
				});
				
				builder.setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
					}
				});
				
				builder.create().show();
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				
				Builder builder = new Builder(act);
				builder.setTitle("Delete Item");
				builder.setMessage("This will delete this record forever");
				builder.setPositiveButton("Delete", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						StopwatchDatabase db = new StopwatchDatabase();
						db.deleteAllRecordsFromDate(act, date.getId());
						list.adapterRemove(date);
					}
				});
				
				builder.setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
					}
				});
				
				builder.create().show();
			}
		});
	}
}
