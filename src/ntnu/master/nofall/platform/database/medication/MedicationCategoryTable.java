package ntnu.master.nofall.platform.database.medication;

import ntnu.master.nofall.platform.provider.MedicationContract.MedicationCategory;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicationCategoryTable {	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MedicationCategory.TABLE_NAME
		      + "(" 
		      + MedicationCategory._ID   + " integer primary key autoincrement, "
		      + MedicationCategory.NAME + " text not null, "
		      + MedicationCategory.CREATED_DATE + " integer," 
		      + MedicationCategory.MODIFIED_DATE + " integer" + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + MedicationCategory.TABLE_NAME);
		onCreate(database);
	}
}
