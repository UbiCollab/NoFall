package ntnu.master.nofall.contentprovider;

import ntnu.master.nofall.contentprovider.provider.Authority;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationCategorySpec;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationListLog;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationLog;
import ntnu.master.nofall.contentprovider.provider.Medication.MedicationSpec;
import ntnu.master.nofall.contentprovider.provider.Sensor.SensorLog;
import ntnu.master.nofall.contentprovider.provider.Sensor.SensorRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Sensor.SensorSpec;
import ntnu.master.nofall.contentprovider.provider.Standard.Standards;
import ntnu.master.nofall.contentprovider.provider.Standard.StandardsForeign;
import ntnu.master.nofall.contentprovider.provider.Standard.StandardsNoFall;
import ntnu.master.nofall.contentprovider.provider.Standard.StandardsRiskMap;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyAnswerLog;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyLog;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyQuestionRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveyQuestionSpec;
import ntnu.master.nofall.contentprovider.provider.Survey.SurveySpec;
import ntnu.master.nofall.contentprovider.provider.Test.TestAnswerLog;
import ntnu.master.nofall.contentprovider.provider.Test.TestLog;
import ntnu.master.nofall.contentprovider.provider.Test.TestMeasureLog;
import ntnu.master.nofall.contentprovider.provider.Test.TestMeasureRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Test.TestMeasureSpec;
import ntnu.master.nofall.contentprovider.provider.Test.TestQuestionRiskSpec;
import ntnu.master.nofall.contentprovider.provider.Test.TestQuestionSpec;
import ntnu.master.nofall.contentprovider.provider.Test.TestSpec;
import ntnu.master.nofall.contentprovider.provider.Users.User;
import ntnu.master.nofall.contentprovider.provider.Users.UserTotalRisk;
import ntnu.master.nofall.database.NoFallDBHelper;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class NoFallProvider extends ContentProvider {
	// database
	private NoFallDBHelper database;	
	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		// User
		sURIMatcher.addURI(Authority.AUTHORITY, User.TABLE_NAME, User.USER);
		sURIMatcher.addURI(Authority.AUTHORITY, User.TABLE_NAME + "/#", User.USER_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, UserTotalRisk.TABLE_NAME, UserTotalRisk.USER_TOTAL_RISK);
		sURIMatcher.addURI(Authority.AUTHORITY, UserTotalRisk.TABLE_NAME + "/#", UserTotalRisk.USER_TOTAL_RISK_ID);

		// Medication
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationSpec.TABLE_NAME, MedicationSpec.MED);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationSpec.TABLE_NAME + "/#", MedicationSpec.MED_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationCategorySpec.TABLE_NAME, MedicationCategorySpec.MED_CAT);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationCategorySpec.TABLE_NAME + "/#", MedicationCategorySpec.MED_CAT_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationLog.TABLE_NAME, MedicationLog.MED_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationLog.TABLE_NAME + "/#", MedicationLog.MED_LOG_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationListLog.TABLE_NAME, MedicationListLog.MED_LIST_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, MedicationListLog.TABLE_NAME + "/#", MedicationListLog.MED_LIST_LOG_ID);

		// Sensor
		sURIMatcher.addURI(Authority.AUTHORITY, SensorSpec.TABLE_NAME, SensorSpec.SENS_SPEC);
		sURIMatcher.addURI(Authority.AUTHORITY, SensorSpec.TABLE_NAME + "/#", SensorSpec.SENS_SPEC_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, SensorRiskSpec.TABLE_NAME, SensorRiskSpec.SENS_RISK_SPEC);
		sURIMatcher.addURI(Authority.AUTHORITY, SensorRiskSpec.TABLE_NAME + "/#", SensorRiskSpec.SENS_RISK_SPEC_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, SensorLog.TABLE_NAME, SensorLog.SENS_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, SensorLog.TABLE_NAME + "/#", SensorLog.SENS_LOG_ID);
		
		// Standards,
		sURIMatcher.addURI(Authority.AUTHORITY, Standards.TABLE_NAME, Standards.RISK_STANDARDS);
		sURIMatcher.addURI(Authority.AUTHORITY, Standards.TABLE_NAME + "/#", Standards.RISK_STANDARDS_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, StandardsRiskMap.TABLE_NAME, StandardsRiskMap.RISK_STANDARDS_MAP);
		sURIMatcher.addURI(Authority.AUTHORITY, StandardsRiskMap.TABLE_NAME + "/#", StandardsRiskMap.RISK_STANDARDS_MAP_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, StandardsNoFall.TABLE_NAME, StandardsNoFall.RISK_STANDARDS_NOFALL);
		sURIMatcher.addURI(Authority.AUTHORITY, StandardsNoFall.TABLE_NAME + "/#", StandardsNoFall.RISK_STANDARDS_NOFALL_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, StandardsForeign.TABLE_NAME, StandardsForeign.RISK_STANDARDS_FOREIGN);
		sURIMatcher.addURI(Authority.AUTHORITY, StandardsForeign.TABLE_NAME + "/#", StandardsForeign.RISK_STANDARDS_FOREIGN_ID);
		
		// Survey
		sURIMatcher.addURI(Authority.AUTHORITY, SurveySpec.TABLE_NAME, SurveySpec.SURVEY);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveySpec.TABLE_NAME + "/#", SurveySpec.SURVEY_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyLog.TABLE_NAME, SurveyLog.SURVEY_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyLog.TABLE_NAME + "/#", SurveyLog.SURVEY_LOG_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyAnswerLog.TABLE_NAME, SurveyAnswerLog.SURVEY_ANSWER_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyAnswerLog.TABLE_NAME + "/#", SurveyAnswerLog.SURVEY_ANSWER_LOG_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyQuestionSpec.TABLE_NAME, SurveyQuestionSpec.SURVEY_QUESTION);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyQuestionSpec.TABLE_NAME + "/#", SurveyQuestionSpec.SURVEY_QUESTION_ID);		
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyQuestionRiskSpec.TABLE_NAME, SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK);
		sURIMatcher.addURI(Authority.AUTHORITY, SurveyQuestionRiskSpec.TABLE_NAME + "/#", SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK_ID);
		
		// Test
		sURIMatcher.addURI(Authority.AUTHORITY, TestSpec.TABLE_NAME, TestSpec.TEST);
		sURIMatcher.addURI(Authority.AUTHORITY, TestSpec.TABLE_NAME + "/#", TestSpec.TEST_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, TestLog.TABLE_NAME, TestLog.TEST_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, TestLog.TABLE_NAME + "/#", TestLog.TEST_LOG_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, TestAnswerLog.TABLE_NAME, TestAnswerLog.TEST_ANSWER_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, TestAnswerLog.TABLE_NAME + "/#", TestAnswerLog.TEST_ANSWER_LOG_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, TestQuestionSpec.TABLE_NAME, TestQuestionSpec.TEST_QUESTION);
		sURIMatcher.addURI(Authority.AUTHORITY, TestQuestionSpec.TABLE_NAME + "/#", TestQuestionSpec.TEST_QUESTION_ID);		
		sURIMatcher.addURI(Authority.AUTHORITY, TestQuestionRiskSpec.TABLE_NAME, TestQuestionRiskSpec.TEST_QUESTION_RISK);
		sURIMatcher.addURI(Authority.AUTHORITY, TestQuestionRiskSpec.TABLE_NAME + "/#", TestQuestionRiskSpec.TEST_QUESTION_RISK_ID);		
		sURIMatcher.addURI(Authority.AUTHORITY, TestMeasureSpec.TABLE_NAME, TestMeasureSpec.TEST_MEASURE);
		sURIMatcher.addURI(Authority.AUTHORITY, TestMeasureSpec.TABLE_NAME + "/#", TestMeasureSpec.TEST_MEASURE_ID);
		sURIMatcher.addURI(Authority.AUTHORITY, TestMeasureLog.TABLE_NAME, TestMeasureLog.TEST_MEASURE_LOG);
		sURIMatcher.addURI(Authority.AUTHORITY, TestMeasureLog.TABLE_NAME + "/#", TestMeasureLog.TEST_MEASURE_LOG_ID);		
		sURIMatcher.addURI(Authority.AUTHORITY, TestMeasureRiskSpec.TABLE_NAME, TestMeasureRiskSpec.TEST_MEASURE_RISK);
		sURIMatcher.addURI(Authority.AUTHORITY, TestMeasureRiskSpec.TABLE_NAME + "/#", TestMeasureRiskSpec.TEST_MEASURE_RISK_ID);
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

		case User.USER:
			//checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(User.TABLE_NAME);
			break;
		case User.USER_ID:
			// check if the caller has requested a column which does not exists
			//checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(User.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(User._ID + "="
					+ uri.getLastPathSegment());
			break;
		case UserTotalRisk.USER_TOTAL_RISK:
			//checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(UserTotalRisk.TABLE_NAME);
			break;
		case UserTotalRisk.USER_TOTAL_RISK_ID:
			// check if the caller has requested a column which does not exists
			//checkUserColumns(projection);
			// Set the table
			queryBuilder.setTables(UserTotalRisk.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(UserTotalRisk._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationSpec.MED:
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationSpec.TABLE_NAME);
			break;
		case MedicationSpec.MED_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationCategorySpec.MED_CAT:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationCategorySpec.TABLE_NAME);
			break;
		case MedicationCategorySpec.MED_CAT_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationCategorySpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationCategorySpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationLog.MED_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationLog.TABLE_NAME);
			break;
		case MedicationLog.MED_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationListLog.MED_LIST_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationListLog.TABLE_NAME);
			break;
		case MedicationListLog.MED_LIST_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationListLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationListLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SensorSpec.SENS_SPEC:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorSpec.TABLE_NAME);
			break;
		case SensorSpec.SENS_SPEC_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SensorRiskSpec.SENS_RISK_SPEC:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorRiskSpec.TABLE_NAME);
			break;
		case SensorRiskSpec.SENS_RISK_SPEC_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorRiskSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorRiskSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SensorLog.SENS_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorLog.TABLE_NAME);
			break;
		case SensorLog.SENS_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SensorLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorLog._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case Standards.RISK_STANDARDS:
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(Standards.TABLE_NAME);
			break;
		case Standards.RISK_STANDARDS_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(Standards.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(Standards._ID + "="
					+ uri.getLastPathSegment());
			break;

		case StandardsRiskMap.RISK_STANDARDS_MAP:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsRiskMap.TABLE_NAME);
			break;
		case StandardsRiskMap.RISK_STANDARDS_MAP_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsRiskMap.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(StandardsRiskMap._ID + "="
					+ uri.getLastPathSegment());
			break;

		case StandardsNoFall.RISK_STANDARDS_NOFALL:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsNoFall.TABLE_NAME);
			break;
		case StandardsNoFall.RISK_STANDARDS_NOFALL_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsNoFall.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(StandardsNoFall._ID + "="
					+ uri.getLastPathSegment());
			break;

		case StandardsForeign.RISK_STANDARDS_FOREIGN:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsForeign.TABLE_NAME);
			break;
		case StandardsForeign.RISK_STANDARDS_FOREIGN_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(StandardsForeign.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(StandardsForeign._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case SurveySpec.SURVEY:
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveySpec.TABLE_NAME);
			break;
		case SurveySpec.SURVEY_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveySpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveySpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SurveyLog.SURVEY_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyLog.TABLE_NAME);
			break;
		case SurveyLog.SURVEY_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SurveyAnswerLog.SURVEY_ANSWER_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyAnswerLog.TABLE_NAME);
			break;
		case SurveyAnswerLog.SURVEY_ANSWER_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyAnswerLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyAnswerLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SurveyQuestionSpec.SURVEY_QUESTION:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQuestionSpec.TABLE_NAME);
			break;
		case SurveyQuestionSpec.SURVEY_QUESTION_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQuestionSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyQuestionSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQuestionRiskSpec.TABLE_NAME);
			break;
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(SurveyQuestionRiskSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyQuestionRiskSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestSpec.TEST:
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestSpec.TABLE_NAME);
			break;
		case TestSpec.TEST_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestLog.TEST_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestLog.TABLE_NAME);
			break;
		case TestLog.TEST_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestAnswerLog.TEST_ANSWER_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestAnswerLog.TABLE_NAME);
			break;
		case TestAnswerLog.TEST_ANSWER_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestAnswerLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestAnswerLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestQuestionSpec.TEST_QUESTION:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestQuestionSpec.TABLE_NAME);
			break;
		case TestQuestionSpec.TEST_QUESTION_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestQuestionSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestQuestionSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestQuestionRiskSpec.TEST_QUESTION_RISK:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestQuestionRiskSpec.TABLE_NAME);
			break;
		case TestQuestionRiskSpec.TEST_QUESTION_RISK_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestQuestionRiskSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestQuestionRiskSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestMeasureSpec.TEST_MEASURE:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureSpec.TABLE_NAME);
			break;
		case TestMeasureSpec.TEST_MEASURE_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestMeasureLog.TEST_MEASURE_LOG:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureLog.TABLE_NAME);
			break;
		case TestMeasureLog.TEST_MEASURE_LOG_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureLog._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestMeasureRiskSpec.TEST_MEASURE_RISK:
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureRiskSpec.TABLE_NAME);
			break;
		case TestMeasureRiskSpec.TEST_MEASURE_RISK_ID:
			// check if the caller has requested a column which does not exists
			// checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(TestMeasureRiskSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureRiskSpec._ID + "="
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

		case User.USER:
			id = sqlDB.insert(User.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(User.TABLE_NAME + "/" + id);
		
		case UserTotalRisk.USER_TOTAL_RISK:
			id = sqlDB.insert(UserTotalRisk.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(UserTotalRisk.TABLE_NAME + "/" + id);
						
		case MedicationSpec.MED:
			id = sqlDB.insert(MedicationSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationSpec.TABLE_NAME + "/" + id);

		case MedicationCategorySpec.MED_CAT:
			id = sqlDB.insert(MedicationCategorySpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationCategorySpec.TABLE_NAME + "/" + id);

		case MedicationLog.MED_LOG:
			id = sqlDB.insert(MedicationLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationLog.TABLE_NAME + "/" + id);

		case MedicationListLog.MED_LIST_LOG:
			id = sqlDB.insert(MedicationListLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationListLog.TABLE_NAME + "/" + id);

		case SensorSpec.SENS_SPEC:
			id = sqlDB
					.insert(SensorSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SensorSpec.TABLE_NAME + "/" + id);

		case SensorRiskSpec.SENS_RISK_SPEC:
			id = sqlDB.insert(SensorRiskSpec.TABLE_NAME,
					null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SensorRiskSpec.TABLE_NAME + "/" + id);

		case SensorLog.SENS_LOG:
			id = sqlDB.insert(SensorLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SensorLog.TABLE_NAME + "/" + id);
			
		case Standards.RISK_STANDARDS:
			id = sqlDB.insert(Standards.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(Standards.TABLE_NAME + "/" + id);

		case StandardsRiskMap.RISK_STANDARDS_MAP:
			id = sqlDB.insert(StandardsRiskMap.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(StandardsRiskMap.TABLE_NAME + "/" + id);

		case StandardsNoFall.RISK_STANDARDS_NOFALL:
			id = sqlDB.insert(StandardsNoFall.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(StandardsNoFall.TABLE_NAME + "/" + id);

		case StandardsForeign.RISK_STANDARDS_FOREIGN:
			id = sqlDB.insert(StandardsForeign.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(StandardsForeign.TABLE_NAME + "/" + id);
			
		case SurveySpec.SURVEY:
			id = sqlDB.insert(SurveySpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SurveySpec.TABLE_NAME + "/" + id);

		case SurveyLog.SURVEY_LOG:
			id = sqlDB.insert(SurveyLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SurveyLog.TABLE_NAME + "/" + id);

		case SurveyAnswerLog.SURVEY_ANSWER_LOG:
			id = sqlDB.insert(SurveyAnswerLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SurveyAnswerLog.TABLE_NAME + "/" + id);

		case SurveyQuestionSpec.SURVEY_QUESTION:
			id = sqlDB.insert(SurveyQuestionSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SurveyQuestionSpec.TABLE_NAME + "/" + id);
			
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK:
			id = sqlDB.insert(SurveyQuestionRiskSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SurveyQuestionRiskSpec.TABLE_NAME + "/" + id);
			
		case TestSpec.TEST:
			id = sqlDB.insert(TestSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestSpec.TABLE_NAME + "/" + id);

		case TestLog.TEST_LOG:
			id = sqlDB.insert(TestLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestLog.TABLE_NAME + "/" + id);

		case TestAnswerLog.TEST_ANSWER_LOG:
			id = sqlDB.insert(TestAnswerLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestAnswerLog.TABLE_NAME + "/" + id);

		case TestQuestionSpec.TEST_QUESTION:
			id = sqlDB.insert(TestQuestionSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestQuestionSpec.TABLE_NAME + "/" + id);
			
		case TestQuestionRiskSpec.TEST_QUESTION_RISK:
			id = sqlDB.insert(TestQuestionRiskSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestQuestionRiskSpec.TABLE_NAME + "/" + id);
			
		case TestMeasureSpec.TEST_MEASURE:
			id = sqlDB.insert(TestMeasureSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestMeasureSpec.TABLE_NAME + "/" + id);

		case TestMeasureLog.TEST_MEASURE_LOG:
			id = sqlDB.insert(TestMeasureLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestMeasureLog.TABLE_NAME + "/" + id);
			
		case TestMeasureRiskSpec.TEST_MEASURE_RISK:
			id = sqlDB.insert(TestMeasureRiskSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(TestMeasureRiskSpec.TABLE_NAME + "/" + id);
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
		case User.USER:
			rowsDeleted = sqlDB.delete(User.TABLE_NAME, selection,
					selectionArgs);
			break;
		case User.USER_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(User.TABLE_NAME,
						User._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(User.TABLE_NAME,
						User._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case UserTotalRisk.USER_TOTAL_RISK:
			rowsDeleted = sqlDB.delete(UserTotalRisk.TABLE_NAME, selection,
					selectionArgs);
			break;
		case UserTotalRisk.USER_TOTAL_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(UserTotalRisk.TABLE_NAME,
						UserTotalRisk._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(UserTotalRisk.TABLE_NAME,
						UserTotalRisk._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MedicationSpec.MED:
			rowsDeleted = sqlDB.delete(MedicationSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MedicationSpec.MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationSpec.TABLE_NAME,
						MedicationSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationSpec.TABLE_NAME,
						MedicationSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case MedicationCategorySpec.MED_CAT:
			rowsDeleted = sqlDB.delete(MedicationCategorySpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MedicationCategorySpec.MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationCategorySpec.TABLE_NAME,
						MedicationCategorySpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationCategorySpec.TABLE_NAME,
						MedicationCategorySpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case MedicationLog.MED_LOG:
			rowsDeleted = sqlDB.delete(MedicationLog.TABLE_NAME, selection,
					selectionArgs);
			break;
		case MedicationLog.MED_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationLog.TABLE_NAME,
						MedicationLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationLog.TABLE_NAME,
						MedicationLog._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case MedicationListLog.MED_LIST_LOG:
			rowsDeleted = sqlDB.delete(MedicationListLog.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MedicationListLog.MED_LIST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationListLog.TABLE_NAME,
						MedicationListLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationListLog.TABLE_NAME,
						MedicationListLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SensorSpec.SENS_SPEC:
			rowsDeleted = sqlDB.delete(SensorSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case SensorSpec.SENS_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SensorSpec.TABLE_NAME,
						SensorSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorSpec.TABLE_NAME,
						SensorSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SensorRiskSpec.SENS_RISK_SPEC:
			rowsDeleted = sqlDB.delete(
					SensorRiskSpec.TABLE_NAME, selection,
					selectionArgs);
			break;
		case SensorRiskSpec.SENS_RISK_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(
						SensorRiskSpec.TABLE_NAME,
						SensorRiskSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorRiskSpec.TABLE_NAME,
						SensorRiskSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SensorLog.SENS_LOG:
			rowsDeleted = sqlDB.delete(SensorLog.TABLE_NAME,
					selection, selectionArgs);
			break;
		case SensorLog.SENS_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SensorLog.TABLE_NAME,
						SensorLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorLog.TABLE_NAME,
						SensorLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case Standards.RISK_STANDARDS:
			rowsDeleted = sqlDB.delete(Standards.TABLE_NAME,
					selection, selectionArgs);
			break;
		case Standards.RISK_STANDARDS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(Standards.TABLE_NAME,
						Standards._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(Standards.TABLE_NAME,
						Standards._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case StandardsRiskMap.RISK_STANDARDS_MAP:
			rowsDeleted = sqlDB.delete(StandardsRiskMap.TABLE_NAME,
					selection, selectionArgs);
			break;
		case StandardsRiskMap.RISK_STANDARDS_MAP_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(StandardsRiskMap.TABLE_NAME,
						StandardsRiskMap._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(StandardsRiskMap.TABLE_NAME,
						StandardsRiskMap._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case StandardsNoFall.RISK_STANDARDS_NOFALL:
			rowsDeleted = sqlDB.delete(StandardsNoFall.TABLE_NAME, selection,
					selectionArgs);
			break;
		case StandardsNoFall.RISK_STANDARDS_NOFALL_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(StandardsNoFall.TABLE_NAME,
						StandardsNoFall._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(StandardsNoFall.TABLE_NAME,
						StandardsNoFall._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case StandardsForeign.RISK_STANDARDS_FOREIGN:
			rowsDeleted = sqlDB.delete(StandardsForeign.TABLE_NAME,
					selection, selectionArgs);
			break;
		case StandardsForeign.RISK_STANDARDS_FOREIGN_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(StandardsForeign.TABLE_NAME,
						StandardsForeign._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(StandardsForeign.TABLE_NAME,
						StandardsForeign._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case SurveySpec.SURVEY:
			rowsDeleted = sqlDB.delete(SurveySpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case SurveySpec.SURVEY_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveySpec.TABLE_NAME,
						SurveySpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveySpec.TABLE_NAME,
						SurveySpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SurveyLog.SURVEY_LOG:
			rowsDeleted = sqlDB.delete(SurveyLog.TABLE_NAME,
					selection, selectionArgs);
			break;
		case SurveyLog.SURVEY_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyLog.TABLE_NAME,
						SurveyLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyLog.TABLE_NAME,
						SurveyLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SurveyAnswerLog.SURVEY_ANSWER_LOG:
			rowsDeleted = sqlDB.delete(SurveyAnswerLog.TABLE_NAME, selection,
					selectionArgs);
			break;
		case SurveyAnswerLog.SURVEY_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyAnswerLog.TABLE_NAME,
						SurveyAnswerLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyAnswerLog.TABLE_NAME,
						SurveyAnswerLog._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case SurveyQuestionSpec.SURVEY_QUESTION:
			rowsDeleted = sqlDB.delete(SurveyQuestionSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case SurveyQuestionSpec.SURVEY_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyQuestionSpec.TABLE_NAME,
						SurveyQuestionSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyQuestionSpec.TABLE_NAME,
						SurveyQuestionSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK:
			rowsDeleted = sqlDB.delete(SurveyQuestionRiskSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(SurveyQuestionRiskSpec.TABLE_NAME,
						SurveyQuestionRiskSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SurveyQuestionRiskSpec.TABLE_NAME,
						SurveyQuestionRiskSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestSpec.TEST:
			rowsDeleted = sqlDB.delete(TestSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case TestSpec.TEST_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestSpec.TABLE_NAME,
						TestSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestSpec.TABLE_NAME,
						TestSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case TestLog.TEST_LOG:
			rowsDeleted = sqlDB.delete(TestLog.TABLE_NAME,
					selection, selectionArgs);
			break;
		case TestLog.TEST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestLog.TABLE_NAME,
						TestLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestLog.TABLE_NAME,
						TestLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case TestAnswerLog.TEST_ANSWER_LOG:
			rowsDeleted = sqlDB.delete(TestAnswerLog.TABLE_NAME, selection,
					selectionArgs);
			break;
		case TestAnswerLog.TEST_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestAnswerLog.TABLE_NAME,
						TestAnswerLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestAnswerLog.TABLE_NAME,
						TestAnswerLog._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TestQuestionSpec.TEST_QUESTION:
			rowsDeleted = sqlDB.delete(TestQuestionSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case TestQuestionSpec.TEST_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestQuestionSpec.TABLE_NAME,
						TestQuestionSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestQuestionSpec.TABLE_NAME,
						TestQuestionSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestQuestionRiskSpec.TEST_QUESTION_RISK:
			rowsDeleted = sqlDB.delete(TestQuestionRiskSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case TestQuestionRiskSpec.TEST_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestQuestionRiskSpec.TABLE_NAME,
						TestQuestionRiskSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestQuestionRiskSpec.TABLE_NAME,
						TestQuestionRiskSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestMeasureSpec.TEST_MEASURE:
			rowsDeleted = sqlDB.delete(TestMeasureSpec.TABLE_NAME, selection,
					selectionArgs);
			break;
		case TestMeasureSpec.TEST_MEASURE_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestMeasureSpec.TABLE_NAME,
						TestMeasureSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestMeasureSpec.TABLE_NAME,
						TestMeasureSpec._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TestMeasureLog.TEST_MEASURE_LOG:
			rowsDeleted = sqlDB.delete(TestMeasureLog.TABLE_NAME,
					selection, selectionArgs);
			break;
		case TestMeasureLog.TEST_MEASURE_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestMeasureLog.TABLE_NAME,
						TestMeasureLog._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestMeasureLog.TABLE_NAME,
						TestMeasureLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestMeasureRiskSpec.TEST_MEASURE_RISK:
			rowsDeleted = sqlDB.delete(TestMeasureRiskSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case TestMeasureRiskSpec.TEST_MEASURE_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TestMeasureRiskSpec.TABLE_NAME,
						TestMeasureRiskSpec._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TestMeasureRiskSpec.TABLE_NAME,
						TestMeasureRiskSpec._ID + "=" + id + " and "
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
		case User.USER:
			rowsUpdated = sqlDB.update(User.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case User.USER_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(User.TABLE_NAME, values,
						User._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(User.TABLE_NAME, values,
						User._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case UserTotalRisk.USER_TOTAL_RISK:
			rowsUpdated = sqlDB.update(UserTotalRisk.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case UserTotalRisk.USER_TOTAL_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(UserTotalRisk.TABLE_NAME, values,
						UserTotalRisk._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(UserTotalRisk.TABLE_NAME, values,
						UserTotalRisk._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case MedicationSpec.MED:
			rowsUpdated = sqlDB.update(MedicationSpec.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case MedicationSpec.MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationSpec.TABLE_NAME,
						values, MedicationSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationSpec.TABLE_NAME,
						values, MedicationSpec._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case MedicationCategorySpec.MED_CAT:
			rowsUpdated = sqlDB.update(MedicationCategorySpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case MedicationCategorySpec.MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB
						.update(MedicationCategorySpec.TABLE_NAME, values,
								MedicationCategorySpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationCategorySpec.TABLE_NAME,
						values, MedicationCategorySpec._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case MedicationLog.MED_LOG:
			rowsUpdated = sqlDB.update(MedicationLog.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case MedicationLog.MED_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationLog.TABLE_NAME, values,
						MedicationLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationLog.TABLE_NAME, values,
						MedicationLog._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case MedicationListLog.MED_LIST_LOG:
			rowsUpdated = sqlDB.update(MedicationListLog.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case MedicationListLog.MED_LIST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationListLog.TABLE_NAME,
						values, MedicationListLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationListLog.TABLE_NAME,
						values, MedicationListLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SensorSpec.SENS_SPEC:
			rowsUpdated = sqlDB.update(SensorSpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case SensorSpec.SENS_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SensorSpec.TABLE_NAME,
						values, SensorSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SensorSpec.TABLE_NAME,
						values, SensorSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case SensorRiskSpec.SENS_RISK_SPEC:
			rowsUpdated = sqlDB.update(
					SensorRiskSpec.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case SensorRiskSpec.SENS_RISK_SPEC_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(
						SensorRiskSpec.TABLE_NAME, values,
						SensorRiskSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SensorRiskSpec.TABLE_NAME,
						values, SensorRiskSpec._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case SensorLog.SENS_LOG:
			rowsUpdated = sqlDB.update(SensorLog.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case SensorLog.SENS_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SensorLog.TABLE_NAME,
						values, SensorLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SensorLog.TABLE_NAME,
						values, SensorLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case Standards.RISK_STANDARDS:
			rowsUpdated = sqlDB.update(Standards.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case Standards.RISK_STANDARDS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(Standards.TABLE_NAME,
						values, Standards._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(Standards.TABLE_NAME,
						values, Standards._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case StandardsRiskMap.RISK_STANDARDS_MAP:
			rowsUpdated = sqlDB.update(StandardsRiskMap.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case StandardsRiskMap.RISK_STANDARDS_MAP_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(StandardsRiskMap.TABLE_NAME, values,
						StandardsRiskMap._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(StandardsRiskMap.TABLE_NAME,
						values, StandardsRiskMap._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case StandardsNoFall.RISK_STANDARDS_NOFALL:
			rowsUpdated = sqlDB.update(StandardsNoFall.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case StandardsNoFall.RISK_STANDARDS_NOFALL_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(StandardsNoFall.TABLE_NAME, values,
						StandardsNoFall._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(StandardsNoFall.TABLE_NAME, values,
						StandardsNoFall._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case StandardsForeign.RISK_STANDARDS_FOREIGN:
			rowsUpdated = sqlDB.update(StandardsForeign.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case StandardsForeign.RISK_STANDARDS_FOREIGN_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(StandardsForeign.TABLE_NAME,
						values, StandardsForeign._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(StandardsForeign.TABLE_NAME,
						values, StandardsForeign._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		case SurveySpec.SURVEY:
			rowsUpdated = sqlDB.update(SurveySpec.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case SurveySpec.SURVEY_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveySpec.TABLE_NAME,
						values, SurveySpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveySpec.TABLE_NAME,
						values, SurveySpec._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case SurveyLog.SURVEY_LOG:
			rowsUpdated = sqlDB.update(SurveyLog.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case SurveyLog.SURVEY_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyLog.TABLE_NAME, values,
						SurveyLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyLog.TABLE_NAME,
						values, SurveyLog._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case SurveyAnswerLog.SURVEY_ANSWER_LOG:
			rowsUpdated = sqlDB.update(SurveyAnswerLog.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case SurveyAnswerLog.SURVEY_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyAnswerLog.TABLE_NAME, values,
						SurveyAnswerLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyAnswerLog.TABLE_NAME, values,
						SurveyAnswerLog._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case SurveyQuestionSpec.SURVEY_QUESTION:
			rowsUpdated = sqlDB.update(SurveyQuestionSpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case SurveyQuestionSpec.SURVEY_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyQuestionSpec.TABLE_NAME,
						values, SurveyQuestionSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyQuestionSpec.TABLE_NAME,
						values, SurveyQuestionSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK:
			rowsUpdated = sqlDB.update(SurveyQuestionRiskSpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(SurveyQuestionRiskSpec.TABLE_NAME,
						values, SurveyQuestionRiskSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SurveyQuestionRiskSpec.TABLE_NAME,
						values, SurveyQuestionRiskSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestSpec.TEST:
			rowsUpdated = sqlDB.update(TestSpec.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case TestSpec.TEST_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestSpec.TABLE_NAME,
						values, TestSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestSpec.TABLE_NAME,
						values, TestSpec._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case TestLog.TEST_LOG:
			rowsUpdated = sqlDB.update(TestLog.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case TestLog.TEST_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestLog.TABLE_NAME, values,
						TestLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestLog.TABLE_NAME,
						values, TestLog._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case TestAnswerLog.TEST_ANSWER_LOG:
			rowsUpdated = sqlDB.update(TestAnswerLog.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case TestAnswerLog.TEST_ANSWER_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestAnswerLog.TABLE_NAME, values,
						TestAnswerLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestAnswerLog.TABLE_NAME, values,
						TestAnswerLog._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TestQuestionSpec.TEST_QUESTION:
			rowsUpdated = sqlDB.update(TestQuestionSpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case TestQuestionSpec.TEST_QUESTION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestQuestionSpec.TABLE_NAME,
						values, TestQuestionSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestQuestionSpec.TABLE_NAME,
						values, TestQuestionSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestQuestionRiskSpec.TEST_QUESTION_RISK:
			rowsUpdated = sqlDB.update(TestQuestionRiskSpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case TestQuestionRiskSpec.TEST_QUESTION_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestQuestionRiskSpec.TABLE_NAME,
						values, TestQuestionRiskSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestQuestionRiskSpec.TABLE_NAME,
						values, TestQuestionRiskSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestMeasureSpec.TEST_MEASURE:
			rowsUpdated = sqlDB.update(TestMeasureSpec.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case TestMeasureSpec.TEST_MEASURE_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestMeasureSpec.TABLE_NAME, values,
						TestMeasureSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestMeasureSpec.TABLE_NAME, values,
						TestMeasureSpec._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case TestMeasureLog.TEST_MEASURE_LOG:
			rowsUpdated = sqlDB.update(TestMeasureLog.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case TestMeasureLog.TEST_MEASURE_LOG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestMeasureLog.TABLE_NAME,
						values, TestMeasureLog._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestMeasureLog.TABLE_NAME,
						values, TestMeasureLog._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
			
		case TestMeasureRiskSpec.TEST_MEASURE_RISK:
			rowsUpdated = sqlDB.update(TestMeasureRiskSpec.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case TestMeasureRiskSpec.TEST_MEASURE_RISK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TestMeasureRiskSpec.TABLE_NAME,
						values, TestMeasureRiskSpec._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TestMeasureRiskSpec.TABLE_NAME,
						values, TestMeasureRiskSpec._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

//	private void checkUserColumns(String[] projection) {
//		String[] available = { User.AGE, User.GENDER,
//				User._ID };
//
//		if (projection != null) {
//			HashSet<String> requestedColumns = new HashSet<String>(
//					Arrays.asList(projection));
//			HashSet<String> availableColumns = new HashSet<String>(
//					Arrays.asList(available));
//			// check if all columns which are requested are available
//			if (!availableColumns.containsAll(requestedColumns)) {
//				throw new IllegalArgumentException(
//						"Unknown columns in projection");
//			}
//		}
//	}
//
//	private void checkColumnsMed(String[] projection) {
//		String[] available = { MedicationSpecTable.COLUMN_NAME,
//				MedicationSpecTable.COLUMN_FK_CATEGORY,
//				MedicationSpecTable.COLUMN_FK_ID, MedicationSpecTable.COLUMN_ID };
//
//		if (projection != null) {
//			HashSet<String> requestedColumns = new HashSet<String>(
//					Arrays.asList(projection));
//			HashSet<String> availableColumns = new HashSet<String>(
//					Arrays.asList(available));
//			// check if all columns which are requested are available
//			if (!availableColumns.containsAll(requestedColumns)) {
//				throw new IllegalArgumentException(
//						"Unknown columns in projection");
//			}
//		}
//	}
}