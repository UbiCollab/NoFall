package ntnu.master.nofall.testapps.pedometer.fragements;

import ntnu.master.nofall.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PedoPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
	}
}