package test.julian.bitgray.Interactors;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import test.julian.bitgray.Asynctask.GetUserList;
import test.julian.bitgray.Models.User;
import test.julian.bitgray.Models.UserAddress;
import test.julian.bitgray.Presenters.MapPresenter;
import test.julian.bitgray.Presenters.MapPresenterImpl;
import test.julian.bitgray.Views.Map.MapView;

/**
 * Created by JulianStack on 09/06/2017.
 */

public class MapInteractorImpl implements MapInteractor {

    // Interactor Log Tag
    private String TAG = "MapInteractorImpl";


    // Get Json object From WebService
    @Override
    public void getUserList(Context context, GetUserList.UserListInterface userListInterface) {
        new GetUserList(userListInterface,context).execute();
    }


}
