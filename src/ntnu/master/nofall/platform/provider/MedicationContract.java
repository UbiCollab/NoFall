package ntnu.master.nofall.platform.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for Medication
 * @author Finn
 *
 */
public final class MedicationContract {
	MedicationContract() {}
	
	/**
	 * Medication Spec table
	 * @author Finn
	 *
	 */
	public static final class MedicationSpec implements BaseColumns {
        // This class cannot be instantiated
        private MedicationSpec() {}
        
        public static final int MED_SPEC = 56;
        public static final int MED_SPEC_ID = 66;
        
        public static final String TABLE_NAME = "tblMedSpec";
    	
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);
        
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final String NAME = "name";	
    	
    	public static final String DESCRIPTION = "description";	
    	
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
	 * Medication Spec table
	 * @author Finn
	 *
	 */
	public static final class MedicationType implements BaseColumns {
        // This class cannot be instantiated
        private MedicationType() {}
        
        public static final int MED = 12;
        public static final int MED_ID = 22;
        
        public static final String TABLE_NAME = "tblMedType";
    	
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);
        
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final String NAME = "name";	    	
    	
    	public static final String FK_MEDICATION_CATEGORY = "fkMedicationCategory";
        
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
	 * Medication Category table
	 * @author Finn
	 *
	 */
	public static final class MedicationCategory implements BaseColumns {
        // This class cannot be instantiated
        private MedicationCategory() {}
       
        public static final int MED_CAT = 13;
        public static final int MED_CAT_ID = 23;
    	   	
        public static final String TABLE_NAME = "tblMedCat";
        
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);
        
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final String NAME = "name";    	
        
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
	 * Medication log table
	 * @author Finn
	 *
	 */
	public static final class MedicationLog implements BaseColumns {
        // This class cannot be instantiated
        private MedicationLog() {}
        
        public static final int MED_LOG = 15;
        public static final int MED_LOG_ID = 25;
        
        public static final String TABLE_NAME = "tblMedLog";
        
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);
        
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
            	
    	public static final String NUMBER_OF = "numberOf";
    	
    	public static final String DATE = "date";
        
    	public static final String FK_MED_SPEC = "fkMedSpec";
    	
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
	 * Medication list log
	 * @author Finn
	 *
	 */
	public static final class MedicationListLog implements BaseColumns {
        // This class cannot be instantiated
        private MedicationListLog() {}
        
        public static final int MED_LIST_LOG = 14;
        public static final int MED_LIST_LOG_ID = 24;
        
        public static final String TABLE_NAME = "tblMedListLog";
        
        public static final Uri CONTENT_URI = Uri.parse("content://" + AuthorityContract.AUTHORITY + "/" + TABLE_NAME);
        
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
    			+ "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
    			+ "/" + TABLE_NAME;
        
    	public static final String FK_MED_TYPE = "fkMedType";
    	
    	public static final String FK_MED_LOG = "fkMedLog";
        
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
