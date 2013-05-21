package ru.spbu.ageevd;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowRatingActivity extends Activity {
	private ArrayList<HashMap<String, Object>> myUsers;

	private static final String USERKEY = "user";

	private static final String RATINGKEY = "rating";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_ratings);

		ListView listView = (ListView) findViewById(R.id.list);
		myUsers = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hm;
		DBAdapter db = new DBAdapter(this);
		db.open();
		Cursor cursor = db.getTopUsers();
		cursor.moveToLast();
		int cnt = 0;
		hm = new HashMap<String, Object>();
		hm.put(USERKEY, cursor.getString(4));
		hm.put(RATINGKEY,"rating : " + cursor.getString(3));
		myUsers.add(hm);
		cnt ++;

		while (cursor.moveToPrevious()) {
			hm = new HashMap<String, Object>();
			hm.put(USERKEY, cursor.getString(4));
			hm.put(RATINGKEY,"rating : " + cursor.getString(3));
			myUsers.add(hm);
			cnt++;
			if (cnt == 10) {
				break;
			}
		}

		SimpleAdapter adapter = new SimpleAdapter(this, myUsers, R.layout.list,
				new String[] { USERKEY, RATINGKEY, }, new int[] { R.id.text1,
						R.id.text2 });

		listView.setAdapter(adapter);
	}
}