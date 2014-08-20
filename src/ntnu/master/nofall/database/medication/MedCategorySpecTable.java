package ntnu.master.nofall.database.medication;

import ntnu.master.nofall.contentprovider.provider.Medication.MedicationCategorySpec;
import ntnu.master.nofall.contentprovider.provider.Users.User;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedCategorySpecTable {	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MedicationCategorySpec.TABLE_NAME
		      + "(" 
		      + MedicationCategorySpec._ID   + " integer primary key autoincrement, "
		      + MedicationCategorySpec.OWNER_ID + " text not null, "
		      + MedicationCategorySpec.NAME + " text not null, "  
		      + MedicationCategorySpec.CREATED_DATE + " integer," 
		      + MedicationCategorySpec.MODIFIED_DATE + " integer" + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + MedicationCategorySpec.TABLE_NAME);
		onCreate(database);
	}
}
