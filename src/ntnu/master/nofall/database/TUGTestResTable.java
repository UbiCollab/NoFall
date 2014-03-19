package ntnu.master.nofall.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TUGTestResTable {
	// Database table
	public static final String TABLE_TUGRES = "TUGres";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_DATE = "date";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_TUGRES
		      + "(" 
		      + COLUMN_ID + " integer primary key autoincrement, " 
		      + COLUMN_DATE + " text not null," 
		      + COLUMN_TIME
		      + " text not null" 
		      + ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TUGTestResTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TUGRES);
		onCreate(database);
	}
}
