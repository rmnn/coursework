package ru.spbu.ageevd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu);
		Typeface tf = Typeface.createFromAsset(getAssets(), "TuschTouch2.ttf");
        TextView t = (TextView) findViewById(R.id.start);
        t.setTypeface(tf);
        TextView t3 = (TextView) findViewById(R.id.exit);
        t3.setTypeface(tf);      
        TextView t4 = (TextView) findViewById(R.id.about);
        t4.setTypeface(tf);
        
        
       
		
       
		/*TextView t = (TextView) findViewById(R.id.count);
		DBAdapter db = new DBAdapter(this);
		db.open();
		CharSequence text = Integer.toString(db.getAllUsers());
		t.setText(text); */
		Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG).show();
	
        
	}
}
