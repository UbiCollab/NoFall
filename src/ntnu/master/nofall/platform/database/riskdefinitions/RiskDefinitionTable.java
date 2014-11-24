package ntnu.master.nofall.platform.database.riskdefinitions;

import ntnu.master.nofall.platform.provider.RiskDefContract.MeasureStandards;
import ntnu.master.nofall.platform.provider.RiskDefContract.RiskDefinition;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RiskDefinitionTable {
		// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + RiskDefinition.TABLE_NAME
		      + "(" 
		      + RiskDefinition._ID   + " integer primary key autoincrement, "
		      + RiskDefinition.NAME + " text not null, "
		      + RiskDefinition.DESCRIPTION + " text, "  
		      + RiskDefinition.CREATED_DATE + " integer," 
		      + RiskDefinition.MODIFIED_DATE + " integer,"
		      + RiskDefinition.FK_MEAS_STAND + " integer, "	     
		      + " FOREIGN KEY ("+RiskDefinition.FK_MEAS_STAND+") " +
		      		"REFERENCES "+MeasureStandards.TABLE_NAME+" ("+MeasureStandards._ID+") ON DELETE CASCADE );";
		
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
			database.execSQL("DROP TABLE IF EXISTS " + RiskDefinition.TABLE_NAME);
			onCreate(database);
		}
}
