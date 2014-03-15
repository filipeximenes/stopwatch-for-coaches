package base.stopwatch.util.design;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ChronoTypeface extends TextView{

	public ChronoTypeface(Context context) {
		super(context);
		initialize(context);
	}
	
	public ChronoTypeface(Context context, AttributeSet arg1) {
		super(context, arg1);
		initialize(context);
	}
	
	public ChronoTypeface(Context context, AttributeSet arg1, int arg2) {
		super(context, arg1, arg2);
		initialize(context);
	}
	
	private void initialize(Context context){
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGIB.TTF"); 
		setTypeface(font); 
	}
}
