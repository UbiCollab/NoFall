package ntnu.master.nofall.database.sensor;

import ntnu.master.nofall.database.UserTable;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MovementRiskSpecTable {
	// Database table
	public static final String TABLE_MOVEMENT_RISK_SPEC = "tblMovementRiskSpec";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_RISK = "risk";
	
	// foreign keys
	public static final String COLUMN_FK_MOVEMENT = "fkMovement";
	public static final String TABLE_FK_MOVEMENT = "tblMovementSpec";
	public static final String COLUMN_FK_MOVEMENT_ID = "_id";
	

	// Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
		      + TABLE_MOVEMENT_RISK_SPEC
		      + "(" 
		      + COLUMN_ID   + " integer primary key autoincrement, " 
		      + COLUMN_RISK + " text not null, "  
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
		Log.w(UserTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVEMENT_RISK_SPEC);
		onCreate(database);
	}
}
