package com.dnerd.dipty.mysecurityapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class EditSafeLocation extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    private boolean mapReady = false;
    private EditText mInputLocation;
    private Button mGoToLocation;
    private Spinner mSpinner;
    private double lat,lng;
    private LatLng newLocation;
    private FirebaseAuth mAuth;
    private DatabaseReference mMapDataBaseReference;
    int markerCounter =0;

    private String[] locations = {"Uttara,Dhaka","Mirpur,Dhaka","Dhanmondi","Badda"
            ,"Mouchak","Mohakhali","Baridhara","Banani","Sherpur","Chitagong","Comilla"
            ,"Shahabag","Azimpur","Khulna","Gazipur","Rangpur"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_safe_location);
       // mInputLocation = findViewById(R.id.mapEditText);
        mSpinner = findViewById(R.id.locationSpinner);
        //mGoToLocation = findViewById(R.id.goButton);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //loading the map

        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mMapDataBaseReference = FirebaseDatabase.getInstance().getReference().child("Saved_location").child(onlineUserId);

        mMapDataBaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locationCounter = dataSnapshot.child("location_counter").getValue().toString();


                markerCounter = Integer.parseInt(locationCounter);

                for(int i = 0; i< markerCounter;i++)
                {
                    String locationName = dataSnapshot.child(String.valueOf(i)).child("location_name").getValue().toString();
                    String locationLatitude = dataSnapshot.child(String.valueOf(i)).child("location_latitude").getValue().toString();
                    String locationLongitude = dataSnapshot.child(String.valueOf(i)).child("location_longitude").getValue().toString();

                    if(!locationLatitude.equals("latitude")&&!locationLongitude.equals("longitude"))
                    {
                        double lat = Double.parseDouble(locationLatitude);
                        double lng = Double.parseDouble(locationLongitude);
                        LatLng loc = new LatLng(lat,lng);
                        markLocation(loc,locationName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,locations);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String loc = locations[position].toString();
                goToLocation(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* mGoToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = mInputLocation.getText().toString();

                if(mapReady) {

                        geoLocate(location);

                }
            }
        });*/


    }

    private void goToLocation(int i) {

        switch (i)
        {
            case 0:
                 lat = 23.8766296;
                 lng = 90.3432684;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 1:
                lat = 23.822349;
                lng = 90.36542;
                 newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 2:
                lat = 23.7470304;
                lng = 90.3671072;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 3:
                lat = 23.7862842;
                lng = 90.4120026;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 4:
                lat = 24.0196933;
                lng = 90.2898395;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 5:
                lat = 23.7811083;
                lng = 90.3909025;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 6:
                lat = 23.8019082;
                lng = 90.4126667;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 7:
                lat = 23.7946976;
                lng = 90.3971412;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 8:
                lat = 25.0283002;
                lng = 89.9774258;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 9:
                lat = 22.3271445;
                lng = 91.7717906;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 10:
                lat = 23.4530549;
                lng = 91.1482908;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 11:
                lat = 23.7403393;
                lng = 90.3919137;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 12:
                lat = 23.7282773;
                lng = 90.3804558;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 13:
                lat = 22.8452866;
                lng = 89.4624609;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                break;
            case 14:
                lat = 23.9946684;
                lng = 90.400157;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            case 15:
                lat = 25.7498343;
                lng = 89.2270259;
                newLocation = new LatLng(lat,lng);
                markLocation(newLocation,locations[i]);
                goToNewPlace(newLocation);
                markerCounter++;
                setValuesInMapTable(markerCounter,locations[i],lat,lng);
                break;
            default:
                break;
        }
    }

    private void setValuesInMapTable(int markerCounter, String location, double lat, double lng) {
        mMapDataBaseReference.child("location_counter").setValue(String.valueOf(markerCounter));
        mMapDataBaseReference.child(String.valueOf(markerCounter-1)).child("location_name").setValue(location);
        mMapDataBaseReference.child(String.valueOf(markerCounter-1)).child("location_latitude").setValue(lat);
        mMapDataBaseReference.child(String.valueOf(markerCounter-1)).child("location_longitude").setValue(lng);
    }

    private void markLocation(LatLng newLocation, String location) {
        MarkerOptions options = new MarkerOptions().position(newLocation).title(location);
        mGoogleMap.addMarker(options);
    }

    private void goToNewPlace(LatLng newLocation) {
        CameraPosition target = CameraPosition.builder().target(newLocation).zoom(14).bearing(0).tilt(45).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 1000, null);
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
