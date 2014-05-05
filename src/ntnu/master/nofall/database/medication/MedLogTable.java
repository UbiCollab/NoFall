package ntnu.master.nofall.database.medication;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedLogTable {
	// Database table
	public static final String TABLE_MED_LOG = "tblMedLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NUMBER_OF = "numberOf";
	public static final String COLUMN_DATE = "date";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MED_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_DATE + " date, " 
		      + COLUMN_NUMBER_OF + " integer "  + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MED_LOG);
		onCreate(database);
	}
}
