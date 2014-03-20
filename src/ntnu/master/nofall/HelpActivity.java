package ntnu.master.nofall;

import ntnu.master.nofall.adapter.MyExpandableListAdapter;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.os.Build;

public class HelpActivity extends Activity {

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_help);
	//
	// if (savedInstanceState == null) {
	// getFragmentManager().beginTransaction()
	// .add(R.id.container, new PlaceholderFragment()).commit();
	// }
	// }

	// more efficient than HashMap for mapping integers to objects
	SparseArray<Group> groups = new SparseArray<Group>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		createData();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
		MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
				groups, listView);
		listView.setAdapter(adapter);
	}

	public void createData() {
		// FAQs
		Group group = new Group("Why should I use this application?");
		group.children.add("The risk of falling for senior citizens are incredble high[...] Facts n shizzle[...]" +
				"this application can help you with a quick and easy assessment and give advice.");
		groups.append(0, group);
		
		group = new Group("Does any information get stored?");
		group.children.add("All the data you provide for this application is only stored on this phone. " +
				"It will not be accessible for anyone else, but the those who have access to this phone.");
		groups.append(1, group);
		
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.help, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
	//
	// /**
	// * A placeholder fragment containing a simple view.
	// */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_help, container,
	// false);
	// return rootView;
	// }
	// }

}
