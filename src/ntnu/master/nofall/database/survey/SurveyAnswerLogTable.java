package ntnu.master.nofall.database.survey;

import ntnu.master.nofall.contentprovider.provider.Survey.SurveyAnswerLog;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyLog;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyQuestionRiskSpec;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyAnswerLogTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + SurveyAnswerLog.TABLE_NAME
		      + "(" 
		      + SurveyAnswerLog._ID   + " integer primary key autoincrement, " 
		      + SurveyAnswerLog.FK_SURVEY_LOG + " integer, "
		      + SurveyAnswerLog.FK_SURVEY_Q_RISK_SPEC + " integer, "
		      + SurveyAnswerLog.CREATED_DATE + " integer," 
		      + SurveyAnswerLog.MODIFIED_DATE + " integer,"
		      + " FOREIGN KEY ("+ SurveyAnswerLog.FK_SURVEY_LOG+") REFERENCES "
		      +SurveyLog.TABLE_NAME+" ("+SurveyLog._ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+SurveyAnswerLog.FK_SURVEY_Q_RISK_SPEC+") REFERENCES "
		      +SurveyQuestionRiskSpec.TABLE_NAME+" ("+SurveyQuestionRiskSpec._ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + SurveyAnswerLog.TABLE_NAME);
		onCreate(database);
	}
}
