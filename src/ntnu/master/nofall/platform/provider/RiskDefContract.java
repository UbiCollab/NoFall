package ntnu.master.nofall.platform.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Standards
 * @author Finn
 *
 */
public final class RiskDefContract {
	RiskDefContract() {}
	
	/**
	 * Standards table
	 * @author Finn
	 *
	 */
	public static final class MeasureStandards implements BaseColumns {
        // This class cannot be instantiated
        private MeasureStandards() {}
        
        public static final String TABLE_NAME = "tblMeasureStandards";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_MEASURE_STANDARDS = 19;
    	
    	public static final int RISK_MEASURE_STANDARDS_ID = 29;
    	
    	public static final String MEASURE_TYPE = "measureType";
    	
    	public static final String DATA_TYPE = "dataType";
    	
    	public static final String DATA_UNIT = "dataUnit";
    	
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
	 * Standards mapping table
	 * @author Finn
	 *
	 */
	public static final class RiskMap implements BaseColumns {
        // This class cannot be instantiated
        private RiskMap() {}
        
        public static final String TABLE_NAME = "tblRiskMap";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_MAP = 30;
    	
    	public static final int RISK_MAP_ID = 40;
    	
		public static final String RANGE_FROM = "rangeFrom";
		
		public static final String RANGE_TO = "rangeTo";
		
		public static final String FK_RISK_LEVELS = "fkRiskLevels";
		
		public static final String FK_RISK_DEFINITIONS = "fkRiskDef";
    	    	
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
	 * NoFall native standards
	 * @author Finn
	 *
	 */
	public static final class RefRiskLevels implements BaseColumns {
        // This class cannot be instantiated
        private RefRiskLevels() {}
        
        public static final String TABLE_NAME = "tblRefRiskLevels";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int REF_RISK_LEVELS = 31;
    	
    	public static final int REF_RISK_LEVELS_ID = 41;
    	
		public static final String NAME = "name";
		
		public static final String DESCRIPTION = "description";
    	
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
	 * Foreign standards
	 * @author Finn
	 *
	 */
	public static final class RiskDefinition implements BaseColumns {
        // This class cannot be instantiated
        private RiskDefinition() {}
        
        public static final String TABLE_NAME = "tblRiskDef";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_DEFINITION = 32;
    	
    	public static final int RISK_DEFINITION_ID = 42;
    	
		public static final String NAME = "name";
		
		public static final String DESCRIPTION = "description";
		
		public static final String FK_MEAS_STAND = "fkMeasStand";
    	
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
