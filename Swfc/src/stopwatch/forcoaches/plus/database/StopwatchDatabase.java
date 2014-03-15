package stopwatch.forcoaches.plus.database;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StopwatchDatabase {	
//	private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
	
	public StopwatchDatabase open(Context ctx) throws SQLException {
		DatabaseHelper dbHelper = new DatabaseHelper(ctx);
        	
		db = dbHelper.getWritableDatabase();
        
        return this;
    }
    
	public void close(){
		if (db != null){
			db.close();
		}
	}
	
	public Cursor makeQuery(String sql){

		return db.rawQuery(sql, null);
	}
	
	public long insert(String table, ContentValues values){
		return db.insert(table, null, values);
	}
	
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
		return db.update(table, values, whereClause, whereArgs);
	}
	
	public int delete(String table, String whereClause, String[] whereArgs){
		return db.delete(table, whereClause, whereArgs);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
            super(context, DatabaseReferences.DATABASE_NAME, null, DatabaseReferences.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	for (int i = 0; i < DatabaseReferences.DATABASE_CREATE.length; i++){	
        		db.execSQL(DatabaseReferences.DATABASE_CREATE[i]);
        	}
        }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
	
	public void deleteAllRecordsFromDate(Activity act, long id){
		open(act);
		delete(DatabaseReferences.RACES_TB, 
				DatabaseReferences.RACES_TB_ID + "==" + id, 
				null);
		delete(DatabaseReferences.CHRONO_TB, 
				DatabaseReferences.CHRONO_TB_RACE_ID + "==" + id, 
				null);
		delete(DatabaseReferences.TIMES_TB, 
				DatabaseReferences.TIMES_TB_RACE_ID + "==" + id, 
				null);
		
		close();
	}
	
	public ArrayList<ChronoNames> getChronoNames(Activity act, long id){
		ArrayList<ChronoNames> list = new ArrayList<ChronoNames>();
		
		open(act);
        
        String sql = "select " + DatabaseReferences.CHRONO_TB_NUM + " , " + DatabaseReferences.CHRONO_TB_NAME  + 
        		" from " + DatabaseReferences.CHRONO_TB + 
        		" where " + DatabaseReferences.CHRONO_TB_RACE_ID + "==" + id + 
        		" ; ";
        
        Cursor cursor = makeQuery(sql);
        act.startManagingCursor(cursor);
        cursor.moveToFirst();
        
        while (!cursor.isAfterLast()){
        	list.add(new ChronoNames(cursor.getInt(0),cursor.getString(1)));
        	cursor.move(1);
        }
        
        act.stopManagingCursor(cursor);
        close();
        
        return list;
	}
	
	public ArrayList<SavedDate> getSavedDates(Activity act){
		ArrayList<SavedDate> list = new ArrayList<SavedDate>();
		
		open(act);
        
		String sql = "select " + DatabaseReferences.RACES_TB_ID + " , " + DatabaseReferences.RACES_TB_STRING  + " from " + DatabaseReferences.RACES_TB + ";";
        
        Cursor cursor = makeQuery(sql);
        act.startManagingCursor(cursor);
        cursor.moveToFirst();
        
        while (!cursor.isAfterLast()){
        	list.add(new SavedDate(cursor.getLong(0),cursor.getString(1)));
        	cursor.move(1);
        }
        
        act.stopManagingCursor(cursor);
        close();
        
        return list;
	}
	
	public ArrayList<Long> getSavedTimes(Activity act, long id, int chronoNum){
		ArrayList<Long> list = new ArrayList<Long>();
		
		open(act);
        
		 String sql = "select " + DatabaseReferences.TIMES_TB_LAP_NUM + " , " + DatabaseReferences.TIMES_TB_TIME  + 
	        		" from " + DatabaseReferences.TIMES_TB + 
	        		" where (" + DatabaseReferences.TIMES_TB_RACE_ID + "==" + id + 
	        		" and " + DatabaseReferences.TIMES_TB_CHRONO_NUM + "==" + chronoNum + 
	        		") ; ";
		
        Cursor cursor = makeQuery(sql);
        act.startManagingCursor(cursor);
        cursor.moveToFirst();
        
        while (!cursor.isAfterLast()){
        	list.add(cursor.getLong(1));
        	cursor.move(1);
        }
        
        act.stopManagingCursor(cursor);
        close();
        
        return list;
	}
}

