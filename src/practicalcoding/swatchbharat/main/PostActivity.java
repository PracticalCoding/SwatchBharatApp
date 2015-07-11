package practicalcoding.swatchbharat.main;


import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import practicalcoding.swatchbharat.main.*;
import practicalcoding.swatchbharat.others.AppLocationService;
import practicalcoding.swatchbharat.others.ParseConstants;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class PostActivity extends Activity {
	Button catureImage_btn, btnSend;
	int REQUEST_CODE = 1;
	ImageView IMG;
	Bitmap BMP;
	//GeocoderHandler geoCoderHandler;
	String loc, msg;
	AppLocationService appLocationService;
	// Date localDate;
	String result,addr;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		catureImage_btn = (Button)findViewById(R.id.bn);

		btnSend = (Button)findViewById(R.id.btnSend);

		IMG =(ImageView)findViewById(R.id.img);




		//Toast.makeText(getApplicationContext(),getGPS(this),Toast.LENGTH_SHORT).show();
		addr = getGPS(this);
		/*appLocationService = new AppLocationService(
				Capture.this);
		Location gpsLocation = appLocationService
				.getLocation(LocationManager.GPS_PROVIDER);

		if (gpsLocation != null) {
			double latitude = gpsLocation.getLatitude();
			double longitude = gpsLocation.getLongitude();
			result = "Latitude: " + gpsLocation.getLatitude() +
					" Longitude: " + gpsLocation.getLongitude();
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			addr = gpsLocation.getLatitude() +
					"," + gpsLocation.getLongitude();
			LocationAddress locationAddress = new LocationAddress();
			locationAddress.getAddressFromLocation(latitude, longitude,getApplicationContext(), new GeocoderHandler());

		}*/

		catureImage_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if(i.resolveActivity(getPackageManager())!=null){
					startActivityForResult(i,REQUEST_CODE);
				}
			}
		});

		btnSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {



				EditText et=(EditText)findViewById(R.id.txtLocation);

				loc=et.getText().toString();
				
				if(loc.equals(""))
					{
					//	if( loc.length() == )
					et.setError( "Land mark is required!" );
                 
			       
					}
				else{
				

						EditText et2=(EditText)findViewById(R.id.txtMessage);

						msg=et2.getText().toString();
						
						if(BMP== null)
						{
				
							Context context = getApplicationContext();
							CharSequence text = "Image is required!!";
							int duration = Toast.LENGTH_SHORT;

							Toast toast = Toast.makeText(context, text, duration);
							toast.getView().setBackgroundColor(Color.RED);
						   // text.setTextColor(Color.WHITE);
							
							toast.show();
							
							
                 
			        
						}
						else{
							ByteArrayOutputStream stream = new ByteArrayOutputStream();
							BMP.compress(Bitmap.CompressFormat.PNG, 100, stream);
							byte[] data = stream.toByteArray();              

							ParseObject upload= new ParseObject(ParseConstants.SWATCH_HUBLI_CLASS);

							//upload.put("ImageName", "CapturedImage");

							ParseFile imageFile = new ParseFile("noname.png", data);

							upload.put(ParseConstants.IMAGE_COL, imageFile);
							if(addr!=null){

							}
							else{
								addr="";
							}
							upload.put(ParseConstants.LANDMARK_COL, loc);
					//upload.put("ImageFile", imageFile);
					upload.put(ParseConstants.LOCATION_COL,addr);
					upload.put(ParseConstants.MESSAGE_COL,msg);
					// upload.put("GeoLocationString", getGPS(getApplicationContext()));
					upload.saveInBackground();

					Context context = getApplicationContext();
					CharSequence text = "Your Request has SENT!!";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.getView().setBackgroundColor(Color.GREEN);
					toast.show();
				}
				
				}

			}
		}
				);

	}

	public String  getGPS(Context cntxt) {
		try {
			LocationManager lm = (LocationManager) cntxt.getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = lm.getProviders(true);

			/* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
			Location l = null;

			for (int i=providers.size()-1; i>=0; i--) {
				l = lm.getLastKnownLocation(providers.get(i));
				if (l != null) break;
			}

			//LatLng gps = null;
			/*if (l != null) {
			gps = new LatLng(l.getLatitude(), l.getLongitude());
   }*/
			String gps=l.getLatitude()+","+l.getLongitude();
			return gps;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}


	public boolean onCreateOptionsMenu(Menu menu){
		//getMenuInflater().inflate(R.menu.activity_main,menu);
		return true ;
	}

	/*private class GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
			case 1:
				Bundle bundle = message.getData();
				locationAddress = bundle.getString("address");
				break;
			default:
				locationAddress = null;
			}
			addr=locationAddress;
			Toast.makeText(getApplicationContext(), locationAddress, Toast.LENGTH_SHORT).show();
		}
	}
*/
	public void onActivityResult(int requestcode,int resultcode, Intent data){
		if(requestcode==REQUEST_CODE)
		{
			if(resultcode==RESULT_OK)
			{
				Bundle bundle = new Bundle();
				bundle = data.getExtras();

				BMP = (Bitmap)bundle.get("data");
				IMG.setImageBitmap(BMP);
				IMG.setVisibility(View.VISIBLE);
			}
		}
	}
}
