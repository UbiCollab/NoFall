package ntnu.master.nofall.database.standards;

import ntnu.master.nofall.provider.StandardContract.Standards;
import ntnu.master.nofall.provider.StandardContract.StandardsForeign;
import ntnu.master.nofall.provider.StandardContract.StandardsNoFall;
import ntnu.master.nofall.provider.StandardContract.StandardsRiskMap;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StandardRiskMapTable {
		// Database creation SQL statement
		  private static final String DATABASE_CREATE = "create table " 
			      + StandardsRiskMap.TABLE_NAME
			      + "(" 
			      + StandardsRiskMap._ID   + " integer primary key autoincrement, " 
			      + StandardsRiskMap.RANGE_FROM + " integer, "
			      + StandardsRiskMap.RANGE_TO + " integer, "
			      + StandardsRiskMap.CREATED_DATE + " integer," 
			      + StandardsRiskMap.MODIFIED_DATE + " integer,"
			      + StandardsRiskMap.FK_STAND_NOFALL + " integer, "
			      + StandardsRiskMap.FK_STAND_FOREIGN + " integer, "
			      + " FOREIGN KEY ("+StandardsRiskMap.FK_STAND_NOFALL+") " +
			      		"REFERENCES "+StandardsNoFall.TABLE_NAME+" ("+StandardsNoFall._ID+") ON DELETE CASCADE"
			      + " FOREIGN KEY ("+StandardsRiskMap.FK_STAND_FOREIGN+") " +
			      		"REFERENCES "+StandardsForeign.TABLE_NAME+" ("+StandardsForeign._ID+") ON DELETE CASCADE );";

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
			database.execSQL("DROP TABLE IF EXISTS " + StandardsRiskMap.TABLE_NAME);
			onCreate(database);
		}
}
