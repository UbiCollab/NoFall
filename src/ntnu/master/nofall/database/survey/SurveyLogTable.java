package ntnu.master.nofall.database.survey;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyLogTable {
	// Database table
	public static final String TABLE_SURVEY_LOG = "tblSurveyLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_CALCULATED_RISK = "calculatedRisk";
	
	// foreign keys
	public static final String COLUMN_FK_SURVEY = "fkSurvey";
	public static final String TABLE_FK_SURVEY = "tblSurveySpec";
	public static final String COLUMN_FK_SURVEY_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SURVEY_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_CALCULATED_RISK + " integer, "  
		      + COLUMN_DATE + " date, " 
		      + COLUMN_FK_SURVEY + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_SURVEY+") REFERENCES "+TABLE_FK_SURVEY+" ("+COLUMN_FK_SURVEY_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY_LOG);
		onCreate(database);
	}
}