package test.julian.bitgray.Views.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import test.julian.bitgray.Presenters.MapPresenter;
import test.julian.bitgray.Presenters.MapPresenterImpl;
import test.julian.bitgray.R;

public class Map extends AppCompatActivity
        implements OnMapReadyCallback, test.julian.bitgray.Views.Map.MapView {


    GoogleMap map;
    private ProgressBar progressBar;
    private MapPresenter presenter;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Create New Presenter Instance
        presenter = new MapPresenterImpl(this);
        // Update Locations
        presenter.refreshLocations(Map.this);



    }

    // Action Bar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // On ActionBar Item Selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    // On Google Map ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        presenter.mapReady(googleMap);
        map = googleMap;
    }

    // SHow Progress Bar
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
    //Hide Progress
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    // Get Context
    @Override
    public Context getContext() {
        return Map.this;
    }

    // Refresh Map Button
    public void refreshMap(View v){
        presenter.refreshLocations(Map.this);
    }
}
