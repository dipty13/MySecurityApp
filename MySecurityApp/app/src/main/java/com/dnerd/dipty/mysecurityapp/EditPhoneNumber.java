package com.dnerd.dipty.mysecurityapp;

import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.firebase.FirebaseException;
        import com.google.firebase.FirebaseTooManyRequestsException;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
        import com.google.firebase.auth.PhoneAuthCredential;
        import com.google.firebase.auth.PhoneAuthProvider;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
import java.util.concurrent.TimeUnit;

public class EditPhoneNumber extends AppCompatActivity {
    private static final String TAG = "PhoneAuth";

    private EditText mPhoneText;
    private EditText mCodeText;
    private Button mVerifyButton;
    private Button mSendButton;
    private Button mResendButton;
    private Button mSignoutButton;
    private TextView mStatusText;
    private ImageView mEditPhoneNumber;

    private String mPhoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mVerificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth mAuth;
    private DatabaseReference mGetUsersDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_number);
        mPhoneText =  findViewById(R.id.phoneText);
        mCodeText =  findViewById(R.id.codeText);
        mVerifyButton =  findViewById(R.id.verifyButton);
        mSendButton =  findViewById(R.id.sendButton);
        mResendButton =  findViewById(R.id.resendButton);
        mSignoutButton =  findViewById(R.id.signoutButton);
        mStatusText =  findViewById(R.id.statusText);

        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mGetUsersDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);
        mGetUsersDataReference.keepSynced(true);

        mSignoutButton.setVisibility(View.INVISIBLE);
        mVerifyButton.setEnabled(false);
        mResendButton.setEnabled(false);
        mSignoutButton.setEnabled(false);


        //fbAuth = FirebaseAuth.getInstance();
        mGetUsersDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String phone = dataSnapshot.child("user_phone").getValue().toString();

                if(!phone.equals("+880***********")) {
                    mStatusText.setText(phone);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode(view);
            }
        });
        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode(view);
            }
        });
        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode(view);
            }
        });
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(view);
            }
        });
    }

    public void sendCode(View view) {

        String phoneNumber = mPhoneText.getText().toString();


        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mVerificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {

        mVerificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        mSignoutButton.setEnabled(true);
                        mStatusText.setText(mPhoneText.getText().toString());
                        String number =  mStatusText.getText().toString();
                        mCodeText.setText("");
                        mPhoneText.setText("");
                        mResendButton.setEnabled(false);
                        mVerifyButton.setEnabled(false);
                        mCodeText.setText("");
                        mGetUsersDataReference.child("user_phone").setValue(number);
                        //signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        mPhoneVerificationId = verificationId;
                        mResendToken = token;

                        mVerifyButton.setEnabled(true);
                        mSendButton.setEnabled(false);
                        mResendButton.setEnabled(true);
                    }
                };
    }

    public void verifyCode(View view) {

        String code = mCodeText.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(mPhoneVerificationId, code);
        //signInWithPhoneAuthCredential(credential);
    }

   /* private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mSignoutButton.setEnabled(true);
                            mCodeText.setText("");
                            mStatusText.setText("Signed In");
                            mResendButton.setEnabled(false);
                            mVerifyButton.setEnabled(false);
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }*/

    public void resendCode(View view) {

        String phoneNumber = mPhoneText.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mVerificationCallbacks,
                mResendToken);
    }

    public void signOut(View view) {
        mAuth.signOut();
        mStatusText.setText("Signed Out");
        mSignoutButton.setEnabled(false);
        mSendButton.setEnabled(true);
    }
}
