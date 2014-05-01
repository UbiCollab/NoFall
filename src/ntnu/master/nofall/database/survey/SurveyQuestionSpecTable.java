package ntnu.master.nofall.database.survey;

import ntnu.master.nofall.database.UserTable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyQuestionSpecTable {
	// Database table
	public static final String TABLE_SURVEY__QUESTION_SPEC = "tblSurveyQuestionSpec";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_QUESTION = "question";
	
	// foreign keys
	public static final String COLUMN_FK_SURVEY = "fkSurvey";
	public static final String TABLE_FK_SURVEY = "tblSurveySpec";
	public static final String COLUMN_FK_SURVEY_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SURVEY__QUESTION_SPEC
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_QUESTION + " text not null "  
		      +   COLUMN_FK_SURVEY + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_SURVEY+") REFERENCES "+TABLE_FK_SURVEY+" ("+COLUMN_FK_SURVEY_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY__QUESTION_SPEC);
		onCreate(database);
	}
}