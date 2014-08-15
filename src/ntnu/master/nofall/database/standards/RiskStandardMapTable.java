package ntnu.master.nofall.database.standards;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RiskStandardMapTable {
	// Database table
		public static final String TABLE_RISK_STAND_MAP = "tblRiskStandMap";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_RANGE_FROM = "rangeFrom";
		public static final String COLUMN_RANGE_TO = "rangeTo";
		public static final String COLUMN_FK_STAND_NOFALL = "fkNoFallStand";
		public static final String COLUMN_FK_STAND_FOREIGN = "fkForeignStand";
		
		//foreign key: NoFall Risk Standard
		public static final String COLUMN_FK_TBL_STAND_NOFALL = "tblNoFallRisk";
		public static final String COLUMN_FK_ID_NOFALL = "_id";
		
		//foreign key: Foreign Risk Standard
		public static final String COLUMN_FK_TBL_FOREIGN_STAND = "tblForeignStandard";
		public static final String COLUMN_FK_ID_FOREIGN= "_id";

		// Database creation SQL statement
		  private static final String DATABASE_CREATE = "create table " 
			      + TABLE_RISK_STAND_MAP
			      + "(" 
			      + COLUMN_ID   + " integer primary key autoincrement, " 
			      + COLUMN_RANGE_FROM + " integer, "
			      + COLUMN_RANGE_TO + " integer, "
			      + COLUMN_FK_STAND_NOFALL + " integer, "
			      + COLUMN_FK_STAND_FOREIGN + " integer, "
			      + " FOREIGN KEY ("+COLUMN_FK_STAND_NOFALL+") REFERENCES "+COLUMN_FK_TBL_STAND_NOFALL+" ("+COLUMN_FK_ID_NOFALL+") ON DELETE CASCADE"
			      + " FOREIGN KEY ("+COLUMN_FK_STAND_FOREIGN+") REFERENCES "+COLUMN_FK_TBL_FOREIGN_STAND+" ("+COLUMN_FK_ID_FOREIGN+") ON DELETE CASCADE );";

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
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_RISK_STAND_MAP);
			onCreate(database);
		}
}
