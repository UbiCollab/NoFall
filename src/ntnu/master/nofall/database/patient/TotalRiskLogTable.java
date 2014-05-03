package ntnu.master.nofall.database.patient;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class TotalRiskLogTable {
	// Database table
	public static final String TABLE_TOTAL_RISK = "tblTotalRiskLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MED_RISK = "medRisk";
	public static final String COLUMN_TEST_RISK = "testRisk";
	public static final String COLUMN_SURVEY_RISK = "surveyRisk";
	public static final String COLUMN_SENSOR_RISK = "sensorRisk";
	public static final String COLUMN_DATE = "date";


	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_TOTAL_RISK
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_MED_RISK + " integer, " 
		      + COLUMN_TEST_RISK + " integer, "
		      + COLUMN_SURVEY_RISK + " integer, "
		      + COLUMN_DATE + " date, "
		      + COLUMN_SENSOR_RISK  + " integer " + ");";

	public static void onCreate(SQLiteDatabase database) {
		try
		{
			database.execSQL(DATABASE_CREATE);
		}
		catch(Exception e)
		{
			Log.w("SQL ERROR", e.toString());
		}
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w("Throwing DB", "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TOTAL_RISK);
		onCreate(database);
	}
}
