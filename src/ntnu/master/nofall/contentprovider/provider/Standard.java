package ntnu.master.nofall.contentprovider.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Standards
 * @author Finn
 *
 */
public final class Standard {
	Standard() {}
	
	/**
	 * Standards table
	 * @author Finn
	 *
	 */
	public static final class Standards implements BaseColumns {
        // This class cannot be instantiated
        private Standards() {}
        
        public static final String TABLE_NAME = "tblStandards";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_STANDARDS = 19;
    	
    	public static final int RISK_STANDARDS_ID = 29;
    	
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
	public static final class StandardsRiskMap implements BaseColumns {
        // This class cannot be instantiated
        private StandardsRiskMap() {}
        
        public static final String TABLE_NAME = "tblRiskStandMap";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_STANDARDS_MAP = 30;
    	
    	public static final int RISK_STANDARDS_MAP_ID = 40;
    	
		public static final String RANGE_FROM = "rangeFrom";
		
		public static final String RANGE_TO = "rangeTo";
		
		public static final String FK_STAND_NOFALL = "fkNoFallStand";
		
		public static final String FK_STAND_FOREIGN = "fkForeignStand";
    	    	
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
	public static final class StandardsNoFall implements BaseColumns {
        // This class cannot be instantiated
        private StandardsNoFall() {}
        
        public static final String TABLE_NAME = "tblNoFallRisk";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_STANDARDS_NOFALL = 31;
    	
    	public static final int RISK_STANDARDS_NOFALL_ID = 41;
    	
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
	public static final class StandardsForeign implements BaseColumns {
        // This class cannot be instantiated
        private StandardsForeign() {}
        
        public static final String TABLE_NAME = "tblForeignStandard";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + ContentContract.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final int RISK_STANDARDS_FOREIGN = 32;
    	
    	public static final int RISK_STANDARDS_FOREIGN_ID = 42;
    	
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

}
