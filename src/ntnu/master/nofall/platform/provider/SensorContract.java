package ntnu.master.nofall.platform.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Sensors
 * @author Finn
 *
 */
public final class SensorContract {
	SensorContract() {}
	
	/**
	 * SensorSpec table
	 * @author Finn
	 *
	 */
	public static final class SensorSpec implements BaseColumns {
        // This class cannot be instantiated
        private SensorSpec() {}
        
        public static final String TABLE_NAME = "tblSensorSpec";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int SENS_SPEC = 16;
    	
    	public static final int SENS_SPEC_ID = 26;
    	
    	public static final String NAME = "name";
    	
    	public static final String OWNER_ID = "ownerId";
    	
    	public static final String ACCURACY = "accuracy";

    	public static final String FK_RISK_DEF = "fkRiskDef";
    	
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
	 * SensorLog table
	 * @author Finn
	 *
	 */
	public static final class SensorLog implements BaseColumns {
        // This class cannot be instantiated
        private SensorLog() {}
        
        public static final String TABLE_NAME = "tblSensorLog";	
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
        public static final int SENS_LOG = 18;
        
        public static final int SENS_LOG_ID = 28;        
    	
    	public static final String VALUE_AVERAGE = "valueAverage";
    	
    	public static final String START_TIME = "startTime";

    	public static final String FK_SENSOR_SPEC = "fkSensorSpec";
        
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
	 * SensorLog table
	 * @author Finn
	 *
	 */
	public static final class SensorLogItem implements BaseColumns {
        // This class cannot be instantiated
        private SensorLogItem() {}
        
        public static final String TABLE_NAME = "tblSensorLogItem";	
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
        public static final int SENS_LOG_ITEM = 17;
        
        public static final int SENS_LOG_ITEM_ID = 27;
        
    	public static final String TIME = "time";
    	
    	public static final String VALUE = "value";

    	public static final String FK_SENSOR_LOG= "fkSensorLog";
        
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
