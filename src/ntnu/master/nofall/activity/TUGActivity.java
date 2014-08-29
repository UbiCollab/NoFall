package ntnu.master.nofall.activity;

import ntnu.master.nofall.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TUGActivity extends Activity {

	TextView timerTextView;
	long startTime = 0;

	// runs without a timer by reposting this handler at the end of the runnable
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {

		@Override
		public void run() {
			long millis = System.currentTimeMillis() - startTime;
			int seconds = (int) (millis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;

			timerTextView.setText(String.format("%d:%02d", minutes, seconds));

			timerHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tug);

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

}
