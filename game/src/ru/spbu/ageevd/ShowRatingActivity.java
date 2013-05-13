package ru.spbu.ageevd;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowRatingActivity extends Activity {
	
	private ArrayList <HashMap<String, Object>> myUsers;
    private static final String USER_KEY = "userName";
    private static final String RATING_KEY = "userRating";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_ratings);
		ListView listView = (ListView) findViewById(R.id.list);
		
		myUsers = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> hm;
		
		hm = new HashMap<String, Object>();
        hm.put(USER_KEY, "Коробке");
        hm.put(RATING_KEY, "какой-то текст");
        
        
		DBAdapter db = new DBAdapter(this);
		db.open();
		Cursor cursor = db.getTopUsers();
		Log.d("ASD", "tut eshe  rabotaem");
		if (cursor.moveToFirst()) {	
			hm = new HashMap<String, Object>();
	        hm.put(USER_KEY, cursor.getString(1));
	        hm.put(RATING_KEY, cursor.getString(3));
	        myUsers.add(hm);
		}
		while (cursor.moveToNext()) {
			hm = new HashMap<String, Object>();
	        hm.put(USER_KEY, cursor.getString(1));
	        hm.put(RATING_KEY, cursor.getString(3));
	        myUsers.add(hm);
		}
		
		 SimpleAdapter adapter = new SimpleAdapter(this, 
                 myUsers, 
                 R.layout.activity_result, new String[]{
                 USER_KEY,         //верхний текст
                 RATING_KEY,        //нижний теккт
                 }, new int[]{
                 R.id.text1, //ссылка на объект отображающий текст
                 R.id.text2, //ссылка на объект отображающий текст
                       }); //добавили ссылку в чем отображать картинки из list.xml

          listView.setAdapter(adapter);
       listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
		
	   
	}

}
