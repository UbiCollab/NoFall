package ntnu.master.nofall.platform.database.survey;

import ntnu.master.nofall.platform.provider.SurveyContract.SurveyQuestionSpec;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveySpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyQuestionSpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + SurveyQuestionSpec.TABLE_NAME
		      + "(" 
		      + SurveyQuestionSpec._ID   + " integer primary key autoincrement, " 
		      + SurveyQuestionSpec.QUESTION + " text not null, "
		      + SurveyQuestionSpec.CREATED_DATE + " integer," 
		      + SurveyQuestionSpec.MODIFIED_DATE + " integer,"
		      + SurveyQuestionSpec.FK_SURVEY_SPEC + " integer, "
		      + " FOREIGN KEY ("+SurveyQuestionSpec.FK_SURVEY_SPEC+") REFERENCES "+SurveySpec.TABLE_NAME+" ("+SurveySpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + SurveyQuestionSpec.TABLE_NAME);
		onCreate(database);
	}
}