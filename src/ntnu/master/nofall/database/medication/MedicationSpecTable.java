package ntnu.master.nofall.database.medication;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicationSpecTable {
	// Database table
	public static final String TABLE_MED = "tblMed";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_RISK = "risk";
	public static final String COLUMN_FK_CATEGORY = "fkCategory";
	
	// Foreign key
	public static final String TABLE_MED_CAT = "tblMedCat";
	public static final String COLUMN_FK_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MED
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_NAME + " text not null, " 
		      + COLUMN_RISK + " integer, "
		      + COLUMN_FK_CATEGORY + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_CATEGORY+") REFERENCES "+TABLE_MED_CAT+" ("+COLUMN_FK_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MED);
		onCreate(database);
	}
}
