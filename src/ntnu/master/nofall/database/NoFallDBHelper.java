package ntnu.master.nofall.database;

import java.util.ArrayList;

import ntnu.master.nofall.R;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationCategorySpec;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationListLog;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationLog;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationSpec;
import ntnu.master.nofall.database.medication.MedCategorySpecTable;
import ntnu.master.nofall.database.medication.MedListLogTable;
import ntnu.master.nofall.database.medication.MedLogTable;
import ntnu.master.nofall.database.medication.MedicationSpecTable;
import ntnu.master.nofall.database.sensor.SensorLogTable;
import ntnu.master.nofall.database.sensor.SensorRiskSpecTable;
import ntnu.master.nofall.database.sensor.SensorSpecTable;
import ntnu.master.nofall.database.standards.StandardForeignTable;
import ntnu.master.nofall.database.standards.StandardNoFallRiskTable;
import ntnu.master.nofall.database.standards.StandardRiskMapTable;
import ntnu.master.nofall.database.standards.StandardsTable;
import ntnu.master.nofall.database.survey.SurveyAnswerLogTable;
import ntnu.master.nofall.database.survey.SurveyLogTable;
import ntnu.master.nofall.database.survey.SurveyQRiskSpecTable;
import ntnu.master.nofall.database.survey.SurveyQuestionSpecTable;
import ntnu.master.nofall.database.survey.SurveySpecTable;
import ntnu.master.nofall.database.test.TestAnswerLogTable;
import ntnu.master.nofall.database.test.TestLogTable;
import ntnu.master.nofall.database.test.TestMeasureLogTable;
import ntnu.master.nofall.database.test.TestMeasureRiskSpecTable;
import ntnu.master.nofall.database.test.TestMeasureSpecTable;
import ntnu.master.nofall.database.test.TestQuestionRiskSpecTable;
import ntnu.master.nofall.database.test.TestQuestionSpecTable;
import ntnu.master.nofall.database.test.TestSpecTable;
import ntnu.master.nofall.database.user.UserTable;
import ntnu.master.nofall.database.user.UserTotalRiskLogTable;
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
	private static final int DATABASE_VERSION = 9;

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
		
		// Use tables
		UserTable.onCreate(database);
		UserTotalRiskLogTable.onCreate(database);
		
		// Medication tables
		MedicationSpecTable.onCreate(database);
		MedCategorySpecTable.onCreate(database);
		MedLogTable.onCreate(database);
		MedListLogTable.onCreate(database);
		
		// Sensor tables
		SensorLogTable.onCreate(database);
		SensorRiskSpecTable.onCreate(database);
		SensorSpecTable.onCreate(database);
		
		// Standards tables
		StandardForeignTable.onCreate(database);
		StandardNoFallRiskTable.onCreate(database);
		StandardRiskMapTable.onCreate(database);
		StandardsTable.onCreate(database);
		
		// Survey tables
		SurveyAnswerLogTable.onCreate(database);
		SurveyLogTable.onCreate(database);
		SurveyQRiskSpecTable.onCreate(database);
		SurveyQuestionSpecTable.onCreate(database);
		SurveySpecTable.onCreate(database);
		
		// Test tables
		TestAnswerLogTable.onCreate(database);
		TestLogTable.onCreate(database);
		TestMeasureLogTable.onCreate(database);
		TestMeasureRiskSpecTable.onCreate(database);
		TestMeasureSpecTable.onCreate(database);
		TestQuestionSpecTable.onCreate(database);
		TestQuestionRiskSpecTable.onCreate(database);
		TestSpecTable.onCreate(database);
		
		// Test data generation
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
		
		// Standards tables
		StandardsTable.onUpgrade(database, oldVersion, newVersion);
		StandardRiskMapTable.onUpgrade(database, oldVersion, newVersion);
		StandardForeignTable.onUpgrade(database, oldVersion, newVersion);
		StandardNoFallRiskTable.onUpgrade(database, oldVersion, newVersion);
		
		// User tables
		UserTable.onUpgrade(database, oldVersion, newVersion);
		UserTotalRiskLogTable.onUpgrade(database, oldVersion, newVersion);
		
		// Medication tables
		MedicationSpecTable.onUpgrade(database, oldVersion, newVersion);
		MedCategorySpecTable.onUpgrade(database, oldVersion, newVersion);
		MedLogTable.onUpgrade(database, oldVersion, newVersion);
		MedListLogTable.onUpgrade(database, oldVersion, newVersion);
		
		// Sensor tables
		SensorLogTable.onUpgrade(database, oldVersion, newVersion);
		SensorRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		SensorSpecTable.onUpgrade(database, oldVersion, newVersion);						
		
		// Survey tables
		SurveyAnswerLogTable.onUpgrade(database, oldVersion, newVersion);
		SurveyLogTable.onUpgrade(database, oldVersion, newVersion);
		SurveyQRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		SurveyQuestionSpecTable.onUpgrade(database, oldVersion, newVersion);
		SurveySpecTable.onUpgrade(database, oldVersion, newVersion);
		
		// Test tables
		TestAnswerLogTable.onUpgrade(database, oldVersion, newVersion);
		TestLogTable.onUpgrade(database, oldVersion, newVersion);
		TestMeasureLogTable.onUpgrade(database, oldVersion, newVersion);
		TestMeasureRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestMeasureSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestQuestionSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestQuestionRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestSpecTable.onUpgrade(database, oldVersion, newVersion);
		
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
			values.put(MedicationCategorySpec.NAME, cat.category_name);
			values.put(MedicationCategorySpec.OWNER_ID, "NoFall");
			int catID = (int)db.insert(MedicationCategorySpec.TABLE_NAME, null, values);
			
			//Add all medications in category to medication table
			for (int i = 0; i < cat.subcategory_array.size(); i++) {
				ContentValues values2 = new ContentValues();
				values2.put(MedicationSpec.NAME, cat.subcategory_array.get(i).subcategory_name);
				values2.put(MedicationSpec.FK_MEDICATION_CATEGORY, catID);
				db.insert(MedicationSpec.TABLE_NAME, null, values2);
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
		String selection = MedicationSpec.FK_MEDICATION_CATEGORY + " = \"" + foreignKey + "\"";
		String[] projection = {MedicationSpec._ID, MedicationSpec.NAME};
		    	
		Cursor cursor = myCR.query(MedicationSpec.CONTENT_URI, 
		              projection, selection, null,
		    	        null);
		return cursor;
	}
	
	/**
	 * Returns all categories
	 * @return Cursor: 0 = COLUMN_ID, 1 = COLUMN_NAME
	 */
	public Cursor getMedCategories(){
		String[] projection = {MedicationCategorySpec._ID, MedicationCategorySpec.NAME};
    	
		Cursor cursor = myCR.query(MedicationCategorySpec.CONTENT_URI, 
		              projection, null, null, null);
		return cursor;
	}
	
	/**
	 * Inserts the number of medications the user takes
	 * @param value
	 */
	public Uri insertNumberOfMedication(int value){
		ContentValues values = new ContentValues();
		values.put(MedicationLog.NUMBER_OF, value);
		 
		Uri temp = myCR.insert(MedicationLog.CONTENT_URI, values);
		
		return temp;
	}
	
	/**
	 * Binds the number of medications the user takes and which specific medications they take
	 * @param medID
	 * @param listID
	 */
	public void insertRegistreredMedication(int medID, int listID){
		ContentValues values = new ContentValues();
		values.put(MedicationListLog.FK_MED_SPEC, medID);
		values.put(MedicationListLog.FK_MED_LOG, listID);
		
		myCR.insert(MedicationListLog.CONTENT_URI, values);
	}
	
	/**
	 * Updates the numberOf to be the same as number of selected medications in the MedicationSelectActivity.
	 * If the user selects more than they chose on the Spinner.
	 * @param listID
	 * @param number
	 */
	public void updateNumberOfMed(int listID, int number){
		ContentValues values = new ContentValues();
		String selection = MedicationLog._ID + " = \"" + listID + "\"";
		values.put(MedicationLog.NUMBER_OF, number);
		 
		myCR.update(MedicationLog.CONTENT_URI, values, selection, null);
		
	}
}
