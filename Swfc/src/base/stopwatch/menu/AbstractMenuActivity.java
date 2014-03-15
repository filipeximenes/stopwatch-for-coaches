package base.stopwatch.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import simultaneous.stopwatches.free.R;
import base.stopwatch.chronometer.ChronoList;

import com.michaelnovakjr.numberpicker.NumberPicker;

public abstract class AbstractMenuActivity extends Activity{
	private NumberPicker picker;
	
	public abstract boolean willItSaveTimes();
	public abstract void onRateButtonClick();
	public abstract void onShowChronometerButtonClick();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);
		
		picker = (NumberPicker) findViewById(R.id.num_picker);
		
		picker.setRange(1, ChronoList.MAX_CHRONO_NUMBER);
		picker.setWrap(false);
		
		Button showChronoBT = (Button) findViewById(R.id.show_chronometers_bt);
		
		showChronoBT.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onShowChronometerButtonClick();
			}
		});
		
		Button savedTimes = (Button) findViewById(R.id.saved_times_bt);
		
		if (willItSaveTimes()){
			savedTimes = (Button) findViewById(R.id.saved_times_bt);
			
			savedTimes.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					onSavedTimesButtonClick();
				}
			});
		}else{
			savedTimes.setVisibility(Button.GONE);
		}
		
		Button market = (Button) findViewById(R.id.email_bt);
		
		market.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent sendMailIntent = new Intent(Intent.ACTION_SEND); 
				sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sugestions and Critics");
				sendMailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"forcoaches@gmail.com"});
				sendMailIntent.setType("message/rfc822");
				startActivity(Intent.createChooser(sendMailIntent, "Send e-mail"));
			}
		});
		
		Button rate = (Button) findViewById(R.id.rate_bt);
		
		rate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onRateButtonClick();
			}
		});
	}
	
	protected void onSavedTimesButtonClick(){
		
	}
	
	protected int getSelectedNumberOfChronos(){
		return picker.getCurrent();
	}
}
