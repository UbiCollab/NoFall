package ntnu.master.nofall.database.survey;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveyAnswerLogTable {
	// Database table
	public static final String TABLE_SURVEY_ANSWER_LOG = "tblSurveyAnswerLog";
	public static final String COLUMN_ID = "_id";
	
	// foreign keys
	public static final String COLUMN_FK_SURVEY_LOG = "fkSurvey";
	public static final String TABLE_FK_SURVEY_LOG = "tblSurveyLog";
	public static final String COLUMN_FK_SURVEY_LOG_ID = "_id";
	// foreign keys
	public static final String COLUMN_FK_SURVEY_Q_RISK_SPEC = "fkSurveyQRisk";
	public static final String TABLE_FK_SURVEY_Q_RISK_SPEC= "tblSurveyQRiskSpec";
	public static final String COLUMN_FK_SURVEY_Q_RISK_SPEC_ID = "_id";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SURVEY_ANSWER_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_FK_SURVEY_LOG + " integer, "
		      + COLUMN_FK_SURVEY_Q_RISK_SPEC + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_SURVEY_LOG+") REFERENCES "+TABLE_FK_SURVEY_LOG+" ("+COLUMN_FK_SURVEY_LOG_ID+") ON DELETE CASCADE"
		      + " FOREIGN KEY ("+COLUMN_FK_SURVEY_Q_RISK_SPEC+") REFERENCES "+TABLE_FK_SURVEY_Q_RISK_SPEC+" ("+COLUMN_FK_SURVEY_Q_RISK_SPEC_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY_ANSWER_LOG);
		onCreate(database);
	}
}
