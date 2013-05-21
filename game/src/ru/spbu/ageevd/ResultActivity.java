package ru.spbu.ageevd;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ResultActivity extends Activity {
	public static String user;
	public static String password;
	public static String name;
	public static boolean isChecked;
	public static int result;
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_result);
		TextView textView = (TextView) findViewById(R.id.result);
		TextView textView1 = (TextView) findViewById(R.id.result2);
		result = getIntent().getExtras().getInt("KEY_RESULT");
		CharSequence text = "You correctly answered "
				+ Integer.toString(result) + " out of 10";
		textView.setText(text);
		text = "Your score is : " + Integer.toString((result * 100) / 10) + " %";
		textView1.setText(text);
		user = getIntent().getExtras().getString("KEY_USER");
		password = getIntent().getExtras().getString("KEY_PASSWORD");
		isChecked = getIntent().getExtras().getBoolean("KEY_ISCHECKED");
		context = this;
		DBAdapter db = new DBAdapter(this);
		db.open();
		name = db.getUserName(user);
		if (db.updateUserRating(user, result)) {
			Toast.makeText(this, "Rating updated", Toast.LENGTH_SHORT).show();
		}
		db.close();
		Button button = (Button) findViewById(R.id.backbutton);
		if (isChecked) {
			Log.d("ASD", "CHECKED");
			MyTask mt = new MyTask();
			mt.execute();
		} else {
			Log.d("ASD", "NOT CHECKED");
		}
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ResultActivity.this,
						MenuActivity.class);
				intent.putExtra("KEY_USER", user);
				intent.putExtra("KEY_PASSWORD", password);
				intent.putExtra("KEY_ISCHECKED", isChecked);
				startActivity(intent);
			}
		});

	}

}

class MyTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		Log.d("ASD", "ZASHLI V SYNC");
		UpdateRating updRating = new UpdateRating();
		try {
			Log.d("ASD", updRating.update(ResultActivity.name,
					ResultActivity.password, ResultActivity.result));
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

	}
}
