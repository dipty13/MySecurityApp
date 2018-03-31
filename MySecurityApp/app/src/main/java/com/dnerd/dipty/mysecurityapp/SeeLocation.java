package com.dnerd.dipty.mysecurityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SeeLocation extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    private boolean mapReady = false;
    private double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_location);

        Intent mapIntent = getIntent();
        lat = mapIntent.getDoubleExtra("lat",1);
        lng = mapIntent.getDoubleExtra("lng",1);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.seeMap);
        mapFragment.getMapAsync(this); //loading the map


        /*String sLat = mapIntent.getStringExtra("lat");
        String sLng = mapIntent.getStringExtra("lng");*/


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;

        mGoogleMap = googleMap;
        Toast.makeText(SeeLocation.this,lat+ " "+lng,Toast.LENGTH_LONG).show();
        LatLng uttara = new LatLng(lat,lng);
        markLocation(uttara,"Transaction Location");
        goToNewPlace(uttara);
        //CameraPosition target = CameraPosition.builder().target(uttara).zoom(4).build();
        //mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    private void markLocation(LatLng newLocation, String location) {
        MarkerOptions options = new MarkerOptions().position(newLocation).title(location);
        mGoogleMap.addMarker(options);
    }
    private void goToNewPlace(LatLng newLocation) {
        CameraPosition target = CameraPosition.builder().target(newLocation).zoom(14).bearing(0).tilt(45).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 1000, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                if(mapReady) {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                }else{
                    Toast.makeText(SeeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeNormal:
                if(mapReady) {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else{
                    Toast.makeText(SeeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeTerrain:
                if(mapReady) {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }else{
                    Toast.makeText(SeeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeSatelite:
                if(mapReady) {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }else{
                    Toast.makeText(SeeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeHybrid:
                if(mapReady) {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }else{
                    Toast.makeText(SeeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
