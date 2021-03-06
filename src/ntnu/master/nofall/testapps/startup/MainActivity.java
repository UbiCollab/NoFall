package ntnu.master.nofall.testapps.startup;

import ntnu.master.nofall.R;
import ntnu.master.nofall.testapps.graphicalplots.SimplePieChartActivity;
import ntnu.master.nofall.testapps.graphicalplots.XYPlotActivity;
import ntnu.master.nofall.testapps.pedometer.Pedometer;
import ntnu.master.nofall.testapps.survey.SurveyActivity;
import ntnu.master.nofall.testapps.test.TUGActivity;
import ntnu.master.nofall.testapps.user.UserOverviewActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {
	private final String PREFS_NAME = "MyPrefsFile";
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		if (settings.getBoolean("my_first_time", true)) {
			// the app is being launched for first time, do something
			Log.d("Comments", "First time");

			if (savedInstanceState == null) {
				getFragmentManager().beginTransaction()
						.add(R.id.container, new RiskAssessmentFragment())
						.commit();
			}

			// record the fact that the app has been started at least once
			settings.edit().putBoolean("my_first_time", false).commit();
		} else {
			if (savedInstanceState == null) {
				getFragmentManager().beginTransaction()
						.add(R.id.container, new MainMenuFragment()).commit();
			}
		}

	}

	// Open up the user activity to register user
	public void OpenUserActivity(View view) {
		Intent intent = new Intent(MainActivity.this,
				UserOverviewActivity.class);
		startActivity(intent);
	}

	public void OpenHelpActivity(View view) {
		Intent intent = new Intent(MainActivity.this, HelpActivity.class);
		startActivity(intent);
	}

	public void OpenTUGActivity(View view) {
		Intent intent = new Intent(MainActivity.this, TUGActivity.class);
		startActivity(intent);
	}

	public void OpenSurveyActivity(View view) {
		Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
		startActivity(intent);
	}

	public void OpenXYPlotActivity(View view) {
		Intent intent = new Intent(MainActivity.this, XYPlotActivity.class);
		startActivity(intent);
	}
	
	public void OpenPieChartActivity(View view) {
		Intent intent = new Intent(MainActivity.this, SimplePieChartActivity.class);
		startActivity(intent);
	}

	public void OpenPedometerActivity(View view) {
		Intent intent = new Intent(MainActivity.this, Pedometer.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(MainActivity.this, HelpActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * The normal main menu.
	 */
	public static class MainMenuFragment extends Fragment {

		public MainMenuFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	/**
	 * The risk assessment process.
	 *
	 */
	public static class RiskAssessmentFragment extends Fragment {

		public RiskAssessmentFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_risk_assessment,
					container, false);
			return rootView;
		}
	}

}
