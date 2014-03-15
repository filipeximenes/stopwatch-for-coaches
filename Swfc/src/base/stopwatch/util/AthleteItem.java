package base.stopwatch.util;

import java.util.ArrayList;

import android.content.Context;
import forcoaches.stopwatch.R;
import base.stopwatch.chronometer.BaseChronometer;

public class AthleteItem extends AbstractItemElement{
	private String name;
	
	private ArrayList<ChronoTime> laps;
	private LapsAdapter lapsStrings;
	private long lastLapTime = 0;
	private String timeTotal = "00:00:00";
	private String lastLapString = "00:00:00";
	
	public class ChronoTime{
		public long time;
		public int number;
		public String timeStr;
		public String totalStr;
		
		public ChronoTime(long time, int number, String timeStr, String totalStr) {
			this.time = time;
			this.number = number;
			this.timeStr = timeStr;
			this.totalStr = totalStr;
		}
	}
	
	public AthleteItem(String name, Context ctx){
		this.name = name;
		this.laps = new ArrayList<AthleteItem.ChronoTime>();
		this.lapsStrings = new LapsAdapter(ctx, R.layout.chrono_item_lap);
	}
	
	public void addNewTime(long time){		
		if(laps.size() > 0){
			lastLapString = BaseChronometer.getTimeString(time-laps.get(laps.size()-1).time);
		}else{
			lastLapString = BaseChronometer.getTimeString(time);
		}
		
		lastLapTime = time;
		timeTotal = BaseChronometer.getTimeString(time);
		
		ChronoTime newTime = new ChronoTime(time,lapsStrings.getCount()+1, lastLapString, timeTotal);
		lapsStrings.insert(newTime, 0);
		laps.add(newTime);
	}
	
	public void resetTimes(){
		this.laps.clear();
		this.lapsStrings.clear();
		timeTotal = "00:00:00";
		lastLapString = "00:00:00";
		lastLapTime = 0;
	}
	
	public String getLapString(long chronoTime){
		if ((chronoTime - lastLapTime) < 10000 && lastLapTime != 0){
			return lastLapString;
		}else{
			return BaseChronometer.getSecTimeString(chronoTime - lastLapTime);
		}		
	}
	
	public String getTimeString(){
		return timeTotal;
	}
	
	public LapsAdapter getLapStringsAdapter(){
		return lapsStrings;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<ChronoTime> getLaps() {
		return laps;
	}

	public void setLaps(ArrayList<ChronoTime> laps) {
		this.laps = laps;
	}
}
