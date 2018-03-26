package com.dnerd.dipty.mysecurityapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CodeVerification extends AppCompatActivity {
    String mCode="00";
    int mCounter = 0;
    private EditText mEnterCode;
    private Button mVerify;
    private ProgressDialog mProgress;
    private TextView mVerified;
    private Button mBackToProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference mTransactionDataReference,mUserDataReference;
    private int inputCodeCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);

        mEnterCode = findViewById(R.id.codeVerficationEditText);
        mVerify = findViewById(R.id.codeVerficationButton);
        mVerified = findViewById(R.id.codeVerificationMessage);
        mBackToProfile = findViewById(R.id.codeVerficationBackButton);

        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mTransactionDataReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child(onlineUserId);
        mUserDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);

        Intent intent = getIntent();
        mCode = intent.getStringExtra("code");
        mCounter = intent.getIntExtra("counter",1);

        /*Verification v = new Verification();
        mCode = v.getVerificationCode();
        mCounter = v.getVerficationCounter();*/
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = mEnterCode.getText().toString();
                inputCodeCount++;
                //Toast.makeText(CodeVerification.this, mCode+ "verified button"+mCounter, Toast.LENGTH_SHORT).show();
              /*  mProgress.setTitle(R.string.verfyProgress);
                mProgress.setMessage("Please wait while we verify your card!");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();*/
                if(inputCodeCount<3&&mCode.equals(enteredCode))
                {
                   // mProgress.dismiss();
                    Toast.makeText(CodeVerification.this, "verified", Toast.LENGTH_SHORT).show();
                   mTransactionDataReference.child(String.valueOf(mCounter-1)).child("transaction_status").setValue("done");
                  /*  AlertDialog.Builder builder = new AlertDialog.Builder(CodeVerification.this);
                    builder.setMessage(R.string.success)
                            .setTitle(R.string.sucessDetails)
                            .setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
*/
                    mEnterCode.setVisibility(View.INVISIBLE);
                    mVerify.setVisibility(View.INVISIBLE);
                    mVerified.setVisibility(View.VISIBLE);
                    mBackToProfile.setVisibility(View.VISIBLE);
                }else if(inputCodeCount<3){
                    Toast.makeText(CodeVerification.this, "invalid code!Please try again!you have "+(3-inputCodeCount)+" left!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CodeVerification.this, "invalid code!Yout card has been blocked!", Toast.LENGTH_SHORT).show();
                    mUserDataReference.child("user_card_status").setValue("Blocked");
                    mEnterCode.setVisibility(View.INVISIBLE);
                    mVerify.setVisibility(View.INVISIBLE);
                    mVerified.setVisibility(View.VISIBLE);
                    mVerified.setText(R.string.cardBlocked);
                    mBackToProfile.setVisibility(View.VISIBLE);
                }
            }
        });

        mBackToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfile = new Intent(CodeVerification.this,Profile.class);
                startActivity(backToProfile);
            }
        });
    }


}
