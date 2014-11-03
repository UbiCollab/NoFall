package ntnu.master.nofall.testapps.test;

import ntnu.master.nofall.R;
import ntnu.master.nofall.platform.database.NoFallDBHelper;
import ntnu.master.nofall.testapps.activity.HelpActivity;
import ntnu.master.nofall.testapps.activity.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TUGActivity extends Activity {

	private TextView timerTextView;
	private long startTime = 0;
	private int seconds;
	private int minutes;
	// runs without a timer by reposting this handler at the end of the runnable
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {

		@Override
		public void run() {
			long millis = System.currentTimeMillis() - startTime;
			seconds = (int) (millis / 1000);
			minutes = seconds / 60;
			seconds = seconds % 60;

			timerTextView.setText(String.format("%d:%02d", minutes, seconds));

			timerHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tug);
		NoFallDBHelper db = new NoFallDBHelper(this);
		//db.insertTUGStandard();
		//db.insertTUGTestData();

		timerTextView = (TextView) findViewById(R.id.activity_TUG_TV_timer);

		Button b = (Button) findViewById(R.id.activity_TUG_btn_StartTUG);
		b.setText("Start");
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Button b = (Button) v;
				if (b.getText().equals("Stop")) {
					timerHandler.removeCallbacks(timerRunnable);
					b.setText("Start");
				} else {
					startTime = System.currentTimeMillis();
					timerHandler.postDelayed(timerRunnable, 0);
					b.setText("Stop");
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		timerHandler.removeCallbacks(timerRunnable);
		Button b = (Button) findViewById(R.id.activity_TUG_btn_StartTUG);
		b.setText("Start");
	}
	
	public void SaveResults(View view) {
		NoFallDBHelper db = new NoFallDBHelper(this);
		if(seconds > 0) db.insertTUGResults(seconds);
		Intent intent = new Intent(TUGActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	

}
