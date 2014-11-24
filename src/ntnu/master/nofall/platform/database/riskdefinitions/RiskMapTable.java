package ntnu.master.nofall.platform.database.riskdefinitions;

import ntnu.master.nofall.platform.provider.RiskDefContract.RiskDefinition;
import ntnu.master.nofall.platform.provider.RiskDefContract.RefRiskLevels;
import ntnu.master.nofall.platform.provider.RiskDefContract.RiskMap;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RiskMapTable {
		// Database creation SQL statement
		  private static final String DATABASE_CREATE = "create table " 
			      + RiskMap.TABLE_NAME
			      + "(" 
			      + RiskMap._ID   + " integer primary key autoincrement, " 
			      + RiskMap.RANGE_FROM + " integer, "
			      + RiskMap.RANGE_TO + " integer, "
			      + RiskMap.CREATED_DATE + " integer," 
			      + RiskMap.MODIFIED_DATE + " integer,"
			      + RiskMap.FK_RISK_LEVELS + " integer, "
			      + RiskMap.FK_RISK_DEFINITIONS + " integer, "
			      + " FOREIGN KEY ("+RiskMap.FK_RISK_LEVELS+") " +
			      		"REFERENCES "+RefRiskLevels.TABLE_NAME+" ("+RefRiskLevels._ID+") ON DELETE CASCADE"
			      + " FOREIGN KEY ("+RiskMap.FK_RISK_DEFINITIONS+") " +
			      		"REFERENCES "+RiskDefinition.TABLE_NAME+" ("+RiskDefinition._ID+") ON DELETE CASCADE );";

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
			database.execSQL("DROP TABLE IF EXISTS " + RiskMap.TABLE_NAME);
			onCreate(database);
		}
}
