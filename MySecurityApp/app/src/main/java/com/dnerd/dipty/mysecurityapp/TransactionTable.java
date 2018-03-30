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

public class TransactionTable {
    private static final String TAG = "TransactionTable";
    private FirebaseAuth mAuth;
    private DatabaseReference  mTransactionDataReference;
    TransactionTable()
    {

        String counter = "0";
        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mTransactionDataReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child(onlineUserId);

        mTransactionDataReference.child("transaction_counter").setValue(1);
  /*      mTransactionDataReference.child(String.valueOf(0)).child("email").setValue("email");*/
        mTransactionDataReference.child(String.valueOf(0)).child("date").setValue("date");
        mTransactionDataReference.child(String.valueOf(0)).child("time").setValue("time");
        mTransactionDataReference.child(String.valueOf(0)).child("latitude").setValue("latitude");
        mTransactionDataReference.child(String.valueOf(0)).child("longitude").setValue("longitude");
        mTransactionDataReference.child(String.valueOf(0)).child("pay_amount").setValue("amount");
     /*   mTransactionDataReference.child(String.valueOf(0)).child("card_number").setValue("cardNumber");
        mTransactionDataReference.child(String.valueOf(0)).child("card_ccv").setValue("ccv");*/
        mTransactionDataReference.child(String.valueOf(0)).child("transaction_status").setValue("not done").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "onComplete: transaction done");
                } else{
                    Log.d(TAG, "onComplete: transaction table not done!");
                }
            }});

    }
}
