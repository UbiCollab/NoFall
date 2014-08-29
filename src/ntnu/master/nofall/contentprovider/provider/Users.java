package ntnu.master.nofall.contentprovider.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Users
 * @author Finn
 *
 */
public final class Users {
    // This class cannot be instantiated
    private Users() {}
    
    /**
     * Users table
     * @author Finn
     *
     */
    public static final class User implements BaseColumns {
        // This class cannot be instantiated
        private User() {}

        public static final String TABLE_NAME = "tblUser";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int USER = 10;
    	
        public static final int USER_ID = 20;
                       
        public static final String GENDER = "gender";

        public static final String AGE = "age";

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
     * UserTotalRisk table
     * @author Finn
     *
     */
    public final static class UserTotalRisk implements BaseColumns{
    	private UserTotalRisk() {}
    	
    	public static final String TABLE_NAME = "tblTotalRiskLog";
    	
    	 /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + Authority.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final int USER_TOTAL_RISK = 11;
    	
    	public static final int USER_TOTAL_RISK_ID = 21;
              	      
        public static final String MEDICATION_RISK = "medicationRisk";

        public static final String TEST_RISK = "testRisk";

        public static final String SURVEY_RISK = "surveyRisk";
        
        public static final String SENSOR_RISK = "sensorRisk";
        
    	public static final String DATE = "date";
        
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
