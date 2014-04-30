package ntnu.master.nofall.database;

import java.util.ArrayList;

import ntnu.master.nofall.R;
import ntnu.master.nofall.contentprovider.MyContentProvider;
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
		MedicationTable.onCreate(database);
		MedCategoryTable.onCreate(database);
		MedRegTable.onCreate(database);
		MedRegListTable.onCreate(database);
		
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
		MedicationTable.onUpgrade(database, oldVersion, newVersion);
		MedCategoryTable.onUpgrade(database, oldVersion, newVersion);
		MedRegTable.onUpgrade(database, oldVersion, newVersion);
		MedRegListTable.onUpgrade(database, oldVersion, newVersion);
		
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
	
	private void fillCatAndMedTable(SQLiteDatabase db){
		ContentValues values = new ContentValues();
		try{
		for (Category cat : category_array) {
			//Add the name of the category to the cat table
			values.put(MedCategoryTable.COLUMN_NAME, cat.category_name);
			int catID = (int)db.insert(MedCategoryTable.TABLE_MED_CAT, null, values);
			
			//Add all medications in category to medication table
			for (int i = 0; i < cat.subcategory_array.size(); i++) {
				ContentValues values2 = new ContentValues();
				values2.put(MedicationTable.COLUMN_NAME, cat.subcategory_array.get(i).subcategory_name);
				values2.put(MedicationTable.COLUMN_FK_CATEGORY, catID);
				db.insert(MedicationTable.TABLE_MED, null, values2);
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
		String[] projection = {MedicationTable.COLUMN_ID, MedicationTable.COLUMN_NAME};
		    	
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
		String[] projection = {MedCategoryTable.COLUMN_ID, MedCategoryTable.COLUMN_NAME};
    	
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
		values.put(MedRegTable.COLUMN_NUMBER_OF, value);
		 
		Uri temp = myCR.insert(MyContentProvider.CONTENT_URI_MED_REG, values);
		
		return temp;
	}
	
	/**
	 * Binds the number of medications the user takes and which specific medications they take
	 * @param medID
	 * @param listID
	 */
	public void insertRegistreredMedication(int medID, int listID){
		ContentValues values = new ContentValues();
		values.put(MedRegListTable.COLUMN_FK_MED, medID);
		values.put(MedRegListTable.COLUMN_FK_MED_REG, listID);
		
		myCR.insert(MyContentProvider.CONTENT_URI_MED_REG_LIST, values);
	}
	
	public void updateNumberOfMed(int listID, int number){
		ContentValues values = new ContentValues();
		String selection = "_id = \"" + listID + "\"";
		values.put(MedRegTable.COLUMN_NUMBER_OF, number);
		 
		myCR.update(MyContentProvider.CONTENT_URI_MED_REG, values, selection, null);
		
	}
}
