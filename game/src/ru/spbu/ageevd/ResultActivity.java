package ru.spbu.ageevd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_result);
		TextView textView = (TextView) findViewById(R.id.result);
		TextView textView1 = (TextView) findViewById(R.id.result2);
		int result = getIntent().getExtras().getInt("KEY_RESULT");
		CharSequence text = "You correctly answered " + Integer.toString(result) + " out of 5";	
		textView.setText(text);
		text = "Your score is : " + Integer.toString(( result * 100) / 5) + " %" ;
		textView1.setText(text);
		final String user = getIntent().getExtras().getString("KEY_USER");
		DBAdapter db = new DBAdapter(this);
		db.open();
		if (db.updateUserRating(user, result)) {
			Toast.makeText(this, "Rating updated", Toast.LENGTH_SHORT).show();
		}
		
	}

}
