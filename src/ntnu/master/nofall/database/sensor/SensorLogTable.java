package ntnu.master.nofall.database.sensor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SensorLogTable {
	// Database table
	public static final String TABLE_SENSOR_LOG = "tblSensorLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_VALUE = "value";
	public static final String COLUMN_VALUE_AVERAGE = "valueAverage";
	public static final String COLUMN_NUM_OF_REG = "numOfReg";
	public static final String COLUMN_LAST_READING = "lastReading";
	// foreign keys
		public static final String COLUMN_FK_SENSOR = "fkSensor";
		public static final String TABLE_FK_SENSOR = "tblSensorSpec";
		public static final String COLUMN_FK_SENSOR_ID = "_id";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SENSOR_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_DATE + " date, "  
		      + COLUMN_VALUE + " integer, " 
		      + COLUMN_VALUE_AVERAGE + " integer, "
		      + COLUMN_NUM_OF_REG + " integer, "
		      + COLUMN_LAST_READING + " integer, "
		      + COLUMN_FK_SENSOR + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_SENSOR+") REFERENCES "+TABLE_FK_SENSOR+" ("+COLUMN_FK_SENSOR_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_LOG);
		onCreate(database);
	}
}
