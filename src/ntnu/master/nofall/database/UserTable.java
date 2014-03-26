package ntnu.master.nofall.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserTable {
	// Database table
	public static final String TABLE_USER = "user";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_AGE = "age";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_USER
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_NAME + " text not null, " 
		      + COLUMN_AGE  + " text not null " + ");";

	public static void onCreate(SQLiteDatabase database) {
		try
		{
			database.execSQL(DATABASE_CREATE);
		}
		catch(Exception e)
		{
			Log.w("SQL ERROR", e);
		}
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(UserTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		onCreate(database);
	}
}
