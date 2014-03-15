package base.stopwatch.chronometer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import forcoaches.stopwatch.R;
import base.stopwatch.chronometer.BaseChronometer.UpdateChronosOnTickListener;
import base.stopwatch.chronometer.BaseChronometerBar.CommandChronoListFromBar;
import base.stopwatch.util.AthleteItem;

public class ChronoList extends LinearLayout implements UpdateChronosOnTickListener, CommandChronoListFromBar{
	private ArrayList<Integer> onScreen;
	private long lastTouchTime = 0;
	private int chronoItemHeight = 0;
	private int chronoListViewHeight = 0;
	private String chronoTime;
	private boolean toggleLapTotal = true;
	private int athletsNumber;
	LinearLayout list;
	private ArrayList<AthleteItem> athletes;
	private FlashChronometerBar flashAnimation;
	private GetChronoInformation chronoInfo;
	
	//communication from ChronoList to ForCoachesChronometerBar
	public interface FlashChronometerBar{
		void startFlashChronoBarAnimation();
	}
	
	//set communication from ChronoList to ForCoachesChronometerBar
	public void setFlashChronometerBar(FlashChronometerBar flashAnimation) {
		this.flashAnimation = flashAnimation;
	}
	
	//communication from ChronoList to ForCoachesChronometer
	public interface GetChronoInformation{
		boolean started();
		long getBase();
		long getChronoElapsedTime();
	}
	
	//set communication from ChronoList to ForCoachesChronometer
	public void setGetChronoInformation (GetChronoInformation chronoInfo) {
		this.chronoInfo = chronoInfo;
	}
	
	public ChronoList(Context context) {
		super(context);
	}

	public ChronoList(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		((Activity)getContext()).getLayoutInflater().inflate(R.layout.chronometer_list, this);

		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			public boolean onPreDraw() {
				chronoListViewHeight = getMeasuredHeight();
				chronoItemHeight = chronoListViewHeight/athletsNumber;
				
				return true;
			}
		});
	}
	
	public void initialize (int number) {
		this.onScreen = new ArrayList<Integer>();
		this.athletsNumber = number;
		
		athletes = new ArrayList<AthleteItem>();
		for (int i = 0; i < athletsNumber; i++){
			athletes.add(new AthleteItem("Stopwatch " + (i+1), getContext()));
		}	
		
		list = (LinearLayout) findViewById(R.id.enclosingLL);
		
		int i = 0;
		for (; i < athletsNumber; i++){
			list.getChildAt(i).setVisibility(LinearLayout.VISIBLE);
		}
		
		for (; i < 4; i++){
			list.getChildAt(i).setVisibility(LinearLayout.GONE);
		}
		
		chronoTime = "00:00";
		
		updateChronos(false);
	}
	
	public void updateChronos(boolean updateLap){
		for (int i = 0; i < athletsNumber; i++){
			updateView(i, list.getChildAt(i), updateLap);
		}
	}
	
	public void updateChrono(int pos){
		updateView(pos, list.getChildAt(pos), true);
	}
	
	public void resetChronoString(){
		chronoTime = "00:00";
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		long actualTime = SystemClock.elapsedRealtime();
		if(chronoInfo.started()){
			Integer position = getItemWhereTouchHappened(event);
			if (position != null && position < athletes.size()){
				AthleteItem item = athletes.get(position.intValue());
				
				long setTime;
				
				if ((actualTime - lastTouchTime) < 35){
					setTime = lastTouchTime;
				}else{
					setTime = actualTime;
				}
				
				item.addNewTime(setTime - chronoInfo.getBase());
				
				lastTouchTime = actualTime;
				updateChrono(position.intValue());
				flashAnimation.startFlashChronoBarAnimation();
			}
		}
		return true;
	}
	
	public void updateView(final int position, View convertView, boolean updateLap){
		View view = convertView;
		
		final AthleteItem item = athletes.get(position);
		
		if (item != null) {			
			TextView athleteTV = (TextView) view.findViewById(R.chrono_item.nameTV);
			TextView timeTV = (TextView) view.findViewById(R.chrono_item.timeTV);
			TextView lapTV = (TextView) view.findViewById(R.chrono_item.lapTV);
			TextView chronoTimeTV = (TextView) view.findViewById(R.chrono_item.chronoTV);
			ListView laps = (ListView) view.findViewById(R.chrono_item.lapsLV);
			
			view.setMinimumHeight(chronoItemHeight);
			
			if (updateLap){
				laps.setAdapter(item.getLapStringsAdapter());
			}
			
			if (athleteTV != null){
				athleteTV.setText(item.getName());
			}
			
			if (timeTV != null){
				timeTV.setText(item.getTimeString());
			}
			
			if (lapTV != null){
				lapTV.setText(item.getLapString(chronoInfo.getChronoElapsedTime()));
			}
			
			if (chronoTimeTV != null){
				chronoTimeTV.setText(chronoTime);
			}
		}
	}
	
	protected Integer getItemWhereTouchHappened(MotionEvent event){
		Integer newitem = null;
		
		final int action = event.getAction();
				  
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) 
			>> MotionEvent.ACTION_POINTER_ID_SHIFT;
		
				
		switch (action & MotionEvent.ACTION_MASK) {
		    case MotionEvent.ACTION_DOWN: {
		    	int y = (int) event.getY();
				int pos = y / chronoItemHeight;
  	    	  	if (!onScreen.contains(pos)){
					newitem = Integer.valueOf(pos);
				}
  	    	  	onScreen.add(Integer.valueOf(pos));
  	    	  	break;
		    }
		    
		    case MotionEvent.ACTION_POINTER_DOWN: {
		    	int y = (int) event.getY(pointerIndex);
				int pos = y / chronoItemHeight;
				if (!onScreen.contains(pos)){
					newitem = Integer.valueOf(pos);
				}
  	    	  	onScreen.add(Integer.valueOf(pos));
  	    	  	break;
		    }
		    
		    case MotionEvent.ACTION_UP: {
	        	onScreen.clear();
	        	break;
		    }
		    
		    case MotionEvent.ACTION_POINTER_UP: {
		    	int y = (int) event.getY(pointerIndex);
				int pos = y / chronoItemHeight;
				onScreen.remove(Integer.valueOf(pos));
				break;
		    }
	    }
	    
	    return newitem;
	}
	
	public void clearAllChronos(){
		athletes.clear();
		for (int i = 0; i < athletsNumber; i++){
			AthleteItem item = new AthleteItem(" ", getContext());
			item.getLapStringsAdapter().setLapOrTotal(this.toggleLapTotal);
			athletes.add(item);
		}
		resetChronoString();
		updateChronos(true);
	}
	
	public void toggleLapOrTotal(){
		toggleLapTotal = !toggleLapTotal;
		
		String title;
		
		if (toggleLapTotal){
			title = getContext().getResources().getString(R.string.CompetitonChronoToggleLap);
		}else{
			title = getContext().getResources().getString(R.string.CompetitonChronoToggleTotal);
		}
		
		for (int i = 0; i < athletsNumber; i++){
			((TextView) list.getChildAt(i).findViewById(R.chrono_item.lapsTV)).setText(title);
		}
		
		for (AthleteItem item: athletes){
			item.getLapStringsAdapter().setLapOrTotal(toggleLapTotal);
			item.getLapStringsAdapter().notifyDataSetChanged();
		}
	}
	
	public void updateChronosOnTickListener(long time) {
		chronoTime = BaseChronometer.getSecTimeString(time);
		updateChronos(false);
	}
	
	public ArrayList<AthleteItem> getAthletes() {
		return athletes;
	}
}