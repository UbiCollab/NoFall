package ntnu.master.nofall;

import java.util.ArrayList;

import ntnu.master.nofall.adapter.MedListViewAdapter;
import ntnu.master.nofall.contentprovider.UserContentProvider;
import ntnu.master.nofall.database.MedicationTable;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MedicationSelectActivity extends Activity implements
LoaderManager.LoaderCallbacks<Cursor>{
	private MedListViewAdapter myAdapter;
	private ListView myListView;
	private Button myButton;
	private SimpleCursorAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_select);
		myListView = (ListView) findViewById(R.id.myListView);
		myButton = (Button) findViewById(R.id.buttonStart);
		fillData();
		myListView.setAdapter(adapter);
		myListView.setItemsCanFocus(false);

		myButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ArrayList<Medication> selectedTeams = new ArrayList<Medication>();
				final SparseBooleanArray checkedItems = myListView
						.getCheckedItemPositions();
				int checkedItemsCount = checkedItems.size();
				for (int i = 0; i < checkedItemsCount; ++i) {
					// Item position in adapter
					int position = checkedItems.keyAt(i);
					// Add team if item is checked == TRUE!
					if (checkedItems.valueAt(i))
						selectedTeams.add(myAdapter.getItem(position));
				}
				if (selectedTeams.size() < 2)
					Toast.makeText(getBaseContext(),
							"Need to select two or more teams.",
							Toast.LENGTH_SHORT).show();
				else {
					// Just logging the output.
					for (Medication t : selectedTeams)
						Log.d("SELECTED TEAMS: ", t.getTeamName());
				}
			}
		});

	}

	private void fillData() {

		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] { MedicationTable.COLUMN_NAME };
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.listview_TeamDescription };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.row_team_layout, null, from,
				to, 0);
		
	}

	// creates a new loader after the initLoader () call
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { MedicationTable.COLUMN_ID, MedicationTable.COLUMN_NAME };
		CursorLoader cursorLoader = new CursorLoader(this,
				UserContentProvider.CONTENT_URI_MED, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
	}
}
