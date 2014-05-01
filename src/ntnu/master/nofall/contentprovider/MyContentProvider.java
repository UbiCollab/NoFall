package ntnu.master.nofall.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import ntnu.master.nofall.database.NoFallDBHelper;
import ntnu.master.nofall.database.UserTable;
import ntnu.master.nofall.database.medication.MedCategorySpecTable;
import ntnu.master.nofall.database.medication.MedListLogTable;
import ntnu.master.nofall.database.medication.MedLogTable;
import ntnu.master.nofall.database.medication.MedicationSpecTable;
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

	// used for the UriMacher
	private static final int USER = 10;
	private static final int USER_ID = 20;
	private static final int MED = 11;
	private static final int MED_ID = 21;
	private static final int MED_CAT = 12;
	private static final int MED_CAT_ID = 22;
	private static final int MED_REG = 13;
	private static final int MED_REG_ID = 23;
	private static final int MED_REG_LIST = 14;
	private static final int MED_REG_LIST_ID = 24;

	private static final String AUTHORITY = "ntnu.master.nofall.contentprovider";

	private static final String BASE_PATH_USER = "tblUser";
	private static final String BASE_PATH_MED = "tblMed";
	private static final String BASE_PATH_MED_CAT = "tblMedCat";
	private static final String BASE_PATH_MED_REG = "tblMedReg";
	private static final String BASE_PATH_MED_REG_LIST = "tblMedRegList";

	public static final Uri CONTENT_URI_USER = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_USER);
	public static final Uri CONTENT_URI_MED = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_MED);	
	public static final Uri CONTENT_URI_MED_CAT = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_MED_CAT);
	public static final Uri CONTENT_URI_MED_REG = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_MED_REG);
	public static final Uri CONTENT_URI_MED_REG_LIST = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_MED_REG_LIST);

	public static final String CONTENT_TYPE_USER = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblUser";
	public static final String CONTENT_ITEM_TYPE_USER = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblUser";

	public static final String CONTENT_TYPE_MED = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMed";
	public static final String CONTENT_ITEM_TYPE_MED = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMed";
	
	public static final String CONTENT_TYPE_MED_CAT = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMedCat";
	public static final String CONTENT_ITEM_TYPE_MED_CAT = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMedCat";
	
	public static final String CONTENT_TYPE_MED_REG = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMedReg";
	public static final String CONTENT_ITEM_TYPE_MED_REG = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMedReg";
	
	public static final String CONTENT_TYPE_MED_REG_LIST = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/tblMedRegList";
	public static final String CONTENT_ITEM_TYPE_MED_REG_LIST = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/tblMedRegList";

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER, USER);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER + "/#", USER_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED, MED);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED + "/#", MED_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_CAT, MED_CAT);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_CAT + "/#", MED_CAT_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_REG, MED_REG);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_REG + "/#", MED_REG_ID);
		
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_REG_LIST, MED_REG_LIST);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED_REG_LIST + "/#", MED_REG_LIST_ID);
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
//		// check if the caller has requested a column which does not exists
//		checkColumns(projection);
//
//		// Set the table
//		queryBuilder.setTables(UserTable.TABLE_USER);

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
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedCategorySpecTable.TABLE_MED_CAT);
			break;
		case MED_CAT_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedCategorySpecTable.TABLE_MED_CAT);
			
			// adding the ID to the original query
			queryBuilder.appendWhere(MedCategorySpecTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case MED_REG:
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedLogTable.TABLE_MED_REG);
			break;
		case MED_REG_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedLogTable.TABLE_MED_REG);
			
			// adding the ID to the original query
			queryBuilder.appendWhere(MedLogTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case MED_REG_LIST:
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedListLogTable.TABLE_MED_REG_LIST);
			break;
		case MED_REG_LIST_ID:
			// check if the caller has requested a column which does not exists
			//checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedListLogTable.TABLE_MED_REG_LIST);
			
			// adding the ID to the original query
			queryBuilder.appendWhere(MedListLogTable.COLUMN_ID + "="
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
			
		case MED:
			id = sqlDB.insert(MedicationSpecTable.TABLE_MED, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED + "/" + id);
			
		case MED_CAT:
			id = sqlDB.insert(MedCategorySpecTable.TABLE_MED_CAT, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED_CAT + "/" + id);
			
		case MED_REG:
			id = sqlDB.insert(MedLogTable.TABLE_MED_REG, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED_REG + "/" + id);
			
		case MED_REG_LIST:
			id = sqlDB.insert(MedListLogTable.TABLE_MED_REG_LIST, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED_REG_LIST + "/" + id);
			
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
			
		case MED:
			rowsDeleted = sqlDB.delete(MedicationSpecTable.TABLE_MED, selection,
					selectionArgs);
			break;
		case MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationSpecTable.TABLE_MED,
						MedicationSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationSpecTable.TABLE_MED,
						MedicationSpecTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED_CAT:
			rowsDeleted = sqlDB.delete(MedCategorySpecTable.TABLE_MED_CAT, selection,
					selectionArgs);
			break;
		case MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedCategorySpecTable.TABLE_MED_CAT,
						MedCategorySpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedCategorySpecTable.TABLE_MED_CAT,
						MedCategorySpecTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED_REG:
			rowsDeleted = sqlDB.delete(MedLogTable.TABLE_MED_REG, selection,
					selectionArgs);
			break;
		case MED_REG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedLogTable.TABLE_MED_REG,
						MedLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedLogTable.TABLE_MED_REG,
						MedLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED_REG_LIST:
			rowsDeleted = sqlDB.delete(MedListLogTable.TABLE_MED_REG_LIST, selection,
					selectionArgs);
			break;
		case MED_REG_LIST_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedListLogTable.TABLE_MED_REG_LIST,
						MedListLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedListLogTable.TABLE_MED_REG_LIST,
						MedListLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
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
			
		case MED:
			rowsUpdated = sqlDB.update(MedicationSpecTable.TABLE_MED, values, selection,
					selectionArgs);
			break;
		case MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationSpecTable.TABLE_MED, values,
						MedicationSpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationSpecTable.TABLE_MED, values,
						MedicationSpecTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED_CAT:
			rowsUpdated = sqlDB.update(MedCategorySpecTable.TABLE_MED_CAT, values, selection,
					selectionArgs);
			break;
		case MED_CAT_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedCategorySpecTable.TABLE_MED_CAT, values,
						MedCategorySpecTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedCategorySpecTable.TABLE_MED_CAT, values,
						MedCategorySpecTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED_REG:
			rowsUpdated = sqlDB.update(MedLogTable.TABLE_MED_REG, values, selection,
					selectionArgs);
			break;
		case MED_REG_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedLogTable.TABLE_MED_REG, values,
						MedLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedLogTable.TABLE_MED_REG, values,
						MedLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
			
		case MED_REG_LIST:
			rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_REG_LIST, values, selection,
					selectionArgs);
			break;
		case MED_REG_LIST_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_REG_LIST, values,
						MedListLogTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedListLogTable.TABLE_MED_REG_LIST, values,
						MedListLogTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
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
		String[] available = { MedicationSpecTable.COLUMN_NAME, MedicationSpecTable.COLUMN_FK_CATEGORY, MedicationSpecTable.COLUMN_FK_ID,
				MedicationSpecTable.COLUMN_ID };

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
