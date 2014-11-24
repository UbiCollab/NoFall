package ntnu.master.nofall.platform.database.riskdefinitions;

import ntnu.master.nofall.platform.provider.RiskDefContract.RefRiskLevels;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RefRiskLevelsTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + RefRiskLevels.TABLE_NAME
		      + "(" 
		      + RefRiskLevels._ID   + " integer primary key autoincrement, "
		      + RefRiskLevels.NAME + " text not null, "
		      + RefRiskLevels.DESCRIPTION + " text, " 
		      + RefRiskLevels.CREATED_DATE + " integer," 
		      + RefRiskLevels.MODIFIED_DATE + " integer"+ ");";
	
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
			database.execSQL("DROP TABLE IF EXISTS " + RefRiskLevels.TABLE_NAME);
			onCreate(database);
		}
}
