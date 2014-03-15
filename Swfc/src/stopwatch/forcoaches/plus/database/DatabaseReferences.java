package stopwatch.forcoaches.plus.database;

public class DatabaseReferences {
	//Table names
	public static final String RACES_TB = "races";
		public static final String RACES_TB_ID = "_id";
		public static final String RACES_TB_STRING = "dateandtime";
	public static final String CHRONO_TB = "chrononame";
		public static final String CHRONO_TB_RACE_ID = "raceid";
		public static final String CHRONO_TB_NUM = "chrononum";
		public static final String CHRONO_TB_NAME = "chrononame";
	public static final String TIMES_TB = "times";
		public static final String TIMES_TB_RACE_ID = "id";
		public static final String TIMES_TB_CHRONO_NUM = "chrono";
		public static final String TIMES_TB_LAP_NUM = "lapnum";
		public static final String TIMES_TB_TIME = "time";
	
	//Database properties
	public static final String DATABASE_NAME = "StopwatchForCoachesPlus";
	public static final int DATABASE_VERSION = 1;
	
	public static final String []DATABASE_CREATE = {
		" create table " + RACES_TB + " ( " +
		RACES_TB_ID + "  integer primary key autoincrement, " +
		RACES_TB_STRING + " text " +
		" ); "
		,
		" create table " + CHRONO_TB + " ( " +
		CHRONO_TB_RACE_ID + "  integer , " +
		CHRONO_TB_NUM + " integer , " +
		CHRONO_TB_NAME + " text " +
		" ); "
		,
		" create table " + TIMES_TB + " ( " +
		TIMES_TB_RACE_ID + "  integer," +
		TIMES_TB_CHRONO_NUM + " integer, " +
		TIMES_TB_LAP_NUM + " integer, " +
		TIMES_TB_TIME + " integer " +
		" ); "
	};
}

