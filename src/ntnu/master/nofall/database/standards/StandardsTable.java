package ntnu.master.nofall.database.standards;

import ntnu.master.nofall.provider.StandardContract.Standards;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StandardsTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + Standards.TABLE_NAME
		      + "(" 
		      + Standards._ID   + " integer primary key autoincrement, " 
		      + Standards.MEASURE_TYPE + " text not null, "
		      + Standards.DATA_TYPE + " text not null, "
		      + Standards.DATA_UNIT + " text not null, "
		      + Standards.CREATED_DATE + " integer," 
		      + Standards.MODIFIED_DATE + " integer"+ ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + Standards.TABLE_NAME);
		onCreate(database);
	}
}
