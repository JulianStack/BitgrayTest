package test.julian.bitgray.Views.Alerts;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JulianStack on 10/06/2017.
 */

public class Toasts {

    public static void longToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
