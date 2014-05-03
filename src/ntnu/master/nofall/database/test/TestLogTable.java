package ntnu.master.nofall.database.test;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestLogTable {
	// Database table
	public static final String TABLE_TEST_LOG = "tblTestLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_RESULT_TIME = "resTime";
	public static final String COLUMN_TOTAL_RISK = "totalRisk";
	
	// foreign keys
		public static final String COLUMN_FK_TEST = "fkTest";
		public static final String TABLE_FK_TEST = "tblTestSpec";
		public static final String COLUMN_FK_TEST_ID = "_id";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_TEST_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_DATE + " date, "  
		      + COLUMN_RESULT_TIME + " integer, " 
		      + COLUMN_RESULT_TIME + " integer, " 
		      + COLUMN_FK_TEST + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_TEST+") REFERENCES "+TABLE_FK_TEST+" ("+COLUMN_FK_TEST_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_LOG);
		onCreate(database);
	}
}
