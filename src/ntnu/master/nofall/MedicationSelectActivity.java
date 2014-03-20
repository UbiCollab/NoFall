package ntnu.master.nofall;

import java.util.ArrayList;

import ntnu.master.nofall.adapter.TeamListViewAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MedicationSelectActivity extends Activity {
	ArrayList<Team> myTeams;
	TeamListViewAdapter myAdapter;
	ListView myListView;
	Button myButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		myTeams = new ArrayList<Team>();
		// Add a few teams to display.
		myTeams.add(new Team("Winners", 10));
		myTeams.add(new Team("Philidelphia Flyers", 5));
		myTeams.add(new Team("Detroit Red Wings", 1));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication_select);
		myListView = (ListView) findViewById(R.id.myListView);
		myButton = (Button) findViewById(R.id.buttonStart);
		// Construct our adapter, using our own layout and myTeams
		myAdapter = new TeamListViewAdapter(this, R.layout.row_team_layout,
				myTeams);
		myListView.setAdapter(myAdapter);
		myListView.setItemsCanFocus(false);

		myButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ArrayList<Team> selectedTeams = new ArrayList<Team>();
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
					for (Team t : selectedTeams)
						Log.d("SELECTED TEAMS: ", t.getTeamName());
				}
			}
		});
	}
}
