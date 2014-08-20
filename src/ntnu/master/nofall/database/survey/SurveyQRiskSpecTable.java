package ntnu.master.nofall.database.survey;

import ntnu.master.nofall.contentprovider.provider.Standard.StandardsRiskMap;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyQuestionRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyQuestionSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyQRiskSpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + SurveyQuestionRiskSpec.TABLE_NAME
		      + "(" 
		      + SurveyQuestionRiskSpec._ID   + " integer primary key autoincrement, " 
		      + SurveyQuestionRiskSpec.ANSWER + " text not null, "  
		      + SurveyQuestionRiskSpec.FK_RISK_STAND_MAP + " integer, " 
		      + SurveyQuestionRiskSpec.FK_SURVEY_QUESTION + " integer, "
		      + SurveyQuestionRiskSpec.CREATED_DATE + " integer," 
		      + SurveyQuestionRiskSpec.MODIFIED_DATE + " integer,"
		      + " FOREIGN KEY ("+SurveyQuestionRiskSpec.FK_RISK_STAND_MAP+") REFERENCES "+StandardsRiskMap.TABLE_NAME+" ("+StandardsRiskMap._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+SurveyQuestionRiskSpec.FK_SURVEY_QUESTION+") REFERENCES "+SurveyQuestionSpec.TABLE_NAME+" ("+SurveyQuestionSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + SurveyQuestionRiskSpec.TABLE_NAME);
		onCreate(database);
	}
}
