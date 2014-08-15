package ntnu.master.nofall.database.test;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestMeasureRiskSpecTable {
	// Database table
	public static final String TABLE_TEST_MEASURE_RISK_SPEC = "tblTestMeasureRiskSpec";
	public static final String COLUMN_ID = "_id";

	// foreign keys: Test Measure Spec
	public static final String COLUMN_FK_MEASURE_SPEC = "fkTestMeasureSpec";
	public static final String TABLE_FK_MEASURE_SPEC = "tblTestMeasureSpec";
	public static final String COLUMN_FK_MEASURE_SPEC_ID = "_id";

	// foreign key: Risk Standard Map
	public static final String COLUMN_FK_RISK_STAND_MAP = "fkRiskStandMap";
	public static final String COLUMN_FK_TBL_RISK_STAND_MAP = "tblRiskStandMap";
	public static final String COLUMN_FK_ID_RISK_STAND_MAP = "_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TEST_MEASURE_RISK_SPEC + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FK_MEASURE_SPEC
			+ " integer, " + COLUMN_FK_RISK_STAND_MAP + " integer, "
			+ " FOREIGN KEY (" + COLUMN_FK_MEASURE_SPEC + ") REFERENCES "
			+ TABLE_FK_MEASURE_SPEC + " (" + COLUMN_FK_MEASURE_SPEC_ID
			+ ") ON DELETE CASCADE" + " FOREIGN KEY ("
			+ COLUMN_FK_RISK_STAND_MAP + ") REFERENCES "
			+ COLUMN_FK_TBL_RISK_STAND_MAP + " (" + COLUMN_FK_ID_RISK_STAND_MAP
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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_MEASURE_RISK_SPEC);
		onCreate(database);
	}
}
