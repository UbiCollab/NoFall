package ntnu.master.nofall.database.standards;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StandardsTable {
	// Database table
	public static final String TABLE_STANDARDS = "tblStandards";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MEASURE_TYPE = "measureType";
	public static final String COLUMN_DATA_TYPE = "dataType";
	public static final String COLUMN_DATA_UNIT = "dataUnit";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_STANDARDS
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_MEASURE_TYPE + " text not null, "
		      + COLUMN_DATA_TYPE + " text not null, "
		      + COLUMN_DATA_UNIT + " text not null "  + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_STANDARDS);
		onCreate(database);
	}
}
