package practicalcoding.swatchbharat.global;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class MainApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();


    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Add your initialization code here
    Parse.initialize(this, "DtzBZQvwTmA6A3gFXlzcm3ZvpOIIeMkmaxoFtuXR", "dGrBLc2isZkWBh7LqvmKR8Acpk2QNaqu5e3d01ci");


    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
      
    // If you would like all objects to be private by default, remove this line.
    defaultACL.setPublicReadAccess(true);
    
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
