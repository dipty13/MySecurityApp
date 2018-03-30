package com.dnerd.dipty.mysecurityapp;

/**
 * Created by Dipty on 3/29/2018.
 */

public class ListInCompleteItem {
    private String mInHead;
    private String mInDescription;

    public ListInCompleteItem(String mInHead, String mInDescription) {
        this.mInHead = mInHead;
        this.mInDescription = mInDescription;
    }

    public String getmHead() {
        return mInHead;
    }

    public String getmDescription() {
        return mInDescription;
    }
}
