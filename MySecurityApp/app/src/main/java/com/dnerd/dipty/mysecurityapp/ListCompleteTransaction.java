package com.dnerd.dipty.mysecurityapp;

/**
 * Created by Dipty on 3/29/2018.
 */

public class ListCompleteTransaction {

    private String mHead;
    private String mDescription;

    public ListCompleteTransaction(String mHead, String mDescription) {
        this.mHead = mHead;
        this.mDescription = mDescription;
    }

    public String getmHead() {
        return mHead;
    }

    public String getmDescription() {
        return mDescription;
    }
}
