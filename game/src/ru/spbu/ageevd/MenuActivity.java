package ru.spbu.ageevd;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {

	private static final String TAG = "DialogActivity";
	private static final int DLG_EXAMPLE1 = 0;
	private static final int TEXT_ID = 0;
	private final DBAdapter db = new DBAdapter(this);
	private static String user;
	private static boolean isChecked;
	public static String password;
	public static String name;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu);
		db.open();
		user = getIntent().getExtras().getString("KEY_USER");
		password = getIntent().getExtras().getString("KEY_PASSWORD");
		isChecked =  getIntent().getExtras().getBoolean("KEY_ISCHECKED");
		Log.d("ASD", "Menu ACTIVITY");
		Log.d("ASD",Boolean.toString(isChecked));
		name = db.getUserName(user);
		if (name.equals("")) {
			showDialog(DLG_EXAMPLE1);
		} else {
			execute();
		}
		
		

		TextView rat = (TextView) findViewById(R.id.rating);
		CharSequence text = "Your rating,  " + db.getUserName(user) + ": "
				+ Integer.toString(db.getUserRating(user));
		rat.setText(text);
		Button newGameButton = (Button) findViewById(R.id.button1);
		db.close();
		newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						GameActivity.class);
				intent.putExtra("KEY_USER", user);
				intent.putExtra("KEY_PASSWORD", password);
				intent.putExtra("KEY_ISCHECKED", isChecked);
				startActivity(intent);
			}
		});
		Button ratingButton = (Button) findViewById(R.id.button0);
		ratingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this,
						ShowRatingActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void execute() {
		MyTask1 mt = new MyTask1();
		mt.execute();
	}

	/**
	 * Called to create a dialog to be shown.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DLG_EXAMPLE1:
			return createExampleDialog();
		default:
			return null;
		}
	}

	/**
	 * If a dialog has already been created, this is called to reset the dialog
	 * before showing it a 2nd time. Optional.
	 */
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		switch (id) {
		case DLG_EXAMPLE1:
			// Clear the input box.
			EditText text = (EditText) dialog.findViewById(TEXT_ID);
			text.setText("");
			break;
		}
	}

	/**
	 * Create and return an example alert dialog with an edit text box.
	 */
	private Dialog createExampleDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Hello User");
		builder.setMessage("What is your name:");

		// Use an EditText view to get user input.
		final EditText input = new EditText(this);
		input.setId(TEXT_ID);
		builder.setView(input);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				name = input.getText().toString();
				db.open();
				db.updateUserName(user, name);
				db.close();
				if (getIntent().getExtras().getBoolean("KEY_ISCHECKED")) {
					execute();
				}
				Log.d(TAG, "User name: " + name);
				return;
			}
		});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});

		return builder.create();
	}
}

class MyTask1 extends AsyncTask<Void, Void, Void> {

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		Log.d("ASD", "ZASHLI V SYNC");
		UpdateRating updRating = new UpdateRating();
		try {
			Log.d("ASD", updRating.register(MenuActivity.name,
					MenuActivity.password));
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
