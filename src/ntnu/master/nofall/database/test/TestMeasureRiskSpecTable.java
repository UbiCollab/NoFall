package ntnu.master.nofall.database.test;

import ntnu.master.nofall.contentprovider.provider.Standard.StandardsRiskMap;
import ntnu.master.nofall.contentprovider.provider.Test.TestMeasureRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Test.TestMeasureSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestMeasureRiskSpecTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TestMeasureRiskSpec.TABLE_NAME + "(" 
			+ TestMeasureRiskSpec._ID + " integer primary key autoincrement, " 
			+ TestMeasureRiskSpec.CREATED_DATE + " integer," 
		    + TestMeasureRiskSpec.MODIFIED_DATE + " integer,"
			+ TestMeasureRiskSpec.FK_MEASURE_SPEC + " integer, " 
			+ TestMeasureRiskSpec.FK_RISK_STAND_MAP + " integer, "
			+ " FOREIGN KEY ("+ TestMeasureRiskSpec.FK_MEASURE_SPEC + ") REFERENCES "+ TestMeasureSpec.TABLE_NAME+ " (" + TestMeasureSpec._ID+ ") ON DELETE CASCADE" 
			+ " FOREIGN KEY ("+ TestMeasureRiskSpec.FK_RISK_STAND_MAP + ") REFERENCES "+ StandardsRiskMap.TABLE_NAME + " (" + StandardsRiskMap._ID+ ") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestMeasureRiskSpec.TABLE_NAME);
		onCreate(database);
	}
}