package com.example.android.myquakereport2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 05/07/2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String mUrl;
    private static final String TAG = "EarthquakeLoader";
    public EarthquakeLoader(Context context,String url) {

        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(TAG, "onStartLoading: ");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.i(TAG, "loadInBackground: ");
        if(TextUtils.isEmpty(mUrl))
            return null;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = QueryUtils.createUrl(mUrl);
        String jsonResponse = "";
        try {
            jsonResponse   = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Earthquake> arrayList = QueryUtils.extractEarthquakes(jsonResponse);
        return arrayList;
    }
}
