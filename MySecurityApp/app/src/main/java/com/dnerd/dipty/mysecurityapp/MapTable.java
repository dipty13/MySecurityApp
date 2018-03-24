package com.dnerd.dipty.mysecurityapp;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dipty on 3/24/2018.
 */

public class MapTable {
    private static final String TAG = "MapTable";
    private FirebaseAuth mAuth;
    private DatabaseReference mMapDatabaseReference;
    MapTable()
    {
        String counter = "0";
        String locationname = "location_name";
        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mMapDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Saved_location").child(onlineUserId);
        mMapDatabaseReference.child("location_counter").setValue(String.valueOf(0));
        mMapDatabaseReference.child(String.valueOf(0)).child("location_name").setValue("locationName");
        mMapDatabaseReference.child(String.valueOf(0)).child("location_latitude").setValue("latitude");
        mMapDatabaseReference.child(String.valueOf(0)).child("location_longitude").setValue("longitude").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: Saved_location table not created!");
                }
                else{
                    Log.d(TAG, "onComplete: Saved_location table created!");
                }
            }
        });
    }
}
