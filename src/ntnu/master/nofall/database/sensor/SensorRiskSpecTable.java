package ntnu.master.nofall.database.sensor;

import ntnu.master.nofall.contentprovider.provider.Sensor.SensorRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Sensor.SensorSpec;
import ntnu.master.nofall.contentprovider.provider.Standard.StandardsRiskMap;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorRiskSpecTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ SensorRiskSpec.TABLE_NAME + "(" 
			+ SensorRiskSpec._ID + " integer primary key autoincrement, " 
			+ SensorRiskSpec.FK_RISK_STAND_MAP + " text not null, " 
			+ SensorRiskSpec.FK_SENSOR + " integer, "
			+ SensorRiskSpec.CREATED_DATE + " integer," 
		    + SensorRiskSpec.MODIFIED_DATE + " integer,"
			+ " FOREIGN KEY (" + SensorRiskSpec.FK_RISK_STAND_MAP + ") REFERENCES "
			+ StandardsRiskMap.TABLE_NAME + " (" + StandardsRiskMap._ID + ") ON DELETE CASCADE" 
			+ " FOREIGN KEY (" + SensorRiskSpec.FK_SENSOR
			+ ") REFERENCES " + SensorSpec.TABLE_NAME + " (" + SensorSpec._ID + ") ON DELETE CASCADE );";

	public static void onCreate(SQLiteDatabase database) {
		try {
			database.execSQL(DATABASE_CREATE);
		} catch (Exception e) {
			Log.w("SQL ERROR", e.toString());
		}
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w("Throwing DB", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + SensorRiskSpec.TABLE_NAME);
		onCreate(database);
	}
}
