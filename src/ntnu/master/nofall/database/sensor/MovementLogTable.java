package ntnu.master.nofall.database.sensor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MovementLogTable {
	// Database table
	public static final String TABLE_MOVEMENT_LOG = "tblMovementLog";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_VALUE = "value";
	
	// foreign keys
		public static final String COLUMN_FK_MOVEMENT = "fkMovement";
		public static final String TABLE_FK_MOVEMENT = "tblMovementSpec";
		public static final String COLUMN_FK_MOVEMENT_ID = "_id";

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MOVEMENT_LOG
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_DATE + " date, "  
		      + COLUMN_VALUE + " integer, " 
		      + COLUMN_FK_MOVEMENT + " integer, "
		      + " FOREIGN KEY ("+COLUMN_FK_MOVEMENT+") REFERENCES "+TABLE_FK_MOVEMENT+" ("+COLUMN_FK_MOVEMENT_ID+") ON DELETE CASCADE );";

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
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVEMENT_LOG);
		onCreate(database);
	}
}
