package com.example.android.myquakereport2;

/**
 * Created by M on 30/06/2018.
 */

public class Earthquake {

    private double mMag;
    private String mLoc;
    private Long mTimeInMilliSeconds;
    private String url;

  /**
   * Constructs a new {@link Earthquake} object
   * @param mMag is the magnitude of the earthquake
   * @param mLoc is the location of the earthquake
   * @param mTimeInMilliSeconds is the time of the occurrence of the earthquake in milliseconds*/

    public Earthquake(double mMag, String mLoc, Long mTimeInMilliSeconds,String url) {
        this.mMag = mMag;
        this.mLoc = mLoc;
        this.mTimeInMilliSeconds = mTimeInMilliSeconds;
        this.url = url;
    }

    /*@return mMag of the earthquake*/
    public double getmMag() {
        return mMag;
    }

    public String getmLoc() {
        return mLoc;
    }

    public Long getmTimeInMilliSeconds() {
        return mTimeInMilliSeconds;
    }

    public String getUrl() {
        return url;
    }
}
