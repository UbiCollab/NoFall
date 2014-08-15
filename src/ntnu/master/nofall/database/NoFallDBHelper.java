package ntnu.master.nofall.database;

import java.util.ArrayList;

import ntnu.master.nofall.R;
import ntnu.master.nofall.contentprovider.MyContentProvider;
import ntnu.master.nofall.database.medication.MedCategorySpecTable;
import ntnu.master.nofall.database.medication.MedListLogTable;
import ntnu.master.nofall.database.medication.MedLogTable;
import ntnu.master.nofall.database.medication.MedicationSpecTable;
import ntnu.master.nofall.database.sensor.SensorLogTable;
import ntnu.master.nofall.database.sensor.SensorRiskSpecTable;
import ntnu.master.nofall.database.sensor.SensorSpecTable;
import ntnu.master.nofall.object.Category;
import ntnu.master.nofall.object.SubCategory;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class NoFallDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "nofall.db";
	private static final int DATABASE_VERSION = 7;

	private ContentResolver myCR;
	
	private final Context fContext;
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

	public NoFallDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		fContext = context;
		myCR = context.getContentResolver();
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("PRAGMA foreign_keys=ON");
		
		UserTable.onCreate(database);
		//Tables for medication section
		MedicationSpecTable.onCreate(database);
		MedCategorySpecTable.onCreate(database);
		MedLogTable.onCreate(database);
		MedListLogTable.onCreate(database);
		//Tables for movement section
		SensorLogTable.onCreate(database);
		SensorRiskSpecTable.onCreate(database);
		SensorSpecTable.onCreate(database);
		
		getMedicationDataFromXML();
		Log.i("XML", "Got data from XML -> Going to insert to DB");
		fillCatAndMedTable(database);
		Log.i("DB", "Inserted data to DB");
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("PRAGMA foreign_keys=ON");
		
		UserTable.onUpgrade(database, oldVersion, newVersion);
		//Tables for medication section
		MedicationSpecTable.onUpgrade(database, oldVersion, newVersion);
		MedCategorySpecTable.onUpgrade(database, oldVersion, newVersion);
		MedLogTable.onUpgrade(database, oldVersion, newVersion);
		MedListLogTable.onUpgrade(database, oldVersion, newVersion);
		//Tables for movement section
		SensorLogTable.onUpgrade(database, oldVersion, newVersion);
		SensorRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		SensorSpecTable.onUpgrade(database, oldVersion, newVersion);
		
		getMedicationDataFromXML();
		Log.i("XML", "Got data from XML -> Going to insert to DB");
		fillCatAndMedTable(database);
		Log.i("DB", "Inserted data to DB");
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onConfigure (SQLiteDatabase db){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
	        db.setForeignKeyConstraintsEnabled(true);
	    } else {
	        db.execSQL("PRAGMA foreign_keys=ON");
	    }
	}
	
	private void getMedicationDataFromXML() {
		//Get xml resource file
        Resources res = fContext.getResources();
        
		// Data
		this.mAnticoagulantsArray =  res.getStringArray(
				R.array.Anticoagulants);
		addXMLDataToCatArray(mAnticoagulantsArray);
		
		this.mAnticonvulsantsArray = res.getStringArray(
				R.array.Anticonvulsants);
		addXMLDataToCatArray(mAnticonvulsantsArray);
		
		this.mAntidepressantArray = res.getStringArray(
				R.array.Antidepressant);
		addXMLDataToCatArray(mAntidepressantArray);
		
		this.mAntihistaminesArray = res.getStringArray(
				R.array.Antihistamines);
		addXMLDataToCatArray(mAntihistaminesArray);
		
		this.mAntipsychoticsArray = res.getStringArray(
				R.array.Antipsychotics);
		addXMLDataToCatArray(mAntipsychoticsArray);
		
		this.mCorticosteroidsArray = res.getStringArray(
				R.array.Corticosteroids);
		addXMLDataToCatArray(mCorticosteroidsArray);
		
		this.mACEInhibitorsArray = res.getStringArray(
				R.array.ACE_Inhibitors);
		addXMLDataToCatArray(mACEInhibitorsArray);
		
		this.mARBArray = res.getStringArray(
				R.array.Alpha_Receptor_Blockers);
		addXMLDataToCatArray(mARBArray);
		
		this.mMRArray = res.getStringArray(R.array.Muscle_Relaxants);
		addXMLDataToCatArray(mMRArray);
	}
	
	private void addXMLDataToCatArray(String[] data){
		Category category = new Category();
		category.category_name = data[0].toString();
		for (int i = 1; i < data.length; i++) {
			SubCategory subcategory = new SubCategory();
			subcategory.subcategory_name = " " +  data[i].toString();
			category.subcategory_array.add(subcategory);
		}
		category_array.add(category);
	}
	
	/**
	 * Inserts the data from XML files to the tables.
	 * @param db
	 */
	private void fillCatAndMedTable(SQLiteDatabase db){
		ContentValues values = new ContentValues();
		try{
		for (Category cat : category_array) {
			//Add the name of the category to the cat table
			values.put(MedCategorySpecTable.COLUMN_NAME, cat.category_name);
			int catID = (int)db.insert(MedCategorySpecTable.TABLE_MED_CAT, null, values);
			
			//Add all medications in category to medication table
			for (int i = 0; i < cat.subcategory_array.size(); i++) {
				ContentValues values2 = new ContentValues();
				values2.put(MedicationSpecTable.COLUMN_NAME, cat.subcategory_array.get(i).subcategory_name);
				values2.put(MedicationSpecTable.COLUMN_FK_CATEGORY, catID);
				db.insert(MedicationSpecTable.TABLE_MED, null, values2);
			}
		}
		}catch(Exception e){
			Log.i("ERROR", "Failed to insert into Category and Medication Table");
		}
	}
	
	/**
	 * Returns Medication to a given category
	 * @param foreignKey
	 * @return Cursor: 0 = COLUMN_ID, 1 = COLUMN_NAME
	 */
	public Cursor getMedicationByCategory(int foreignKey){		
		String selection = "fkCategory = \"" + foreignKey + "\"";
		String[] projection = {MedicationSpecTable.COLUMN_ID, MedicationSpecTable.COLUMN_NAME};
		    	
		Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI_MED, 
		              projection, selection, null,
		    	        null);
		return cursor;
	}
	
	/**
	 * Returns all categories
	 * @return Cursor: 0 = COLUMN_ID, 1 = COLUMN_NAME
	 */
	public Cursor getMedCategories(){
		String[] projection = {MedCategorySpecTable.COLUMN_ID, MedCategorySpecTable.COLUMN_NAME};
    	
		Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI_MED_CAT, 
		              projection, null, null,
		    	        null);
		return cursor;
	}
	
	/**
	 * Inserts the number of medications the user takes
	 * @param value
	 */
	public Uri insertNumberOfMedication(int value){
		ContentValues values = new ContentValues();
		values.put(MedLogTable.COLUMN_NUMBER_OF, value);
		 
		Uri temp = myCR.insert(MyContentProvider.CONTENT_URI_MED_LOG, values);
		
		return temp;
	}
	
	/**
	 * Binds the number of medications the user takes and which specific medications they take
	 * @param medID
	 * @param listID
	 */
	public void insertRegistreredMedication(int medID, int listID){
		ContentValues values = new ContentValues();
		values.put(MedListLogTable.COLUMN_FK_MED, medID);
		values.put(MedListLogTable.COLUMN_FK_MED_REG, listID);
		
		myCR.insert(MyContentProvider.CONTENT_URI_MED_LIST_LOG, values);
	}
	
	/**
	 * Updates the numberOf to be the same as number of selected medications in the MedicationSelectActivity.
	 * If the user selects more than they chose on the Spinner.
	 * @param listID
	 * @param number
	 */
	public void updateNumberOfMed(int listID, int number){
		ContentValues values = new ContentValues();
		String selection = "_id = \"" + listID + "\"";
		values.put(MedLogTable.COLUMN_NUMBER_OF, number);
		 
		myCR.update(MyContentProvider.CONTENT_URI_MED_LOG, values, selection, null);
		
	}
}
