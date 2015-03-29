package com.saksham.tvtracker;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.saksham.tvtracker.ContentDbHelper.edit.Constants;

public class AddRemoveChannels extends ListActivity {

	public static List<String> channel = new ArrayList<String>();
	public static List<String> channel_name = new ArrayList<String>();
	public static List<String[]> channels = new ArrayList<String[]>();
	public ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		list = getListView();
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		List<Integer> included = new ArrayList<Integer>();
		int index = 0;
		
		ContentDbHelper mHelp = new ContentDbHelper(getApplicationContext());
		SQLiteDatabase db = mHelp.getReadableDatabase();
		
		channel_name.clear();
		channel.clear();
		included.clear();
		
		String columns[] = new String[]{Constants.COLUMN_CHANNEL, Constants.COLUMN_CHANNEL_NAME, Constants.COLUMN_INCLUDE, Constants.COLUMN_CODE};
		try
		{
			Cursor cursor = db.query(Constants.TABLE_CHANNELS, columns, null, null, null, null, Constants.COLUMN_CHANNEL + " ASC");
			while(cursor.moveToNext())
			{
				channel.add(cursor.getString(0));
				channel_name.add(cursor.getString(1));
				channels.add(new String[]{cursor.getString(1), cursor.getString(3)});
				if(cursor.getInt(2)==1)
					included.add(index);
				index++;
			}
		} 
		catch (Exception ex)
		{
			channel_name.add("Please Click Populate Database in options menu");
		}
		
		if(channel_name.size()==0)
			channel_name.add("Please Click Populate Database in options menu");
		
		list.setAdapter(new channelAdapter(this, android.R.id.text1, channels, getResources(), getPackageName()));
		
		int size = included.size();
		for(int i=0; i<size; i++)
			list.setItemChecked(included.get(i), true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_remove_channels, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.action_save_status)
		{
			onSave();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSave()
	{
		ContentDbHelper mHelp = new ContentDbHelper(getApplicationContext());
		ContentDbHelper.edit mHelper = new ContentDbHelper.edit();
		mHelper.setDb(mHelp.getWritableDatabase());
		
		int size = channel.size();
		for(int i=0; i<size; i++)
		{
			if(list.isItemChecked(i))
				mHelper.updateRow(channel.get(i), "1", null);
			else
				mHelper.updateRow(channel.get(i), "0", null);
		}
		
		Intent intent = new Intent(this, ItemListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
//		this.finishActivity(getIntent().getIntExtra("PARENT", 0));
		this.finish();
	}
	
	public static class channelAdapter extends ArrayAdapter<String[]>
	{
		public Resources res = null;
		public String pkg = null;
		
		public channelAdapter(Context context, int textViewResourceId, List<String[]> all, Resources resources, String packageName)
		{
			super(context, textViewResourceId, all);
			res = resources;
			pkg = packageName;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			
			if(v==null)
			{
				LayoutInflater li = LayoutInflater.from(getContext());
				v = li.inflate(android.R.layout.simple_list_item_activated_1, null);
			}
			
			String[] ele = getItem(position);
			
			if(ele!=null)
			{
				TextView tv = (TextView) v.findViewById(android.R.id.text1);
				if(tv!=null)
				{
					tv.setText(ele[0]);
					if(ele[1] != "")
					{
						int id = res.getIdentifier("ic_ch_"+ele[1], "drawable", pkg);
	//					Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
						tv.setCompoundDrawablesWithIntrinsicBounds(res.getDrawable(id), null, null, null);
						tv.setCompoundDrawablePadding(10);
					}
				}
			}
			
			return v;
		}
	}

}
