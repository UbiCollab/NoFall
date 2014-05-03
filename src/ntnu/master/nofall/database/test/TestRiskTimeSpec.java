package ntnu.master.nofall.database.test;

import ntnu.master.nofall.database.UserTable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestRiskTimeSpec {
	// Database table
	public static final String TABLE_TEST_Q_RISK_SPEC = "tblTestTimeRisk";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_INTERVAL = "interval";
	public static final String COLUMN_RISK = "risk";
	
	// foreign keys
	public static final String COLUMN_FK_TEST_SPEC = "fkTestSpec";
	public static final String TABLE_FK_TEST_SPEC = "tblTestSpec";
	public static final String COLUMN_FK_TEST_SPEC_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_TEST_Q_RISK_SPEC
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_INTERVAL + " text not null, "  
		      + COLUMN_RISK + " integer, " 
		      + COLUMN_FK_TEST_SPEC + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_TEST_SPEC+") REFERENCES "+TABLE_FK_TEST_SPEC+" ("+COLUMN_FK_TEST_SPEC_ID+") ON DELETE CASCADE );";

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
		Log.w(UserTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_Q_RISK_SPEC);
		onCreate(database);
	}
}
