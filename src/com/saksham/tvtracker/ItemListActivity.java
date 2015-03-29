package com.saksham.tvtracker;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.saksham.tvtracker.AddRemoveChannels.channelAdapter;
import com.saksham.tvtracker.ContentDbHelper.edit.Constants;

public class ItemListActivity extends ListActivity {

	public static List<String> channel = new ArrayList<String>();
	public static List<String[]> channels_codes = new ArrayList<String[]>();
	
	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		updateView();	
	}

	public void updateView()
	{
		ContentDbHelper mHelp = new ContentDbHelper(getApplicationContext());
	//	if(ContentDbHelper.context!=null)
	//		toast(ContentDbHelper.context.toString());
	//	else
	//		toast("NULL");
		SQLiteDatabase db = mHelp.getReadableDatabase();
		
	//	toast("Populating Database");
		
	//	Intent intent = new Intent(getActivity(), PopulateDatabaseActivity.class);
	//	startActivity(intent);
		
	//	toast("Database Populated");
		
		channels_codes.clear();
		channel.clear();
		String columns[] = new String[]{Constants.COLUMN_CHANNEL, Constants.COLUMN_CHANNEL_NAME, Constants.COLUMN_CODE, Constants.COLUMN_INCLUDE};
		try
		{
			Cursor cursor = db.query(Constants.TABLE_CHANNELS, columns, null, null, null, null, Constants.COLUMN_CHANNEL + " ASC");
		//	toast(cursor.toString());
			while(cursor.moveToNext()) {
				if(cursor.getInt(3)==1)
				{	
					channel.add(cursor.getString(0));
					channels_codes.add(new String[]{cursor.getString(1), cursor.getString(2)});
				}
			}
		} catch (Exception ex)
		{
			channels_codes.add(new String[]{"Initialising", ""});
			PopulateDatabaseOnline mytask = new PopulateDatabaseOnline();
			mytask.execute(this, "TABLE", null);
		}
		
		if(channels_codes.size()==0)
		{
			channels_codes.add(new String[]{"Please Select Channels to show from Add/Remove Channels page", ""});
		}
		
		setListAdapter(new channelAdapter(this, android.R.id.text1, channels_codes, getResources(), getPackageName()));

	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id)
	{
		super.onListItemClick(listView, view, position, id);
		
		toast(channel.get(position) + " " + channels_codes.get(position)[1]);
		
		Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		detailIntent.putExtra(Constants.TABLENAME, channel.get(position));
		detailIntent.putExtra(Constants.CHANNEL_NAME, channels_codes.get(position)[0]);
		startActivity(detailIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()) 
		{
			case R.id.add_remove_channels:
				openAddRemoveChannels();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void openAddRemoveChannels()
	{
		Intent intent = new Intent(this, AddRemoveChannels.class);
		startActivity(intent);
	}
}
