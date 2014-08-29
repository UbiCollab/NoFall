package ntnu.master.nofall.contentprovider.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Sensors
 * @author Finn
 *
 */
public final class Sensor {
	Sensor() {}
	
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
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int SENS_SPEC = 16;
    	
    	public static final int SENS_SPEC_ID = 26;
    	
    	public static final String NAME = "name";
    	
    	public static final String OWNER_ID = "ownerId";
    	
    	public static final String ACCURACY = "accuracy";
    	
    	public static final String SENSOR_ATTACHMENT = "sensorAttachment";
    	
    	public static final String SENSOR_PLACEMENT = "sensorPlacement";

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
	 * SensorRiskSpec table
	 * @author Finn
	 *
	 */
	public static final class SensorRiskSpec implements BaseColumns {
        // This class cannot be instantiated
        private SensorRiskSpec() {}
        
        public static final String TABLE_NAME = "tblSensorRiskSpec";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
        public static final int SENS_RISK_SPEC = 17;
        
        public static final int SENS_RISK_SPEC_ID = 27;
        
    	public static final String FK_RISK_STAND_MAP = "fkRiskStandMap";
    	
    	public static final String FK_SENSOR = "fkSensor";
        
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
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
        public static final int SENS_LOG = 18;
        
        public static final int SENS_LOG_ID = 28;
        
    	public static final String DATE = "date";
    	
    	public static final String VALUE = "value";
    	
    	public static final String VALUE_AVERAGE = "valueAverage";
    	
    	public static final String NUM_OF_REG = "numOfReg";
    	
    	public static final String LAST_READING = "lastReading";

    	public static final String FK_SENSOR = "fkSensor";
        
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
