package ntnu.master.nofall.database.sensor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorSpecTable {
	// Database table
	public static final String TABLE_SENSOR_SPEC = "tblSensorSpec";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_OWNER_ID = "ownerId";
	public static final String COLUMN_ACCURACY = "accuracy";
	public static final String COLUMN_SENSOR_ATTACHMENT = "sensorAttachment";
	public static final String COLUMN_SENSOR_PLACEMENT = "sensorPlacement";

	// foreing key: Standards
	public static final String COLUMN_FK_STANDARDS = "fkStandards";
	public static final String TABLE_FK_STANDARDS = "tblStandards";
	public static final String COLUMN_FK_STANDARDS_ID = "_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SENSOR_SPEC + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_OWNER_ID
			+ " text not null, " + COLUMN_SENSOR_ATTACHMENT
			+ " text not null, " + COLUMN_SENSOR_PLACEMENT + " text not null, "
			+ COLUMN_ACCURACY + " integer, " + COLUMN_NAME + " text not null, "
			+ COLUMN_FK_STANDARDS + " integer, " + " FOREIGN KEY ("
			+ COLUMN_FK_STANDARDS + ") REFERENCES " + TABLE_FK_STANDARDS + " ("
			+ COLUMN_FK_STANDARDS_ID + ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_SPEC);
		onCreate(database);
	}
}
