package stopwatch.forcoaches.plus.database;

import simultaneous.stopwatches.free.R;
import stopwatch.forcoaches.plus.database.SavedDatesListView.GetActivity;
import stopwatch.forcoaches.plus.database.SavedDatesListView.StartActivityFromItemSelected;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SavedDatesActivity extends Activity implements StartActivityFromItemSelected, GetActivity{
	private StopwatchDatabase db;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.saved_dates_list);
        
        SavedDatesListView list = (SavedDatesListView) findViewById(R.id.list);
        
        list.setStartActivityFromItemSelected(this);
        list.setActivityInt(this);
        
        list.initialize();
        
        db = new StopwatchDatabase();
        list.adapterAddAll(db.getSavedDates(this));
        db.close();
    }
	
	@Override
	public void startActivityFromSelectedItem(long num, String dateName) {
		Intent a = new Intent(SavedDatesActivity.this, ChronoNamesActivity.class);
		a.putExtra("id", num);
		startActivity(a);
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (db != null) {
	        db.close();
	    }
	}

	@Override
	public Activity getActivity() {
		return this;
	}
}
