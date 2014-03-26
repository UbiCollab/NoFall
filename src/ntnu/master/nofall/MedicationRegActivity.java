package ntnu.master.nofall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MedicationRegActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reg);
		Spinner mspin = (Spinner) findViewById(R.id.spinner_nmb_medication);
		Integer[] items = new Integer[]{0,1,2,3,4,5,6,7,8};
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, items);
		mspin.setAdapter(adapter);
		
		
		
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	
	public void OpenSelectMedActivity(View view){
		Intent intent = new Intent(MedicationRegActivity.this, MedicationSelectActivity.class);
	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medication_reg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public class PlaceholderFragment extends Fragment {
//		import android.widget.ArrayAdapter;
//		
//		public PlaceholderFragment() {
//
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_select_med,
//					container, false);
//			return rootView;
//		}
//	}

}
