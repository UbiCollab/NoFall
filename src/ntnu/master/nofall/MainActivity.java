package ntnu.master.nofall;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	// Open up the user activity to register user
	public void OpenUserActivity(View view){
		Intent intent = new Intent(MainActivity.this, UserOverviewActivity.class);
	    startActivity(intent);
	}
	
	public void OpenHelpActivity(View view){
		Intent intent = new Intent(MainActivity.this, HelpActivity.class);
		startActivity(intent);
	}
	
	public void OpenTUGActivity(View view){
		Intent intent = new Intent(MainActivity.this, TUGActivity.class);
		startActivity(intent);
	}
	
	public void OpenSurveyActivity(View view){
		Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
		startActivity(intent);
	}
	
	public void OpenXYPlotActivity(View view){
		Intent intent = new Intent(MainActivity.this, XYPlotActivity.class);
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
