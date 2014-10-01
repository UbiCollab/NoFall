package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.TestContract.TestLog;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureLog;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestMeasureLogTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TestMeasureLog.TABLE_NAME + "(" 
			+ TestMeasureLog._ID + " integer primary key autoincrement, " 
			+ TestMeasureLog.VALUE + " integer, " 
			+ TestMeasureLog.CREATED_DATE + " integer," 
		    + TestMeasureLog.MODIFIED_DATE + " integer,"
			+ TestMeasureLog.FK_MEASURE_SPEC + " integer, "
			+ TestMeasureLog.FK_TEST_LOG + " integer, " 
			+ " FOREIGN KEY ("+ TestMeasureLog.FK_MEASURE_SPEC + ") REFERENCES " + TestMeasureSpec.TABLE_NAME+ " (" + TestMeasureSpec._ID + ") ON DELETE CASCADE"
			+ " FOREIGN KEY (" + TestMeasureLog.FK_TEST_LOG + ") REFERENCES "+ TestLog.TABLE_NAME + " (" + TestLog._ID+ ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestMeasureLog.TABLE_NAME);
		onCreate(database);
	}
}
