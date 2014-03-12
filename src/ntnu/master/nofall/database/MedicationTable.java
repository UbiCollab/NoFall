package ntnu.master.nofall.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicationTable {
	// Database table
	public static final String TABLE_MED = "med";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TYPE = "age";
	public static final String COLUMN_DESC = "desc";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MED
		      + "(" 
		      + COLUMN_ID + " integer primary key autoincrement, " 
		      + COLUMN_NAME + " text not null," 
		      + COLUMN_DESC + " text not null,"
		      + COLUMN_TYPE
		      + " text not null" 
		      + ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(UserTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MED);
		onCreate(database);
	}
}
