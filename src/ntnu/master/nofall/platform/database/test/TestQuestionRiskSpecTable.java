package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.RiskDefContract.RefRiskLevels;
import ntnu.master.nofall.platform.provider.TestContract.TestQuestionRiskSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestQuestionSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestQuestionRiskSpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TestQuestionRiskSpec.TABLE_NAME
		      + "(" 
		      + TestQuestionRiskSpec._ID   + " integer primary key autoincrement, " 
		      + TestQuestionRiskSpec.ANSWER + " text not null, "
		      + TestQuestionRiskSpec.CREATED_DATE + " integer," 
		      + TestQuestionRiskSpec.MODIFIED_DATE + " integer,"
		      + TestQuestionRiskSpec.FK_REF_RISK_LEVELS + " integer, " 
		      + TestQuestionRiskSpec.FK_TEST_QUESTION_SPEC + " integer, "
		      + " FOREIGN KEY ("+TestQuestionRiskSpec.FK_REF_RISK_LEVELS+") " +
		      		"REFERENCES "+RefRiskLevels.TABLE_NAME+" ("+RefRiskLevels._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+TestQuestionRiskSpec.FK_TEST_QUESTION_SPEC+") " +
		      		"REFERENCES "+TestQuestionSpec.TABLE_NAME+" ("+TestQuestionSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestQuestionRiskSpec.TABLE_NAME);
		onCreate(database);
	}
}