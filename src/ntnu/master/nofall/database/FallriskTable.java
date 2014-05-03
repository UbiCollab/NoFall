package ntnu.master.nofall.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FallriskTable {
	// Database table
	public static final String TABLE_FALL_RISK = "tblFallRisk";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_RISK = "risk";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_FALL_RISK
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_RISK + " text not null "  + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_FALL_RISK);
		onCreate(database);
	}
}
