package ntnu.master.nofall.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import ntnu.master.nofall.database.NoFallDBHelper;
import ntnu.master.nofall.database.UserTable;
import ntnu.master.nofall.database.medication.MedCategorySpecTable;
import ntnu.master.nofall.database.medication.MedListLogTable;
import ntnu.master.nofall.database.medication.MedLogTable;
import ntnu.master.nofall.database.medication.MedicationSpecTable;
import ntnu.master.nofall.database.sensor.SensorLogTable;
import ntnu.master.nofall.database.sensor.SensorRiskSpecTable;
import ntnu.master.nofall.database.sensor.SensorSpecTable;
import ntnu.master.nofall.database.standards.ForeignStandardsTable;
import ntnu.master.nofall.database.standards.NoFallRiskTable;
import ntnu.master.nofall.database.standards.RiskStandardMapTable;
import ntnu.master.nofall.database.standards.StandardsTable;
import ntnu.master.nofall.database.survey.SurveyAnswerLogTable;
import ntnu.master.nofall.database.survey.SurveyLogTable;
import ntnu.master.nofall.database.survey.SurveyQRiskSpecTable;
import ntnu.master.nofall.database.survey.SurveyQuestionSpecTable;
import ntnu.master.nofall.database.survey.SurveySpecTable;
import ntnu.master.nofall.database.test.TestAnswerLogTable;
import ntnu.master.nofall.database.test.TestLogTable;
import ntnu.master.nofall.database.test.TestMeasureLogTable;
import ntnu.master.nofall.database.test.TestMeasureRiskSpecTable;
import ntnu.master.nofall.database.test.TestMeasureSpecTable;
import ntnu.master.nofall.database.test.TestQuestionSpecTable;
import ntnu.master.nofall.database.test.TestRiskQuestionSpecTable;
import ntnu.master.nofall.database.test.TestSpecTable;
import ntnu.master.nofall.database.user.TotalRiskLogTable;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {
	// database
	private NoFallDBHelper database;

	// User for the UriMacher
	private static final int USER = 10;
	private static final int USER_ID = 20;
	private static final int USER_TOTAL_RISK = 11;
	private static final int USER_TOTAL_RISK_ID = 21;
	
	// Medication UriMatches
	private static final int MED = 12;
	private static final int MED_ID = 22;
	private static final int MED_CAT = 13;
	private static final int MED_CAT_ID = 23;
	private static final int MED_LIST_LOG = 14;
	private static final int MED_LIST_LOG_ID = 24;
	private static final int MED_LOG = 15;
	private static final int MED_LOG_ID = 25;
	
	// Sensor UriMatches
	private static final int SENS_SPEC = 16;
	private static final int SENS_SPEC_ID = 26;
	private static final int SENS_RISK_SPEC = 17;
	private static final int SENS_RISK_SPEC_ID = 27;
	private static final int SENS_LOG = 18;
	private static final int SENS_LOG_ID = 28;
	
	// Standards UriMatches
	private static final int RISK_STANDARDS = 19;
	private static final int RISK_STANDARDS_ID = 29;
	private static final int RISK_STANDARDS_MAP = 30;
	private static final int RISK_STANDARDS_MAP_ID = 40;
	private static final int RISK_STANDARDS_NOFALL = 31;
	private static final int RISK_STANDARDS_NOFALL_ID = 41;
	private static final int RISK_STANDARDS_FOREIGN = 32;
	private static final int RISK_STANDARDS_FOREIGN_ID = 42;

	// Survey UriMatches
	private static final int SURVEY = 33;
	private static final int SURVEY_ID = 43;
	private static final int SURVEY_LOG = 34;
	private static final int SURVEY_LOG_ID = 44;
	private static final int SURVEY_ANSWER_LOG = 35;
	private static final int SURVEY_ANSWER_LOG_ID = 45;
	private static final int SURVEY_QUESTION = 36;
	private static final int SURVEY_QUESTION_ID = 46;
	private static final int SURVEY_QUESTION_RISK = 37;
	private static final int SURVEY_QUESTION_RISK_ID = 47;
	
	// Test UriMatches
	private static final int TEST = 38;
	private static final int TEST_ID = 48;
	private static final int TEST_LOG = 39;
	private static final int TEST_LOG_ID = 49;
	private static final int TEST_ANSWER_LOG = 50;
	private static final int TEST_ANSWER_LOG_ID = 60;
	private static final int TEST_QUESTION = 51;
	private static final int TEST_QUESTION_ID = 61;
	private static final int TEST_QUESTION_RISK = 52;
	private static final int TEST_QUESTION_RISK_ID = 62;
	private static final int TEST_MEASURE = 53;
	private static final int TEST_MEASURE_ID = 63;
	private static final int TEST_MEASURE_LOG = 54;
	private static final int TEST_MEASURE_LOG_ID = 64;
	private static final int TEST_MEASURE_RISK = 55;
	private static final int TEST_MEASURE_RISK_ID = 65;
	
	private static final String AUTHORITY = "ntnu.master.nofall.contentprovider";
	
	// User
	private static final String BASE_PATH_USER = "tblUser";
	private static final String BASE_PATH_USER_TOTAL_RISK = "tblTotalRiskLog";
	
	// Medication
	private static final String BASE_PATH_MED = "tblMed";
	private static final String BASE_PATH_MED_CAT = "tblMedCat";
	private static final String BASE_PATH_MED_LOG = "tblMedLog";
	private static final String BASE_PATH_MED_LIST_LOG = "tblMedListLog";
	
	// Sensor
	private static final String BASE_PATH_SENS_SPEC = "tblSensorSpec";
	private static final String BASE_PATH_SENS_RISK_SPEC = "tblSensorRiskSpec";
	private static final String BASE_PATH_SENS_LOG = "tblSensorLog";
	
	// Standards
	private static final String BASE_PATH_RISK_STANDARDS = "tblStandards";
	private static final String BASE_PATH_RISK_STANDARDS_MAP = "tblRiskStandMap";
	private static final String BASE_PATH_RISK_STANDARDS_NOFALL = "tblNoFallRisk";
	private static final String BASE_PATH_RISK_STANDARDS_FOREIGN = "tblForeignStandard";
	
	// Survey
	private static final String BASE_PATH_SURVEY = "tblSurveySpec";
	private static final String BASE_PATH_SURVEY_LOG = "tblSurveyLog";
	private static final String BASE_PATH_SURVEY_ANSWER_LOG = "tblSurveyAnswerLog";
	private static final String BASE_PATH_SURVEY_QUESTION = "tblSurveyQuestionSpec";
	private static final String BASE_PATH_SURVEY_QUESTION_RISK = "tblSurveyQRiskSpec";
	
	// Test
	private static final String BASE_PATH_TEST = "tblTestSpec";
	private static final String BASE_PATH_TEST_LOG = "tblTestLog";
	private static final String BASE_PATH_TEST_ANSWER_LOG = "tblAnswerLog";
	private static final String BASE_PATH_TEST_QUESTION = "tblTestQuestionSpec";
	private static final String BASE_PATH_TEST_QUESTION_RISK = "tblTestQRisk";
	private static final String BASE_PATH_TEST_MEASURE = "tblTestMeasureSpec";
	private static final String BASE_PATH_TEST_MEASURE_LOG = "tblTestMeasureLog";
	private static final String BASE_PATH_TEST_MEASURE_RISK = "tblTestMeasureRiskSpec";
	
	// User
	public static final Uri CONTENT_URI_USER = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_USER);
	public static final Uri CONTENT_URI_USER_TOTAL_RISK = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_USER_TOTAL_RISK);
	
	// Medication
	public static final Uri CONTENT_URI_MED = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_MED);
	public static final Uri CONTENT_URI_MED_CAT = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_MED_CAT);
	public static final Uri CONTENT_URI_MED_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_MED_LOG);
	public static final Uri CONTENT_URI_MED_LIST_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_MED_LIST_LOG);
	
	// Sensor
	public static final Uri CONTENT_URI_SENS_SPEC = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SENS_SPEC);
	public static final Uri CONTENT_URI_SENS_RISK_SPEC = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SENS_RISK_SPEC);
	public static final Uri CONTENT_URI_SENS_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SENS_LOG);

	// Standards
	public static final Uri CONTENT_URI_RISK_STANDARDS = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_RISK_STANDARDS);
	public static final Uri CONTENT_URI_RISK_STANDARDS_MAP = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_RISK_STANDARDS_MAP);
	public static final Uri CONTENT_URI_RISK_STANDARDS_NOFALL = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_RISK_STANDARDS_NOFALL);
	public static final Uri CONTENT_URI_RISK_STANDARDS_FOREIGN = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_RISK_STANDARDS_FOREIGN);
	
	// Survey
	public static final Uri CONTENT_URI_SURVEY = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SURVEY);
	public static final Uri CONTENT_URI_SURVEY_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SURVEY_LOG);
	public static final Uri CONTENT_URI_SURVEY_ANSWER_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SURVEY_ANSWER_LOG);
	public static final Uri CONTENT_URI_SURVEY_QUESTION = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SURVEY_QUESTION);
	public static final Uri CONTENT_URI_SURVEY_QUESTION_RISK = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_SURVEY_QUESTION_RISK);
	
	// Test
	public static final Uri CONTENT_URI_TEST = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST);
	public static final Uri CONTENT_URI_TEST_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_LOG);
	public static final Uri CONTENT_URI_TEST_ANSWER_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_ANSWER_LOG);
	public static final Uri CONTENT_URI_TEST_QUESTION = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_QUESTION);
	public static final Uri CONTENT_URI_TEST_QUESTION_RISK = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_QUESTION_RISK);
	public static final Uri CONTENT_URI_TEST_MEASURE = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_MEASURE);
	public static final Uri CONTENT_URI_TEST_MEASURE_LOG = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_MEASURE_LOG);
	public static final Uri CONTENT_URI_TEST_MEASURE_RISK = Uri.parse("content://"
			+ AUTHORITY + "/" + BASE_PATH_TEST_MEASURE_RISK);
	
	// User
	public static final String CONTENT_TYPE_USER = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblUser";
	public static final String CONTENT_ITEM_TYPE_USER = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblUser";
	
	// Medication
	public static final String CONTENT_TYPE_MED = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMed";
	public static final String CONTENT_ITEM_TYPE_MED = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMed";

	public static final String CONTENT_TYPE_MED_CAT = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMedCat";
	public static final String CONTENT_ITEM_TYPE_MED_CAT = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMedCat";

	public static final String CONTENT_TYPE_MED_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMedLog";
	public static final String CONTENT_ITEM_TYPE_MED_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMedLog";

	public static final String CONTENT_TYPE_MED_LIST_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMedListLog";
	public static final String CONTENT_ITEM_TYPE_MED_LIST_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMedListLog";

	// Sensors
	public static final String CONTENT_TYPE_SENS_SPEC = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSensorSpec";
	public static final String CONTENT_ITEM_TYPE_SENS_SPEC = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSensorSpec";

	public static final String CONTENT_TYPE_MED_SENS_RISK_SPEC = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSensorRiskSpec";
	public static final String CONTENT_ITEM_TYPE_MED_SENS_RISK_SPEC = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSensorRiskSpec";

	public static final String CONTENT_TYPE_SENS_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSensorLog";
	public static final String CONTENT_ITEM_TYPE_SENS_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSensorLog";

	// Standards
	public static final String CONTENT_TYPE_RISK_STANDARDS = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblStandards";
	public static final String CONTENT_ITEM_TYPE_RISK_STANDARDS = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblStandards";

	public static final String CONTENT_TYPE_RISK_STANDARDS_MAP = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblRiskStandMap";
	public static final String CONTENT_ITEM_TYPE_RISK_STANDARDS_MAP = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblRiskStandMap";

	public static final String CONTENT_TYPE_RISK_STANDARDS_NOFALL = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblNoFallRisk";
	public static final String CONTENT_ITEM_TYPE_RISK_STANDARDS_NOFALL = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblNoFallRisk";

	public static final String CONTENT_TYPE_RISK_STANDARDS_FOREIGN = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblForeignStandard";
	public static final String CONTENT_ITEM_TYPE_RISK_STANDARDS_FOREIGN = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblForeignStandard";
	
	// Survey
	public static final String CONTENT_TYPE_SURVEY = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSurveySpec";
	public static final String CONTENT_ITEM_TYPE_SURVEY = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSurveySpec";

	public static final String CONTENT_TYPE_SURVEY_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSurveyLog";
	public static final String CONTENT_ITEM_TYPE_SURVEY_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSurveyLog";

	public static final String CONTENT_TYPE_SURVEY_ANSWER_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSurveyAnswerLog";
	public static final String CONTENT_ITEM_TYPE_SURVEY_ANSWER_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSurveyAnswerLog";

	public static final String CONTENT_TYPE_SURVEY_QUESTION = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSurveyQuestionSpec";
	public static final String CONTENT_ITEM_TYPE_SURVEY_QUESTION = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSurveyQuestionSpec";
	
	public static final String CONTENT_TYPE_SURVEY_QUESTION_RISK = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblSurveyQRiskSpec";
	public static final String CONTENT_ITEM_TYPE_SURVEY_QUESTION_RISK = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblSurveyQRiskSpec";
	
	// Test
	public static final String CONTENT_TYPE_TEST = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestSpec";
	public static final String CONTENT_ITEM_TYPE_TEST = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestSpec";

	public static final String CONTENT_TYPE_TEST_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestLog";
	public static final String CONTENT_ITEM_TYPE_TEST_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestLog";

	public static final String CONTENT_TYPE_TEST_ANSWER_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblAnswerLog";
	public static final String CONTENT_ITEM_TYPE_TEST_ANSWER_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblAnswerLog";

	public static final String CONTENT_TYPE_TEST_QUESTION = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestQuestionSpec";
	public static final String CONTENT_ITEM_TYPE_TEST_QUESTION = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestQuestionSpec";
	
	public static final String CONTENT_TYPE_TEST_QUESTION_RISK = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestQRisk";
	public static final String CONTENT_ITEM_TYPE_TEST_QUESTION_RISK = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestQRisk";
	
	public static final String CONTENT_TYPE_TEST_MEASURE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestMeasureSpec";
	public static final String CONTENT_ITEM_TYPE_TEST_MEASURE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestMeasureSpec";

	public static final String CONTENT_TYPE_TEST_MEASURE_LOG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestMeasureLog";
	public static final String CONTENT_ITEM_TYPE_TEST_MEASURE_LOG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestMeasureLog";
	
	public static final String CONTENT_TYPE_TEST_MEASURE_RISK = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblTestMeasureRiskSpec";
	public static final String CONTENT_ITEM_TYPE_TEST_MEASURE_RISK = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblTestMeasureRiskSpec";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		// User
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER, USER);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER + "/#", USER_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER_TOTAL_RISK, USER_TOTAL_RISK);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER_TOTAL_RISK + "/#", USER_TOTAL_RISK_ID);

		// Medication
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED, MED);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED + "/#", MED_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_CAT, MED_CAT);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_CAT + "/#", MED_CAT_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_LOG, MED_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_LOG + "/#", MED_LOG_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_LIST_LOG, MED_LIST_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_LIST_LOG + "/#", MED_LIST_LOG_ID);

		// Sensor
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SENS_SPEC, SENS_SPEC);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SENS_SPEC + "/#", SENS_SPEC_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SENS_RISK_SPEC, SENS_RISK_SPEC);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SENS_RISK_SPEC + "/#",
				SENS_RISK_SPEC_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SENS_LOG, SENS_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SENS_LOG + "/#", SENS_LOG_ID);
		
		// Standards,
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS, RISK_STANDARDS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS + "/#", RISK_STANDARDS_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS_MAP, RISK_STANDARDS_MAP);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS_MAP + "/#", RISK_STANDARDS_MAP_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS_NOFALL, RISK_STANDARDS_NOFALL);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS_NOFALL + "/#", RISK_STANDARDS_NOFALL_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS_FOREIGN, RISK_STANDARDS_FOREIGN);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_RISK_STANDARDS_FOREIGN + "/#", RISK_STANDARDS_FOREIGN_ID);
		
		// Survey
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY, SURVEY);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY + "/#", SURVEY_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_LOG, SURVEY_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_LOG + "/#", SURVEY_LOG_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_ANSWER_LOG, SURVEY_ANSWER_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_ANSWER_LOG + "/#", SURVEY_ANSWER_LOG_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_QUESTION, SURVEY_QUESTION);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_QUESTION + "/#", SURVEY_QUESTION_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_QUESTION_RISK, SURVEY_QUESTION_RISK);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_SURVEY_QUESTION_RISK + "/#", SURVEY_QUESTION_RISK_ID);
		
		// Test
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST, TEST);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST + "/#", TEST_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_LOG, TEST_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_LOG + "/#", TEST_LOG_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_ANSWER_LOG, TEST_ANSWER_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_ANSWER_LOG + "/#", TEST_ANSWER_LOG_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_QUESTION, TEST_QUESTION);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_QUESTION + "/#", TEST_QUESTION_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_QUESTION_RISK, TEST_QUESTION_RISK);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_QUESTION_RISK + "/#", TEST_QUESTION_RISK_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_MEASURE, TEST_MEASURE);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_MEASURE + "/#", TEST_MEASURE_ID);

		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_MEASURE_LOG, TEST_MEASURE_LOG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_MEASURE_LOG + "/#", TEST_MEASURE_LOG_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_MEASURE_RISK, TEST_MEASURE_RISK);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TEST_MEASURE_RISK + "/#", TEST_MEASURE_RISK_ID);
	}

	@Override
	public boolean onCreate() {
		database = new NoFallDBHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// Using SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		//
		// // check if the caller has requested a column which does not exists
		// checkColumns(projection);
		//
		// // Set the table
		// queryBuilder.setTables(UserTable.TABLE_USER);

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {

		case USER:
			checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(UserTable.TABLE_USER);
			break;
		case USER_ID:
			// check if the caller has requested a column which does not exists
			checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(UserTable.TABLE_USER);

			// adding the ID to the original query
			queryBuilder.appendWhere(UserTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case USER_TOTAL_RISK:
			checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(TotalRiskLogTable.TABLE_TOTAL_RISK);
			break;
		case USER_TOTAL_RISK_ID:
			// check if the caller has requested a column which does not exists
			checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(TotalRiskLogTable.TABLE_TOTAL_RISK);

			// adding the ID to the original query
			queryBuilder.appendWhere(UserTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case MED:
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationSpecTable.TABLE_MED);
			break;
		case MED_ID:
			// check if the caller has requested a column which does not exists
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationSpecTable.TABLE_MED);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case MED_CAT:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedCategorySpecTable.TABLE_MED_CAT);
			break;
		case MED_CAT_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedCategorySpecTable.TABLE_MED_CAT);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedCategorySpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case MED_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedLogTable.TABLE_MED_LOG);
			break;
		case MED_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedLogTable.TABLE_MED_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case MED_LIST_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedListLogTable.TABLE_MED_LIST_LOG);
			break;
		case MED_LIST_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedListLogTable.TABLE_MED_LIST_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedListLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case SENS_SPEC:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorSpecTable.TABLE_SENSOR_SPEC);
			break;
		case SENS_SPEC_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorSpecTable.TABLE_SENSOR_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case SENS_RISK_SPEC:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder
					.setTables(SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC);
			break;
		case SENS_RISK_SPEC_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder
					.setTables(SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorRiskSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case SENS_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorLogTable.TABLE_SENSOR_LOG);
			break;
		case SENS_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorLogTable.TABLE_SENSOR_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case RISK_STANDARDS:
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsTable.TABLE_STANDARDS);
			break;
		case RISK_STANDARDS_ID:
			// check if the caller has requested a column which does not exists
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsTable.TABLE_STANDARDS);

			// adding the ID to the original query
			queryBuilder.appendWhere(StandardsTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case RISK_STANDARDS_MAP:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(RiskStandardMapTable.TABLE_RISK_STAND_MAP);
			break;
		case RISK_STANDARDS_MAP_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(RiskStandardMapTable.TABLE_RISK_STAND_MAP);

			// adding the ID to the original query
			queryBuilder.appendWhere(RiskStandardMapTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case RISK_STANDARDS_NOFALL:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(NoFallRiskTable.TABLE_NOFALL_RISK);
			break;
		case RISK_STANDARDS_NOFALL_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(NoFallRiskTable.TABLE_NOFALL_RISK);

			// adding the ID to the original query
			queryBuilder.appendWhere(NoFallRiskTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case RISK_STANDARDS_FOREIGN:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(ForeignStandardsTable.TABLE_FOREIGN_STANDARD);
			break;
		case RISK_STANDARDS_FOREIGN_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(ForeignStandardsTable.TABLE_FOREIGN_STANDARD);

			// adding the ID to the original query
			queryBuilder.appendWhere(ForeignStandardsTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case SURVEY:
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveySpecTable.TABLE_SURVEY_SPEC);
			break;
		case SURVEY_ID:
			// check if the caller has requested a column which does not exists
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveySpecTable.TABLE_SURVEY_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveySpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case SURVEY_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyLogTable.TABLE_SURVEY_LOG);
			break;
		case SURVEY_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyLogTable.TABLE_SURVEY_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case SURVEY_ANSWER_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG);
			break;
		case SURVEY_ANSWER_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyAnswerLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case SURVEY_QUESTION:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC);
			break;
		case SURVEY_QUESTION_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyQuestionSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case SURVEY_QUESTION_RISK:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC);
			break;
		case SURVEY_QUESTION_RISK_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyQRiskSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TEST:
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestSpecTable.TABLE_TEST_SPEC);
			break;
		case TEST_ID:
			// check if the caller has requested a column which does not exists
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestSpecTable.TABLE_TEST_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case TEST_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestLogTable.TABLE_TEST_LOG);
			break;
		case TEST_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestLogTable.TABLE_TEST_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case TEST_ANSWER_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestAnswerLogTable.TABLE_ANSWER_LOG);
			break;
		case TEST_ANSWER_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestAnswerLogTable.TABLE_ANSWER_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestAnswerLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case TEST_QUESTION:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC);
			break;
		case TEST_QUESTION_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestQuestionSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TEST_QUESTION_RISK:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC);
			break;
		case TEST_QUESTION_RISK_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestRiskQuestionSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TEST_MEASURE:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC);
			break;
		case TEST_MEASURE_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case TEST_MEASURE_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG);
			break;
		case TEST_MEASURE_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TEST_MEASURE_RISK:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC);
			break;
		case TEST_MEASURE_RISK_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureRiskSpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);

		// make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;

		switch (uriType) {

		case USER:
			id = sqlDB.insert(UserTable.TABLE_USER, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_USER + "/" + id);
		
		case USER_TOTAL_RISK:
			id = sqlDB.insert(TotalRiskLogTable.TABLE_TOTAL_RISK, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_USER + "/" + id);
						
		case MED:
			id = sqlDB.insert(MedicationSpecTable.TABLE_MED, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED + "/" + id);

		case MED_CAT:
			id = sqlDB.insert(MedCategorySpecTable.TABLE_MED_CAT, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED_CAT + "/" + id);

		case MED_LOG:
			id = sqlDB.insert(MedLogTable.TABLE_MED_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED_LOG + "/" + id);

		case MED_LIST_LOG:
			id = sqlDB.insert(MedListLogTable.TABLE_MED_LIST_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED_LIST_LOG + "/" + id);

		case SENS_SPEC:
			id = sqlDB
					.insert(SensorSpecTable.TABLE_SENSOR_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SENS_SPEC + "/" + id);

		case SENS_RISK_SPEC:
			id = sqlDB.insert(SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC,
					null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SENS_RISK_SPEC + "/" + id);

		case SENS_LOG:
			id = sqlDB.insert(SensorLogTable.TABLE_SENSOR_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SENS_LOG + "/" + id);
			
		case RISK_STANDARDS:
			id = sqlDB.insert(StandardsTable.TABLE_STANDARDS, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_RISK_STANDARDS + "/" + id);

		case RISK_STANDARDS_MAP:
			id = sqlDB.insert(RiskStandardMapTable.TABLE_RISK_STAND_MAP, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_RISK_STANDARDS_MAP + "/" + id);

		case RISK_STANDARDS_NOFALL:
			id = sqlDB.insert(NoFallRiskTable.TABLE_NOFALL_RISK, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_RISK_STANDARDS_NOFALL + "/" + id);

		case RISK_STANDARDS_FOREIGN:
			id = sqlDB.insert(ForeignStandardsTable.TABLE_FOREIGN_STANDARD, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_RISK_STANDARDS_FOREIGN + "/" + id);
			
		case SURVEY:
			id = sqlDB.insert(SurveySpecTable.TABLE_SURVEY_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SURVEY + "/" + id);

		case SURVEY_LOG:
			id = sqlDB.insert(SurveyLogTable.TABLE_SURVEY_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SURVEY_LOG + "/" + id);

		case SURVEY_ANSWER_LOG:
			id = sqlDB.insert(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SURVEY_ANSWER_LOG + "/" + id);

		case SURVEY_QUESTION:
			id = sqlDB.insert(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SURVEY_QUESTION + "/" + id);
			
		case SURVEY_QUESTION_RISK:
			id = sqlDB.insert(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_SURVEY_QUESTION_RISK + "/" + id);
			
		case TEST:
			id = sqlDB.insert(TestSpecTable.TABLE_TEST_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST + "/" + id);

		case TEST_LOG:
			id = sqlDB.insert(TestLogTable.TABLE_TEST_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_LOG + "/" + id);

		case TEST_ANSWER_LOG:
			id = sqlDB.insert(TestAnswerLogTable.TABLE_ANSWER_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_ANSWER_LOG + "/" + id);

		case TEST_QUESTION:
			id = sqlDB.insert(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_QUESTION + "/" + id);
			
		case TEST_QUESTION_RISK:
			id = sqlDB.insert(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_QUESTION_RISK + "/" + id);
			
		case TEST_MEASURE:
			id = sqlDB.insert(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_MEASURE + "/" + id);

		case TEST_MEASURE_LOG:
			id = sqlDB.insert(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_MEASURE_LOG + "/" + id);
			
		case TEST_MEASURE_RISK:
			id = sqlDB.insert(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_TEST_MEASURE_RISK + "/" + id);
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		String id;
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;

		switch (uriType) {
		case USER:
			rowsDeleted = sqlDB.delete(UserTable.TABLE_USER, selection,
					selectionArgs);
			break;
		case USER_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(UserTable.TABLE_USER,
						UserTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(UserTable.TABLE_USER,
						UserTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case USER_TOTAL_RISK:
			rowsDeleted = sqlDB.delete(TotalRiskLogTable.TABLE_TOTAL_RISK, selection,
					selectionArgs);
			break;
		case USER_TOTAL_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TotalRiskLogTable.TABLE_TOTAL_RISK,
						UserTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TotalRiskLogTable.TABLE_TOTAL_RISK,
						UserTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED:
			rowsDeleted = sqlDB.delete(MedicationSpecTable.TABLE_MED,
					selection, selectionArgs);
			break;
		case MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationSpecTable.TABLE_MED,
						MedicationSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationSpecTable.TABLE_MED,
						MedicationSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case MED_CAT:
			rowsDeleted = sqlDB.delete(MedCategorySpecTable.TABLE_MED_CAT,
					selection, selectionArgs);
			break;
		case MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedCategorySpecTable.TABLE_MED_CAT,
						MedCategorySpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedCategorySpecTable.TABLE_MED_CAT,
						MedCategorySpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case MED_LOG:
			rowsDeleted = sqlDB.delete(MedLogTable.TABLE_MED_LOG, selection,
					selectionArgs);
			break;
		case MED_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedLogTable.TABLE_MED_LOG,
						MedLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedLogTable.TABLE_MED_LOG,
						MedLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case MED_LIST_LOG:
			rowsDeleted = sqlDB.delete(MedListLogTable.TABLE_MED_LIST_LOG,
					selection, selectionArgs);
			break;
		case MED_LIST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedListLogTable.TABLE_MED_LIST_LOG,
						MedListLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedListLogTable.TABLE_MED_LIST_LOG,
						MedListLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SENS_SPEC:
			rowsDeleted = sqlDB.delete(SensorSpecTable.TABLE_SENSOR_SPEC,
					selection, selectionArgs);
			break;
		case SENS_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SensorSpecTable.TABLE_SENSOR_SPEC,
						SensorSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorSpecTable.TABLE_SENSOR_SPEC,
						SensorSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SENS_RISK_SPEC:
			rowsDeleted = sqlDB.delete(
					SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC, selection,
					selectionArgs);
			break;
		case SENS_RISK_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(
						SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC,
						SensorRiskSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorSpecTable.TABLE_SENSOR_SPEC,
						SensorRiskSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SENS_LOG:
			rowsDeleted = sqlDB.delete(SensorLogTable.TABLE_SENSOR_LOG,
					selection, selectionArgs);
			break;
		case SENS_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SensorLogTable.TABLE_SENSOR_LOG,
						SensorLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorSpecTable.TABLE_SENSOR_SPEC,
						SensorLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case RISK_STANDARDS:
			rowsDeleted = sqlDB.delete(StandardsTable.TABLE_STANDARDS,
					selection, selectionArgs);
			break;
		case RISK_STANDARDS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(StandardsTable.TABLE_STANDARDS,
						StandardsTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(StandardsTable.TABLE_STANDARDS,
						StandardsTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case RISK_STANDARDS_MAP:
			rowsDeleted = sqlDB.delete(RiskStandardMapTable.TABLE_RISK_STAND_MAP,
					selection, selectionArgs);
			break;
		case RISK_STANDARDS_MAP_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(RiskStandardMapTable.TABLE_RISK_STAND_MAP,
						RiskStandardMapTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(RiskStandardMapTable.TABLE_RISK_STAND_MAP,
						RiskStandardMapTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case RISK_STANDARDS_NOFALL:
			rowsDeleted = sqlDB.delete(NoFallRiskTable.TABLE_NOFALL_RISK, selection,
					selectionArgs);
			break;
		case RISK_STANDARDS_NOFALL_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(NoFallRiskTable.TABLE_NOFALL_RISK,
						NoFallRiskTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(NoFallRiskTable.TABLE_NOFALL_RISK,
						NoFallRiskTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case RISK_STANDARDS_FOREIGN:
			rowsDeleted = sqlDB.delete(ForeignStandardsTable.TABLE_FOREIGN_STANDARD,
					selection, selectionArgs);
			break;
		case RISK_STANDARDS_FOREIGN_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(ForeignStandardsTable.TABLE_FOREIGN_STANDARD,
						ForeignStandardsTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(ForeignStandardsTable.TABLE_FOREIGN_STANDARD,
						ForeignStandardsTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case SURVEY:
			rowsDeleted = sqlDB.delete(SurveySpecTable.TABLE_SURVEY_SPEC,
					selection, selectionArgs);
			break;
		case SURVEY_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveySpecTable.TABLE_SURVEY_SPEC,
						SurveySpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveySpecTable.TABLE_SURVEY_SPEC,
						SurveySpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SURVEY_LOG:
			rowsDeleted = sqlDB.delete(SurveyLogTable.TABLE_SURVEY_LOG,
					selection, selectionArgs);
			break;
		case SURVEY_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyLogTable.TABLE_SURVEY_LOG,
						SurveyLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyLogTable.TABLE_SURVEY_LOG,
						SurveyLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SURVEY_ANSWER_LOG:
			rowsDeleted = sqlDB.delete(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG, selection,
					selectionArgs);
			break;
		case SURVEY_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG,
						SurveyAnswerLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG,
						SurveyAnswerLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case SURVEY_QUESTION:
			rowsDeleted = sqlDB.delete(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC,
					selection, selectionArgs);
			break;
		case SURVEY_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC,
						SurveyQuestionSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC,
						SurveyQuestionSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case SURVEY_QUESTION_RISK:
			rowsDeleted = sqlDB.delete(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC,
					selection, selectionArgs);
			break;
		case SURVEY_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC,
						SurveyQRiskSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC,
						SurveyQRiskSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST:
			rowsDeleted = sqlDB.delete(TestSpecTable.TABLE_TEST_SPEC,
					selection, selectionArgs);
			break;
		case TEST_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestSpecTable.TABLE_TEST_SPEC,
						TestSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveySpecTable.TABLE_SURVEY_SPEC,
						TestSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case TEST_LOG:
			rowsDeleted = sqlDB.delete(TestLogTable.TABLE_TEST_LOG,
					selection, selectionArgs);
			break;
		case TEST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestLogTable.TABLE_TEST_LOG,
						TestLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestLogTable.TABLE_TEST_LOG,
						TestLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case TEST_ANSWER_LOG:
			rowsDeleted = sqlDB.delete(TestAnswerLogTable.TABLE_ANSWER_LOG, selection,
					selectionArgs);
			break;
		case TEST_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestAnswerLogTable.TABLE_ANSWER_LOG,
						TestAnswerLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestAnswerLogTable.TABLE_ANSWER_LOG,
						TestAnswerLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TEST_QUESTION:
			rowsDeleted = sqlDB.delete(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC,
					selection, selectionArgs);
			break;
		case TEST_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC,
						TestQuestionSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC,
						TestQuestionSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST_QUESTION_RISK:
			rowsDeleted = sqlDB.delete(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC,
					selection, selectionArgs);
			break;
		case TEST_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC,
						TestRiskQuestionSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC,
						TestRiskQuestionSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST_MEASURE:
			rowsDeleted = sqlDB.delete(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC, selection,
					selectionArgs);
			break;
		case TEST_MEASURE_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC,
						TestMeasureSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC,
						TestMeasureSpecTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TEST_MEASURE_LOG:
			rowsDeleted = sqlDB.delete(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG,
					selection, selectionArgs);
			break;
		case TEST_MEASURE_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG,
						TestMeasureLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG,
						TestMeasureLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST_MEASURE_RISK:
			rowsDeleted = sqlDB.delete(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC,
					selection, selectionArgs);
			break;
		case TEST_MEASURE_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC,
						TestMeasureRiskSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC,
						TestMeasureRiskSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		String id;
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;

		switch (uriType) {
		case USER:
			rowsUpdated = sqlDB.update(UserTable.TABLE_USER, values, selection,
					selectionArgs);
			break;
		case USER_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(UserTable.TABLE_USER, values,
						UserTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(UserTable.TABLE_USER, values,
						UserTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case USER_TOTAL_RISK:
			rowsUpdated = sqlDB.update(UserTable.TABLE_USER, values, selection,
					selectionArgs);
			break;
		case USER_TOTAL_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TotalRiskLogTable.TABLE_TOTAL_RISK, values,
						TotalRiskLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TotalRiskLogTable.TABLE_TOTAL_RISK, values,
						TotalRiskLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case MED:
			rowsUpdated = sqlDB.update(MedicationSpecTable.TABLE_MED, values,
					selection, selectionArgs);
			break;
		case MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationSpecTable.TABLE_MED,
						values, MedicationSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationSpecTable.TABLE_MED,
						values, MedicationSpecTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case MED_CAT:
			rowsUpdated = sqlDB.update(MedCategorySpecTable.TABLE_MED_CAT,
					values, selection, selectionArgs);
			break;
		case MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB
						.update(MedCategorySpecTable.TABLE_MED_CAT, values,
								MedCategorySpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedCategorySpecTable.TABLE_MED_CAT,
						values, MedCategorySpecTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case MED_LOG:
			rowsUpdated = sqlDB.update(MedLogTable.TABLE_MED_LOG, values,
					selection, selectionArgs);
			break;
		case MED_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedLogTable.TABLE_MED_LOG, values,
						MedLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedLogTable.TABLE_MED_LOG, values,
						MedLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case MED_LIST_LOG:
			rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_LIST_LOG,
					values, selection, selectionArgs);
			break;
		case MED_LIST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_LIST_LOG,
						values, MedListLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_LIST_LOG,
						values, MedListLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SENS_SPEC:
			rowsUpdated = sqlDB.update(SensorSpecTable.TABLE_SENSOR_SPEC,
					values, selection, selectionArgs);
			break;
		case SENS_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SensorSpecTable.TABLE_SENSOR_SPEC,
						values, SensorSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_LIST_LOG,
						values, SensorSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SENS_RISK_SPEC:
			rowsUpdated = sqlDB.update(
					SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC, values,
					selection, selectionArgs);
			break;
		case SENS_RISK_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(
						SensorRiskSpecTable.TABLE_SENSOR_RISK_SPEC, values,
						SensorRiskSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_LIST_LOG,
						values, SensorRiskSpecTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case SENS_LOG:
			rowsUpdated = sqlDB.update(SensorLogTable.TABLE_SENSOR_LOG,
					values, selection, selectionArgs);
			break;
		case SENS_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SensorLogTable.TABLE_SENSOR_LOG,
						values, SensorLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_LIST_LOG,
						values, SensorLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case RISK_STANDARDS:
			rowsUpdated = sqlDB.update(StandardsTable.TABLE_STANDARDS, values,
					selection, selectionArgs);
			break;
		case RISK_STANDARDS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(StandardsTable.TABLE_STANDARDS,
						values, StandardsTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(StandardsTable.TABLE_STANDARDS,
						values, StandardsTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case RISK_STANDARDS_MAP:
			rowsUpdated = sqlDB.update(RiskStandardMapTable.TABLE_RISK_STAND_MAP,
					values, selection, selectionArgs);
			break;
		case RISK_STANDARDS_MAP_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(RiskStandardMapTable.TABLE_RISK_STAND_MAP, values,
						RiskStandardMapTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(RiskStandardMapTable.TABLE_RISK_STAND_MAP,
						values, RiskStandardMapTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case RISK_STANDARDS_NOFALL:
			rowsUpdated = sqlDB.update(NoFallRiskTable.TABLE_NOFALL_RISK, values,
					selection, selectionArgs);
			break;
		case RISK_STANDARDS_NOFALL_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(NoFallRiskTable.TABLE_NOFALL_RISK, values,
						NoFallRiskTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(NoFallRiskTable.TABLE_NOFALL_RISK, values,
						NoFallRiskTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case RISK_STANDARDS_FOREIGN:
			rowsUpdated = sqlDB.update(ForeignStandardsTable.TABLE_FOREIGN_STANDARD,
					values, selection, selectionArgs);
			break;
		case RISK_STANDARDS_FOREIGN_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(ForeignStandardsTable.TABLE_FOREIGN_STANDARD,
						values, ForeignStandardsTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(ForeignStandardsTable.TABLE_FOREIGN_STANDARD,
						values, ForeignStandardsTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		case SURVEY:
			rowsUpdated = sqlDB.update(SurveySpecTable.TABLE_SURVEY_SPEC, values,
					selection, selectionArgs);
			break;
		case SURVEY_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveySpecTable.TABLE_SURVEY_SPEC,
						values, SurveySpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveySpecTable.TABLE_SURVEY_SPEC,
						values, SurveySpecTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case SURVEY_LOG:
			rowsUpdated = sqlDB.update(SurveyLogTable.TABLE_SURVEY_LOG,
					values, selection, selectionArgs);
			break;
		case SURVEY_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyLogTable.TABLE_SURVEY_LOG, values,
						SurveyLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyLogTable.TABLE_SURVEY_LOG,
						values, SurveyLogTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case SURVEY_ANSWER_LOG:
			rowsUpdated = sqlDB.update(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG, values,
					selection, selectionArgs);
			break;
		case SURVEY_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG, values,
						SurveyAnswerLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyAnswerLogTable.TABLE_SURVEY_ANSWER_LOG, values,
						SurveyAnswerLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case SURVEY_QUESTION:
			rowsUpdated = sqlDB.update(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC,
					values, selection, selectionArgs);
			break;
		case SURVEY_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC,
						values, SurveyQuestionSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyQuestionSpecTable.TABLE_SURVEY_QUESTION_SPEC,
						values, SurveyQuestionSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case SURVEY_QUESTION_RISK:
			rowsUpdated = sqlDB.update(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC,
					values, selection, selectionArgs);
			break;
		case SURVEY_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC,
						values, SurveyQRiskSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyQRiskSpecTable.TABLE_SURVEY_Q_RISK_SPEC,
						values, SurveyQRiskSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST:
			rowsUpdated = sqlDB.update(TestSpecTable.TABLE_TEST_SPEC, values,
					selection, selectionArgs);
			break;
		case TEST_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestSpecTable.TABLE_TEST_SPEC,
						values, TestSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestSpecTable.TABLE_TEST_SPEC,
						values, TestSpecTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case TEST_LOG:
			rowsUpdated = sqlDB.update(TestLogTable.TABLE_TEST_LOG,
					values, selection, selectionArgs);
			break;
		case TEST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestLogTable.TABLE_TEST_LOG, values,
						TestLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyLogTable.TABLE_SURVEY_LOG,
						values, TestLogTable.COLUMN_ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case TEST_ANSWER_LOG:
			rowsUpdated = sqlDB.update(TestAnswerLogTable.TABLE_ANSWER_LOG, values,
					selection, selectionArgs);
			break;
		case TEST_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestAnswerLogTable.TABLE_ANSWER_LOG, values,
						TestAnswerLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestAnswerLogTable.TABLE_ANSWER_LOG, values,
						TestAnswerLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TEST_QUESTION:
			rowsUpdated = sqlDB.update(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC,
					values, selection, selectionArgs);
			break;
		case TEST_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC,
						values, TestQuestionSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestQuestionSpecTable.TABLE_TEST_QUESTION_SPEC,
						values, TestQuestionSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST_QUESTION_RISK:
			rowsUpdated = sqlDB.update(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC,
					values, selection, selectionArgs);
			break;
		case TEST_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC,
						values, TestRiskQuestionSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestRiskQuestionSpecTable.TABLE_TEST_Q_RISK_SPEC,
						values, TestRiskQuestionSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST_MEASURE:
			rowsUpdated = sqlDB.update(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC, values,
					selection, selectionArgs);
			break;
		case TEST_MEASURE_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC, values,
						TestMeasureSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestMeasureSpecTable.TABLE_TEST_MEASURE_SPEC, values,
						TestMeasureSpecTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TEST_MEASURE_LOG:
			rowsUpdated = sqlDB.update(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG,
					values, selection, selectionArgs);
			break;
		case TEST_MEASURE_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG,
						values, TestMeasureLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestMeasureLogTable.TABLE_TEST_MEASURE_LOG,
						values, TestMeasureLogTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TEST_MEASURE_RISK:
			rowsUpdated = sqlDB.update(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC,
					values, selection, selectionArgs);
			break;
		case TEST_MEASURE_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC,
						values, TestMeasureRiskSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestMeasureRiskSpecTable.TABLE_TEST_MEASURE_RISK_SPEC,
						values, TestMeasureRiskSpecTable.COLUMN_ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkUserColumns(String[] projection) {
		String[] available = { UserTable.COLUMN_AGE, UserTable.COLUMN_NAME,
				UserTable.COLUMN_ID };

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}

	private void checkColumnsMed(String[] projection) {
		String[] available = { MedicationSpecTable.COLUMN_NAME,
				MedicationSpecTable.COLUMN_FK_CATEGORY,
				MedicationSpecTable.COLUMN_FK_ID, MedicationSpecTable.COLUMN_ID };

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}
}
