package ntnu.master.nofall.database.medication;

import ntnu.master.nofall.provider.MedicationContract.MedicationListLog;
import ntnu.master.nofall.provider.MedicationContract.MedicationLog;
import ntnu.master.nofall.provider.MedicationContract.MedicationType;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedListLogTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + MedicationListLog.TABLE_NAME
		      + "(" 
		      + MedicationListLog._ID   + " integer primary key autoincrement, " 
		      + MedicationListLog.CREATED_DATE + " integer," 
		      + MedicationListLog.MODIFIED_DATE + " integer,"
		      + MedicationListLog.FK_MED_TYPE_SPEC + " integer, "
		      + MedicationListLog.FK_MED_LOG + " integer, "
		      + " FOREIGN KEY ("+MedicationListLog.FK_MED_TYPE_SPEC+") REFERENCES "+MedicationType.TABLE_NAME+" ("+MedicationType._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+MedicationListLog.FK_MED_LOG+") REFERENCES "+MedicationLog.TABLE_NAME+" ("+MedicationLog._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + MedicationListLog.TABLE_NAME);
		onCreate(database);
	}
}
