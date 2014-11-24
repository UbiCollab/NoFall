package ntnu.master.nofall.platform.database.sensor;

import ntnu.master.nofall.platform.provider.RiskDefContract.RiskDefinition;
import ntnu.master.nofall.platform.provider.SensorContract.SensorSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorSpecTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ SensorSpec.TABLE_NAME + "(" 
			+ SensorSpec._ID + " integer primary key autoincrement, " 
			+ SensorSpec.OWNER_ID + " text not null, " 			
			+ SensorSpec.ACCURACY + " integer, " 
			+ SensorSpec.NAME + " text not null, "
			+ SensorSpec.CREATED_DATE + " integer," 
		    + SensorSpec.MODIFIED_DATE + " integer,"
			+ SensorSpec.FK_RISK_DEF + " integer, " 
			+ " FOREIGN KEY ("+ SensorSpec.FK_RISK_DEF + ") REFERENCES " + RiskDefinition.TABLE_NAME + " ("+ RiskDefinition._ID + ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + SensorSpec.TABLE_NAME);
		onCreate(database);
	}
}
