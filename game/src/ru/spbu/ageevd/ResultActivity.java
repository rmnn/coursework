package ru.spbu.ageevd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ResultActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_result);
		TextView textView = (TextView) findViewById(R.id.result);
		String result = getIntent().getExtras().getString("KEY_RESULT");
		CharSequence text = "Ваш результат : " + result;
		textView.setText(text);
	}

}
