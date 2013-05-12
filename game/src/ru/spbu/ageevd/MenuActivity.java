package ru.spbu.ageevd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
		DBAdapter db = new DBAdapter(this);
		db.open();
		TextView rat = (TextView) findViewById(R.id.rating);
		final String user = getIntent().getExtras().getString("KEY_USER");
		CharSequence text = "Your rating : "
				+ Integer.toString(db.getUserRating(user));
		rat.setText(text);
		Button newGameButton = (Button) findViewById(R.id.button1);
		newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, GameActivity.class);
				intent.putExtra("KEY_USER", user);
				startActivity(intent);
			}
		});
		Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();

	}
}
