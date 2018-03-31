package com.dnerd.dipty.mysecurityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuccessfulTransaction extends AppCompatActivity {
    private TextView mDate;
    private TextView mTime;
    private TextView mAmount;
    private TextView mBalance;
    private Button mSeeLocation;
    private double lat,lng;

    private FirebaseAuth mAuth;
    private DatabaseReference mGetUsersDataReference,mTransactionDataReference;

    private String date,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_transaction);

        mDate = findViewById(R.id.successTvDate);
        mTime = findViewById(R.id.successTvTime);
        mAmount = findViewById(R.id.successTvAmount);
        mBalance = findViewById(R.id.successTvBalance);
        mSeeLocation = findViewById(R.id.successBtnLocation);

        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mGetUsersDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);
        mTransactionDataReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child(onlineUserId);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");


        mTime.setText(time);
        mDate.setText(date);
        mGetUsersDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String balance = dataSnapshot.child("user_balance").getValue().toString();

                mBalance.setText("Balance: "+balance);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Intent seeMap = new Intent(SuccessfulTransaction.this,SeeLocation.class);
        mTransactionDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cnt = dataSnapshot.child("transaction_counter").getValue().toString();
                int counter = Integer.parseInt(cnt);

                for(int i=counter-1;i>=0;i--)
                {
                    String sDate = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                    String sTime = dataSnapshot.child(String.valueOf(i)).child("time").getValue().toString();
                    String sAmount = dataSnapshot.child(String.valueOf(i)).child("pay_amount").getValue().toString();
                    String sLatitude = dataSnapshot.child(String.valueOf(i)).child("latitude").getValue().toString();
                    String sLongitude = dataSnapshot.child(String.valueOf(i)).child("longitude").getValue().toString();


                    if(sDate.equals(date)&&sTime.equals(time))
                    {
                        mAmount.setText(sAmount);
                        lat = Double.parseDouble(sLatitude);
                        lng  = Double.parseDouble(sLongitude);
                        seeMap.putExtra("lat",lat);
                        seeMap.putExtra("lng",lng);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSeeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SuccessfulTransaction.this,lat+ "button clicked" +lng,Toast.LENGTH_LONG).show();
                startActivity(seeMap);
            }
        });
    }
}
