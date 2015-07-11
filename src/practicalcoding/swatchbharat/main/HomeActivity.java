package practicalcoding.swatchbharat.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import practicalcoding.swatchbharat.main.HomeActivity;
import  practicalcoding.swatchbharat.main.R;
import practicalcoding.swatchbharat.others.ParseConstants;
import practicalcoding.swatchbharat.others.message;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;



public class HomeActivity extends Activity {
	
	private List<message> message = new ArrayList<message>();

	Date date;
	Button btnClosePopup;
	Button button;
	ProgressDialog progressDialog;
	Button postBtn;
	
	private PopupWindow pwindo;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
       button=( Button)findViewById(R.id.item_picture);
       postBtn = (Button)findViewById(R.id.bnn);
	    
       postBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getApplicationContext(), PostActivity.class );
				startActivity(intent);
				}
			});
			
		
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.SWATCH_HUBLI_CLASS);
    	query.addDescendingOrder(ParseConstants.UpdatedAt_columnDefault);
		query.setLimit(35);
        query.findInBackground(new FindCallback<ParseObject>() {
         

			public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                      
                	for (int i = 0; i < scoreList.size(); i++) {
              
                     /*ParseGeoPoint userLocation;

                     userLocation = scoreList.get(i).getParseGeoPoint("latLng");
                     double geo1Int = (double) (userLocation.getLatitude());
                     double geo2Int = (double) (userLocation.getLongitude());
                     s = String.valueOf(geo1Int)+"\t"+String.valueOf(geo2Int);
                     Log.d("SWATCH",s);
                     String labelLocation="ssssssssssss";
                     */
                     


                         ParseObject p= scoreList.get(i);
                         Date temp = scoreList.get(i).getUpdatedAt();
							String temp2 = getDayAndMonthFromParseUpdate(temp);
                		 message.add(new message(p.getString(ParseConstants.MESSAGE_COL)+ "  at  "+p.getString(ParseConstants.LANDMARK_COL),temp2,p.getString(ParseConstants.LOCATION_COL),p.getString(ParseConstants.LANDMARK_COL),p.getObjectId()));
                		// Log.d("CREATEDAT",String.valueOf(p.getDate("date")));
                		/* s=p.getString("GeoLocationString");
                		 String S1="geo:"+s;
                		 String S2=Uri.encode(S1);
                		 String uristring=S1+"?q="+S2;*/
                		//Log.d("GEO POINT",uristring);
                	//	 [query orderByDescending:@"date"];
                		 
                	}
                	populateListView();
                   
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        
        //populateListloc();
        
    }


	private void populateListView() 
	{
	    ArrayAdapter<message> adapter = new MyListAdapter();
		ListView List = (ListView) findViewById(R.id.listView1);
		List.setAdapter(adapter);
			
	}
	
	public void AboutOnCLick(View V){
		Intent goToAbout = new Intent(this, AboutActivity.class);
		startActivity(goToAbout);
	}
	
	public class MyListAdapter extends ArrayAdapter<message>
	{
		public MyListAdapter() 
		{
			super(HomeActivity.this, R.layout.item_list, message );
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View itemView = convertView;
			if(itemView==null)
			{
				itemView = getLayoutInflater().inflate(R.layout.item_list, parent, false);
			}
			
			
			message CurrentDemo = message.get(position);
		
			//ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
			//imageView.setImageResource(CurrentDemo.getIconid());
			
			TextView msgText = (TextView) itemView.findViewById(R.id.item_msg);
			msgText.setText(CurrentDemo.getMsg());
			

			/*TextView pictureText = (TextView) itemView.findViewById(R.id.item_picture);
			pictureText.setText(CurrentDemo.getpicture());*/

			TextView dateText = (TextView) itemView.findViewById(R.id.item_date);
			dateText.setText((CharSequence) CurrentDemo.getPostedDate());
			
			Button locationBtn = (Button) itemView.findViewById(R.id.item_map);
			locationBtn.setTag(message.get(position).getLocationTemp());
			locationBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//Toast.makeText(getApplicationContext(), v.getTag().toString(), 10).show();
					sendToMapView(v.getTag().toString());
				}
			});
			
			Button shareBtn = (Button) itemView.findViewById(R.id.item_share);
			String shareMsge = "Swatch Hubli app: " +"\n"+  message.get(position).getMsg() + " at " + message.get(position).getLandmarkTemp()
					+ "\n"+ " .Hey check this app it lets you to view and post about Grabage and in line with Swatch Bharat Abhiyan." +"\n"+ "Get the app from playstore at https://play.google.com/store/apps/details?id=practicalcoding.swatchbharat.main ";
			shareBtn.setTag(shareMsge);
			shareBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//Toast.makeText(getApplicationContext(), v.getTag().toString(), 10).show();
					share(v.getTag().toString());
				}
			});
			
			Button pictureBtn = (Button) itemView.findViewById(R.id.item_picture);
			pictureBtn.setTag(message.get(position).getObjectId());
			pictureBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//Toast.makeText(getApplicationContext(), v.getTag().toString(), 10).show();
					showPicture(v.getTag().toString());
				}
			});
			
			
		//	ImageView imageView = (ImageView)itemView.findViewById(R.id.item_share);
			//imageView.setImageResource(CurrentDemo.getShare());
			
			
			
			return itemView;
			
			//return super.getView(position, convertView, parent);
	    }
		
	}
	
	public void maps(View v) {
		// URI absolute = new URI("geo:47.6,-122.3");
		// showMap(absolute);
		// Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		//		    Uri.parse("geo:15.21,75.05"));
		//geo:15.21,75.05?q=15.21,75.05(app)
		   Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:15.21,75.05?q=15.21,75.05(app)"));
		 startActivity(intent);
				//startActivity(intent);
		}
	
	public void sendToMapView(String latLng){
		if(latLng.equals("") || latLng.equals(null)){
			Toast.makeText(this, "sorry location is not avialble", 10).show();
		}else{
		Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:" + latLng + "?q=" + latLng));
		 startActivity(intent);
		}
	}
	
	public void showMap(Uri geoLocation) {
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setData(geoLocation);
	    if (intent.resolveActivity(getPackageManager()) != null) {
	        startActivity(intent);
	    }
	}

	public void share(String msge)
	{
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		sharingIntent.setType("text/plain");
		String shareBody = msge;
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Swatch Hubli App");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Tell your freinds"));
	}
	
/*	
	public void onOpenWebBrowser(View v)
    {
        Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
        webPageIntent.setData(Uri.parse("http://techblogon.com"));
 
        try {
                  startActivity(webPageIntent);
        } catch (ActivityNotFoundException ex) {
        // can do something about the exception.
        }
    }*/

	 public void link (View view) {
	        goToUrl ( "http://www.practicalcoding.in");
	    }

	    private void goToUrl (String url) {
	        Uri uriUrl = Uri.parse(url);
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
	    }
    
	

	
	/*  @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/


	public static String getDayAndMonthFromParseUpdate(Date temp){
   		try {
   			SimpleDateFormat isoFormat = new SimpleDateFormat("dd-MM");
   			isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
   			return isoFormat.format(temp);
   		} catch (Exception e) {
   			return "";
   		}
     }
		
	
	
	
public void showPicture(String objectId)
{


	progressDialog = ProgressDialog.show(HomeActivity.this, "","Downloading Image...", true);

	// Locate the class table named "ImageUpload" in Parse.com
	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.SWATCH_HUBLI_CLASS);

	// Locate the objectId from the class
	query.getInBackground(objectId,
			new GetCallback<ParseObject>() 
			{

				public void done(ParseObject object,ParseException e) 
				{
				

					// Locate the column named "ImageName" and set
					// the string
					ParseFile fileObject = (ParseFile) object.get(ParseConstants.IMAGE_COL);
					fileObject.getDataInBackground(new GetDataCallback() 
					{

								public void done(byte[] data,ParseException e) 
								{
									if (e == null) 
									{
										Log.d("test","We've got data in data.");
										// Decode the Byte[] into
										// Bitmap
										Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);

										try 
										{
											// We need to get the instance of the LayoutInflater
											LayoutInflater inflater = (LayoutInflater) HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
											View layout = inflater.inflate(R.layout.dialog_image,(ViewGroup) findViewById(R.id.popup_element));
											pwindo = new PopupWindow(layout, LayoutParams.FILL_PARENT,  
								                     LayoutParams.FILL_PARENT);
											//pwindo = new PopupWindow(layout);
											//pwindo.setFocusable(true);
											pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
											pwindo.setOutsideTouchable(true);
										//	pwindo.
											
											
											ImageView image = (ImageView)layout.findViewById(R.id.image);
											image.setImageBitmap(bmp);
													
											btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);	
											btnClosePopup.setOnClickListener(new OnClickListener()
											{

												public void onClick(View v) 
												{
													pwindo.dismiss();

												}
											});

								
												
										} 
											
										catch (Exception e1)
										{
											e1.printStackTrace();
					
										}
										// Get the ImageView from
										// main.xml
										//ImageView image = (ImageView) findViewById(R.id.image);

										// Set the Bitmap into the
										// ImageView
										//image.setImageBitmap(bmp);

										// Close progress dialog
										progressDialog.dismiss();

								} 
								else
								{
										Log.d("test",
												"There was a problem downloading the data.");
								}
							}
								
						});
							
				}
			});

}

	
}	
	

