package ru.spbu.ageevd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameActivity extends Activity {
	private static final GamePlay gamePlay = new GamePlay();
	private static final int MAX_ROUNDS = 5;
	private boolean isButtonPressed = false;
	private static int id = 0;
	private Handler mHandler;
	private static Button button;
	private static CharSequence ch;
	private final Context context = this;
	private static RadioGroup rdGroup;
	private static RadioButton rdButton1;
	private static RadioButton rdButton2;
	private static RadioButton rdButton3;
	private static RadioButton rdButton0;
	private static ImageView view;
	private static String result;
	private static int answer;
	private static int numberOfRound;
	private static int trueAnswers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		numberOfRound = 0;
		trueAnswers = 0;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game);
		Log.d("ASD", "TUT ESHE OK");
		rdGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		Log.d("ASD", "TUT ESHE OK");
		rdButton1 = (RadioButton) findViewById(R.id.radio1);
		rdButton2 = (RadioButton) findViewById(R.id.radio2);
		rdButton3 = (RadioButton) findViewById(R.id.radio3);
		rdButton0 = (RadioButton) findViewById(R.id.radio0);
		view = (ImageView) findViewById(R.id.image);
		Log.d("ASD", "TUT ESHE OK");
	    button = (Button) findViewById(R.id.buttonnext);
	    Log.d("ASD", "TUT ESHE OK");
	    getNextRound();
	    
		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				answer = gamePlay.getAnswer();
				if (id == answer) {
					result = "Верно";
					trueAnswers++;
				} else {
					result = "Неверно";
				}
				Log.d("ASD", "gameplayh: " + Integer.toString(answer) + ", button : " + Integer.toString(id));
				Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
				if (numberOfRound == 5) {
					Intent intent = new Intent(GameActivity.this, ResultActivity.class);
					intent.putExtra("KEY_RESULT", Integer.toString(trueAnswers));
					startActivity(intent);
				}
				getNextRound();								
			};
		};
	
		button.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				id = (rdGroup.getCheckedRadioButtonId()) % 10;
				numberOfRound++;
				Thread t = new Thread(new Runnable() {
			        public void run() {			          			      
			            mHandler.sendEmptyMessage(id);		            
			        }
			      });
			      t.start();
				
			}
		});

			
	}
	
	private void getNextRound() {
		gamePlay.generateNextRound();
		view.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),
				gamePlay.getPathToImage()));

		String variants[] = gamePlay.getVariants();
		ch = variants[0];
		rdButton0.setText(ch);
		ch = variants[1];
		rdButton1.setText(ch);
		ch = variants[2];
		rdButton2.setText(ch);
		ch = variants[3];
		rdButton3.setText(ch);
	}

}
