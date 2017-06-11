package test.julian.bitgray.Config;

import android.app.Activity;
import android.content.Context;

import test.julian.bitgray.R;

/**
 * Created by JulianStack on 09/06/2017.
 */

public class Env {

    // Web Service URL
    public static String getUsersUrl(Context activity){
        return activity.getResources().getString(R.string.users_api);
    }

    // Request Error Message
    public static String getRequestError(Context activity){
        return activity.getResources().getString(R.string.request_error);
    }


}
