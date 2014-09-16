package ntnu.master.nofall.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ntnu.master.nofall.R;
import ntnu.master.nofall.database.NoFallDBHelper;
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

		// fun little snippet that prevents users from taking screenshots
		// on ICS+ devices :-)
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
		Cursor cur = db.getMovementSpeed();
		movementSpeed = new Number[cur.getCount()];
		//createdDate = new Number[cur.getCount()];
		while(cur.moveToNext()){
			movementSpeed[temp] = Integer.parseInt(cur.getString(0));
			//createdDate[temp] = Integer.parseInt(cur.getString(1));
			//Log.i("Got data from DB for XYPlot", "Value " +  cur.getString(0) + " Date " + cur.getString(1));
			temp++;
		}
		cur.close();

		// Turn the above arrays into XYSeries':
//		XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), // SimpleXYSeries
//																				// takes
//																				// a
//																				// List
//																				// so
//																				// turn
//																				// our
//																				// array
//																				// into
//																				// a
//																				// List
//				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use
//														// the element index as
//														// the x value
//				"Series1"); // Set the display title of the series

		// same as above
		//XYSeries series2 = new SimpleXYSeries(Arrays.asList(createdDate), Arrays.asList(movementSpeed), "Series2");
		XYSeries series2 = new SimpleXYSeries( Arrays.asList(movementSpeed), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
		// Create a formatter to use for drawing a series using
		// LineAndPointRenderer
		LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);


		// add a new series' to the xyplot:
		//plot.addSeries(series1, series1Format);

		// same as above:
		LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.GREEN, Color.BLUE, Color.RED, null);

		plot.addSeries(series2, series2Format);

		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.getGraphWidget().setDomainLabelOrientation(-45);

	}
}