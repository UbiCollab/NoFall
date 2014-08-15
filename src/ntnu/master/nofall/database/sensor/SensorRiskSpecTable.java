package ntnu.master.nofall.database.sensor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorRiskSpecTable {
	// Database table
	public static final String TABLE_SENSOR_RISK_SPEC = "tblSensorRiskSpec";
	public static final String COLUMN_ID = "_id";

	// foreign key: Risk Standard Map
	public static final String COLUMN_FK_RISK_STAND_MAP = "fkRiskStandMap";
	public static final String COLUMN_FK_TBL_RISK_STAND_MAP = "tblRiskStandMap";
	public static final String COLUMN_FK_ID_RISK_STAND_MAP = "_id";

	// foreign keys: Sensor
	public static final String COLUMN_FK_SENSOR = "fkSensor";
	public static final String TABLE_FK_SENSOR = "tblSensorSpec";
	public static final String COLUMN_FK_SENSOR_ID = "_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SENSOR_RISK_SPEC + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FK_RISK_STAND_MAP
			+ " text not null, " + COLUMN_FK_SENSOR + " integer, "
			+ " FOREIGN KEY (" + COLUMN_FK_RISK_STAND_MAP + ") REFERENCES "
			+ COLUMN_FK_TBL_RISK_STAND_MAP + " (" + COLUMN_FK_ID_RISK_STAND_MAP
			+ ") ON DELETE CASCADE" + " FOREIGN KEY (" + COLUMN_FK_SENSOR
			+ ") REFERENCES " + TABLE_FK_SENSOR + " (" + COLUMN_FK_SENSOR_ID
			+ ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_RISK_SPEC);
		onCreate(database);
	}
}
