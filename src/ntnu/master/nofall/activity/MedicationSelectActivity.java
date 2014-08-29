package ntnu.master.nofall.activity;

import java.util.ArrayList;

import ntnu.master.nofall.R;
import ntnu.master.nofall.adapter.ChkbxExpandableListViewAdapter;
import ntnu.master.nofall.database.NoFallDBHelper;
import ntnu.master.nofall.object.Category;
import ntnu.master.nofall.object.SubCategory;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class MedicationSelectActivity extends Activity {

	private ExpandableListView expandableListview;
	private ChkbxExpandableListViewAdapter adapter;
	private ArrayList<Category> category_array = new ArrayList<Category>();
	private int listID = -1;
	private int numberOfMed = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list_view);
		
		// Gets the ID for the list of number of medications table
		Bundle b = getIntent().getExtras();
		if(b != null) {
			listID = b.getInt("listID");
			numberOfMed = b.getInt("numberOfMed");
		}
		// Data
		fillData();

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
		insertToDBSelectedMedication();
		Intent intent = new Intent(MedicationSelectActivity.this, MedicationRegActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * Inserts the selected medications associated with the correct listID
	 */
	private void insertToDBSelectedMedication(){
		NoFallDBHelper db = new NoFallDBHelper(this);
		int temp = 0;
		for(Category cat: category_array){
			for(SubCategory sCat: cat.subcategory_array){
				if(sCat.selected) 
					db.insertRegistreredMedication(sCat.medID, listID);
					temp++;
			}
		}
		
		if(temp > numberOfMed){
			db.updateNumberOfMed(listID, numberOfMed);
		}
	}
	
	/**
	 * Adds the data from DB to the objects used in the expandable list
	 */
	private void fillData(){
		NoFallDBHelper db = new NoFallDBHelper(this);
		Cursor cur = db.getMedCategories();
		Category category = null;
		//Loops through all the categories
		while(cur.moveToNext()){
			category = new Category();
			category.category_name = cur.getString(1);
			Cursor cur2 = db.getMedicationByCategory(Integer.parseInt(cur.getString(0)));
			//For each category add the medications
			while(cur2.moveToNext()){
				SubCategory subcategory = new SubCategory();
				subcategory.medID = Integer.parseInt(cur2.getString(0));
				subcategory.subcategory_name = " " +  cur2.getString(1);
				category.subcategory_array.add(subcategory);
			}
			cur2.close();
			if(category != null) category_array.add(category);
		}
		cur.close();
	}
}