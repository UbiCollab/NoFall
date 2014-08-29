package ntnu.master.nofall.contentprovider.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Test {
    // This class cannot be instantiated
    private Test() {}
    
    /**
     * TestSpec table
     * @author Finn
     *
     */
    public static final class TestSpec implements BaseColumns {
        // This class cannot be instantiated
        private TestSpec() {}
        
        public static final String TABLE_NAME = "tblTestSpec";	
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
        public static final int TEST = 38;
        
    	public static final int TEST_ID = 48;
    	
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
     * TestLog table
     * @author Finn
     *
     */
    public static final class TestLog implements BaseColumns {
        // This class cannot be instantiated
        private TestLog() {}
        
    	public static final String TABLE_NAME = "tblTestLog";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
       
        public static final int TEST_LOG = 39;
        
    	public static final int TEST_LOG_ID = 49;  	
    	
    	public static final String DATE = "date";
    	
    	public static final String TOTAL_RISK = "totalRisk";
    	
    	public static final String FK_TEST = "fkTest";

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
     * TestAnswerLog table
     * @author Finn
     *
     */
    public static final class TestAnswerLog implements BaseColumns {
        // This class cannot be instantiated
        private TestAnswerLog() {}
        
    	public static final String TABLE_NAME = "tblAnswerLog";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int TEST_ANSWER_LOG = 50;
        
    	public static final int TEST_ANSWER_LOG_ID = 60;
    	
    	public static final String FK_TEST_LOG = "fkTestLog";
    	
    	public static final String FK_TEST_Q_RISK = "fkTestQRisk";
    	
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
     * TestQuestionSpec table
     * @author Finn
     *
     */
    public static final class TestQuestionSpec implements BaseColumns {
        // This class cannot be instantiated
        private TestQuestionSpec() {}
        
    	public static final String TABLE_NAME = "tblTestQuestionSpec";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int TEST_QUESTION = 51;
        
    	public static final int TEST_QUESTION_ID = 61;
    	
    	public static final String QUESTION = "question";
    	
    	public static final String FK_TEST = "fkTestSpec";
    	
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
     * TestQuestionRiskSpec table
     * @author Finn
     *
     */
    public static final class TestQuestionRiskSpec implements BaseColumns {
        // This class cannot be instantiated
        private TestQuestionRiskSpec() {}
        
    	public static final String TABLE_NAME = "tblTestQuestionRisk";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int TEST_QUESTION_RISK = 52;
        
        public static final int TEST_QUESTION_RISK_ID = 62;
        
    	public static final String ANSWER = "answer";
    	
    	public static final String FK_RISK_STAND_MAP = "fkRiskStandMap";
    	
    	public static final String FK_TEST_QUESTION = "fkTestQuestion";
    	
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
     * TestMeasreSpec table
     * @author Finn
     *
     */
    public static final class TestMeasureSpec implements BaseColumns {
        // This class cannot be instantiated
        private TestMeasureSpec() {}
        
    	public static final String TABLE_NAME = "tblTestMeasureSpec";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int TEST_MEASURE = 53;
        
        public static final int TEST_MEASURE_ID = 63;
        
    	public static final String FK_TEST = "fkTest";
    	
    	public static final String FK_STANDARDS = "fkStandards";
        
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
     * TestMeasureLog table
     * @author Finn
     *
     */
    public static final class TestMeasureLog implements BaseColumns {
        // This class cannot be instantiated
        private TestMeasureLog() {}
        
    	public static final String TABLE_NAME = "tblTestMeasureLog";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int TEST_MEASURE_LOG = 54;
        
    	public static final int TEST_MEASURE_LOG_ID = 64;
    	
    	public static final String VALUE = "value";
    	
    	public static final String FK_MEASURE_SPEC = "fkTestMeasureSpec";
    	
    	public static final String FK_TEST_LOG = "fkTestLog";
    	
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
     * TestMeasureRiskSpec table
     * @author Finn
     *
     */
    public static final class TestMeasureRiskSpec implements BaseColumns {
        // This class cannot be instantiated
        private TestMeasureRiskSpec() {}
        
    	public static final String TABLE_NAME = "tblTestMeasureRiskSpec";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
    	 
        public static final int TEST_MEASURE_RISK = 55;
        
    	public static final int TEST_MEASURE_RISK_ID = 65;
    	
    	public static final String FK_MEASURE_SPEC = "fkTestMeasureSpec";
    	
    	public static final String FK_RISK_STAND_MAP = "fkRiskStandMap";
    	
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
