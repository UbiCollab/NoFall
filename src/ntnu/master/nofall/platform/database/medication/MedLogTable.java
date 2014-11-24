package ntnu.master.nofall.platform.database.medication;

import ntnu.master.nofall.platform.provider.MedicationContract.MedicationLog;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedLogTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MedicationLog.TABLE_NAME
		      + "(" 
		      + MedicationLog._ID   + " integer primary key autoincrement, " 
		      + MedicationLog.DATE + " date, " 
		      + MedicationLog.NUMBER_OF + " integer, "
		      + MedicationLog.CREATED_DATE + " integer," 
		      + MedicationLog.MODIFIED_DATE + " integer,"
		      + MedicationLog.FK_MED_SPEC + " integer, "
		      + " FOREIGN KEY ("+MedicationLog.FK_MED_SPEC+") REFERENCES "+MedicationSpec.TABLE_NAME+" ("+MedicationSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + MedicationLog.TABLE_NAME);
		onCreate(database);
	}
}
