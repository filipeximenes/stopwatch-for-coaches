package forcoaches.stopwatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import stopwatch.forcoaches.plus.database.DatabaseReferences;
import stopwatch.forcoaches.plus.database.StopwatchDatabase;
import android.content.ContentValues;
import base.stopwatch.chronometer.AbstractChronometerActivity;
import base.stopwatch.util.AthleteItem;
import base.stopwatch.util.AthleteItem.ChronoTime;

public class ChronometerActivity extends AbstractChronometerActivity{

	@Override
	public void saveButtonOnClickListenner() {
		StopwatchDatabase db = new StopwatchDatabase();
		db.open(this);
		
		ContentValues values = new ContentValues();
		
		values.put(DatabaseReferences.RACES_TB_STRING, getCurrDate());
		
		long id = db.insert(DatabaseReferences.RACES_TB, values);
		
		ArrayList<AthleteItem> list = getChronometers();
		
		for (int i = 0; i < list.size(); i++){
			AthleteItem item = list.get(i);
			
			ArrayList<ChronoTime> times = item.getLaps();
			
			values = new ContentValues();
			
			values.put(DatabaseReferences.CHRONO_TB_RACE_ID, id);
			values.put(DatabaseReferences.CHRONO_TB_NUM, i);
			values.put(DatabaseReferences.CHRONO_TB_NAME, "Stopwatch " + (i+1));
			
			db.insert(DatabaseReferences.CHRONO_TB, values);
			
			for (int j = 0; j < times.size(); j++){
				values = new ContentValues();
				
				values.put(DatabaseReferences.TIMES_TB_RACE_ID, id);
				values.put(DatabaseReferences.TIMES_TB_CHRONO_NUM, i);
				values.put(DatabaseReferences.TIMES_TB_LAP_NUM, j);
				values.put(DatabaseReferences.TIMES_TB_TIME, times.get(j).time);
				
				db.insert(DatabaseReferences.TIMES_TB, values);
			}
		}
		
		db.close();
		
		getChronoList().clearAllChronos();
		getChronometerBar().resetChronometers();
	}
	
	public String getCurrDate()
	{
	    String dt;
	    Date cal=Calendar.getInstance().getTime();
	    dt=cal.toLocaleString();      
	    return dt;
	}

}
