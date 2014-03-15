package base.stopwatch.chronometer;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import simultaneous.stopwatches.free.R;
import base.stopwatch.chronometer.BaseChronometer.UpdateChronosOnTickListener;
import base.stopwatch.chronometer.ChronoList.FlashChronometerBar;
import base.stopwatch.util.design.Colors;

public final class BaseChronometerBar extends LinearLayout implements FlashChronometerBar{
//	private View view;
	
	private Button startBT;
	private Button stopBT;
	private Button clearBT;
	private Button saveBT;
	private ToggleButton toggleLapTotal;
	private BaseChronometer chronometer;
	private Animation flashChrono;
	private CommandChronoListFromBar chronoListComunication;
	private OnSaveButtonOnClickListenner saveButtonListenner;
	
	//communication from ForCoachesChronometerBar to ChronoList 
	public interface CommandChronoListFromBar{
		void clearAllChronos();
		void toggleLapOrTotal();
	}
	
	public interface OnSaveButtonOnClickListenner{
		void saveButtonOnClickListenner();
	}
	
	//set communication from ForCoachesChronometerBar to ChronoList 
	public void setCommandChronoListFromBar(CommandChronoListFromBar chronoListComunication){
		this.chronoListComunication = chronoListComunication;
	}
	
	//set communication from ForCoachesChronometer to ChronoList 
	public void setUpdateChronosOnTickListener (UpdateChronosOnTickListener up){
		chronometer.setUpdateChronosOnTickListener(up);
	}
	
	public void setOnSaveButtonOnClickListenner(OnSaveButtonOnClickListenner saveButtonListenner){
		this.saveButtonListenner = saveButtonListenner;
	}
	
	public BaseChronometerBar(Context context) {
		super(context);
		inflateLayout(context);
	}
	
	public BaseChronometerBar(Context context, AttributeSet attset) {
		super(context, attset);
		inflateLayout(context);
	}
	
	private void inflateLayout(Context ctx){
		((Activity)getContext()).getLayoutInflater().inflate(R.layout.chronometer_bar, this);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		startBT = (Button) findViewById(R.now_swimming.startBT);
		stopBT = (Button) findViewById(R.now_swimming.stopBT);
		clearBT = (Button) findViewById(R.now_swimming.clearBT);
		saveBT = (Button) findViewById(R.now_swimming.saveBT);
		toggleLapTotal = (ToggleButton) findViewById(R.now_swimming.toggleBT);
		chronometer = (BaseChronometer) findViewById(R.now_swimming.chronoCH);
		
		startBT.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				chronometer.startChrono();
				onStartButtonClick();
			}
		});
		
		stopBT.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				chronometer.stopChrono();
				onStopButtonClick();
			}
		});
		
		clearBT.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				resetChronometers();
			}
		});
		
		saveBT.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				saveButtonListenner.saveButtonOnClickListenner();
			}
		});
		
		toggleLapTotal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				chronoListComunication.toggleLapOrTotal();
			}
		});
		
		flashChrono = AnimationUtils.loadAnimation(getContext(), R.anim.time_slice);
		flashChrono.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
				setBackgroundColor(Colors.ORANGE);
			}
			
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			public void onAnimationEnd(Animation animation) {
				setBackgroundColor(Colors.STEAMP_BLUE);
			}
		});
		
		activateStartButton();
	}
	
	public final void resetChronometers(){
		chronometer.resetChrono();
		chronoListComunication.clearAllChronos();
		activateStartButton();
	}
	
	public void deactivateAllButtons(){
		startBT.setVisibility(Button.GONE);
		stopBT.setVisibility(Button.GONE);
		clearBT.setVisibility(Button.GONE);
		saveBT.setVisibility(Button.GONE);
	}
	
	public void activateStartButton(){
		startBT.setVisibility(Button.VISIBLE);
		stopBT.setVisibility(Button.GONE);
		clearBT.setVisibility(Button.GONE);
		saveBT.setVisibility(Button.GONE);
	}
	
	private void onStartButtonClick(){
		startBT.setVisibility(Button.GONE);
		stopBT.setVisibility(Button.VISIBLE);
		clearBT.setVisibility(Button.GONE);
		saveBT.setVisibility(Button.GONE);
	}
	
	private void onStopButtonClick(){
		startBT.setVisibility(Button.GONE);
		stopBT.setVisibility(Button.GONE);
		clearBT.setVisibility(Button.VISIBLE);
		saveBT.setVisibility(Button.VISIBLE);
	}
	
	public BaseChronometer getForCoachesChronometer() {
		return chronometer;
	}
	
	public void startFlashChronoBarAnimation() {
		startAnimation(flashChrono);
	}
}
