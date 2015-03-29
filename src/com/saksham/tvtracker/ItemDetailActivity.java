package com.saksham.tvtracker;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.saksham.tvtracker.ContentDbHelper.edit.Constants;

public class ItemDetailActivity extends Activity {

	public String tablename = null;
	public Activity activity = null;
	public View rootView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_item_detail);
		
		rootView = findViewById(R.id.activity_item_detail);
		tablename = getIntent().getStringExtra(Constants.TABLENAME);
		activity = this;
		
		setTitle(getIntent().getStringExtra(Constants.CHANNEL_NAME));
		
		setFullView(tablename, activity, rootView);

	}

	public static void setFullView(String tablename, Activity activity, View rootView)
	{
		ContentDbHelper mHelp = new ContentDbHelper(activity.getApplicationContext());
		ContentDbHelper.edit mHelper = new ContentDbHelper.edit();
		mHelper.setDb(mHelp.getWritableDatabase());
		SQLiteDatabase DB = mHelp.getWritableDatabase();
		
		String last_refreshed_date = "NEVER";
		
		try
		{
			String qr = "SELECT " + Constants.COLUMN_REFRESHED + " FROM " + Constants.TABLE_CHANNELS + " WHERE " + Constants.COLUMN_CHANNEL + "=\"" + tablename + '"'; 
			Cursor cr = DB.rawQuery(qr, null);
			cr.moveToFirst();
			last_refreshed_date = cr.getString(cr.getColumnIndex(Constants.COLUMN_REFRESHED));
			cr.close();
			
		} catch(Exception ex)
		{
			last_refreshed_date = ex.toString();
		}
		
		List<String[]> all = mHelper.getData(tablename, activity);
		
		TextView tv = (TextView) rootView.findViewById(R.id.last_refreshed);
		tv.setText("Last Refreshed: " + last_refreshed_date);
		ListView lv = (ListView) rootView.findViewById(R.id.item_detail);
		myListAdapter customAdapter = new myListAdapter(activity, R.layout.rowlayout, all);
		lv.setAdapter(customAdapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id)
		{
			case R.id.reload_button:
				loadContent();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detailmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void loadContent()
	{
		PopulateDatabaseOnline mytask = new PopulateDatabaseOnline();
		mytask.execute(this, tablename, rootView);
	}
	
	
	public static class myListAdapter extends ArrayAdapter<String[]>
	{
		public myListAdapter(Context context, int textViewResourceId)
		{
			super(context, textViewResourceId);
		}
		
		public myListAdapter(Context context, int textViewResourceId, List<String[]> all)
		{
			super(context, textViewResourceId, all);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			
			if(v==null)
			{
				LayoutInflater li;
				li = LayoutInflater.from(getContext());
				v = li.inflate(R.layout.rowlayout, null);
			}
			
			String[] ele = getItem(position);
			
			if(ele!=null)
			{
				TextView timing = (TextView) v.findViewById(R.id.timing);
				TextView show = (TextView) v.findViewById(R.id.show);
				
				if(timing!=null)
					timing.setText(ele[0]);
				if(show!=null)
					show.setText(ele[1]);
			}
			
			return v;
		}
	}
	
}
