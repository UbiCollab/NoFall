package ntnu.master.nofall.database.survey;

import ntnu.master.nofall.database.UserTable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyQRiskSpecTable {
	// Database table
	public static final String TABLE_SURVEY_Q_RISK_SPEC = "tblSurveyQRiskSpec";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ANSWER = "answer";
	public static final String COLUMN_RISK = "risk";
	
	// foreign keys
	public static final String COLUMN_FK_SURVEY_QUESTION = "fkSurveyQuestion";
	public static final String TABLE_FK_SURVEY_QUESTION = "tblSurveyQuestionSpec";
	public static final String COLUMN_FK_SURVEY_QUESTION_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SURVEY_Q_RISK_SPEC
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_ANSWER + " text not null, "  
		      + COLUMN_RISK + " integer, " 
		      + COLUMN_FK_SURVEY_QUESTION + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_SURVEY_QUESTION+") REFERENCES "+TABLE_FK_SURVEY_QUESTION+" ("+COLUMN_FK_SURVEY_QUESTION_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY_Q_RISK_SPEC);
		onCreate(database);
	}
}
