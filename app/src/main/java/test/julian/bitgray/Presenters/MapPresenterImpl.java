package test.julian.bitgray.Presenters;

import android.content.Context;
import android.graphics.Color;

import android.location.Location;

import android.util.Log;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import test.julian.bitgray.Asynctask.GetUserList;
import test.julian.bitgray.Interactors.MapInteractorImpl;
import test.julian.bitgray.Interactors.MapInteractor;

import test.julian.bitgray.Models.MarkerAnimator;
import test.julian.bitgray.Models.User;
import test.julian.bitgray.Models.UserAddress;
import test.julian.bitgray.R;
import test.julian.bitgray.Views.Alerts.Toasts;
import test.julian.bitgray.Views.Map.MapView;


/**
 * Created by JulianStack on 09/06/2017.
 */

public class MapPresenterImpl implements MapPresenter{

    // Log Tag
    private String TAG = "MapPresenter";

    // Classes
    private MapView View;
    private MapInteractor interactor;


    // Objects
    private GoogleMap map = null;
    private HashMap<String,User> currentUsers = new HashMap<>();
    private HashMap<String,Marker> currentMarkers = new HashMap<>();
    private Polyline nearPolyline;
    private Polyline awayPolyline;
    int motionFinished = 0;


    // Constructor
    public MapPresenterImpl(MapView view){
        this.View = view;
        interactor = new MapInteractorImpl();
    }

    // Refresh Users locations
    // Get User List
    @Override
    public void refreshLocations(Context context) {
        showProgress();
        interactor.getUserList(context, new GetUserList.UserListInterface() {
            @Override
            public void success(ArrayList<User> lista) {
                userListResults(ProcessList(lista));
            }

            @Override
            public void failed(String messageError) {
                Toasts.longToast(View.getContext(),messageError);
            }
        });
    }


    // Handle OnMapReady google map
    @Override
    public void mapReady(GoogleMap googleMap) {
        map = googleMap;

    }

    // Handle item menu Actions
    @Override
    public void onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

        }
    }


    // Put users's markers in map
    @Override
    public void userListResults(HashMap<String, User> userList) {
        Log.d(TAG,String.valueOf(userList));


        // Get Users From List
        User user = userList.get("user");
        User near = userList.get("near");
        User away = userList.get("away");


        //Get Users Locations
        final LatLng userLocation = new LatLng(user.getAddress().getGeo().getLatitude(), user.getAddress().getGeo().getLongitude());
        final LatLng nearLocation = new LatLng(near.getAddress().getGeo().getLatitude(), near.getAddress().getGeo().getLongitude());
        final LatLng awayLocation = new LatLng(away.getAddress().getGeo().getLatitude(), away.getAddress().getGeo().getLongitude());

        // Hide Progress Bar
        View.hideProgress();

        // Check if list is empty
        if(currentUsers.isEmpty()){

            // move camera to user location
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(userLocation.latitude, userLocation.longitude)));

            //Add markers to map and HashMap List
            currentMarkers.put(String.valueOf("user"),map.addMarker(new MarkerOptions().anchor(0.5f,1).position(userLocation).title(user.getName()).snippet(user.getEmail()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
            currentMarkers.put(String.valueOf("near"),map.addMarker(new MarkerOptions().anchor(0.5f,1).position(nearLocation).title(near.getName()).snippet(near.getEmail())));
            currentMarkers.put(String.valueOf("away"), map.addMarker(new MarkerOptions().anchor(0.5f,1).position(awayLocation).title(away.getName()).snippet(away.getEmail())));

            // Save Users in HashMap List for data access
            currentUsers.put(String.valueOf("user"),user);
            currentUsers.put(String.valueOf("near"),near);
            currentUsers.put(String.valueOf("away"),away);


            // Set On Marker Click
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // Show info window marker and marker title in toast
                    marker.showInfoWindow();
                    Toasts.longToast(View.getContext(),marker.getTitle());
                    return true;
                }
            });

        }else{

            // check if polilyne has data and remove lines from map
            if(nearPolyline!=null){
                nearPolyline.remove();
                awayPolyline.remove();
            }

            // Create an Instance of MarkerAnimator fo animate markers
            MarkerAnimator markerAnimator = new MarkerAnimator(map);

            //Set on Polyline click and print Distance
            map.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline polyline) {
                        // conver LatLng to Location for calculate distances
                        Location point1 = convertLatLngToLocation(polyline.getPoints().get(0));
                        Location point2 = convertLatLngToLocation(polyline.getPoints().get(1));
                        float distance = point1.distanceTo(point2)/1000;
                        // Show distance
                        Toasts.longToast(View.getContext(),"Distance : "+distance+" km");

                }
            });


            // Set the most away user's marker
            final Marker awayMarker = currentMarkers.get("away");
            awayMarker.setTitle(away.getName());
            awayMarker.setSnippet(away.getEmail());

            // Set the most near user's marker
            final Marker nearMarker = currentMarkers.get("near");
            nearMarker.setTitle(near.getName());
            nearMarker.setSnippet(near.getEmail());

            // Set the user's marker
            final Marker marker = currentMarkers.get("user");
            marker.setTitle(user.getName());
            marker.setSnippet(user.getEmail());
            marker.showInfoWindow();



            // Animate Marker and Listen finish with markerAnimation Interface
            markerAnimator.animateMarker(marker, userLocation, false, new MarkerAnimator.markerAnimation() {
                @Override
                public void finished() {
                    markerAnimationFinished(marker,nearMarker,awayMarker);
                }
            });
            markerAnimator.animateMarker(nearMarker, nearLocation, false, new MarkerAnimator.markerAnimation() {
                @Override
                public void finished() {
                    markerAnimationFinished(marker,nearMarker,awayMarker);
                }
            });
            markerAnimator.animateMarker(awayMarker, awayLocation, false, new MarkerAnimator.markerAnimation() {
                @Override
                public void finished() {
                    markerAnimationFinished(marker,nearMarker,awayMarker);
                }
            });

            // update camera location
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(userLocation, 2);
            map.animateCamera(location);


        }

    }

    // Handle Marker Animation Finish
    private void markerAnimationFinished(Marker userLocation, Marker nearLocation, Marker awayLocation) {
        motionFinished++;
        // check for every markers finished animation
        if(motionFinished==currentMarkers.size()){
            // Instantiates a new Polyline object
            addPolylines(userLocation,nearLocation,awayLocation);
            motionFinished = 0;
        }
    }


    private void addPolylines(Marker userLocation, Marker nearLocation, Marker awayLocation){
        // Add the most near marker's polyline
        nearPolyline = map.addPolyline(new PolylineOptions()
                .add(nearLocation.getPosition(),userLocation.getPosition())
                .width(3)
                .color(Color.BLUE));
        nearPolyline.setClickable(true);

        // Add the most away marker's polyline
        awayPolyline = map.addPolyline(new PolylineOptions()
                .add(userLocation.getPosition(),awayLocation.getPosition())
                .width(3)
                .color(Color.BLUE));
        awayPolyline.setClickable(true);
    }


    // Convert LatLng into Location
    private Location convertLatLngToLocation(LatLng latLng) {
        Location location = new Location("someLoc");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }


    // Hide Progress Bar
    private void hideProgress(){
        if(View!=null)
            View.hideProgress();
    }

    // Show Progress Bar
    private void showProgress(){
        if(View!=null)
            View.showProgress();
    }


    // Get HashMap with random user, the most near user, and most away user
    @Override
    public HashMap<String, User> ProcessList(ArrayList<User> lista) {
        Log.d(TAG,"Recorriendo arreglo");

        //
        float nearNumber = 0;
        float awayNumber = 0;

        // get user
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(lista.size());
        User user = lista.get(randomInt);
        User awayUser = null;
        User nearUser = null;

        // remove user from list
        lista.remove(randomInt);

        // get User data
        UserAddress address = user.getAddress();
        Location location = address.getGeo();

        // check if list if empty
        if(lista.size()>0){

            for(int i = 0; i<lista.size();i++){

                // get user data from list
                User usr = lista.get(i);
                UserAddress usraddress = usr.getAddress();
                Location usrlocation = usraddress.getGeo();

                // calculate distance
                float CurrentDistance = location.distanceTo(usrlocation);

                // Check for the most Away User
                if(CurrentDistance>awayNumber){
                    awayNumber = CurrentDistance;
                    awayUser = usr;
                }

                // Check for the most Near User;
                if(CurrentDistance<nearNumber){
                    nearNumber = CurrentDistance;
                    nearUser = usr;
                }

                // set distance to compare
                if(nearNumber==0){
                    nearNumber = CurrentDistance;
                }

            }
        }

        // Create HashMap
        HashMap<String,User> UsersResult = new HashMap<>();
        UsersResult.put("user",user);
        UsersResult.put("away",awayUser);
        UsersResult.put("near",nearUser);

        return UsersResult;
    }

}
