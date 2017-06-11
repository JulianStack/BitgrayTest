package test.julian.bitgray.Presenters;

import android.content.Context;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;

import test.julian.bitgray.Models.User;

/**
 * Created by JulianStack on 09/06/2017.
 */

public interface MapPresenter {

    void refreshLocations(Context context);
    void mapReady(GoogleMap googleMap);
    void onOptionsItemSelected(MenuItem item);
    void userListResults(HashMap<String,User> userList);
    HashMap<String,User> ProcessList (ArrayList<User> lista);

}
