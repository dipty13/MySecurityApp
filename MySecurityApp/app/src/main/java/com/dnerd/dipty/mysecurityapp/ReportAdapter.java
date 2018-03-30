package com.dnerd.dipty.mysecurityapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Dipty on 3/26/2018.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.TransactionViewHolder> {
    private static final String TAG = "ReportAdapter";
    private int mNumberItems;
    private static int viewHolderCount;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener{
        void onListItemClick(int clickItemIndex);
    }
    public ReportAdapter(int numberOfItems, ListItemClickListener onClickListener)
    {
        mNumberItems = numberOfItems;
        this.mOnClickListener = onClickListener;
        viewHolderCount =0;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.transaction_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachParentImmediately);
        TransactionViewHolder viewHolder = new TransactionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: #"+position);
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView listItemNumberView;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            //listItemNumberView = itemView.findViewById(R.id.reportTextView);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {

            listItemNumberView.setText(String.valueOf(position));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
