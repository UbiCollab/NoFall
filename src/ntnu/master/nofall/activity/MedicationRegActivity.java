package ntnu.master.nofall.activity;

import ntnu.master.nofall.R;
import ntnu.master.nofall.database.NoFallDBHelper;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MedicationRegActivity extends Activity {
	Spinner mspin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_reg);
		mspin = (Spinner) findViewById(R.id.activity_med_spinner_nmb_medication);
		Integer[] items = new Integer[]{0,1,2,3,4,5,6,7,8};
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, items);
		mspin.setAdapter(adapter);
	}

	
	public void OpenNextActivity(View view){
		Intent intent = new Intent(MedicationRegActivity.this, TUGActivity.class);
	    startActivity(intent);
	}
	
	// Saves the number of medications they take, and opens up the next activity
	public void OpenSelectMedActivity(View view){		
		int numberOfMed = (Integer) mspin.getSelectedItem();
		int id = -1;
		
		if(numberOfMed > 0){
			NoFallDBHelper db = new NoFallDBHelper(this);
			Uri temp = db.insertNumberOfMedication(numberOfMed);
			String path = temp.getPath();
			String idStr = path.substring(path.lastIndexOf('/') + 1);
			id = Integer.parseInt(idStr);
		}
		
		Intent intent = new Intent(MedicationRegActivity.this, MedicationSelectActivity.class);
		Bundle b = new Bundle();
		if(id > -1) b.putInt("listID", id); 
		b.putInt("numberOfMed", numberOfMed);
		intent.putExtras(b); 
		startActivity(intent);
		finish();
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
}
