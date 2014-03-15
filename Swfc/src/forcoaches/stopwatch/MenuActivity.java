package forcoaches.stopwatch;

import stopwatch.forcoaches.plus.database.SavedDatesActivity;
import android.content.Intent;
import android.net.Uri;
import base.stopwatch.menu.AbstractMenuActivity;

public class MenuActivity extends AbstractMenuActivity{

	@Override
	public boolean willItSaveTimes() {
		return true;
	}

	@Override
	public void onRateButtonClick() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://details?id=simultaneous.stopwatches.free"));
		startActivity(intent);
	}
	
	@Override
	public void onShowChronometerButtonClick() {
		Intent a = new Intent(MenuActivity.this, ChronometerActivity.class);
    	a.putExtra("number", getSelectedNumberOfChronos());
    	startActivity(a);		
	}
	
	@Override
	protected void onSavedTimesButtonClick() {
		Intent a = new Intent(MenuActivity.this, SavedDatesActivity.class);
		startActivity(a);
	}
}
