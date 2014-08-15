package ntnu.master.nofall.database.test;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAnswerLogTable {
	// Database table
	public static final String TABLE_ANSWER_LOG = "tblAnswerLog";
	public static final String COLUMN_ID = "_id";
	
	//foreign key: testlog
	public static final String COLUMN_FK_TEST_LOG = "fkTestLog";
	public static final String TABLE_FK_TEST_LOG = "tblTestLog";
	public static final String COLUMN_FK_TEST_LOG_ID = "_id";
	
	//foreign key: questionrisk
	public static final String COLUMN_FK_TEST_Q_RISK = "fkTestQRisk";
	public static final String TABLE_FK_TEST_Q_RISK = "tblTestQRisk";
	public static final String COLUMN_FK_TEST_Q_RISK_ID = "_id";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_ANSWER_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_FK_TEST_LOG + " integer, "
		      + COLUMN_FK_TEST_Q_RISK + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_TEST_LOG+") REFERENCES "+TABLE_FK_TEST_LOG+" ("+COLUMN_FK_TEST_LOG_ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+COLUMN_FK_TEST_Q_RISK+") REFERENCES "+TABLE_FK_TEST_Q_RISK+" ("+COLUMN_FK_TEST_Q_RISK_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER_LOG);
		onCreate(database);
	}
}
