package com.dnerd.dipty.mysecurityapp;

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

public class MyInCompleteAdapter extends RecyclerView.Adapter<MyInCompleteAdapter.ViewHolderInComplete> {

    private List<ListInCompleteItem> mListItems;
    private Context mContext;

    public MyInCompleteAdapter(List<ListInCompleteItem> mListItems, Context mContext) {
        this.mListItems = mListItems;
        this.mContext = mContext;
    }
    @Override
    public ViewHolderInComplete onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_incomplete_transaction,parent,false);

        return new MyInCompleteAdapter.ViewHolderInComplete(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderInComplete holder, int position) {

        final ListInCompleteItem listItem = mListItems.get(position);

        holder.mTvInComHead.setText(listItem.getmHead());
        holder.mTvInComDescription.setText(listItem.getmDescription());

        holder.mInComleteLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"you clicked "+listItem.getmHead(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UnsuccessfulTransaction.class);
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

    public class ViewHolderInComplete extends RecyclerView.ViewHolder{
        public TextView mTvInComHead,mTvInComDescription;
        public LinearLayout mInComleteLinearLayout;

        public ViewHolderInComplete(View itemView) {
            super(itemView);

            mTvInComHead = itemView.findViewById(R.id.incompleteHeading);
            mTvInComDescription = itemView.findViewById(R.id.incompleteDescribtion);
            mInComleteLinearLayout = itemView.findViewById(R.id.incompleteLinearLayout);
        }


    }
}
