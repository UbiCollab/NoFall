package ntnu.master.nofall.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.myapps.quiz/databases/";
	private static String DB_NAME = "quiz";
	private static String Table_name = "Quiz";

	private SQLiteDatabase myDataBase;
	private SQLiteDatabase myData;
	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

//	/**
//	 * Creates a empty database on the system and rewrites it with your own
//	 * database.
//	 * */
//	public void createDataBase() throws IOException {
//
//		boolean dbExist = checkDataBase();
//		if (dbExist) {
//			// do nothing - database already exist
//		} else {
//			CopyFiles();
//		}
//	}

//	private void CopyFiles()
//	{
//	try
//	{ 
//	InputStream is = myContext.getAssets().open(DB_NAME); 
//	File outfile = new File(DB_PATH,DB_NAME);
//	outfile.getParentFile().mkdirs();
//	outfile.createNewFile();
//
//
//	if (is == null)
//	throw new RuntimeException("stream is null");
//	else
//	{
//	FileOutputStream out = new FileOutputStream(outfile); 
//	// BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(outfile));
//	byte buf[] = new byte[128];
//	do {
//	int numread = is.read(buf);
//	if (numread <= 0) break; out.write(buf, 0, numread); } while (true); is.close(); out.close(); } //AssetFileDescriptor af = am.openFd("world_treasure_hunter_deluxe.apk"); } catch (IOException e) { throw new RuntimeException(e); } } /** * Check if the database already exist to avoid re-copying the file each time you open the application. * @return true if it exists, false if it doesn't */ private boolean checkDataBase(){ SQLiteDatabase checkDB = null; try{ String myPath = DB_PATH + DB_NAME; checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY); }catch(SQLiteException e){ } if(checkDB != null){ checkDB.close(); } return checkDB != null ? true : false; } /** * Copies your database from your local assets-folder to the just created empty database in the * system folder, from where it can be accessed and handled. * This is done by transfering bytestream. * */ private void copyDataBase() throws IOException{ //Open your local db as the input stream InputStream myInput = myContext.getAssets().open(DB_NAME); // Path to the just created empty db String outFileName = DB_PATH + DB_NAME; //Open the empty db as the output stream OutputStream myOutput = new FileOutputStream(outFileName); //transfer bytes from the inputfile to the outputfile byte[] buffer = new byte[1024]; int length; while ((length = myInput.read(buffer))>0){
//	myOutput.write(buffer, 0, length);
//	}
//
//
//	//Close the streams
//	myOutput.flush();
//	myOutput.close();
//	myInput.close();
//
//
//	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// / Get Book content////////
	public Cursor getQuiz_Content(int bookId) {
		String myPath = DB_PATH + DB_NAME;
		myData = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

		Cursor cur;
		cur = myData.rawQuery("select quiz_text from Quiz where quiz_id='"
				+ bookId + "'", null);
		cur.moveToFirst();

		myData.close();
		return cur;
	};

	// ////////////////////////

	// / Get Book content////////
	public Cursor getQuiz_List() {
		String myPath = DB_PATH + DB_NAME;
		myData = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
		int i;

		Cursor cur;
		cur = myData.rawQuery(
				"select quiz_id,quiz_text,correct_answer from quiz", null);
		cur.moveToFirst();
		i = cur.getCount();
		myData.close();
		return cur;
	};

	// ////////////////////////

	public Cursor getAns(int quizid) {
		String myPath = DB_PATH + DB_NAME;
		myData = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

		Cursor cur;
		cur = myData.rawQuery("select answers from answer where quiz_id='"
				+ quizid + "'", null);
		cur.moveToFirst();
		myData.close();
		return cur;
	}

	public Cursor getAnsList() {
		String myPath = DB_PATH + DB_NAME;
		myData = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

		Cursor cur;
		cur = myData.rawQuery("select answers from answer", null);
		cur.moveToFirst();
		myData.close();

		return cur;
	}

	public Cursor getCorrAns() {
		String myPath = DB_PATH + DB_NAME;
		myData = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

		Cursor cur;
		cur = myData.rawQuery("select correct_answer from quiz", null);
		cur.moveToFirst();
		myData.close();

		return cur;
	}
	// ---updates a title---
	/*
	 * public boolean UpdateFavourite_Individual(long rowid,String fav) { String
	 * myPath = DB_PATH + DB_NAME; myData = SQLiteDatabase.openDatabase(myPath,
	 * null, SQLiteDatabase.OPEN_READWRITE);
	 * 
	 * 
	 * ContentValues args = new ContentValues(); args.put("bookmark", fav);
	 * return myData.update("lyrics", args, "rowid=" + rowid, null) > 0; }
	 */
	// ////////////////

}
