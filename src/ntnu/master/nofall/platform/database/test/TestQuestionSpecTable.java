package ntnu.master.nofall.platform.database.test;

import ntnu.master.nofall.platform.provider.TestContract.TestQuestionSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestQuestionSpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TestQuestionSpec.TABLE_NAME
		      + "(" 
		      + TestQuestionSpec._ID   + " integer primary key autoincrement, " 
		      + TestQuestionSpec.QUESTION + " text not null, "
		      + TestQuestionSpec.CREATED_DATE + " integer," 
		      + TestQuestionSpec.MODIFIED_DATE + " integer,"
		      + TestQuestionSpec.FK_TEST_SPEC + " integer, "
		      + " FOREIGN KEY ("+TestQuestionSpec.FK_TEST_SPEC+") REFERENCES "+TestSpec.TABLE_NAME+" ("+TestSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TestQuestionSpec.TABLE_NAME);
		onCreate(database);
	}
}
