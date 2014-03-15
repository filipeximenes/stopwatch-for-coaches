package stopwatch.forcoaches.plus.database;

import simultaneous.stopwatches.free.R;
import stopwatch.forcoaches.plus.database.ChronoNamesListView.GetRaceIdFromActivity;
import stopwatch.forcoaches.plus.database.ChronoNamesListView.StartActivityFromItemSelected2;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ChronoNamesActivity extends Activity implements StartActivityFromItemSelected2, GetRaceIdFromActivity{
	private long id;
	private StopwatchDatabase db;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.chrono_name_list);
        
        Bundle extras = getIntent().getExtras();
		id = extras.getLong("id");
		
        
        final ChronoNamesListView list = (ChronoNamesListView) findViewById(R.id.list);
        
        list.setStartActivityFromItemSelected(this);
        list.setGetRaceIdFromActivity(this);
        
        list.initialize();
        
        db = new StopwatchDatabase();
        list.adapterAddAll(db.getChronoNames(this, id));
        db.close();
    }

	@Override
	public void startActovityFromSelectedItem(int num, String name) {
		Intent a = new Intent(ChronoNamesActivity.this, TimeListActivity.class);
		a.putExtra("id", id);
		a.putExtra("chrononum", num);
		a.putExtra("name", name);
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
	public long getRaceId() {
		return id;
	}
}
