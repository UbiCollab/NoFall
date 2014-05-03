package ntnu.master.nofall.database.medication;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedListLogTable {
	// Database table
	public static final String TABLE_MED_REG_LIST = "tblMedRegList";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FK_MED = "fkMed";
	public static final String COLUMN_FK_MED_REG = "fkMedReg";
	
	//foreign key: medication
	public static final String COLUMN_FK_TBL_MED = "tblMed";
	public static final String COLUMN_FK_ID_MED = "_id";
	
	//foreign key: medreg
	public static final String COLUMN_FK_TBL_MED_REG = "tblMedReg";
	public static final String COLUMN_FK_ID_MED_REG = "_id";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MED_REG_LIST
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_FK_MED + " integer, "
		      + COLUMN_FK_MED_REG + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_MED+") REFERENCES "+COLUMN_FK_TBL_MED+" ("+COLUMN_FK_ID_MED+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+COLUMN_FK_MED_REG+") REFERENCES "+COLUMN_FK_TBL_MED_REG+" ("+COLUMN_FK_ID_MED_REG+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MED_REG_LIST);
		onCreate(database);
	}
}
