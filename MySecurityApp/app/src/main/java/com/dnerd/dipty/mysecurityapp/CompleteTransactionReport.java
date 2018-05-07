package com.dnerd.dipty.mysecurityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompleteTransactionReport extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<ListCompleteTransaction> mListItems;

    private FirebaseAuth mAuth;
    private DatabaseReference mTransactionDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_transaction_report);

        mRecyclerView = findViewById(R.id.completeRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListItems = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        String onlineUserId = mAuth.getCurrentUser().getUid();
        mTransactionDataReference = FirebaseDatabase.getInstance().getReference().child("Transaction").child(onlineUserId);

       /* for(int i=0;i<=10;i++)
        {
            ListCompleteTransaction listItem = new ListCompleteTransaction(
                    "Heading"+ (i+1),
                    "description....."
            );

            mListItems.add(listItem);
        }*/

       mTransactionDataReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String cnt  = dataSnapshot.child("transaction_counter").getValue().toString();

               int counter = Integer.parseInt(cnt);

               for(int i=counter-1;i>=0;i--)
               {
                   try {
                       String date = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                       String time = dataSnapshot.child(String.valueOf(i)).child("time").getValue().toString();
                       String status = dataSnapshot.child(String.valueOf(i)).child("transaction_status").getValue().toString();

                       if (status.equals("done")) {
                           ListCompleteTransaction listItem = new ListCompleteTransaction(
                                   time,
                                   date
                           );

                           mListItems.add(listItem);
                       }
                   }catch(Exception e)
                   {

                   }
               }

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        mAdapter = new MyCompleteAdatapter(mListItems,this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
