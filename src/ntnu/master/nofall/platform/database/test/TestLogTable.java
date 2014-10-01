package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.TestContract.TestLog;
import ntnu.master.nofall.platform.provider.TestContract.TestSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestLogTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TestLog.TABLE_NAME
		      + "(" 
		      + TestLog._ID   + " integer primary key autoincrement, " 
		      + TestLog.DATE + " date, "
		      + TestLog.TOTAL_RISK + " integer," 
		      + TestLog.CREATED_DATE + " integer," 
		      + TestLog.MODIFIED_DATE + " integer,"
		      + TestLog.FK_TEST + " integer, "
		      + " FOREIGN KEY ("+TestLog.FK_TEST+") REFERENCES "+TestSpec.TABLE_NAME+" ("+TestSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestLog.TABLE_NAME);
		onCreate(database);
	}
}
