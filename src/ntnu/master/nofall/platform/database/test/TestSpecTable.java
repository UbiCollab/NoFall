package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.TestContract.TestSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestSpecTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TestSpec.TABLE_NAME + "(" 
			+ TestSpec._ID + " integer primary key autoincrement, " 
			+ TestSpec.OWNER_ID + " text not null, " 
			+ TestSpec.NAME + " text not null, " 
			+ TestSpec.CREATED_DATE + " integer," 
		    + TestSpec.MODIFIED_DATE + " integer" + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestSpec.TABLE_NAME);
		onCreate(database);
	}
}
