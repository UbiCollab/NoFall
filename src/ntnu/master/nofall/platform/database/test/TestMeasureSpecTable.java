package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.StandardContract.Standards;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestMeasureSpecTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TestMeasureSpec.TABLE_NAME + "(" 
			+ TestMeasureSpec._ID + " integer primary key autoincrement, "
			+ TestMeasureSpec.CREATED_DATE + " integer," 
		    + TestMeasureSpec.MODIFIED_DATE + " integer,"
			+ TestMeasureSpec.FK_TEST + " integer, " 
			+ TestMeasureSpec.FK_STANDARDS + " integer, "
			+ " FOREIGN KEY (" + TestMeasureSpec.FK_TEST + ") REFERENCES " + TestSpec.TABLE_NAME + " (" + TestSpec._ID + ") ON DELETE CASCADE"
			+ " FOREIGN KEY (" + TestMeasureSpec.FK_STANDARDS + ") REFERENCES " + Standards.TABLE_NAME + " (" + Standards._ID + ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestMeasureSpec.TABLE_NAME);
		onCreate(database);
	}
}
