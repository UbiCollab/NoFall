package ntnu.master.nofall.platform.database.user;

import ntnu.master.nofall.platform.provider.UsersContract.User;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserTable {

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + User.TABLE_NAME
		      + "(" 
		      + User._ID   + " integer primary key autoincrement, " 
		      + User.GENDER + " text not null, " 
		      + User.AGE  + " integer, "
		      + User.CREATED_DATE + " integer," 
		      + User.MODIFIED_DATE + " integer" +");";
	  
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
		database.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
		onCreate(database);
	}
}
