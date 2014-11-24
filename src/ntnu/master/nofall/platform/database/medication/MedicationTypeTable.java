package ntnu.master.nofall.platform.database.medication;

import ntnu.master.nofall.platform.provider.MedicationContract.MedicationCategory;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationType;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicationTypeTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MedicationType.TABLE_NAME
		      + "(" 
		      + MedicationType._ID   + " integer primary key autoincrement, " 
		      + MedicationType.NAME + " text not null, " 
		      + MedicationType.FK_MEDICATION_CATEGORY + " integer, "
		      + MedicationType.CREATED_DATE + " integer," 
		      + MedicationType.MODIFIED_DATE + " integer,"
		      + " FOREIGN KEY ("+MedicationType.FK_MEDICATION_CATEGORY+") " +
		      		"REFERENCES "+MedicationCategory.TABLE_NAME+" ("+MedicationCategory._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + MedicationType.TABLE_NAME);
		onCreate(database);
	}
}
