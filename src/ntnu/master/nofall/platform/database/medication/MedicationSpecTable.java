package ntnu.master.nofall.platform.database.medication;

import ntnu.master.nofall.platform.provider.MedicationContract.MedicationSpec;
import ntnu.master.nofall.platform.provider.RiskDefContract.RiskDefinition;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicationSpecTable {
	// Database creation SQL statement
		  private static final String DATABASE_CREATE = "create table " 
			      + MedicationSpec.TABLE_NAME
			      + "(" 
			      + MedicationSpec._ID   + " integer primary key autoincrement, "
			      + MedicationSpec.NAME + " text not null, "
			      + MedicationSpec.DESCRIPTION + " text, "
			      + MedicationSpec.FK_RISK_DEF + " integer, "
			      + MedicationSpec.CREATED_DATE + " integer," 
			      + MedicationSpec.MODIFIED_DATE + " integer,"
			      + " FOREIGN KEY ("+MedicationSpec.FK_RISK_DEF+") " +
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
			database.execSQL("DROP TABLE IF EXISTS " + MedicationSpec.TABLE_NAME);
			onCreate(database);
		}
}
