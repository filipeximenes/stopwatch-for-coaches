package stopwatch.forcoaches.plus.database;

import forcoaches.stopwatch.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TimeListActivity extends Activity{
	private long id;
	private int chronoNum;
	private StopwatchDatabase db;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.times_list);
        
        Bundle extras = getIntent().getExtras();
		id = extras.getLong("id");
		chronoNum = extras.getInt("chrononum");
		
		TimesListView list = (TimesListView) findViewById(R.id.list);
		TextView chronoName = (TextView) findViewById(R.id.chrono_name_tv);
		
		chronoName.setText(extras.getString("name"));
        
        list.initialize();
        
        db = new StopwatchDatabase();
        list.adapterAddAll(db.getSavedTimes(this, id, chronoNum));
        db.close();
    }
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (db != null) {
	        db.close();
	    }
	}
}
