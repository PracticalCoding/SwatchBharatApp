package practicalcoding.swatchbharat.utility;

import android.content.Context;
import android.widget.Toast;


public class Utility {
	
	/**
	 * used for testing purpose
	 */
	public static void showToast(Context context,String message) {
			if(message != null && !message.equals(""))
				Toast.makeText(context, message, 10).show();
	}

}
