package ntnu.master.nofall;

import java.util.ArrayList;

import ntnu.master.nofall.adapter.ChkbxExpandableListViewAdapter;
import ntnu.master.nofall.object.Category;
import ntnu.master.nofall.object.SubCategory;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class MedicationSelectActivity extends Activity {

	ExpandableListView expandableListview;
	ChkbxExpandableListViewAdapter adapter;
	ArrayList<Category> category_array = new ArrayList<Category>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list_view);

		// Data
		for (int i = 0; i < 10; i++) {
			Category category = new Category();
			category.category_name = "Category - " + i;
			for (int j = 0; j < 2; j++) {
				SubCategory subcategory = new SubCategory();
				subcategory.subcategory_name = "  SubCategory - " + j;
				subcategory.selected = true;
				category.subcategory_array.add(subcategory);
			}
			category_array.add(category);
		}

		expandableListview = (ExpandableListView) findViewById(R.id.expandableListView);
		adapter = new ChkbxExpandableListViewAdapter(MedicationSelectActivity.this,
				expandableListview, category_array);
		expandableListview.setAdapter(adapter);

		expandableListview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Log.e("Group position :: " + groupPosition,
						" &&   Child position :: " + childPosition);
				if (category_array.get(groupPosition).subcategory_array
						.get(childPosition).selected) {
					category_array.get(groupPosition).subcategory_array
							.get(childPosition).selected = false;
				} else {
					category_array.get(groupPosition).subcategory_array
							.get(childPosition).selected = true;
				}
				adapter.notifyDataSetChanged();
				return true;
			}
		});
	}
}