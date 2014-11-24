package ntnu.master.nofall.platform.database.riskdefinitions;

import ntnu.master.nofall.platform.provider.RiskDefContract.MeasureStandards;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MeasureStandardsTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MeasureStandards.TABLE_NAME
		      + "(" 
		      + MeasureStandards._ID   + " integer primary key autoincrement, " 
		      + MeasureStandards.MEASURE_TYPE + " text not null, "
		      + MeasureStandards.DATA_TYPE + " text not null, "
		      + MeasureStandards.DATA_UNIT + " text not null, "
		      + MeasureStandards.CREATED_DATE + " integer," 
		      + MeasureStandards.MODIFIED_DATE + " integer"+ ");";

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
		Log.w("Throwing DB", "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + MeasureStandards.TABLE_NAME);
		onCreate(database);
	}
}
