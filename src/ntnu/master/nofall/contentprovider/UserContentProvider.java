package ntnu.master.nofall.contentprovider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import ntnu.master.nofall.database.MedicationTable;
import ntnu.master.nofall.database.NoFallDBHelper;
import ntnu.master.nofall.database.UserTable;

public class UserContentProvider extends ContentProvider {
	// database
	private NoFallDBHelper database;

	// used for the UriMacher
	private static final int USER = 10;
	private static final int USER_ID = 20;
	private static final int MED = 11;
	private static final int MED_ID = 21;

	private static final String AUTHORITY = "ntnu.master.nofall.contentprovider";

	private static final String BASE_PATH_USER = "user";
	private static final String BASE_PATH_MED = "med";

	public static final Uri CONTENT_URI_USER = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_USER);
	public static final Uri CONTENT_URI_MED = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_MED);

	public static final String CONTENT_TYPE_USER = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/user";
	public static final String CONTENT_ITEM_TYPE_USER = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/user";

	public static final String CONTENT_TYPE_MED = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/med";
	public static final String CONTENT_ITEM_TYPE_MED = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/med";

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER, USER);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER + "/#", USER_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED, MED);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_MED + "/#", MED_ID);
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
			checkColumns(projection);
			// Set the table
			queryBuilder.setTables(UserTable.TABLE_USER);
			break;
		case USER_ID:
			// check if the caller has requested a column which does not exists
			checkColumns(projection);
			// Set the table
			queryBuilder.setTables(UserTable.TABLE_USER);
			
			// adding the ID to the original query
			queryBuilder.appendWhere(UserTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case MED:
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationTable.TABLE_MED);
			break;
		case MED_ID:
			// check if the caller has requested a column which does not exists
			checkColumnsMed(projection);
			// Set the table
			queryBuilder.setTables(MedicationTable.TABLE_MED);
			
			// adding the ID to the original query
			queryBuilder.appendWhere(MedicationTable.COLUMN_ID + "="
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
			id = sqlDB.insert(MedicationTable.TABLE_MED, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Uri.parse(BASE_PATH_MED + "/" + id);
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
			rowsDeleted = sqlDB.delete(MedicationTable.TABLE_MED, selection,
					selectionArgs);
			break;
		case MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MedicationTable.TABLE_MED,
						MedicationTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(MedicationTable.TABLE_MED,
						MedicationTable.COLUMN_ID + "=" + id + " and " + selection,
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
			rowsUpdated = sqlDB.update(MedicationTable.TABLE_MED, values, selection,
					selectionArgs);
			break;
		case MED_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MedicationTable.TABLE_MED, values,
						MedicationTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(MedicationTable.TABLE_MED, values,
						MedicationTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
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
		String[] available = { MedicationTable.COLUMN_NAME, MedicationTable.COLUMN_DESC, MedicationTable.COLUMN_TYPE,
				MedicationTable.COLUMN_ID };

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
