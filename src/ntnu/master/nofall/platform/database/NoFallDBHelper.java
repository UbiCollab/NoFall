package ntnu.master.nofall.platform.database;

import java.util.ArrayList;

import ntnu.master.nofall.R;
import ntnu.master.nofall.platform.database.medication.MedListLogTable;
import ntnu.master.nofall.platform.database.medication.MedLogTable;
import ntnu.master.nofall.platform.database.medication.MedicationCategoryTable;
import ntnu.master.nofall.platform.database.medication.MedicationSpecTable;
import ntnu.master.nofall.platform.database.medication.MedicationTypeTable;
import ntnu.master.nofall.platform.database.riskdefinitions.MeasureStandardsTable;
import ntnu.master.nofall.platform.database.riskdefinitions.RefRiskLevelsTable;
import ntnu.master.nofall.platform.database.riskdefinitions.RiskDefinitionTable;
import ntnu.master.nofall.platform.database.riskdefinitions.RiskMapTable;
import ntnu.master.nofall.platform.database.sensor.SensorLogItemTable;
import ntnu.master.nofall.platform.database.sensor.SensorLogTable;
import ntnu.master.nofall.platform.database.sensor.SensorSpecTable;
import ntnu.master.nofall.platform.database.survey.SurveyAnswerLogTable;
import ntnu.master.nofall.platform.database.survey.SurveyLogTable;
import ntnu.master.nofall.platform.database.survey.SurveyQRiskSpecTable;
import ntnu.master.nofall.platform.database.survey.SurveyQuestionSpecTable;
import ntnu.master.nofall.platform.database.survey.SurveySpecTable;
import ntnu.master.nofall.platform.database.test.TestAnswerLogTable;
import ntnu.master.nofall.platform.database.test.TestLogTable;
import ntnu.master.nofall.platform.database.test.TestMeasureLogTable;
import ntnu.master.nofall.platform.database.test.TestMeasureSpecTable;
import ntnu.master.nofall.platform.database.test.TestQuestionRiskSpecTable;
import ntnu.master.nofall.platform.database.test.TestQuestionSpecTable;
import ntnu.master.nofall.platform.database.test.TestSpecTable;
import ntnu.master.nofall.platform.database.user.UserTable;
import ntnu.master.nofall.platform.database.user.UserTotalRiskLogTable;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationCategory;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationListLog;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationLog;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationType;
import ntnu.master.nofall.platform.provider.RiskDefContract.MeasureStandards;
import ntnu.master.nofall.platform.provider.SensorContract.SensorLog;
import ntnu.master.nofall.platform.provider.SensorContract.SensorSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestLog;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureLog;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestSpec;
import ntnu.master.nofall.platform.provider.UsersContract.UserTotalRisk;
import ntnu.master.nofall.testapps.object.Category;
import ntnu.master.nofall.testapps.object.SubCategory;
import ntnu.master.nofall.testapps.pedometer.Utils;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class NoFallDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "nofall.db";
	private static final int DATABASE_VERSION = 16;

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
		Log.i("Inserted sensor", "Pedo");
	}		
	
	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("PRAGMA foreign_keys=ON");
		
		// MeasureStandards tables
		MeasureStandardsTable.onCreate(database);
		RefRiskLevelsTable.onCreate(database);
		RiskDefinitionTable.onCreate(database);
		RiskMapTable.onCreate(database);
		
		// Use tables
		UserTable.onCreate(database);
		UserTotalRiskLogTable.onCreate(database);
		
		// Medication tables
		MedicationSpecTable.onCreate(database);
		MedicationTypeTable.onCreate(database);
		MedicationCategoryTable.onCreate(database);
		MedLogTable.onCreate(database);
		MedListLogTable.onCreate(database);
		
		// Sensor tables
		SensorSpecTable.onCreate(database);
		SensorLogTable.onCreate(database);
		SensorLogItemTable.onCreate(database);
		
		// Survey tables
		SurveyAnswerLogTable.onCreate(database);
		SurveyLogTable.onCreate(database);
		SurveyQRiskSpecTable.onCreate(database);
		SurveyQuestionSpecTable.onCreate(database);
		SurveySpecTable.onCreate(database);
		
		// Test tables
		TestSpecTable.onCreate(database);
		TestLogTable.onCreate(database);
		TestMeasureSpecTable.onCreate(database);
		TestAnswerLogTable.onCreate(database);
		TestMeasureLogTable.onCreate(database);
		TestQuestionSpecTable.onCreate(database);
		TestQuestionRiskSpecTable.onCreate(database);
		
		
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
		
		// MeasureStandards tables
		MeasureStandardsTable.onUpgrade(database, oldVersion, newVersion);
		RefRiskLevelsTable.onUpgrade(database, oldVersion, newVersion);
		RiskDefinitionTable.onUpgrade(database, oldVersion, newVersion);
		RiskMapTable.onUpgrade(database, oldVersion, newVersion);
		
		// Use tables
		UserTable.onUpgrade(database, oldVersion, newVersion);
		UserTotalRiskLogTable.onUpgrade(database, oldVersion, newVersion);
		
		// Medication tables
		MedicationSpecTable.onUpgrade(database, oldVersion, newVersion);
		MedicationCategoryTable.onUpgrade(database, oldVersion, newVersion);
		MedicationTypeTable.onUpgrade(database, oldVersion, newVersion);
		MedLogTable.onUpgrade(database, oldVersion, newVersion);
		MedListLogTable.onUpgrade(database, oldVersion, newVersion);
		
		// Sensor tables
		SensorSpecTable.onUpgrade(database, oldVersion, newVersion);
		SensorLogTable.onUpgrade(database, oldVersion, newVersion);
		SensorLogItemTable.onUpgrade(database, oldVersion, newVersion);
		
		// Survey tables
		SurveyAnswerLogTable.onUpgrade(database, oldVersion, newVersion);
		SurveyLogTable.onUpgrade(database, oldVersion, newVersion);
		SurveyQRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		SurveyQuestionSpecTable.onUpgrade(database, oldVersion, newVersion);
		SurveySpecTable.onUpgrade(database, oldVersion, newVersion);
		
		// Test tables
		TestSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestLogTable.onUpgrade(database, oldVersion, newVersion);
		TestMeasureSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestAnswerLogTable.onUpgrade(database, oldVersion, newVersion);
		TestMeasureLogTable.onUpgrade(database, oldVersion, newVersion);
		TestQuestionSpecTable.onUpgrade(database, oldVersion, newVersion);
		TestQuestionRiskSpecTable.onUpgrade(database, oldVersion, newVersion);
		
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
	
	/**
	 * Fills the DB tables for medication based on data from a XML file. 
	 * The data in the XML file comes from research based on medications identified as related to fall risk.
	 */
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
				values.put(MedicationCategory.NAME, cat.category_name);
				int catID = (int)db.insert(MedicationCategory.TABLE_NAME, null, values);
				
				//Add all medications in category to medication table
				for (int i = 0; i < cat.subcategory_array.size(); i++) {
					ContentValues values2 = new ContentValues();
					values2.put(MedicationType.NAME, cat.subcategory_array.get(i).subcategory_name);
					values2.put(MedicationType.FK_MEDICATION_CATEGORY, catID);
					db.insert(MedicationType.TABLE_NAME, null, values2);
				}
		}
		}catch(Exception e){
			Log.i("ERROR", "Failed to insert into Category and Medication Table");
		}
	}
	
	/**
	 * Returns Medication to a given type
	 * @param foreignKey
	 * @return Cursor: 0 = COLUMN_ID, 1 = COLUMN_NAME
	 */
	public Cursor getMedicationByType(int foreignKey){	
		Cursor cursor = null;
		try{
			String selection = MedicationType.FK_MEDICATION_CATEGORY + " = \"" + foreignKey + "\"";
			String[] projection = {MedicationType._ID, MedicationType.NAME};
			    	
			cursor = myCR.query(MedicationType.CONTENT_URI, 
			              projection, selection, null,
			    	        null);
			return cursor;
		}catch(SQLiteException e){
			Log.i("SQLiteException when inserting movement speed", "error: " + e);
			return null;
		}finally{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
		}
	}
	
	/**
	 * Returns all categories
	 * @return Cursor: 0 = COLUMN_ID, 1 = COLUMN_NAME
	 */
	public Cursor getMedCategories(){
		Cursor cursor = null;
		try{
			String[] projection = {MedicationCategory._ID, MedicationCategory.NAME};
	    	
			cursor = myCR.query(MedicationCategory.CONTENT_URI, 
			              projection, null, null, null);
			return cursor;
		}catch(SQLiteException e){
			Log.i("SQLiteException when inserting movement speed", "error: " + e);
			return null;
		}
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
	public Uri insertRegistreredMedication(int medID, int listID){
		ContentValues values = new ContentValues();
		values.put(MedicationListLog.FK_MED_TYPE, medID);
		values.put(MedicationListLog.FK_MED_LOG, listID);
		
		Uri temp = myCR.insert(MedicationListLog.CONTENT_URI, values);
		
		return temp;
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
	
	   public void insertStepsToDB(int speed, int numOfReg){
	    	Cursor cursor = null;
	    	try{
		    	String selection = SensorSpec.NAME + " = \"" + "pedometer" + "\"";
				String[] projection = {SensorSpec._ID};
				    	
				cursor = myCR.query(SensorSpec.CONTENT_URI, 
				              projection, selection, null,
				    	        null);
				boolean temp = cursor.moveToFirst();
		    	if(temp == true){
		        	ContentValues values = new ContentValues();
		        	values.put(SensorLog.VALUE_AVERAGE, numOfReg);
		        	values.put(SensorLog.FK_SENSOR_SPEC, cursor.getString(0));
		        	values.put(SensorLog.CREATED_DATE, Utils.currentTimeInMillis()/1000);
		        	myCR.insert(SensorLog.CONTENT_URI, values);
	    	}else{
	    		Log.i("Did not find sensor", "pedometer");
	    	}
	    	}catch(SQLiteException e){
	    		Log.i("SQLiteException when inserting movement speed", "error: " + e);
	    	}finally{
	    		if(cursor != null){
	    			cursor.close();
	    			cursor = null;
	    		}
	    	}
	    }
	
    public void insertNumberOfStepsToDB(int speed, int numOfReg){
    	Cursor cursor = null;
    	try{
	    	String selection = SensorSpec.NAME + " = \"" + "pedometer" + "\"";
			String[] projection = {SensorSpec._ID};
			    	
			cursor = myCR.query(SensorSpec.CONTENT_URI, 
			              projection, selection, null,
			    	        null);
			boolean temp = cursor.moveToFirst();
	    	if(temp == true){
	        	ContentValues values = new ContentValues();
	        	values.put(SensorLog.VALUE_AVERAGE, numOfReg);
	        	values.put(SensorLog.FK_SENSOR_SPEC, cursor.getString(0));
	        	values.put(SensorLog.CREATED_DATE, Utils.currentTimeInMillis()/1000);
	        	myCR.insert(SensorLog.CONTENT_URI, values);
    	}else{
    		Log.i("Did not find sensor", "pedometer");
    	}
    	}catch(SQLiteException e){
    		Log.i("SQLiteException when inserting movement speed", "error: " + e);
    	}finally{
    		if(cursor != null){
    			cursor.close();
    			cursor = null;
    		}
    	}
    }
    
    /**
     * Gets the values related to the sensor pedometer
     * @return Cursor 0 = Value
     */
    public Cursor getNumberOfSteps(){
    	Cursor cursor1 = null;
    	Cursor cursor = null;
    	try{
	        String selection = SensorSpec.NAME + " = \"" + "pedometer" + "\"";
	    	String[] projection = {SensorSpec._ID};
	    		    	
	    	cursor1 = myCR.query(SensorSpec.CONTENT_URI, 
	    		             projection, selection, null,
	    		    	       null);
	    	boolean temp;
	    	if(cursor1.moveToFirst() != false){
	    		temp = cursor1.moveToFirst();
		    	
		    	Log.i("Getting ID for pedometer", cursor1.getString(0));
		    		
		    	String selection2 = SensorLog.FK_SENSOR_SPEC + " = \"" + cursor1.getString(0) + "\"";
		    	String[] projection2 = {SensorLog.VALUE_AVERAGE};
		    	cursor = myCR.query(SensorLog.CONTENT_URI, 
	    	             projection2, selection2, null,
	    	    	       null);
		    	temp = cursor.moveToFirst();
		    	Log.i("Getting value for movementspeed", "movetofirst" + temp);
	    	}	    		    			    	
	    		
	    	return cursor;
    	}catch(SQLiteException e){
    		Log.i("SQLiteException when inserting movement speed", "error: " + e);
    		return null;
//    	}finally{
//    		if(cursor != null){
//    			cursor.close();
//    			cursor = null;
//    		}
//    		if(cursor1 != null){
//    			cursor1.close();
//    			cursor1 = null;
//    		}
    	}
    }
    
    /**
     * Returns the total risk calcualted for the user related to sensor data.
     * @return
     */
    public Cursor getUserSensorRiskData(){
		Cursor cursor = null;
		try{
			String[] projection = {UserTotalRisk.SENSOR_RISK};
	    	
			cursor = myCR.query(UserTotalRisk.CONTENT_URI, 
			              projection, null, null, null);
			return cursor;
		}catch(SQLiteException e){
			Log.i("SQLiteException when getting data for user sensor risk", "error: " + e);
			return null;
		}
    }
    
    /**
     * Returns the total risk calcualted for the user related to test data.
     * @return
     */
    public Cursor getUserTestRiskData(){
		Cursor cursor = null;
		try{
			String[] projection = {UserTotalRisk.TEST_RISK};
	    	
			cursor = myCR.query(UserTotalRisk.CONTENT_URI, 
			              projection, null, null, null);
			return cursor;
		}catch(SQLiteException e){
			Log.i("SQLiteException when getting data for user test risk", "error: " + e);
			return null;
		}
    }
    
    /**
     * Returns the total risk calcualted for the user related to medication data.
     * @return
     */
    public Cursor getUserMedicationRiskData(){
		Cursor cursor = null;
		try{
			String[] projection = {UserTotalRisk.MEDICATION_RISK};
	    	
			cursor = myCR.query(UserTotalRisk.CONTENT_URI, 
			              projection, null, null, null);
			return cursor;
		}catch(SQLiteException e){
			Log.i("SQLiteException when getting data for user medication risk", "error: " + e);
			return null;
		}
    }
    
    /**
     * Returns the total risk calcualted for the user related to survey data.
     * @return
     */
    public Cursor getUserSurveyRiskData(){
		Cursor cursor = null;
		try{
			String[] projection = {UserTotalRisk.SURVEY_RISK};
	    	
			cursor = myCR.query(UserTotalRisk.CONTENT_URI, 
			              projection, null, null, null);
			return cursor;
		}catch(SQLiteException e){
			Log.i("SQLiteException when getting data for user survey risk", "error: " + e);
			return null;
		}
    }
    
    public Uri insertUserRiskForTesting(){
    	//method to insert some values to present in other apps.
    	ContentValues values = new ContentValues();
		values.put(UserTotalRisk.SENSOR_RISK, 1);
		values.put(UserTotalRisk.MEDICATION_RISK, 1);
		values.put(UserTotalRisk.SURVEY_RISK, 2);
		values.put(UserTotalRisk.TEST_RISK, 3);
		
		Uri temp = myCR.insert(UserTotalRisk.CONTENT_URI, values);
		
		return temp;
    }
    
    public void oneTimeInsertSensor(){
    	Cursor cursor = null;
    	try{
        	String selection = SensorSpec.NAME + " = \"" + "pedometer" + "\"";
    		String[] projection = {SensorSpec._ID};
    		    	
    		cursor = myCR.query(SensorSpec.CONTENT_URI, 
    		              projection, selection, null,
    		    	        null);
    		boolean temp = cursor.moveToFirst();
        	if(temp != true){
    	    	ContentValues values = new ContentValues();
    	    	values.put(SensorSpec.NAME, "pedometer");
    	    	values.put(SensorSpec.ACCURACY, "50");
    	    	values.put(SensorSpec.OWNER_ID, "Mobile");
    	    	myCR.insert(SensorSpec.CONTENT_URI, values);
    	    	Log.i("pedoes", "inserted pedo");
        	}
    	}catch(SQLiteException e){
			Log.i("SQLiteException when getting data for user survey risk", "error: " + e);
    	}
    }
    
    public Uri insertTUGStandard(){
    	ContentValues values = new ContentValues();
    	values.put(MeasureStandards.MEASURE_TYPE, "WalkingSpeed");
    	values.put(MeasureStandards.DATA_TYPE, "int");
    	values.put(MeasureStandards.DATA_UNIT, "meter/second");
    	Uri temp = myCR.insert(MeasureStandards.CONTENT_URI, values);
    	
    	return temp;
    }
    
    public void insertTUGTestData(){
    	ContentValues values = new ContentValues();
    	values.put(TestSpec.NAME, "TUGTest");
    	values.put(TestSpec.OWNER_ID, "Mobile");
    	Uri temp = myCR.insert(TestSpec.CONTENT_URI, values);
		
    	String path = temp.getPath();
		String idStr = path.substring(path.lastIndexOf('/') + 1);
		int id = Integer.parseInt(idStr);
    	
		Uri temp2 = insertTUGStandard();
    	String path2 = temp2.getPath();
		String idStr2 = path2.substring(path2.lastIndexOf('/') + 1);
		int id2 = Integer.parseInt(idStr2);
		
    	values = new ContentValues();
    	values.put(TestMeasureSpec.FK_TEST, id);
    	values.put(TestMeasureSpec.FK_RISK_DEF, id2);    
    	temp = myCR.insert(TestMeasureSpec.CONTENT_URI, values);
    }
    
    public Uri insertTUGResults(int time){
    	Cursor cursor = null;
    	Cursor cursor2 = null;
    	Cursor cursor3 = null;
    	try{
	        String selection = MeasureStandards.MEASURE_TYPE + " = \"" + "WalkingSpeed" + "\"";
	    	String[] projection = {MeasureStandards._ID};
	    		    	
	    	cursor2 = myCR.query(MeasureStandards.CONTENT_URI, 
	    		             projection, selection, null,
	    		    	       null);
	    	boolean temp;
	    	if(cursor2.moveToFirst() != false){
	    		temp = cursor2.moveToLast();
		    	String selection2 = TestMeasureSpec.FK_RISK_DEF + " = \"" + cursor2.getString(0) + "\"";
		    	String[] projection2 = {TestMeasureSpec._ID};
		    	cursor = myCR.query(TestMeasureSpec.CONTENT_URI, 
	    	             projection2, selection2, null,
	    	    	       null);
	    	}
	    	String selection3 = TestSpec.NAME + " = \"" + "TUGTest" + "\"";
	    	String[] projection3 = {TestSpec._ID};
	    	cursor3 = myCR.query(TestSpec.CONTENT_URI, 
   	             projection3, selection3, null,
   	    	       null);
	    	int id = -1;
	    	if(temp = cursor3.moveToFirst()){
	    		ContentValues values = new ContentValues();
	    		values.put(TestLog.FK_TEST_SPEC, Integer.parseInt(cursor3.getString(0)));
	    		// here the risk can be set based on calculations
	    		Uri testLogUri = myCR.insert(TestLog.CONTENT_URI, values);
	    		
	        	String path = testLogUri.getPath();
	    		String idStr = path.substring(path.lastIndexOf('/') + 1);
	    		id = Integer.parseInt(idStr);
	    	}
	    	
	    	ContentValues values = new ContentValues();
	    	values.put(TestMeasureLog.VALUE, time);
	    	if(temp = cursor.moveToFirst()){
	    		values.put(TestMeasureLog.FK_MEASURE_SPEC, Integer.parseInt(cursor.getString(0)));
	    	}
	    	if(id != -1)
	    		values.put(TestMeasureLog.FK_TEST_LOG, id);
	    	
	    	return myCR.insert(TestMeasureLog.CONTENT_URI, values);
	    	
    	}catch(SQLiteException e){
    		Log.i("SQLiteException when getting Standards and Test IDs", "error: " + e);
    		return null;
    	}
    }
}
