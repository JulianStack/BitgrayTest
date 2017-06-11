package test.julian.bitgray.Views.Map;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import test.julian.bitgray.Models.User;

/**
 * Created by JulianStack on 09/06/2017.
 */

public interface MapView {

    void showProgress();
    void hideProgress();
    Context getContext();

}
