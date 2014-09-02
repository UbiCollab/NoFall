package ntnu.master.nofall.database.standards;

import ntnu.master.nofall.provider.StandardContract.StandardsNoFall;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StandardNoFallRiskTable {
	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + StandardsNoFall.TABLE_NAME
		      + "(" 
		      + StandardsNoFall._ID   + " integer primary key autoincrement, "
		      + StandardsNoFall.NAME + " text not null, "
		      + StandardsNoFall.DESCRIPTION + " text not null, " 
		      + StandardsNoFall.CREATED_DATE + " integer," 
		      + StandardsNoFall.MODIFIED_DATE + " integer"+ ");";
	
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
			database.execSQL("DROP TABLE IF EXISTS " + StandardsNoFall.TABLE_NAME);
			onCreate(database);
		}
}
