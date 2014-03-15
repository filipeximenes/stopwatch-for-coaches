package base.stopwatch.chronometer;

import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;
import base.stopwatch.chronometer.ChronoList.GetChronoInformation;

public class BaseChronometer extends Chronometer implements GetChronoInformation{
	private boolean STOPED = false;
	private boolean STARTED = false;
	private UpdateChronosOnTickListener tickUpdater;
	
	//communication from ForCoachesChronometer to ChronoList 
	public interface UpdateChronosOnTickListener{
		void updateChronosOnTickListener(long time);
	}
	
	//set communication from ForCoachesChronometer to ChronoList 
	public void setUpdateChronosOnTickListener(UpdateChronosOnTickListener tickUpdater) {
		this.tickUpdater = tickUpdater;
	}
	
	public BaseChronometer(Context context) {
		super(context);
		initialize(context);
	}
	
	public BaseChronometer(Context context, AttributeSet arg1) {
		super(context, arg1);
		initialize(context);
	}
	
	public BaseChronometer(Context context, AttributeSet arg1, int arg2) {
		super(context, arg1, arg2);
		initialize(context);
	}
	
	private void initialize(Context context){
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGIB.TTF"); 
		setTypeface(font); 
		
		setOnChronometerTickListener(new OnChronometerTickListener() {
			public void onChronometerTick(Chronometer chronometer) {
				tickUpdater.updateChronosOnTickListener(getChronoElapsedTime());
			}
		});
	}
	
	public long getChronoElapsedTime(){
    	if (started()){	
    		return getElapsedTime();
    	}else{
    		return 0;
    	}
    }
	
	public long getElapsedTime(){
		return SystemClock.elapsedRealtime() - getBase();
	}
	
	public void startChrono(){
		setBase(SystemClock.elapsedRealtime());
		start();
		STARTED = true;
		STOPED = false;
	}
	
	public void startChronoWithoutSettingBase(){
		start();
		STARTED = true;
	}
	
	public void restartChrono(){
		start();
		STARTED = true;
		STOPED = false;
	}
	
	public void stopChrono(){
		stop();
		STARTED = false;
		STOPED = true;
	}
	
	public void resetChrono(){
		setText("00:00");
		STARTED = false;
		STOPED = false;
	}
	
	public boolean started(){
		return STARTED;
	}
	
	public boolean stoped(){
		return STOPED;
	}	
	
	public static String getTimeString(long time){
		long hours = (time/1000)/60/60;
		long minutes = (time/1000)/60;
		long seconds = ((time)/1000)%60;
		long cent = (time%1000)/10;
		
		String timeStr = "";
		
		if (hours != 0){
			if (hours < 10){
				timeStr += "0" + hours;
			}else{
				timeStr += hours;
			}
			timeStr += ":";
		}
		if (minutes < 10){
			timeStr += "0" + minutes;
		}else{
			timeStr += minutes;
		}
		
		timeStr += ":";
		
		if (seconds < 10){
			timeStr += "0" + seconds;
		}else{
			timeStr += seconds;
		}
		
		timeStr += ":";
		
		if (cent < 10){
			timeStr += "0" + cent;
		}else{
			timeStr += cent;
		}
		
		return timeStr;
	}
	
	public static String getSecTimeString(long time){
		long hours = (time/1000)/60/60;
		long minutes = (time/1000)/60;
		long seconds = ((time)/1000)%60;
		
		String timeStr = "";
		
		if (hours != 0){
			if (hours < 10){
				timeStr += "0" + hours;
			}else{
				timeStr += hours;
			}
			timeStr += ":";
		}
		if (minutes < 10){
			timeStr += "0" + minutes;
		}else{
			timeStr += minutes;
		}
		
		timeStr += ":";
		
		if (seconds < 10){
			timeStr += "0" + seconds;
		}else{
			timeStr += seconds;
		}
		
		return timeStr;
	}
}
