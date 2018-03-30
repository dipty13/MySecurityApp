package com.dnerd.dipty.mysecurityapp;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Dipty on 3/29/2018.
 */

public class MyCompleteAdatapter extends RecyclerView.Adapter<MyCompleteAdatapter.ViewHolderComplete>{
    private List<ListCompleteTransaction> mListItems;
    private Context mContext;

    public MyCompleteAdatapter(List<ListCompleteTransaction> mListItems, Context mContext) {
        this.mListItems = mListItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolderComplete onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_complete_transaction,parent,false);

        return new ViewHolderComplete(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderComplete holder, int position) {
    final ListCompleteTransaction listItem = mListItems.get(position);

        holder.mTvComHead.setText(listItem.getmHead());
        holder.mTvComDescription.setText(listItem.getmDescription());

        holder.mComleteLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"you clicked "+listItem.getmHead(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext,SuccessfulTransaction.class);
                intent.putExtra("time",listItem.getmHead());
                intent.putExtra("date",listItem.getmDescription());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class ViewHolderComplete extends RecyclerView.ViewHolder{
        public TextView mTvComHead,mTvComDescription;
        public LinearLayout mComleteLinearLayout;

        public ViewHolderComplete(View itemView) {
            super(itemView);

            mTvComHead = itemView.findViewById(R.id.completeHeading);
            mTvComDescription = itemView.findViewById(R.id.completeDescribtion);
            mComleteLinearLayout = itemView.findViewById(R.id.completeLinearLayout);
        }


    }
}
