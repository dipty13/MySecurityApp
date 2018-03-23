package com.dnerd.dipty.mysecurityapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class EditSafeLocation extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    private boolean mapReady = false;
    private EditText mInputLocation;
    private Button mGoToLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_safe_location);
        mInputLocation = findViewById(R.id.mapEditText);
        mGoToLocation = findViewById(R.id.goButton);

        mGoToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = mInputLocation.getText().toString();

                if(mapReady) {

                        geoLocate(location);

                }
            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //loading the map

    }

    private void geoLocate(String location)  {

        /*Geocoder geoCoder = new Geocoder(this);
        if(geoCoder.isPresent()) {
            try {
                Toast.makeText(this,"geo coder "+location,Toast.LENGTH_LONG).show();
                List<Address> list = geoCoder.getFromLocationName(location, 1);

                if(list == null||list.isEmpty())
                {
                    Toast.makeText(this, "No address found", Toast.LENGTH_LONG).show();
                }else {
                    Address address = list.get(0);
                    String locality = address.getLocality();

                    Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

                    double lat = address.getLatitude();
                    double lng = address.getLongitude();

                    Toast.makeText(this, "geo coder " + lat + " " + lng, Toast.LENGTH_LONG).show();

                    LatLng newLocation = new LatLng(lat, lng);
                    CameraPosition target = CameraPosition.builder().target(newLocation).zoom(14).bearing(0).tilt(45).build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 1000, null);
                }
            } catch (IOException e) {
                Toast.makeText(this, "IO exception", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }*/
        if(location.equals("Mirpur,Dhaka"))
        {
            double lat = 23.822349;
            double lng = 90.36542;

            Toast.makeText(this, "geo coder " + lat + " " + lng, Toast.LENGTH_LONG).show();

            LatLng newLocation = new LatLng(lat, lng);
            CameraPosition target = CameraPosition.builder().target(newLocation).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 1000, null);
        }

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
                    Toast.makeText(EditSafeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeNormal:
                if(mapReady) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else{
                    Toast.makeText(EditSafeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeTerrain:
                if(mapReady) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }else{
            Toast.makeText(EditSafeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
        }
                break;
            case R.id.mapTypeSatelite:
                if(mapReady) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }else{
                    Toast.makeText(EditSafeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mapTypeHybrid:
                if(mapReady) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }else{
                Toast.makeText(EditSafeLocation.this, R.string.mapNotReady, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;

        mGoogleMap = googleMap;
        LatLng uttara = new LatLng(23.902776,90.445488);
        CameraPosition target = CameraPosition.builder().target(uttara).zoom(14).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));

    }
}
