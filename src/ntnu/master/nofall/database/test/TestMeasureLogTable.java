package ntnu.master.nofall.database.test;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestMeasureLogTable {
	// Database table
	public static final String TABLE_TEST_MEASURE_LOG = "tblTestMeasureLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_VALUE = "value";

	// foreign keys: Test Measure Spec
	public static final String COLUMN_FK_MEASURE_SPEC = "fkTestMeasureSpec";
	public static final String TABLE_FK_MEASURE_SPEC = "tblTestMeasureSpec";
	public static final String COLUMN_FK_MEASURE_SPEC_ID = "_id";

	// foreing key: Test Log Id
	public static final String COLUMN_FK_TEST_LOG = "fkTestLog";
	public static final String TABLE_FK_TEST_LOG = "tblTestLog";
	public static final String COLUMN_FK_TEST_LOG_ID = "_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TEST_MEASURE_LOG + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_VALUE
			+ " integer, " + COLUMN_FK_MEASURE_SPEC + " integer, "
			+ COLUMN_FK_TEST_LOG + " integer, " + " FOREIGN KEY ("
			+ COLUMN_FK_MEASURE_SPEC + ") REFERENCES " + TABLE_FK_MEASURE_SPEC
			+ " (" + COLUMN_FK_MEASURE_SPEC_ID + ") ON DELETE CASCADE"
			+ " FOREIGN KEY (" + COLUMN_FK_TEST_LOG + ") REFERENCES "
			+ TABLE_FK_TEST_LOG + " (" + COLUMN_FK_TEST_LOG_ID
			+ ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_MEASURE_LOG);
		onCreate(database);
	}
}
