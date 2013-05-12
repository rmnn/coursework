package ru.spbu.ageevd;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowRatingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_ratings);
		ListView lv = (ListView) findViewById(R.id.listView1);
		DBAdapter db = new DBAdapter(this);
		db.open();
		String[] users = new String[1000];
		int cnt = 0;
		Log.d("ASD", "tut rabotaem");
		Cursor cursor = db.getTopUsers();
		Log.d("ASD", "tut eshe  rabotaem");
		if (cursor.moveToFirst()) {	
			Log.d("ASD", "tut ne zaidem");
			users[cnt] = cursor.getString(1);
			Log.d("ASD", "tut uje upali");
			cnt++;
		}
		while (cursor.moveToNext()) {
			Log.d("ASD", "tut uje upali09090i");
			users[cnt] = cursor.getString(1);
			cnt++;
		}
		Log.d("ASD", "vyshli iz while:) " + users[0]);
		
	    String[] catnames = new String[cnt - 1];
		for (int i = 0; i < cnt - 1; i++) {
			catnames[i] = users[i];
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, catnames);

		lv.setAdapter(adapter);
	}

}
