package ntnu.master.nofall.testapps.graphicalplots;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ntnu.master.nofall.R;
import ntnu.master.nofall.platform.database.NoFallDBHelper;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class XYPlotActivity extends Activity {

	private XYPlot plot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
				WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.xy_plot);

		// initialize our XYPlot reference:
		plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

		// Create a couple arrays of y-values to plot:
		Number[] series1Numbers = { 1, 8, 5, 2, 7, 4 };
		Number[] series2Numbers = { 4, 6, 3, 8, 2, 10 };
		Number[] movementSpeed;
		//Number[] createdDate;
		int temp = 0;
		NoFallDBHelper db = new NoFallDBHelper(this);
		Log.i("Getting movement speed", "yeeahh");
		Cursor cur = db.getNumberOfSteps();
		movementSpeed = new Number[cur.getCount()];
		
		//createdDate = new Number[cur.getCount()];
		cur.moveToFirst();
		Log.i("number of steps", "is " + cur.getString(0));
		movementSpeed[temp] = Integer.parseInt(cur.getString(0));
		temp++;
		
		while(cur.moveToNext()){
			movementSpeed[temp] = Integer.parseInt(cur.getString(0));
			//createdDate[temp] = Integer.parseInt(cur.getString(1));
			//Log.i("Got data from DB for XYPlot", "Value " +  cur.getString(0) + " Date " + cur.getString(1));
			temp++;
		}
		cur.close();

		// Turn the above arrays into XYSeries':
		XYSeries series2 = new SimpleXYSeries( Arrays.asList(movementSpeed), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Movement Speed");
		LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.GREEN, Color.BLUE, Color.RED, null);

		plot.addSeries(series2, series2Format);

		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.getGraphWidget().setDomainLabelOrientation(-45);

	}
}