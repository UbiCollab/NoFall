package ntnu.master.nofall.database.medication;

import ntnu.master.nofall.contentprovider.provider.Medication.MedicationCategorySpec;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationSpec;
import ntnu.master.nofall.contentprovider.provider.Standard.StandardsRiskMap;
import ntnu.master.nofall.contentprovider.provider.Users.User;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicationSpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MedicationSpec.TABLE_NAME
		      + "(" 
		      + MedicationSpec._ID   + " integer primary key autoincrement, " 
		      + MedicationSpec.NAME + " text not null, " 
		      + MedicationSpec.FK_RISK_STAND_MAP + " integer, "
		      + MedicationSpec.FK_MEDICATION_CATEGORY + " integer, "
		      + MedicationSpec.CREATED_DATE + " integer," 
		      + MedicationSpec.MODIFIED_DATE + " integer,"
		      + " FOREIGN KEY ("+MedicationSpec.FK_RISK_STAND_MAP+") REFERENCES "+StandardsRiskMap.TABLE_NAME+" ("+StandardsRiskMap._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+MedicationSpec.FK_MEDICATION_CATEGORY+") REFERENCES "+MedicationCategorySpec.TABLE_NAME+" ("+MedicationCategorySpec._ID+") ON DELETE CASCADE );";

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
