package ntnu.master.nofall.database.survey;

import ntnu.master.nofall.provider.SurveyContract.SurveyLog;
import ntnu.master.nofall.provider.SurveyContract.SurveySpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyLogTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + SurveyLog.TABLE_NAME
		      + "(" 
		      + SurveyLog._ID   + " integer primary key autoincrement, " 
		      + SurveyLog.CALCULATED_RISK + " integer, "  
		      + SurveyLog.DATE + " date, " 
		      + SurveyLog.FK_SURVEY + " integer, "
		      + SurveyLog.CREATED_DATE + " integer," 
		      + SurveyLog.MODIFIED_DATE + " integer,"
		      + " FOREIGN KEY ("+SurveyLog.FK_SURVEY+") REFERENCES "+SurveySpec.TABLE_NAME+" ("+SurveySpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + SurveyLog.TABLE_NAME);
		onCreate(database);
	}
}