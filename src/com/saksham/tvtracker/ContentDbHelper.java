package com.saksham.tvtracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

@SuppressWarnings("unused")
public class ContentDbHelper extends SQLiteOpenHelper {
	
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "tvtracker";
	
	public static Context context = null;
	
	public ContentDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		ContentDbHelper.context = context;
	}
	
	public void onCreate(SQLiteDatabase db) {
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	public static class edit {
		
		public SQLiteDatabase DB; 
		
		public void setDb(SQLiteDatabase db) {
			this.DB = db;
		}
		
		public boolean checkTableExists(String tablename) {

		    Cursor cursor = this.DB.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tablename+"'", null);
		    if(cursor!=null) {
		        if(cursor.getCount()>0) {
		            cursor.close();
		            return true;
		        }
		        cursor.close();
		    }
		    return false;
		}
		
		public static abstract class Constants implements BaseColumns{
			public static final String TIMINGS = "timings";
			public static final String SHOWS = "shows";
			public static final String TABLE_CHANNELS = "channels";
			public static final String COLUMN_CHANNEL = "channel";
			public static final String COLUMN_CHANNEL_NAME = "channel_name";
			public static final String TABLENAME = "tablename";
			public static final String COLUMN_CODE = "code";
			public static final String COLUMN_INCLUDE = "include";
			public static final String COLUMN_REFRESHED = "refreshed";
			public static final String CHANNEL_NAME = "channel_name";
		}
		
		public void createTable(String tablename) {
			
			tablename = "["+tablename+"]";
			
			this.DB.execSQL("DROP TABLE IF EXISTS " + tablename + ";");
			
			String CreateTable = "CREATE TABLE " + tablename + " (" + 
					Constants._ID + " INTEGER PRIMARY KEY," + 
					Constants.TIMINGS + " TEXT," + 
					Constants.SHOWS + " TEXT" + " );";
			
			this.DB.execSQL(CreateTable);
		}
		
		public void putData(String tablename, String timings, String show) {
			
			tablename = "["+tablename+"]";
			
			ContentValues values = new ContentValues();
			values.put(Constants.TIMINGS, timings);
			values.put(Constants.SHOWS, show);
			
			long newRowId;
			newRowId = this.DB.insert(tablename, "null", values);
			
		}
		
		public List<String[]> getData(String tablename, Activity act) {
			
			tablename = "["+tablename+"]";
			
			String columns[] = new String[]{Constants.TIMINGS, Constants.SHOWS};
			List<String[]> all = new ArrayList<String[]>();
			try
			{
				Cursor cursor = this.DB.query(tablename, columns, null, null, null, null, Constants._ID + " ASC");
				while(cursor.moveToNext()) {
					String str1 = cursor.getString(0);
					String str2 = cursor.getString(1);
					all.add(new String[]{str1, str2});
				}
				cursor.close();
			} catch(Exception ex)
			{
				//Nothing to Do Here
			}
			return all;
		}
		
		public void updateRow(String channel, String include, String refresh)
		{
		    ContentValues args = new ContentValues();
		    if(include!=null)
		    	args.put(Constants.COLUMN_INCLUDE, include);
		    if(refresh!=null)
		    	args.put(Constants.COLUMN_REFRESHED, refresh);
	
		    DB.update(Constants.TABLE_CHANNELS, args, Constants.COLUMN_CHANNEL + "=\"" + channel + '"', null);
		}
	
	}
	
}
