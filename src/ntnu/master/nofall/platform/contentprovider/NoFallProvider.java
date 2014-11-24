package ntnu.master.nofall.platform.contentprovider;

import ntnu.master.nofall.platform.database.NoFallDBHelper;
import ntnu.master.nofall.platform.provider.AuthorityContract;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationCategory;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationListLog;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationLog;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationSpec;
import ntnu.master.nofall.platform.provider.MedicationContract.MedicationType;
import ntnu.master.nofall.platform.provider.RiskDefContract.MeasureStandards;
import ntnu.master.nofall.platform.provider.RiskDefContract.RefRiskLevels;
import ntnu.master.nofall.platform.provider.RiskDefContract.RiskDefinition;
import ntnu.master.nofall.platform.provider.RiskDefContract.RiskMap;
import ntnu.master.nofall.platform.provider.SensorContract.SensorLog;
import ntnu.master.nofall.platform.provider.SensorContract.SensorLogItem;
import ntnu.master.nofall.platform.provider.SensorContract.SensorSpec;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveyAnswerLog;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveyLog;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveyQuestionRiskSpec;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveyQuestionSpec;
import ntnu.master.nofall.platform.provider.SurveyContract.SurveySpec;
import ntnu.master.nofall.platform.provider.TestContract.TestAnswerLog;
import ntnu.master.nofall.platform.provider.TestContract.TestLog;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureLog;
import ntnu.master.nofall.platform.provider.TestContract.TestMeasureSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestQuestionRiskSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestQuestionSpec;
import ntnu.master.nofall.platform.provider.TestContract.TestSpec;
import ntnu.master.nofall.platform.provider.UsersContract.User;
import ntnu.master.nofall.platform.provider.UsersContract.UserTotalRisk;
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
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, User.TABLE_NAME, User.USER);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, User.TABLE_NAME + "/#", User.USER_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, UserTotalRisk.TABLE_NAME, UserTotalRisk.USER_TOTAL_RISK);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, UserTotalRisk.TABLE_NAME + "/#", UserTotalRisk.USER_TOTAL_RISK_ID);

		// Medication
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationType.TABLE_NAME, MedicationType.MED);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationType.TABLE_NAME + "/#", MedicationType.MED_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationCategory.TABLE_NAME, MedicationCategory.MED_CAT);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationCategory.TABLE_NAME + "/#", MedicationCategory.MED_CAT_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationLog.TABLE_NAME, MedicationLog.MED_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationLog.TABLE_NAME + "/#", MedicationLog.MED_LOG_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationListLog.TABLE_NAME, MedicationListLog.MED_LIST_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MedicationListLog.TABLE_NAME + "/#", MedicationListLog.MED_LIST_LOG_ID);

		// Sensor
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SensorSpec.TABLE_NAME, SensorSpec.SENS_SPEC);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SensorSpec.TABLE_NAME + "/#", SensorSpec.SENS_SPEC_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SensorLogItem.TABLE_NAME, SensorLogItem.SENS_LOG_ITEM);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SensorLogItem.TABLE_NAME + "/#", SensorLogItem.SENS_LOG_ITEM_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SensorLog.TABLE_NAME, SensorLog.SENS_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SensorLog.TABLE_NAME + "/#", SensorLog.SENS_LOG_ID);
		
		// Standards,
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MeasureStandards.TABLE_NAME, MeasureStandards.RISK_MEASURE_STANDARDS);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, MeasureStandards.TABLE_NAME + "/#", MeasureStandards.RISK_MEASURE_STANDARDS_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, RiskMap.TABLE_NAME, RiskMap.RISK_MAP);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, RiskMap.TABLE_NAME + "/#", RiskMap.RISK_MAP_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, RefRiskLevels.TABLE_NAME, RefRiskLevels.REF_RISK_LEVELS);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, RefRiskLevels.TABLE_NAME + "/#", RefRiskLevels.REF_RISK_LEVELS_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, RiskDefinition.TABLE_NAME, RiskDefinition.RISK_DEFINITION);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, RiskDefinition.TABLE_NAME + "/#", RiskDefinition.RISK_DEFINITION_ID);
		
		// Survey
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveySpec.TABLE_NAME, SurveySpec.SURVEY);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveySpec.TABLE_NAME + "/#", SurveySpec.SURVEY_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyLog.TABLE_NAME, SurveyLog.SURVEY_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyLog.TABLE_NAME + "/#", SurveyLog.SURVEY_LOG_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyAnswerLog.TABLE_NAME, SurveyAnswerLog.SURVEY_ANSWER_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyAnswerLog.TABLE_NAME + "/#", SurveyAnswerLog.SURVEY_ANSWER_LOG_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyQuestionSpec.TABLE_NAME, SurveyQuestionSpec.SURVEY_QUESTION);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyQuestionSpec.TABLE_NAME + "/#", SurveyQuestionSpec.SURVEY_QUESTION_ID);		
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyQuestionRiskSpec.TABLE_NAME, SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, SurveyQuestionRiskSpec.TABLE_NAME + "/#", SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK_ID);
		
		// Test
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestSpec.TABLE_NAME, TestSpec.TEST);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestSpec.TABLE_NAME + "/#", TestSpec.TEST_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestLog.TABLE_NAME, TestLog.TEST_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestLog.TABLE_NAME + "/#", TestLog.TEST_LOG_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestAnswerLog.TABLE_NAME, TestAnswerLog.TEST_ANSWER_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestAnswerLog.TABLE_NAME + "/#", TestAnswerLog.TEST_ANSWER_LOG_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestQuestionSpec.TABLE_NAME, TestQuestionSpec.TEST_QUESTION);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestQuestionSpec.TABLE_NAME + "/#", TestQuestionSpec.TEST_QUESTION_ID);		
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestQuestionRiskSpec.TABLE_NAME, TestQuestionRiskSpec.TEST_QUESTION_RISK);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestQuestionRiskSpec.TABLE_NAME + "/#", TestQuestionRiskSpec.TEST_QUESTION_RISK_ID);		
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestMeasureSpec.TABLE_NAME, TestMeasureSpec.TEST_MEASURE);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestMeasureSpec.TABLE_NAME + "/#", TestMeasureSpec.TEST_MEASURE_ID);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestMeasureLog.TABLE_NAME, TestMeasureLog.TEST_MEASURE_LOG);
		sURIMatcher.addURI(AuthorityContract.AUTHORITY, TestMeasureLog.TABLE_NAME + "/#", TestMeasureLog.TEST_MEASURE_LOG_ID);		
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
			// Set the table
			queryBuilder.setTables(User.TABLE_NAME);
			break;
		case User.USER_ID:
			// Set the table
			queryBuilder.setTables(User.TABLE_NAME);
			// adding the ID to the original query
			queryBuilder.appendWhere(User._ID + "="
					+ uri.getLastPathSegment());
			break;
		case UserTotalRisk.USER_TOTAL_RISK:
			// Set the table
			queryBuilder.setTables(UserTotalRisk.TABLE_NAME);
			break;
		case UserTotalRisk.USER_TOTAL_RISK_ID:
			// Set the table
			queryBuilder.setTables(UserTotalRisk.TABLE_NAME);
			// adding the ID to the original query
			queryBuilder.appendWhere(UserTotalRisk._ID + "="
					+ uri.getLastPathSegment());
			break;
		case MedicationSpec.MED_SPEC:
			// Set the table
			queryBuilder.setTables(MedicationSpec.TABLE_NAME);
			break;
		case MedicationSpec.MED_SPEC_ID:
			// Set the table
			queryBuilder.setTables(MedicationSpec.TABLE_NAME);
			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationType._ID + "="
					+ uri.getLastPathSegment());
			break;
		case MedicationType.MED:
			// Set the table
			queryBuilder.setTables(MedicationType.TABLE_NAME);
			break;
		case MedicationType.MED_ID:
			// Set the table
			queryBuilder.setTables(MedicationType.TABLE_NAME);
			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationType._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationCategory.MED_CAT:
			// Set the table
			queryBuilder.setTables(MedicationCategory.TABLE_NAME);
			break;
		case MedicationCategory.MED_CAT_ID:
			// Set the table
			queryBuilder.setTables(MedicationCategory.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationCategory._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationLog.MED_LOG:
			// Set the table
			queryBuilder.setTables(MedicationLog.TABLE_NAME);
			break;
		case MedicationLog.MED_LOG_ID:
			// Set the table
			queryBuilder.setTables(MedicationLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case MedicationListLog.MED_LIST_LOG:
			// Set the table
			queryBuilder.setTables(MedicationListLog.TABLE_NAME);
			break;
		case MedicationListLog.MED_LIST_LOG_ID:
			// Set the table
			queryBuilder.setTables(MedicationListLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationListLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SensorSpec.SENS_SPEC:
			// Set the table
			queryBuilder.setTables(SensorSpec.TABLE_NAME);
			break;
		case SensorSpec.SENS_SPEC_ID:
			// Set the table
			queryBuilder.setTables(SensorSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SensorLogItem.SENS_LOG_ITEM:
			// Set the table
			queryBuilder.setTables(SensorLogItem.TABLE_NAME);
			break;
		case SensorLogItem.SENS_LOG_ITEM_ID:
			// Set the table
			queryBuilder.setTables(SensorLogItem.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorLogItem._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SensorLog.SENS_LOG:
			// Set the table
			queryBuilder.setTables(SensorLog.TABLE_NAME);
			break;
		case SensorLog.SENS_LOG_ID:
			// Set the table
			queryBuilder.setTables(SensorLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SensorLog._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case MeasureStandards.RISK_MEASURE_STANDARDS:
			// Set the table
			queryBuilder.setTables(MeasureStandards.TABLE_NAME);
			break;
		case MeasureStandards.RISK_MEASURE_STANDARDS_ID:
			// Set the table
			queryBuilder.setTables(MeasureStandards.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(MeasureStandards._ID + "="
					+ uri.getLastPathSegment());
			break;

		case RiskMap.RISK_MAP:
			// Set the table
			queryBuilder.setTables(RiskMap.TABLE_NAME);
			break;
		case RiskMap.RISK_MAP_ID:
			// Set the table
			queryBuilder.setTables(RiskMap.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(RiskMap._ID + "="
					+ uri.getLastPathSegment());
			break;

		case RefRiskLevels.REF_RISK_LEVELS:
			// Set the table
			queryBuilder.setTables(RefRiskLevels.TABLE_NAME);
			break;
		case RefRiskLevels.REF_RISK_LEVELS_ID:
			// Set the table
			queryBuilder.setTables(RefRiskLevels.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(RefRiskLevels._ID + "="
					+ uri.getLastPathSegment());
			break;

		case RiskDefinition.RISK_DEFINITION:
			// Set the table
			queryBuilder.setTables(RiskDefinition.TABLE_NAME);
			break;
		case RiskDefinition.RISK_DEFINITION_ID:
			// Set the table
			queryBuilder.setTables(RiskDefinition.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(RiskDefinition._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case SurveySpec.SURVEY:
			// Set the table
			queryBuilder.setTables(SurveySpec.TABLE_NAME);
			break;
		case SurveySpec.SURVEY_ID:
			// Set the table
			queryBuilder.setTables(SurveySpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveySpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SurveyLog.SURVEY_LOG:
			// Set the table
			queryBuilder.setTables(SurveyLog.TABLE_NAME);
			break;
		case SurveyLog.SURVEY_LOG_ID:
			// Set the table
			queryBuilder.setTables(SurveyLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SurveyAnswerLog.SURVEY_ANSWER_LOG:
			// Set the table
			queryBuilder.setTables(SurveyAnswerLog.TABLE_NAME);
			break;
		case SurveyAnswerLog.SURVEY_ANSWER_LOG_ID:
			// Set the table
			queryBuilder.setTables(SurveyAnswerLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyAnswerLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case SurveyQuestionSpec.SURVEY_QUESTION:
			// Set the table
			queryBuilder.setTables(SurveyQuestionSpec.TABLE_NAME);
			break;
		case SurveyQuestionSpec.SURVEY_QUESTION_ID:
			// Set the table
			queryBuilder.setTables(SurveyQuestionSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyQuestionSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK:
			// Set the table
			queryBuilder.setTables(SurveyQuestionRiskSpec.TABLE_NAME);
			break;
		case SurveyQuestionRiskSpec.SURVEY_QUESTION_RISK_ID:
			// Set the table
			queryBuilder.setTables(SurveyQuestionRiskSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(SurveyQuestionRiskSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestSpec.TEST:
			// Set the table
			queryBuilder.setTables(TestSpec.TABLE_NAME);
			break;
		case TestSpec.TEST_ID:
			// Set the table
			queryBuilder.setTables(TestSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestLog.TEST_LOG:
			// Set the table
			queryBuilder.setTables(TestLog.TABLE_NAME);
			break;
		case TestLog.TEST_LOG_ID:
			// Set the table
			queryBuilder.setTables(TestLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestAnswerLog.TEST_ANSWER_LOG:
			// Set the table
			queryBuilder.setTables(TestAnswerLog.TABLE_NAME);
			break;
		case TestAnswerLog.TEST_ANSWER_LOG_ID:
			// Set the table
			queryBuilder.setTables(TestAnswerLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestAnswerLog._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestQuestionSpec.TEST_QUESTION:
			// Set the table
			queryBuilder.setTables(TestQuestionSpec.TABLE_NAME);
			break;
		case TestQuestionSpec.TEST_QUESTION_ID:
			// Set the table
			queryBuilder.setTables(TestQuestionSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestQuestionSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestQuestionRiskSpec.TEST_QUESTION_RISK:
			// Set the table
			queryBuilder.setTables(TestQuestionRiskSpec.TABLE_NAME);
			break;
		case TestQuestionRiskSpec.TEST_QUESTION_RISK_ID:
			// Set the table
			queryBuilder.setTables(TestQuestionRiskSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestQuestionRiskSpec._ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case TestMeasureSpec.TEST_MEASURE:
			// Set the table
			queryBuilder.setTables(TestMeasureSpec.TABLE_NAME);
			break;
		case TestMeasureSpec.TEST_MEASURE_ID:
			// Set the table
			queryBuilder.setTables(TestMeasureSpec.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureSpec._ID + "="
					+ uri.getLastPathSegment());
			break;

		case TestMeasureLog.TEST_MEASURE_LOG:
			// Set the table
			queryBuilder.setTables(TestMeasureLog.TABLE_NAME);
			break;
		case TestMeasureLog.TEST_MEASURE_LOG_ID:
			// Set the table
			queryBuilder.setTables(TestMeasureLog.TABLE_NAME);

			// adding the ID to the original query
			queryBuilder.appendWhere(TestMeasureLog._ID + "="
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
		
		case MedicationSpec.MED_SPEC:
			id = sqlDB.insert(MedicationSpec.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationSpec.TABLE_NAME + "/" + id);
			
		case MedicationType.MED:
			id = sqlDB.insert(MedicationType.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationType.TABLE_NAME + "/" + id);

		case MedicationCategory.MED_CAT:
			id = sqlDB.insert(MedicationCategory.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MedicationCategory.TABLE_NAME + "/" + id);

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

		case SensorLogItem.SENS_LOG_ITEM:
			id = sqlDB.insert(SensorLogItem.TABLE_NAME,
					null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SensorLogItem.TABLE_NAME + "/" + id);

		case SensorLog.SENS_LOG:
			id = sqlDB.insert(SensorLog.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(SensorLog.TABLE_NAME + "/" + id);
			
		case MeasureStandards.RISK_MEASURE_STANDARDS:
			id = sqlDB.insert(MeasureStandards.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(MeasureStandards.TABLE_NAME + "/" + id);

		case RiskMap.RISK_MAP:
			id = sqlDB.insert(RiskMap.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(RiskMap.TABLE_NAME + "/" + id);

		case RefRiskLevels.REF_RISK_LEVELS:
			id = sqlDB.insert(RefRiskLevels.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(RefRiskLevels.TABLE_NAME + "/" + id);

		case RiskDefinition.RISK_DEFINITION:
			id = sqlDB.insert(RiskDefinition.TABLE_NAME, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(RiskDefinition.TABLE_NAME + "/" + id);
			
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
			
		case MedicationSpec.MED_SPEC:
			rowsDeleted = sqlDB.delete(MedicationSpec.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MedicationSpec.MED_SPEC_ID:
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
			
		case MedicationType.MED:
			rowsDeleted = sqlDB.delete(MedicationType.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MedicationType.MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationType.TABLE_NAME,
						MedicationType._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationType.TABLE_NAME,
						MedicationType._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case MedicationCategory.MED_CAT:
			rowsDeleted = sqlDB.delete(MedicationCategory.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MedicationCategory.MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationCategory.TABLE_NAME,
						MedicationCategory._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationCategory.TABLE_NAME,
						MedicationCategory._ID + "=" + id + " and "
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

		case SensorLogItem.SENS_LOG_ITEM:
			rowsDeleted = sqlDB.delete(
					SensorLogItem.TABLE_NAME, selection,
					selectionArgs);
			break;
		case SensorLogItem.SENS_LOG_ITEM_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(
						SensorLogItem.TABLE_NAME,
						SensorLogItem._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(SensorLogItem.TABLE_NAME,
						SensorLogItem._ID + "=" + id + " and "
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
			
		case MeasureStandards.RISK_MEASURE_STANDARDS:
			rowsDeleted = sqlDB.delete(MeasureStandards.TABLE_NAME,
					selection, selectionArgs);
			break;
		case MeasureStandards.RISK_MEASURE_STANDARDS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MeasureStandards.TABLE_NAME,
						MeasureStandards._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MeasureStandards.TABLE_NAME,
						MeasureStandards._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case RiskMap.RISK_MAP:
			rowsDeleted = sqlDB.delete(RiskMap.TABLE_NAME,
					selection, selectionArgs);
			break;
		case RiskMap.RISK_MAP_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(RiskMap.TABLE_NAME,
						RiskMap._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(RiskMap.TABLE_NAME,
						RiskMap._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;

		case RefRiskLevels.REF_RISK_LEVELS:
			rowsDeleted = sqlDB.delete(RefRiskLevels.TABLE_NAME, selection,
					selectionArgs);
			break;
		case RefRiskLevels.REF_RISK_LEVELS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(RefRiskLevels.TABLE_NAME,
						RefRiskLevels._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(RefRiskLevels.TABLE_NAME,
						RefRiskLevels._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case RiskDefinition.RISK_DEFINITION:
			rowsDeleted = sqlDB.delete(RiskDefinition.TABLE_NAME,
					selection, selectionArgs);
			break;
		case RiskDefinition.RISK_DEFINITION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(RiskDefinition.TABLE_NAME,
						RiskDefinition._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(RiskDefinition.TABLE_NAME,
						RiskDefinition._ID + "=" + id + " and "
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

		case MedicationSpec.MED_SPEC:
			rowsUpdated = sqlDB.update(MedicationSpec.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case MedicationSpec.MED_SPEC_ID:
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
			
		case MedicationType.MED:
			rowsUpdated = sqlDB.update(MedicationType.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case MedicationType.MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationType.TABLE_NAME,
						values, MedicationType._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationType.TABLE_NAME,
						values, MedicationType._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case MedicationCategory.MED_CAT:
			rowsUpdated = sqlDB.update(MedicationCategory.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case MedicationCategory.MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB
						.update(MedicationCategory.TABLE_NAME, values,
								MedicationCategory._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationCategory.TABLE_NAME,
						values, MedicationCategory._ID + "=" + id
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

		case SensorLogItem.SENS_LOG_ITEM:
			rowsUpdated = sqlDB.update(
					SensorLogItem.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case SensorLogItem.SENS_LOG_ITEM_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(
						SensorLogItem.TABLE_NAME, values,
						SensorLogItem._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(SensorLogItem.TABLE_NAME,
						values, SensorLogItem._ID + "=" + id
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
			
		case MeasureStandards.RISK_MEASURE_STANDARDS:
			rowsUpdated = sqlDB.update(MeasureStandards.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case MeasureStandards.RISK_MEASURE_STANDARDS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MeasureStandards.TABLE_NAME,
						values, MeasureStandards._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MeasureStandards.TABLE_NAME,
						values, MeasureStandards._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case RiskMap.RISK_MAP:
			rowsUpdated = sqlDB.update(RiskMap.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case RiskMap.RISK_MAP_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(RiskMap.TABLE_NAME, values,
						RiskMap._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(RiskMap.TABLE_NAME,
						values, RiskMap._ID + "=" + id
								+ " and " + selection, selectionArgs);
			}
			break;

		case RefRiskLevels.REF_RISK_LEVELS:
			rowsUpdated = sqlDB.update(RefRiskLevels.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case RefRiskLevels.REF_RISK_LEVELS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(RefRiskLevels.TABLE_NAME, values,
						RefRiskLevels._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(RefRiskLevels.TABLE_NAME, values,
						RefRiskLevels._ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;

		case RiskDefinition.RISK_DEFINITION:
			rowsUpdated = sqlDB.update(RiskDefinition.TABLE_NAME,
					values, selection, selectionArgs);
			break;
		case RiskDefinition.RISK_DEFINITION_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(RiskDefinition.TABLE_NAME,
						values, RiskDefinition._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(RiskDefinition.TABLE_NAME,
						values, RiskDefinition._ID + "=" + id + " and "
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
