package base.stopwatch.chronometer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import forcoaches.stopwatch.R;
import base.stopwatch.chronometer.BaseChronometerBar.OnSaveButtonOnClickListenner;
import base.stopwatch.util.AthleteItem;

public abstract class AbstractChronometerActivity extends Activity implements OnSaveButtonOnClickListenner{
	private ChronoList chronometers;
	private BaseChronometerBar chronoBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chronometer_layout);
		
		chronometers = (ChronoList) findViewById(R.id.chrono_list);
		chronoBar = (BaseChronometerBar) findViewById(R.id.chrono_bar);
		
		chronoBar.setCommandChronoListFromBar(chronometers);
		chronoBar.setUpdateChronosOnTickListener(chronometers);
		chronoBar.setOnSaveButtonOnClickListenner(this);
		
		chronometers.setFlashChronometerBar(chronoBar);
		chronometers.setGetChronoInformation(chronoBar.getForCoachesChronometer());
		
		Bundle extras = getIntent().getExtras();
		int athletsNumber = extras.getInt("number");
		chronometers.initialize(athletsNumber);
	}
	
	protected final ArrayList<AthleteItem> getChronometers(){
		return this.chronometers.getAthletes();
	}
	
	protected final ChronoList getChronoList(){
		return this.chronometers;
	}
	
	protected final BaseChronometerBar getChronometerBar(){
		return chronoBar;
	}
}
