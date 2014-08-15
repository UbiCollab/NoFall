package ntnu.master.nofall.database.test;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestMeasureSpecTable {
	// Database table
	public static final String TABLE_TEST_MEASURE_SPEC = "tblTestMeasureSpec";
	public static final String COLUMN_ID = "_id";

	// foreign keys: Test Spec
	public static final String COLUMN_FK_TEST = "fkTest";
	public static final String TABLE_FK_TEST = "tblTestSpec";
	public static final String COLUMN_FK_TEST_ID = "_id";

	// foreing key: Standards
	public static final String COLUMN_FK_STANDARDS = "fkStandards";
	public static final String TABLE_FK_STANDARDS = "tblStandards";
	public static final String COLUMN_FK_STANDARDS_ID = "_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TEST_MEASURE_SPEC + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FK_TEST
			+ " integer, " + COLUMN_FK_STANDARDS + " integer, "
			+ " FOREIGN KEY (" + COLUMN_FK_TEST + ") REFERENCES "
			+ TABLE_FK_TEST + " (" + COLUMN_FK_TEST_ID + ") ON DELETE CASCADE"
			+ " FOREIGN KEY (" + COLUMN_FK_STANDARDS + ") REFERENCES "
			+ TABLE_FK_STANDARDS + " (" + COLUMN_FK_STANDARDS_ID
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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_MEASURE_SPEC);
		onCreate(database);
	}
}
