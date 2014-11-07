package ntnu.master.nofall.testapps.survey;

import ntnu.master.nofall.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SurveyActivity extends Activity{
	   /** Called when the activity is first created. */
		private TextView surveyQuestion;
		
		private int rowIndex = 1;
		private static int score=0;
		private int questNo=0;
		private boolean checked=false;
		private boolean flag=true;
		
		private RadioGroup radioGroup;
		private RadioButton radioButton;
		//final DataBaseHelper db = new DataBaseHelper(this);
		
		Cursor c1;
		Cursor c2;
		Cursor c3;
		
		int counter=1;	
		String label;
		
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_survey);
	        String options[] = new String[19];
	     
	        
	        // get reference to radio group in layout
	    	radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
			
//	        try {
//				db.createDataBase();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        c3 = db.getCorrAns();
//	        
//	        
//	        for (int i=0;i<=4;i++)
//	        {
//	        	corrAns[i]=c3.getString(0);
//	        	c3.moveToNext();
//	        
//	        }
	        
	        
	        surveyQuestion = (TextView) findViewById(R.id.activity_survey_TV_SurveyQuestion);
	        
	       // displayQuestion();
	       
	    }
	    
//		private void displayQuestion()
//		{
//			//Fetching data quiz data and incrementing on each click
//			
//			//c1=db.getQuiz_Content(rowIndex);
//			
//		
//			surveyQuestion.setText(c1.getString(0));
//			
//			radioGroup.removeAllViews();
//			for (int i=0;i<=3;i++)
//			{
//				//Generating and adding 4 radio buttons dynamically 
//				radioButton = new RadioButton(this);
//				radioButton.setText(c2.getString(0));
//				radioButton.setId(i);
//				c2.moveToNext();
//				radioGroup.addView(radioButton);
//				
//			}
//		}
}
