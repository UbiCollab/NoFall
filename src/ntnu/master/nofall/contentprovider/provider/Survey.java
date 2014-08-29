package ntnu.master.nofall.contentprovider.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Sensors
 * @author Finn
 *
 */
public final class Survey {
	Survey() {}
	
	/**
	 * SensorSpec table
	 * @author Finn
	 *
	 */
	public static final class SurveySpec implements BaseColumns {
        // This class cannot be instantiated
        private SurveySpec() {}
        
        public static final String TABLE_NAME = "tblSurveySpec";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int SURVEY = 33;
    	
    	public static final int SURVEY_ID = 43;
    	
    	public static final String NAME = "name";
    	
    	public static final String OWNER_ID = "ownerId";
    	
       	/**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        
        /**
         * The timestamp for when the note was created
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CREATED_DATE = "created";

        /**
         * The timestamp for when the note was last modified
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String MODIFIED_DATE = "modified";
	}
	
	/**
	 * SurveyLog table
	 * @author Finn
	 *
	 */
	public static final class SurveyLog implements BaseColumns {
        // This class cannot be instantiated
        private SurveyLog() {}
        
        public static final String TABLE_NAME = "tblSurveyLog";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

    	public static final int SURVEY_LOG = 34;
    	
    	public static final int SURVEY_LOG_ID = 44;

    	public static final String DATE = "date";
    	
    	public static final String CALCULATED_RISK = "calculatedRisk";
    	
    	public static final String FK_SURVEY = "fkSurvey";
    	
       	/**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        
        /**
         * The timestamp for when the note was created
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CREATED_DATE = "created";

        /**
         * The timestamp for when the note was last modified
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String MODIFIED_DATE = "modified";
	}
	
	/**
	 * SurveyAnswerLog table
	 * @author Finn
	 *
	 */
	public static final class SurveyAnswerLog implements BaseColumns {
        // This class cannot be instantiated
        private SurveyAnswerLog() {}
        
        public static final String TABLE_NAME = "tblSurveyAnswerLog";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

    	public static final int SURVEY_ANSWER_LOG = 35;
    	
    	public static final int SURVEY_ANSWER_LOG_ID = 45;
    	
    	public static final String FK_SURVEY_LOG = "fkSurvey";
    	
    	public static final String FK_SURVEY_Q_RISK_SPEC = "fkSurveyQRisk";
    	
       	/**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        
        /**
         * The timestamp for when the note was created
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CREATED_DATE = "created";

        /**
         * The timestamp for when the note was last modified
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String MODIFIED_DATE = "modified";
	}
	
	/**
	 * SurveyQuestionSpec table
	 * @author Finn
	 *
	 */
	public static final class SurveyQuestionSpec implements BaseColumns {
        // This class cannot be instantiated
        private SurveyQuestionSpec() {}
        
        public static final String TABLE_NAME = "tblSurveyQuestionSpec";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

    	public static final int SURVEY_QUESTION = 36;
    	
    	public static final int SURVEY_QUESTION_ID = 46;
    	
    	public static final String QUESTION = "question";

    	public static final String FK_SURVEY = "fkSurvey";
    	
       	/**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        
        /**
         * The timestamp for when the note was created
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CREATED_DATE = "created";

        /**
         * The timestamp for when the note was last modified
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String MODIFIED_DATE = "modified";
	}
	
	/**
	 * SurveyQuestionRiskSpec table
	 * @author Finn
	 *
	 */
	public static final class SurveyQuestionRiskSpec implements BaseColumns {
        // This class cannot be instantiated
        private SurveyQuestionRiskSpec() {}
        
        public static final String TABLE_NAME = "tblSurveyQRiskSpec";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int SURVEY_QUESTION_RISK = 37;
    	
    	public static final int SURVEY_QUESTION_RISK_ID = 47;
    	
    	public static final String ANSWER = "answer";
    	
    	public static final String FK_RISK_STAND_MAP = "fkRiskStandMap";
    	
    	public static final String FK_SURVEY_QUESTION = "fkSurveyQuestion";
    	
       	/**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";
        
        /**
         * The timestamp for when the note was created
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CREATED_DATE = "created";

        /**
         * The timestamp for when the note was last modified
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String MODIFIED_DATE = "modified";
	}
}
