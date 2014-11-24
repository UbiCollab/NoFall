package ntnu.master.nofall.platform.database.sensor;

import ntnu.master.nofall.platform.provider.SensorContract.SensorLog;
import ntnu.master.nofall.platform.provider.SensorContract.SensorLogItem;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorLogItemTable {
	// Database creation SQL statement
		private static final String DATABASE_CREATE = "create table " 
		      + SensorLogItem.TABLE_NAME
		      + "(" 
		      + SensorLogItem._ID   + " integer primary key autoincrement, " 
		      + SensorLogItem.VALUE + " integer, " 
		      + SensorLogItem.TIME + " integer, "
		      + SensorLogItem.CREATED_DATE + " integer," 
		      + SensorLogItem.MODIFIED_DATE + " integer,"
		      + SensorLogItem.FK_SENSOR_LOG + " integer, "
		      + " FOREIGN KEY ("+SensorLogItem.FK_SENSOR_LOG+") REFERENCES "+SensorLog.TABLE_NAME+" ("+SensorLog._ID+") ON DELETE CASCADE );";

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
