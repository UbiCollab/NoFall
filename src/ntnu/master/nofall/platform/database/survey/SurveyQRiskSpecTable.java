package ntnu.master.nofall.platform.database.survey;

import ntnu.master.nofall.platform.provider.RiskDefContract.RefRiskLevels;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveyQuestionRiskSpec;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveyQuestionSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyQRiskSpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + SurveyQuestionRiskSpec.TABLE_NAME
		      + "(" 
		      + SurveyQuestionRiskSpec._ID   + " integer primary key autoincrement, " 
		      + SurveyQuestionRiskSpec.ANSWER + " text not null, "  
		      + SurveyQuestionRiskSpec.FK_REF_RISK_LEVELS + " integer, " 
		      + SurveyQuestionRiskSpec.FK_SURVEY_QUESTION_SPEC + " integer, "
		      + SurveyQuestionRiskSpec.CREATED_DATE + " integer," 
		      + SurveyQuestionRiskSpec.MODIFIED_DATE + " integer,"
		      + " FOREIGN KEY ("+SurveyQuestionRiskSpec.FK_REF_RISK_LEVELS+") " +
		      		"REFERENCES "+RefRiskLevels.TABLE_NAME+" ("+RefRiskLevels._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+SurveyQuestionRiskSpec.FK_SURVEY_QUESTION_SPEC+") " +
		      		"REFERENCES "+SurveyQuestionSpec.TABLE_NAME+" ("+SurveyQuestionSpec._ID+") ON DELETE CASCADE );";

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
