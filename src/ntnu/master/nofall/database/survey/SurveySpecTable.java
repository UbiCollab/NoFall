package ntnu.master.nofall.database.survey;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveySpecTable {
	// Database table
	public static final String TABLE_SURVEY_SPEC = "tblSurveySpec";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_OWNER_ID = "ownerId";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_SURVEY_SPEC
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_OWNER_ID + " text not null, "
		      + COLUMN_NAME + " text not null "  + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY_SPEC);
		onCreate(database);
	}
}
