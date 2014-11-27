package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.TestContract.TestAnswerLog;
import ntnu.master.nofall.platform.provider.TestContract.TestLog;
import ntnu.master.nofall.platform.provider.TestContract.TestQuestionRiskSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAnswerLogTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TestAnswerLog.TABLE_NAME
		      + "(" 
		      + TestAnswerLog._ID   + " integer primary key autoincrement, "
		      + TestAnswerLog.CREATED_DATE + " integer," 
		      + TestAnswerLog.MODIFIED_DATE + " integer,"
		      + TestAnswerLog.FK_TEST_LOG + " integer, "
		      + TestAnswerLog.FK_TEST_Q_RISK + " integer, "
		      + " FOREIGN KEY ("+TestAnswerLog.FK_TEST_LOG+") REFERENCES "+TestLog.TABLE_NAME+" ("+TestLog._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+TestAnswerLog.FK_TEST_Q_RISK+") REFERENCES "+TestQuestionRiskSpec.TABLE_NAME+" ("+TestQuestionRiskSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestAnswerLog.TABLE_NAME);
		onCreate(database);
	}
}
