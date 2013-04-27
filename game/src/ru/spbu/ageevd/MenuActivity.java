package ru.spbu.ageevd;

import android.app.Activity;
import android.os.Bundle;
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
		TextView t = (TextView) findViewById(R.id.count);
		DBAdapter db = new DBAdapter(this);
		db.open();
		CharSequence text = Integer.toString(db.getAllUsers());
		t.setText(text);
		Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG).show();
	}
}
