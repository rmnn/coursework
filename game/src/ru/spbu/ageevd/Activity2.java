package ru.spbu.ageevd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends Activity {
	DBAdapter db = new DBAdapter(this);
	EditText Quote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_activity2);
		/** Вызывается, когда оператор создается впервые. */
		// Захватывает кнопку из макета
		Button setButton = (Button) findViewById(R.id.go);
		Button getButton = (Button) findViewById(R.id.genRan);
		// Регистрирует приемник OnClick
		setButton.setOnClickListener(mAddListener);
		getButton.setOnClickListener(mAddListener);
	}

	// Создает анонимную реализацию OnClickListener
	private OnClickListener mAddListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.go:
				db.open();
				long id = 0;
				// Действие при нажатии кнопки
				try {
					CharSequence text;
					EditText password = (EditText) findViewById(R.id.password);
					EditText login = (EditText) findViewById(R.id.login);
					if (!db.isUserExist(login.getText().toString())) {
						db.createUser(login.getText().toString(), password
								.getText().toString());
						id = db.getAllUsers();
						text = "The user '" + login.getText()
								+ "' was added successfully!\nUsers Total = "
								+ id;
					} else {
						text = "The user '" + login.getText()
								+ " is already exist";
					}

					Context context = getApplicationContext();
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					login.setText("");
					password.setText("");
				} catch (Exception ex) {
					Context context = getApplicationContext();
					CharSequence text = ex.toString() + "ID = " + id;
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				db.close();
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity2, menu);
		return true;
	}

}
