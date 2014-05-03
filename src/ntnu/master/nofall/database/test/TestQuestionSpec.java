package ntnu.master.nofall.database.test;

import ntnu.master.nofall.database.UserTable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestQuestionSpec {
	// Database table
	public static final String TABLE_SURVEY_QUESTION_SPEC = "tblTestQuestionSpec";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_QUESTION = "question";
	
	// foreign keys
	public static final String COLUMN_FK_TEST = "fkTestSpec";
	public static final String TABLE_FK_TEST = "tblTestSpec";
	public static final String COLUMN_FK_TEST_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SURVEY_QUESTION_SPEC
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_QUESTION + " text not null "  
		      +   COLUMN_FK_TEST + " integer, "
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
		Log.w(UserTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY_QUESTION_SPEC);
		onCreate(database);
	}
}
