package ntnu.master.nofall.database.standards;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ForeignStandardsTable {
	// Database table
		public static final String TABLE_FOREIGN_STANDARD = "tblForeignStandard";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_DESCRIPTION = "description";
		

		// Database creation SQL statement
		  private static final String DATABASE_CREATE = "create table " 
			      + TABLE_FOREIGN_STANDARD
			      + "(" 
			      + COLUMN_ID   + " integer primary key autoincrement, "
			      + COLUMN_NAME + " text not null, "
			      + COLUMN_DESCRIPTION + " text not null "  + ");";

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
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_FOREIGN_STANDARD);
			onCreate(database);
		}
}
