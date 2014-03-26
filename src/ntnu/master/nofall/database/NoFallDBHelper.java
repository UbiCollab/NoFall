package ntnu.master.nofall.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoFallDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "nofall.db";
	private static final int DATABASE_VERSION = 2;

	public NoFallDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		UserTable.onCreate(database);
		MedicationTable.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		UserTable.onUpgrade(database, oldVersion, newVersion);
		MedicationTable.onUpgrade(database, oldVersion, newVersion);
	}
}
