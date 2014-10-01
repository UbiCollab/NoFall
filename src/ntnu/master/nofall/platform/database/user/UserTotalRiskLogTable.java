package ntnu.master.nofall.platform.database.user;

import ntnu.master.nofall.platform.provider.UsersContract.UserTotalRisk;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class UserTotalRiskLogTable {

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + UserTotalRisk.TABLE_NAME
		      + "(" 
		      + UserTotalRisk._ID   + " integer primary key autoincrement, " 
		      + UserTotalRisk.MEDICATION_RISK + " integer, " 
		      + UserTotalRisk.TEST_RISK + " integer, "
		      + UserTotalRisk.SURVEY_RISK + " integer, "
		      + UserTotalRisk.DATE + " date, "
		      + UserTotalRisk.SENSOR_RISK  + " integer, " 
		      + UserTotalRisk.MODIFIED_DATE + " integer,"
		      + UserTotalRisk.CREATED_DATE + " integer" +  ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + UserTotalRisk.TABLE_NAME);
		onCreate(database);
	}
}
