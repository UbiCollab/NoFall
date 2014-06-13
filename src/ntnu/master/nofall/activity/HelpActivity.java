package ntnu.master.nofall.activity;

import ntnu.master.nofall.R;
import ntnu.master.nofall.adapter.MyExpandableListAdapter;
import ntnu.master.nofall.object.FAQ;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

public class HelpActivity extends Activity {

	SparseArray<FAQ> groups = new SparseArray<FAQ>();

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
		FAQ group = new FAQ("Why should I use this application?");
		group.children.add("The risk of falling for senior citizens are incredble high[...] Facts n shizzle[...]" +
				"this application can help you with a quick and easy assessment and give advice.");
		groups.append(0, group);
		
		group = new FAQ("Does any information get stored?");
		group.children.add("All the data you provide for this application is only stored on this phone. " +
				"It will not be accessible for anyone else, but those who have access to this phone.");
		groups.append(1, group);
		
	}


}
