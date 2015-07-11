package practicalcoding.swatchbharat.main;

import practicalcoding.swatchbharat.others.TeamListAdapter;
import practicalcoding.swatchbharat.others.TeamObject;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class AboutActivity extends Activity {

	 private ListView listView;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_about);
	        
	        TeamObject weather_data[] = new TeamObject[]
	        {
	            new TeamObject(R.drawable.sir1, "Basavaraj"),
	            new TeamObject(R.drawable.deepali1, "Deepali"),
	            new TeamObject(R.drawable.madhu3, "Madhu"),
	            new TeamObject(R.drawable.namrata, "Namrata"),
	            new TeamObject(R.drawable.samata, "Samata"),
	            new TeamObject(R.drawable.shwetha, "Shwetha"),
	            new TeamObject(R.drawable.sonia2, "Sonia"),
	            new TeamObject(R.drawable.soumya, "Soumya")
	        };
	        
	        TeamListAdapter adapter = new TeamListAdapter(this, 
	                R.layout.listview_team_row, weather_data);
	        
	        
	        listView = (ListView)findViewById(R.id.listView1);
	         
	       // View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
	        //listView1.addHeaderView(header);
	        
	        listView.setAdapter(adapter);
	    }
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.about_menu, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	            case R.id.share:
	            	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
	                sharingIntent.setType("text/plain");
	                String shareBody = "Hey check swatch hubli app at playstore https://play.google.com/store/apps/details?id=practicalcoding.swatchbharat.main it was built by BVBCET students";
	                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Swatch bharat app");
	                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	            startActivity(Intent.createChooser(sharingIntent, "Tell your friends"));
	                return true;
	            case R.id.like:
	            	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/PracticalCoding"));
	            	startActivity(browserIntent);
	                return true;
	            case R.id.rate:
	            	Intent intent = new Intent(Intent.ACTION_VIEW);
	            	intent.setData(Uri.parse("market://details?id=practicalcoding.swatchbharat.main"));
	            	startActivity(intent);
	                return true;
	            case R.id.feedback:
	            	String[] TO = {"basuhampali@gmail.com"};
	                String[] CC = {"deepali265@gmail.com,madhukalmath31@gmail.com,namrataangadi5749@gmail.com,samatasulakhe@gmail.com,shwethavd4@gmail.com,sonia31.8.94@gmail.com,soumyadh.dwd@gmail.com"};
	                Intent emailIntent = new Intent(Intent.ACTION_SEND);
	                emailIntent.setData(Uri.parse("mailto:"));
	                emailIntent.setType("text/plain");


	                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	                emailIntent.putExtra(Intent.EXTRA_CC, CC);
	                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback of Swatch Hubli app");
	                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

	                try {
	                   startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	                   finish();
	                   Log.i("Finished sending email...", "");
	                } catch (android.content.ActivityNotFoundException ex) {
	                   Toast.makeText(getApplicationContext(), 
	                   "There is no email client installed.", Toast.LENGTH_SHORT).show();
	                }
	            	
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }
}
