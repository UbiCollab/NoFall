package ntnu.master.nofall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import ntnu.master.nofall.R;
import ntnu.master.nofall.Medication;

public class MedListViewAdapter extends ArrayAdapter<Medication> {
	View row;
	ArrayList<Medication> medicationList;
	int resLayout;
	Context context;

	public MedListViewAdapter(Context context, int textViewResourceId,
			ArrayList<Medication> medicationList) {
		super(context, textViewResourceId, medicationList);
		this.medicationList = medicationList;
		resLayout = textViewResourceId;
		this.context = context;
	}

	@Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            row = convertView;
            if(row == null)
            {   // inflate our custom layout. resLayout == R.layout.row_team_layout.xml
                LayoutInflater ll = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = ll.inflate(resLayout, parent, false);
            }

            Medication item = medicationList.get(position); // Produce a row for each Team.

            if(item != null)
            {   // Find our widgets and populate them with the Team data.
                TextView myTeamDescription = (TextView) row.findViewById(R.id.listview_TeamDescription);
                TextView myTeamWins = (TextView) row.findViewById(R.id.listview_TeamWins);
                if(myTeamDescription != null)
                    myTeamDescription.setText(item.getTeamName());
//                if(myTeamWins != null)
//                    myTeamWins.setText("Wins: " + String.valueOf(item.getTeamWins()));
            }
            return row;
        }
}
