package ntnu.master.nofall;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ntnu.master.nofall.adapter.ChkbxExpandableListViewAdapter;
import ntnu.master.nofall.object.Category;
import ntnu.master.nofall.object.SubCategory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class MedicationSelectActivity extends Activity {

	private ExpandableListView expandableListview;
	private ChkbxExpandableListViewAdapter adapter;
	private ArrayList<Category> category_array = new ArrayList<Category>();
	private String[] mAnticoagulantsArray;
	private String[] mAnticonvulsantsArray;
	private String[] mAntidepressantArray;
	private String[] mAntihistaminesArray;
	private String[] mAntipsychoticsArray;
	private String[] mCorticosteroidsArray;
	private String[] mACEInhibitorsArray;
	private String[] mARBArray;
	private String[] mMRArray;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list_view);

		// Data
		this.mAnticoagulantsArray =  getResources().getStringArray(R.array.Anticoagulants);
		fillData(mAnticoagulantsArray);
		this.mAnticonvulsantsArray =  getResources().getStringArray(R.array.Anticonvulsants);
		fillData(mAnticonvulsantsArray);
		this.mAntidepressantArray =  getResources().getStringArray(R.array.Antidepressant);
		fillData(mAntidepressantArray);
		this.mAntihistaminesArray =  getResources().getStringArray(R.array.Antihistamines);
		fillData(mAntihistaminesArray);
		this.mAntipsychoticsArray =  getResources().getStringArray(R.array.Antipsychotics);
		fillData(mAntipsychoticsArray);
		this.mCorticosteroidsArray =  getResources().getStringArray(R.array.Corticosteroids);
		fillData(mCorticosteroidsArray);
		this.mACEInhibitorsArray =  getResources().getStringArray(R.array.ACE_Inhibitors);
		fillData(mACEInhibitorsArray);
		this.mARBArray =  getResources().getStringArray(R.array.Alpha_Receptor_Blockers);
		fillData(mARBArray);
		this.mMRArray =  getResources().getStringArray(R.array.Muscle_Relaxants);
		fillData(mMRArray);
		
		//TO-DO: Add check for the medications in table, to set them as selected in the list

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
	
	// Buttonclick for Confirm button
	public void ConfirmMed(View view){
		
		//TO-DO Add code for query to add the selected medication to the medication table
		
		Intent intent = new Intent(MedicationSelectActivity.this, MedicationRegActivity.class);
	    startActivity(intent);
	}
	
	// Adds the data from XML file to the objects used in the expandable list
	private void fillData(String[] data){
		Category category = new Category();
		category.category_name = data[0].toString();
		for (int i = 1; i < data.length; i++) {
			SubCategory subcategory = new SubCategory();
			subcategory.subcategory_name = " " +  data[i].toString();
			category.subcategory_array.add(subcategory);
		}
		category_array.add(category);
	}
}