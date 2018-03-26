package com.dnerd.dipty.mysecurityapp;

/**
 * Created by Dipty on 3/25/2018.
 */

public class Verification {
    String mCode;
    int mCounter;
    double mLat,mLng;

   void setVerificationCode(String code)
    {
        mCode = code;



    }
     String getVerificationCode()
    {
        //mCode = code;

        return mCode;

    }
    void setVerficationCounter(int counter)
    {
        mCounter =counter;

    }
    int getVerficationCounter()
    {
        //mCounter =counter;
        return mCounter;
    }
    void setLat(double lat)
    {
        mLat = lat;
    }
    void setLng(double lng)
    {
        mLng = lng;
    }
    double getLat()
    {
        return mLat;
    }
   double getLng()
    {
       return  mLng;
    }
}

