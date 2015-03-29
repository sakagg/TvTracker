package com.saksham.tvtracker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.saksham.tvtracker.ContentDbHelper.edit.Constants;

@SuppressWarnings("unused")

public class PopulateDatabaseOnline extends AsyncTask<Object, Integer, Long> {

	public static final String HOST = "http://web.iiit.ac.in/~saksham.aggarwal/TvTracker/";
	public static final String HOST2 = "http://192.168.0.2/TvTracker/scripts/";
	public static final String HOST3 = "http://tvtracker.host56.com/";
	
	public void RetreiveData(Context context, String what) throws IOException {
	   
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    
	    if (networkInfo != null && networkInfo.isConnected()) {
	        
	    	//Get Database
			
			ContentDbHelper mHelp = new ContentDbHelper(context);
			ContentDbHelper.edit mHelper = new ContentDbHelper.edit();
			mHelper.setDb(mHelp.getWritableDatabase());
			
			if(what == "TABLE")
	    	{
	    		String[] tableList = {"food-food","Food Food","251","zee-khana-khazana","Zee Khana Khazana","209","-pictures","& Pictures","385","asianet-movies","Asianet Movies","271","b4u-movies","B4U Movies","88","cinema-tv","Cinema TV","280","filmy","FILMY","82","gemini-movies","Gemini Movies","185","hbo","HBO","8","hbo-defined","HBO Defined","286","hbo-hits","HBO Hits","285","india-talkies","India Talkies","199","jaya-movie","Jaya Movie","221","kiran-tv","Kiran Tv","145","ktv","KTV","162","ktv-hd","Ktv HD","502","maa-movies","Maa Movies","231","mgm","MGM","95","movies-now","Movies Now","207","movies-now-hd","Movies Now HD","518","movies-ok","Movies Ok","270","raj-digital-plus","Raj Digital Plus","161","romedy-now","Romedy Now","482","set-max","SET MAX","51","sony-pix","Sony PIX","53","star-gold","Star Gold","64","star-gold-hd","Star Gold HD","243","star-movies","Star Movies","59","star-movies-action","Star Movies Action","284","star-movies-hd","Star Movies HD","245","udaya-movies","Udaya Movies","140","utv-action","UTV Action","73","utv-movies","UTV Movies","76","wb","WB","99","world-movies","World Movies","74","zee-action","Zee Action","90","zee-cinema","Zee Cinema","4","zee-classic","Zee Classic","92","zee-premier","Zee Premier","91","zee-studio","Zee Studio","63","zee-talkies","Zee Talkies","132","9x","9X","84","akash-bangla","Akash Bangla","322","amrita-tv","Amrita Tv","205","anjan-tv","Anjan Tv","376","asianet","Asianet","153","asianet-plus","Asianet Plus","157","asianet-suvarna","Asianet Suvarna","165","australia-network","Australia Network","187","axn","AXN","1","big-cbs-love","Big CBS Love","219","big-cbs-prime","Big CBS Prime","213","big-magic","Big Magic","357","big-rtl-thrill","BIG RTL Thrill","288","bindass-tv","Bindass Tv","75","channel-v-","Channel [V]","81","colors","Colors","66","dabangg-tv","Dabangg TV","297","dd-bharati","DD Bharati","177","dd-india","DD India","178","dd-national","DD National","174","dd-urdu","DD Urdu","179","dhamaal-tv","Dhamaal TV","298","dy-365","DY 365","340","e-24","E 24","184","etv","Etv","120","etv-bangla","Etv Bangla","115","etv-bihar","Etv Bihar","116","etv-gujarati","Etv Gujarati","122","etv-kannada","Etv Kannada","124","etv-marathi","Etv Marathi","117","etv-mp","Etv MP","118","etv-oriya","Etv Oriya","6","etv-rajasthan","Etv Rajasthan","44","etv-up","Etv UP","126","etv-urdu","Etv Urdu","125","firangi","Firangi","255","fox-crime","Fox Crime","168","fox-traveller","Fox Traveller","83","fox-traveller-hd","Fox Traveller HD","504","fx","FX","169","gemini-tv","Gemini Tv","137","gemini-tv-hd","Gemini Tv HD","497","imayam-tv","Imayam Tv","263","jan-tv","Jan TV","344","jaya-max","Jaya Max","149","jaya-plus","Jaya Plus","148","jaya-tv","Jaya Tv","147","jeevan-tv","Jeevan Tv","279","kairali-tv","Kairali Tv","197","kalaignar-chithiram","Kalaignar Chithiram","215","kalaignar-sirippoli","Kalaignar Sirippoli","191","kalaignar-tv","Kalaignar Tv","189","kasthuri-tv","Kasthuri TV","500","khushboo-tv","Khushboo Tv","437","life-ok","Life Ok","259","life-ok-hd","Life Ok HD","358","maa-gold","Maa Gold","274","maa-tv","Maa Tv","247","mahuaa-tv","Mahuaa Tv","507","mega-tv","Mega TV","499","mi-marathi","Mi Marathi","114","om-bangla","Om Bangla","362","polimer-tv","Polimer Tv","352","raj-tv","Raj Tv","144","ruposhi-bangla","Ruposhi Bangla","490","saam-tv","Saam TV","350","sab-tv","SAB TV","77","sada-channel","Sada Channel","356","sahara-one","Sahara One","89","sony-entertainment-tv","Sony Entertainment TV","78","sony-tv-hd","Sony TV HD","506","star-jalsha","Star Jalsha","119","star-plus","Star Plus","29","star-plus-hd","Star Plus HD","239","star-pravah","Star Pravah","131","star-utsav","Star Utsav","30","star-vijay","Star Vijay","164","star-world","Star World","23","star-world-hd","Star World HD","241","star-world-premier-hd","Star World Premier HD","515","sun-tv","Sun Tv","152","sun-tv-hd","Sun Tv HD","508","surya-tv","Surya Tv","136","suvarna-plus","Suvarna Plus","460","udaya","Udaya","134","utv-stars","UTV Stars","253","zee-bangla","Zee Bangla","489","zee-cafe","Zee Cafe","65","zee-kannada","Zee Kannada","272","zee-marathi","Zee Marathi","133","zee-smile","Zee Smile","155","zee-telugu","Zee Telugu","292","zee-tv","ZEE TV","32","zee-tv-hd","Zee TV HD","438","zee-variasi","Zee Variasi","501","zetc-punjabi","ZETC Punjabi","367","zing","Zing","109","zoom","Zoom","20","adithya-tv","Adithya TV","299","animax","Animax","57","baby-tv","Baby TV","315","cartoon-network","Cartoon Network","3","chintu-tv","Chintu Tv","150","chutti-tv","Chutti TV","112","discovery-kids","Discovery Kids","291","disney-channel","Disney Channel","93","disney-xd","Disney XD","96","hungama-tv","Hungama TV","130","kochu-tv","Kochu Tv","278","kushi-tv","Kushi Tv","151","nick-junior","Nick Junior","294","nickelodeon","Nickelodeon","79","pogo","POGO","58","sonic","Sonic","293","topper-tv","Topper TV","341","zeeq","ZeeQ","273","animal-planet","Animal Planet","45","discovery-channel","Discovery Channel","46","discovery-hd","Discovery HD","182","discovery-science","Discovery Science","172","discovery-tamil","Discovery Tamil","364","discovery-telugu","Discovery Telugu","510","discovery-turbo","Discovery Turbo","171","history-tv-18","History Tv 18","265","nat-geo-people","Nat Geo People","173","nat-geo-people-hd","Nat Geo People HD","498","nat-geo-wild","Nat Geo Wild","180","national-geographic-channel","National Geographic Channel","71","national-geographic-channel-hd","National Geographic Channel HD","181","ftv","FTV","7","homeshop-18","HomeShop 18","128","ndtv-good-times","NDTV Good Times","98","tlc","TLC","61","travel-xp","Travel XP","211","zee-trendz","Zee Trendz","94","9x-jalwa","9X Jalwa","277","9x-jhakaas","9X Jhakaas","276","9x-tashan","9X Tashan","371","9xm","9XM","104","9xo","9xO","295","b4u-music","B4U Music","87","dhoom-music","Dhoom Music","318","etc","Etc","113","gemini-music","Gemini Music","139","kalaignar-isai-aruvi","Kalaignar Isai Aruvi","193","kalaignar-murasu","Kalaignar Murasu","505","maa-music","Maa Music","235","mastiii","Mastiii","237","mega-musiq","Mega Musiq","360","mtunes","Mtunes","339","mtunes-hd","Mtunes HD","331","mtv","MTV","9","music-india","Music India","203","music-xpress","Music Xpress","365","nat-geo-music","Nat Geo Music","217","ptc-chak-de","PTC Chak De","354","raj-music-karnataka","Raj Music Karnataka","481","raj-musix","Raj Musix","163","raj-musix-telugu","Raj Musix Telugu","418","sangeet-bangla","Sangeet Bangla","229","sangeet-bhojpuri","Sangeet Bhojpuri","227","sony-mix","Sony Mix","296","ss-music","SS Music","373","sun-music","Sun Music","308","tarang-music","Tarang Music","319","udaya-music","Udaya Music","310","vh1","Vh1","80","a2z-news","A2Z News","383","aaj-tak","Aaj Tak","290","aalami-sahara","Aalami Sahara","336","abn-telugu-news","ABN Telugu News","378","asianet-news","Asianet News","166","bansal-news","Bansal News","316","bbc-world-news","BBC World News","85","bizz-news","Bizz News","323","captain-news","Captain News","317","channel-10","Channel 10","342","cnbc-awaaz","CNBC AWAAZ","110","cnbc-tv18","CNBC TV18","106","cnbc-tv18-prime-hd","CNBC TV18 Prime HD","516","cnn","CNN","86","cnn-ibn","CNN-IBN","50","dd-news","DD News","175","dilli-aaj-tak","Dilli Aaj Tak","303","etv-2","Etv 2","121","focus-tv","Focus Tv","368","gnn-news","GNN News","359","headlines-today","Headlines Today","289","ibn-7","IBN 7","111","ibn-lokmat","IBN Lokmat","108","india-news","India News","281","india-tv","India TV","423","indiavision","Indiavision","261","janasri-news","Janasri News","300","kairali-people-tv","Kairali People TV","387","kolkata-tv","Kolkata TV","512","lemon-news","Lemon News","338","mahuaa-news","Mahuaa News","330","mathrubhumi-news","Mathrubhumi News","334","mh1-news","MH1 News","332","ndtv-24x7","NDTV 24X7","158","ndtv-india","NDTV India","160","ndtv-profit","NDTV Profit","159","news-24","News 24","329","news-express","News Express","335","news-time","News Time","363","orissa-tv","Orissa Tv","349","ptc-news","PTC News","353","raj-news-24x7","Raj News 24x7","346","raj-news-malyalam","Raj News Malyalam","419","sahara-up","Sahara UP","511","samaya-tv","Samaya TV","351","sandesh-news-dnn","Sandesh News DNN","361","sea-news","Sea News","382","seithigal","Seithigal","311","sun-news","Sun News","309","suvarna-news-24x7","Suvarna News 24x7","348","tez","Tez","283","times-now","Times Now","127","tv9-kannada","TV9 Kannada","425","udaya-news","Udaya News","313","v6-news","V6 news","384","zee-24-gantalu","Zee 24 Gantalu","305","zee-24-ghanta","Zee 24 Ghanta","302","zee-24-ghante-mp","Zee 24 Ghante MP","306","zee-24-taas","Zee 24 Taas","301","zee-business","Zee Business","2","zee-news","Zee News","287","zee-news-up","Zee News UP","307","zee-phh","Zee PHH","503","zee-punjabi","Zee Punjabi","304","zee-up-uttarakhand","Zee UP Uttarakhand","509","adhyatma-tv","Adhyatma TV","347","angel-tv","Angel Tv","156","arihant-tv","Arihant Tv","337","aseervatham-tv","Aseervatham TV","321","bhakti-tv","Bhakti TV","386","divya-tv","Divya Tv","345","god-tv","God Tv","369","ishwar-tv","Ishwar Tv","324","my-tv","MY TV","333","soham-tv","Soham TV","355","dd-sports","DD Sports","176","neo-prime","Neo Prime","100","neo-sports","Neo Sports","49","sony-six","Sony Six","282","sony-six-hd","Sony Six HD","496","star-sports-1","Star Sports 1","102","star-sports-2","Star Sports 2","513","star-sports-3","Star Sports 3","103","star-sports-4","Star Sports 4","101","ten-action","Ten Action","154","ten-cricket","Ten Cricket","186","ten-golf","Ten Golf","269","ten-hd","Ten HD","314","ten-sports","Ten Sports","105","chirithira","Chirithira","381","comedy-central","Comedy Central","267","udaya-comedy","Udaya Comedy","142"};
		        int size = tableList.length;
				
				//Create Table containing list of all channels
				
				mHelper.DB.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CHANNELS + ";");
				
				String chTable = "CREATE TABLE " + Constants.TABLE_CHANNELS + " (" + 
						Constants._ID + " INTEGER PRIMARY KEY," + 
						Constants.COLUMN_CHANNEL + " TEXT," +
						Constants.COLUMN_CHANNEL_NAME + " TEXT," +
						Constants.COLUMN_INCLUDE + " TEXT," +
						Constants.COLUMN_REFRESHED + " TEXT," +
						Constants.COLUMN_CODE + " TEXT"	+ ");";
				
				mHelper.DB.execSQL(chTable);
				
				//Inserting channel list
				
				for(int i=0; i<size; i+=3) {
					ContentValues values = new ContentValues();
					values.put(Constants.COLUMN_CHANNEL, tableList[i]);
					values.put(Constants.COLUMN_CHANNEL_NAME, tableList[i+1]);
					values.put(Constants.COLUMN_CODE, tableList[i+2]);
					values.put(Constants.COLUMN_INCLUDE, "0");
					values.put(Constants.COLUMN_REFRESHED, "NEVER");
					
					long newRowId;
					newRowId = mHelper.DB.insert(Constants.TABLE_CHANNELS, "null", values);
				}			
	    	}
	    	else
	    	{
	    		String tablename = what;
				String code="";
				String columns[] = new String[]{Constants.COLUMN_CHANNEL, Constants.COLUMN_CODE};
				Cursor cursor = mHelper.DB.query(Constants.TABLE_CHANNELS, columns, Constants.COLUMN_CHANNEL+"='"+tablename+"'", null, null, null, Constants.COLUMN_CHANNEL + " ASC");
				if(cursor!=null)
				{
					cursor.moveToNext();
					code = cursor.getString(1);
					cursor.close();
				}
				String[] data = downloadURL(HOST+"get_table_contents.php?channel="+tablename+"&code="+code, context);
		    	int datasize = data.length;
				mHelper.createTable(tablename);
				for(int j=0; j<datasize-1; j+=2) {
					mHelper.putData(tablename, data[j], data[j+1]);
				}
				
				String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
				mHelper.updateRow(tablename, "1", date);
				
				msg = tablename + " " + date;
			}
			
		//	msg = "Successfully Loaded";

	    } else {
	    	msg = "Error establishing network connection";
	    }
	}
	
	private String[] downloadURL(String givenurl, Context context) throws IOException{
		
		InputStream is = null;
		int len = 10000;
		
		try {
			URL url = new URL(givenurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			
			conn.connect();
			int response = conn.getResponseCode();
			msg = "Response Code: " + Integer.toString(response);
			is = conn.getInputStream();
			
			Reader reader = null;
			reader = new InputStreamReader(is, "UTF-8");
			char[] buffer = new char[len];
			reader.read(buffer);
			String str = new String(buffer);
			String[] all_data = str.split(";");
			msg = Integer.toString(all_data.length);
			return all_data;
		} finally {
			if(is!=null)
				is.close();
		}
	}

	Activity activity = null;
	String tablename = null;
	View rootView = null;
	String msg;
	
	@Override
	protected Long doInBackground(Object... object) {
		
		this.activity = (Activity) object[0];
		this.tablename = (String) object[1];
		this.rootView = (View) object[2];
		
		try {
			RetreiveData(this.activity.getApplicationContext(), this.tablename);
		} catch (Exception ex) {
			msg = "IOException Caught " + ex.toString();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Long result) {		
		if(tablename == "TABLE")
		{
			Intent intent = new Intent(this.activity, ItemListActivity.class);
			this.activity.startActivity(intent);
			this.activity.finish();
		}
		else
		{
			Toast.makeText(this.activity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			ItemDetailActivity.setFullView(tablename, activity, rootView);
		}
	}
}
