package com.dnerd.dipty.mysecurityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    LinearLayout mLineaLayout1,mLinearLayout2;
    Button mbtnlogin,mbtnsignup;
    Animation mUptoDown,mDowntoUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: in mainactivity!");

        mbtnlogin = findViewById(R.id.buttonlogin);
        mbtnsignup = findViewById(R.id.buttonsignup);
        mLineaLayout1 = findViewById(R.id.linearlayout1);
        mLinearLayout2 = findViewById(R.id.linearlayout2);
        mUptoDown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        mLineaLayout1.setAnimation(mUptoDown);
        mDowntoUp = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        mLinearLayout2.setAnimation(mDowntoUp);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user!=null&&user.isEmailVerified())
        {
            Intent intent = new Intent(MainActivity.this,Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

        mbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: login button clicked!");
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });
        mbtnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: signup button clicked");
                Intent in = new Intent(MainActivity.this,SignUp.class);
                startActivity(in);
            }
        });


    }
}
