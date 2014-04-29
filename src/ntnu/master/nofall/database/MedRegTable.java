package ntnu.master.nofall.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedRegTable {
	// Database table
	public static final String TABLE_MED_REG = "tblMedReg";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NUMBER_OF = "numberOf";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MED_REG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
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
		Log.w(UserTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MED_REG);
		onCreate(database);
	}
}
