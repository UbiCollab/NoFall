package ntnu.master.nofall.database.standards;

import ntnu.master.nofall.contentprovider.provider.Standard.StandardsForeign;
import ntnu.master.nofall.contentprovider.provider.Standard.StandardsNoFall;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StandardForeignTable {
		// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + StandardsForeign.TABLE_NAME
		      + "(" 
		      + StandardsForeign._ID   + " integer primary key autoincrement, "
		      + StandardsForeign.NAME + " text not null, "
		      + StandardsForeign.DESCRIPTION + " text not null, "  
		      + StandardsForeign.CREATED_DATE + " integer," 
		      + StandardsForeign.MODIFIED_DATE + " integer"+ ");";

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
			database.execSQL("DROP TABLE IF EXISTS " + StandardsForeign.TABLE_NAME);
			onCreate(database);
		}
}
