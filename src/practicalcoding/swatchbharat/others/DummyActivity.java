package practicalcoding.swatchbharat.others;

import practicalcoding.swatchbharat.main.PostActivity;
import practicalcoding.swatchbharat.main.R;
import practicalcoding.swatchbharat.main.R.id;
import practicalcoding.swatchbharat.main.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


import com.parse.ParseAnalytics;



public class DummyActivity extends Activity {
	
	Button bnn, bn;
	
	int REQUEST_CODE = 1;
	ImageView IMG;
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());
		
		 bnn = (Button)findViewById(R.id.bnn);
	    
		bnn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getApplicationContext(), PostActivity.class );
				startActivity(intent);
				}
			});
			
			
		}
	
	
	
		
	}
	
	
	
	
	

