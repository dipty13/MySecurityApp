package com.dnerd.dipty.mysecurityapp;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EditCardNumber extends AppCompatActivity {

    private EditText mCardNumber,mCcv,mBalance;
    private TextView mShowCardNumber,mCardStatus,mShowBalance;
    private Button mSaveButton;
    private ImageView mEditCardNumber;
    private FirebaseAuth mAuth;
    private DatabaseReference mGetUsersDataReference,mTransactionDataReference;
    private ProgressDialog mRegProgress;
    int intAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_number);

        mShowCardNumber = findViewById(R.id.cardTextView);
        mCardNumber = findViewById(R.id.cardEditTextNumber);
        mCcv = findViewById(R.id.cardEditTextCcv);
        mEditCardNumber = findViewById(R.id.cardImageViewEdit);
        mSaveButton = findViewById(R.id.cardSaveButton);
        mCardStatus = findViewById(R.id.cardTextViewStatus);
        mBalance = findViewById(R.id.cardEditTextBalance);
        mShowBalance = findViewById(R.id.cardBalanceTextView);

        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mGetUsersDataReference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);
        mTransactionDataReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child(onlineUserId);

        mGetUsersDataReference.keepSynced(true);
        mTransactionDataReference.keepSynced(true);

        mCardNumber.setVisibility(View.INVISIBLE);
        mCcv.setVisibility(View.INVISIBLE);
        mBalance.setVisibility(View.INVISIBLE);
        mSaveButton.setVisibility(View.INVISIBLE);
        final int balance1 =0;
       /* mTransactionDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String amount = dataSnapshot.child("pay_amount").getValue().toString();
                intAmount = Integer.parseInt(amount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
         mGetUsersDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("user_name").getValue().toString();
                String email = dataSnapshot.child("user_email").getValue().toString();
                String phone = dataSnapshot.child("user_phone").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String balance = dataSnapshot.child("user_balance").getValue().toString();
               /* String thumb_image = dataSnapshot.child("user_thumb_image").toString();*/
                String card = dataSnapshot.child("user_card").getValue().toString();
                String cardStatus = dataSnapshot.child("user_card_status").getValue().toString();

                //int intBalance = Integer.parseInt(balance);

                if (!card.equals("card_number")) {
                    //mShowCardNumber.setText(card);
                    mCardStatus.setText(cardStatus);
                }
                if (!balance.equals("balance")) {
                   mShowBalance.setText(getString(R.string.balance1)+balance+getString(R.string.tk));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        mEditCardNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardNumber.setVisibility(View.VISIBLE);
                mCcv.setVisibility(View.VISIBLE);
                mBalance.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mRegProgress.setTitle(R.string.savingInfo);
                mRegProgress.setMessage("Please wait while we save your information!");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();*/
                String cardNumber  = mCardNumber.getText().toString();
                String ccv = mCcv.getText().toString();
                String balance = mBalance.getText().toString();
                for(int i=0;i<balance.length();i++)
                {
                    if(balance.charAt(i)>='1'&&balance.charAt(i)>='9')
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditCardNumber.this);
                        builder.setMessage(R.string.balance_char_error)
                                .setTitle(R.string.invalidBalance_error_title)
                                .setPositiveButton(android.R.string.ok,null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }
                double testBalance = Double.parseDouble(balance);

                if(testBalance<=0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCardNumber.this);
                    builder.setMessage(R.string.balance_error)
                            .setTitle(R.string.balance_error_title)
                            .setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                if(cardNumber.length()==16&&ccv.length()==4) {
                    // mRegProgress.hide();

                    for(int i=0;i<16;i++)
                    {
                        if(cardNumber.charAt(i)>='1'&&cardNumber.charAt(i)>='9')
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EditCardNumber.this);
                            builder.setMessage(R.string.cardNumber_error)
                                    .setTitle(R.string.cardNumber_error_title)
                                    .setPositiveButton(android.R.string.ok,null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return;
                        }
                    }
                    for(int i=0;i<4;i++)
                    {
                        if(ccv.charAt(i)>='1'&&ccv.charAt(i)>='9')
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EditCardNumber.this);
                            builder.setMessage(R.string.ccv_error)
                                    .setTitle(R.string.cccv_error_title)
                                    .setPositiveButton(android.R.string.ok,null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return;
                        }
                    }
                    Toast.makeText(EditCardNumber.this, R.string.cardNUmberSaved, Toast.LENGTH_SHORT).show();
                    mCardNumber.setVisibility(View.INVISIBLE);
                    mCcv.setVisibility(View.INVISIBLE);
                    mBalance.setVisibility(View.INVISIBLE);
                    mSaveButton.setVisibility(View.INVISIBLE);

                    /*Intent in = new Intent(CardNumber.this,CardNumber.class);
                    startActivity(in);*/
                    hashCardNumber(cardNumber);
                    hashCcvNumber(ccv);
                    //mGetUsersDataReference.child("user_card").setValue(cardNumber);
                   // mGetUsersDataReference.child("user_ccv").setValue(ccv);
                    mGetUsersDataReference.child("user_balance").setValue(balance);
                }
                else{
                    // mRegProgress.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCardNumber.this);
                    builder.setMessage(R.string.validCard)
                            .setTitle(R.string.invalidCard)
                            .setPositiveButton(android.R.string.ok,null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    mCardNumber.setHint(R.string.cardNumber);
                    mCcv.setHint(R.string.cardccv);
                    mBalance.setHint("Account Balance");
                    mCardNumber.findFocus();
                    return;
                }
            }
        });

    }

    private void hashCcvNumber(String ccv) {
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(ccv.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for(int i=0;i<messageDigest.length;i++)
            {
                String x =Integer.toHexString(0xFF & messageDigest[i]);
                while (x.length()<2)
                {
                    x ="0"+x;
                }
                MD5Hash.append(x);

            }
            mGetUsersDataReference.child("user_ccv").setValue(MD5Hash.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void hashCardNumber(String cardNumber) {
        //String cardHash="";//ccvHash="";
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(cardNumber.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for(int i=0;i<messageDigest.length;i++)
            {
                String x =Integer.toHexString(0xFF & messageDigest[i]);
                while (x.length()<2)
                {
                    x ="0"+x;
                }
                MD5Hash.append(x);

            }
            mGetUsersDataReference.child("user_card").setValue(MD5Hash.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
