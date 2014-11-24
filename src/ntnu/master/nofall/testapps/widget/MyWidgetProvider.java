package ntnu.master.nofall.testapps.widget;

import ntnu.master.nofall.R;
import ntnu.master.nofall.platform.provider.SensorContract.SensorLog;
import ntnu.master.nofall.platform.provider.SensorContract.SensorSpec;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";
	private ContentResolver myCR;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				MyWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			// create some random data
			//int number = (new Random().nextInt(100));
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			
			int number = 0;
			
			myCR = context.getContentResolver();
			Log.i("My CR is", "CR: " + myCR);
			
			Cursor cur = getNumberOfSteps();
			if(cur != null){
				cur.moveToPosition(cur.getCount() - 1);
				number = Integer.parseInt(cur.getString(0));
			}
			
			Log.w("WidgetExample", String.valueOf(number));
			// Set the text
			remoteViews.setTextViewText(R.id.update, String.valueOf(number));

			// Register an onClickListener
			Intent intent = new Intent(context, MyWidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
			
			cur.close();
		}
	}	
	
	/**
	 * Gets the number of steps registered by the pedometer
	 * @return Curs 0 = value
	 */
	 public Cursor getNumberOfSteps(){
	    	Cursor cursor1 = null;
	    	Cursor cursor = null;
	    	try{
		        String selection = SensorSpec.NAME + " = \"" + "pedometer" + "\"";
		    	String[] projection = {SensorSpec._ID};
		    		    	
		    	cursor1 = myCR.query(SensorSpec.CONTENT_URI, 
		    		             projection, selection, null,
		    		    	       null);
		    	boolean temp;
		    	if(cursor1.moveToFirst() != false){
		    		temp = cursor1.moveToFirst();
			    	
			    	Log.i("Getting ID for pedometer", cursor1.getString(0));
			    		
			    	String selection2 = SensorLog.FK_SENSOR_SPEC + " = \"" + cursor1.getString(0) + "\"";
			    	String[] projection2 = {SensorLog.VALUE_AVERAGE};
			    	cursor = myCR.query(SensorLog.CONTENT_URI, 
		    	             projection2, selection2, null,
		    	    	       null);
			    	temp = cursor.moveToFirst();
			    	if(temp == false){
			    		return null;
			    	}
		    	}	    		    			    	
		    		
		    	return cursor;
	    	}catch(SQLiteException e){
	    		Log.i("SQLiteException when inserting movement speed", "error: " + e);
	    		return null;
//	    	}finally{
//	    		if(cursor != null){
//	    			cursor.close();
//	    			cursor = null;
//	    		}
//	    		if(cursor1 != null){
//	    			cursor1.close();
//	    			cursor1 = null;
//	    		}
	    	}
	    }
}
