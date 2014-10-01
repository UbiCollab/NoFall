package ntnu.master.nofall.platform.database.sensor;

import ntnu.master.nofall.platform.provider.SensorContract.SensorLog;
import ntnu.master.nofall.platform.provider.SensorContract.SensorSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorLogTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
	      + SensorLog.TABLE_NAME
	      + "(" 
	      + SensorLog._ID   + " integer primary key autoincrement, " 
	      + SensorLog.DATE + " date, "  
	      + SensorLog.VALUE + " integer, " 
	      + SensorLog.VALUE_AVERAGE + " integer, "
	      + SensorLog.NUM_OF_REG + " integer, "
	      + SensorLog.LAST_READING + " integer, "
	      + SensorLog.CREATED_DATE + " integer," 
	      + SensorLog.MODIFIED_DATE + " integer,"
	      + SensorLog.FK_SENSOR + " integer, "
	      + " FOREIGN KEY ("+SensorLog.FK_SENSOR+") REFERENCES "+SensorSpec.TABLE_NAME+" ("+SensorSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + SensorLog.TABLE_NAME);
		onCreate(database);
	}
}
