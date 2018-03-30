package com.dnerd.dipty.mysecurityapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TransactionRequest extends AppCompatActivity {
    private static final String TAG = "TransactionRequest";
    static HashMap<Pair<String, String>,String> mSavedLocation;

    private EditText mAmonut,mEmail,mCardNumber,mCccv;
    private Button mPay;
    private RelativeLayout mLayout;

    private FirebaseAuth mAuth;
    private DatabaseReference mTransactionDataReference,mUserDataReference,mMapDatabaseReference;
    private int counter = 0;

    private   double lat =0.0;
    private   double lng = 0.0;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;

    private String loc;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION =android. Manifest.permission.ACCESS_COARSE_LOCATION;
    private static  final int LOCATION_PERMISSION_REQUEST_CODE =1234;
    private final int SEND_SMS_PERMISSION_REQUEST_CODE =1000;
    private final String SMS_API_KEY = "IB8MN1kLDv8-fQOg8BWyf1viCoAVgziP5dDvAtEjye";
    private String phoneNumber;
    private int a,b;

    int dBalance;
    int dAmount;
    //Verification v = new Verification();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_request);

        mAmonut = findViewById(R.id.editTextTotal);
        mEmail = findViewById(R.id.editTextEmail);
        mCardNumber = findViewById(R.id.ediitTextCardNumber);
        mCccv = findViewById(R.id.ediitTextCcv);
        mPay = findViewById(R.id.buttonPay);
        


        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);
        mTransactionDataReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child(onlineUserId);
        mMapDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Saved_location").child(onlineUserId);


        mTransactionDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cnt  = dataSnapshot.child("transaction_counter").getValue().toString();

                counter = Integer.parseInt(cnt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String blnc = dataSnapshot.child("user_balance").getValue().toString();

                a = Integer.parseInt(blnc);
                //return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // String amount;
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmonut.getText().toString();
                String email = mEmail.getText().toString();
                String cardNumber = mCardNumber.getText().toString();
                String ccv = mCccv.getText().toString();
                checkDtataWithUsersTable(amount,email,cardNumber,ccv);

              /*  dAmount = Integer.parseInt(amount);
                      //  dBalance = Integer.parseInt(balance);
                String x = String.valueOf(a-dAmount);
                mUserDataReference.child("user_balance").setValue(x);*/
                //return;

        //Toast.makeText(TransactionRequest.this, dAmount+" "+dBalance, Toast.LENGTH_LONG).show();
            }
        });



      /*  String x = String.valueOf(a-dAmount);
        mUserDataReference.child("user_balance").setValue(x);
        return;*/
            //setUserData();
       /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/
    }

    private void checkDtataWithUsersTable(final String amount, final String email, final String cardNumber, final String ccv) {
        mUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userEmail = dataSnapshot.child("user_email").getValue().toString();
                String userCard = dataSnapshot.child("user_card").getValue().toString();
                String userCcv = dataSnapshot.child("user_ccv").getValue().toString();
                String userCardStatus = dataSnapshot.child("user_card_status").getValue().toString();
                phoneNumber = dataSnapshot.child("user_phone").getValue().toString();
                 String balance = dataSnapshot.child("user_balance").getValue().toString();

                 dBalance = Integer.parseInt(balance);
                 dAmount = Integer.parseInt(amount);
                 //setBalance(dBalance,dAmount);

                if(userEmail.equals(email)&&userCard.equals(cardNumber)&&userCcv.equals(ccv)){
                    if(userCardStatus.equals("Blocked")){
                        counter++;
                        mTransactionDataReference.child("transaction_counter").setValue(counter);
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("date").setValue(getCurrentDate(mPay));
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("time").setValue(getCurrentTime(mPay));
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("pay_amount").setValue(amount);
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("latitude").setValue("latitude");
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("longitude").setValue("longitude");
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("transaction_status").setValue("not done");
                        Toast.makeText(TransactionRequest.this,R.string.cardBlocked,LENGTH_LONG).show();
                        return;
                    }else if(dBalance < dAmount){
                        counter++;
                        mTransactionDataReference.child("transaction_counter").setValue(counter);
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("date").setValue(getCurrentDate(mPay));
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("time").setValue(getCurrentTime(mPay));
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("pay_amount").setValue(amount);
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("latitude").setValue("latitude");
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("longitude").setValue("longitude");
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("transaction_status").setValue("not done");
                        Toast.makeText(TransactionRequest.this,balance,LENGTH_LONG).show();
                        Toast.makeText(TransactionRequest.this,R.string.cardBalanceNotEnough,LENGTH_LONG).show();
                        return;
                    }
                    else{
                        counter++;
                        mTransactionDataReference.child("transaction_counter").setValue(counter);
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("date").setValue(getCurrentDate(mPay));
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("time").setValue(getCurrentTime(mPay));
                        mTransactionDataReference.child(String.valueOf(counter - 1)).child("pay_amount").setValue(amount);

                        mTransactionDataReference.child(String.valueOf(counter-1)).child("latitude").setValue("latitude");
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("longitude").setValue("longitude");
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("transaction_status").setValue("not done");
                       /* mUserDataReference.child("user_balance").setValue(dBalance-dAmount);*/
                        getLocationPermission();
                        getDeviceLocation();

                       /* mUserDataReference.child("user_balance").setValue(dBalance-dAmount);
                        return;*/
                    /*double lat1 = getLat();
                    double lng1 = getLng();*/
                        //Toast.makeText(TransactionRequest.this, lat+" "+lng, Toast.LENGTH_LONG).show();

                    }

                }else{
                    Toast.makeText(TransactionRequest.this,"Transaction not done", LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*        dBalance = Integer.parseInt(balance);
        dAmount = Integer.parseInt(amount);
        String x = String.valueOf(dBalance-dAmount);
        mUserDataReference.child("user_balance").setValue(x);
        Toast.makeText(TransactionRequest.this, dAmount+" "+dBalance, Toast.LENGTH_LONG).show();*/

        /*mUserDataReference.child("user_balance").setValue(dBalance-dAmount);*/
    }



    private void checkMapTable(final String loc,final double lat, double lng) {
        final String lat1 = String.valueOf(lat);
        final String lng1 = String.valueOf(lng);

        Toast.makeText(TransactionRequest.this, lat1+" map  "+lng1, Toast.LENGTH_LONG).show();
        mMapDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cnt = dataSnapshot.child("location_counter").getValue().toString();

                int locCount = Integer.parseInt(cnt);
                int c=0;
                for(int i = 0; i< locCount;i++)
                {
                    String locationName = dataSnapshot.child(String.valueOf(i)).child("location_name").getValue().toString();
                    String locationLatitude = dataSnapshot.child(String.valueOf(i)).child("location_latitude").getValue().toString();
                    String locationLongitude = dataSnapshot.child(String.valueOf(i)).child("location_longitude").getValue().toString();

                    if(locationLatitude.equals(lat1)&&locationLongitude.equals(lng1))
                    {
                        c=1;
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("latitude").setValue(lat1);
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("longitude").setValue(lng1);
                        mTransactionDataReference.child(String.valueOf(counter-1)).child("transaction_status").setValue("done");
                      //  mUserDataReference.child("user_balance").setValue(dBalance-dAmount);
                        mAmonut.setText("");
                        mEmail.setText("");
                        mCardNumber.setText("");
                        mCccv.setText("");
                        Toast.makeText(TransactionRequest.this,"Transaction done", LENGTH_LONG).show();
                        break;
                    }
                }
                if(c==0){
                    mTransactionDataReference.child(String.valueOf(counter-1)).child("latitude").setValue(lat1);
                    mTransactionDataReference.child(String.valueOf(counter-1)).child("longitude").setValue(lng1);
                    mTransactionDataReference.child(String.valueOf(counter-1)).child("transaction_status").setValue("not done");
                  /*  mUserDataReference.child("user_balance").setValue(dBalance-dAmount);*/
                    mAmonut.setText("");
                    mEmail.setText("");
                    mCardNumber.setText("");
                    mCccv.setText("");
                    Toast.makeText(TransactionRequest.this,"Location didn't match.Code has been sent!", LENGTH_LONG).show();
                    String code = getRandomNumber();
                    sendCode(code);


                   /* v.setVerficationCounter(counter);
                    v.setVerificationCode(code);*/
                    Intent cv = new Intent(TransactionRequest.this,CodeVerification.class);
                    cv.putExtra("code",code);
                    cv.putExtra("counter",counter);
                    /*cv.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cv.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                    startActivity(cv);
                    return;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendCode(String code) {
        if(getSmsPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();



            smsManager.sendTextMessage(phoneNumber,null,
                    "Is it you purchasing? Here's your code to confirm it: "+code,
                    null,null);
            Toast.makeText(TransactionRequest.this,"Message sent!", LENGTH_LONG).show();

        }
        else{
            Toast.makeText(TransactionRequest.this,"Permission denied", LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        /*try {
            // Construct data
            String apiKey =  "apikey="+ SMS_API_KEY;
            String message = "&message=" + code;
            String sender = "&sender=" + "InSecurity";
            String numbers = "&numbers=" + phoneNumber;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                //stringBuffer.append(line);

                Toast.makeText(TransactionRequest.this,"Message sent!"+line, LENGTH_LONG).show();

            }
            rd.close();

            //return stringBuffer.toString();
        } catch (Exception e) {
            Toast.makeText(TransactionRequest.this,"error "+e,LENGTH_LONG).show();
            //System.out.println("Error SMS "+e);
           // return "Error "+e;
        }*/



    }



    HashMap<Pair<String, String>,String> getResult()
    {
        return  mSavedLocation;
    }
    protected static void setLocation(HashMap<Pair<String, String>,String> x)
    {
        mSavedLocation = x;
    }

    public String getCurrentDate(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
        return strDate;
        //display(strDate);
    }

    public String getCurrentTime(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strTime = "Current Time : " + mdformat.format(calendar.getTime());
        return strTime;
        // display(strDate);
    }

    String getRandomNumber()
    {
        Random rand = new Random();

        String id = String.format("%04d", rand.nextInt(10000));
        return id;
    }

    private void getDeviceLocation()
    {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionGranted)
            {
                Task location = mFusedLocationProviderClient.getLastLocation();


                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();

                            //loc = String.valueOf(currentLocation);

                            /*if(Geocoder.isPresent()) {
                                Geocoder geoCoder = new Geocoder(TransactionRequest.this, Locale.getDefault());

                                try {
                                    Toast.makeText(TransactionRequest.this, "geo coder ", Toast.LENGTH_LONG).show();
                                    List<Address> list = geoCoder.getFromLocation(lat, lng, 1);
                                    Address address = list.get(0);
                                    String locality = address.getLocality();
                                    loc = locality;
                                } catch (IOException e) {
                                    Toast.makeText(TransactionRequest.this, "IO exception", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }*/
                           lat = currentLocation.getLatitude();
                           lng = currentLocation.getLongitude();

                            checkMapTable(loc,lat,lng);
                            //Toast.makeText(TransactionRequest.this, dAmount+" "+lat+" device location "+lng, Toast.LENGTH_LONG).show();
                            /*setLat(lat);
                            setLng(lng);*/
                           // moveCamera(new LatLng(currentLocation.getAltitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,"I'm here!");

                        }
                        else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(TransactionRequest.this,R.string.currentLocationInvalid,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException "+e.getMessage());
        }

    }
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting permission");
        String[] permission = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted = true;
                //if the phone has sdk less than 23, we don't have to ask permission explicitly
                //or permission is granted once
                /*if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                {*/
                Log.d(TAG, "onRequestPermissionsResult: permission self granted");
                //initMap();
                //}
            }
            else{
                ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean getSmsPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this,permission);

        return (check==PackageManager.PERMISSION_GRANTED);
    }

/*    void setLat(double lat)
    {
        this.lat = lat;
    }
    void setLng(double lng)
    {
        this.lng = lng;
    }
    double getLat()
    {
        return lat;
    }
    double getLng()
    {
        return lng;
    }*/
}
