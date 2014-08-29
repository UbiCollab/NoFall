package ntnu.master.nofall.database.survey;

import ntnu.master.nofall.contentprovider.provider.Survey.SurveySpec;
import ntnu.master.nofall.contentprovider.provider.Users.User;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SurveySpecTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + SurveySpec.TABLE_NAME
		      + "(" 
		      + SurveySpec._ID   + " integer primary key autoincrement, " 
		      + SurveySpec.OWNER_ID + " text not null, "
		      + SurveySpec.NAME + " text not null, "  
		      + User.CREATED_DATE + " integer," 
		      + User.MODIFIED_DATE + " integer" + ");";

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
		database.execSQL("DROP TABLE IF EXISTS " + SurveySpec.TABLE_NAME);
		onCreate(database);
	}
}
