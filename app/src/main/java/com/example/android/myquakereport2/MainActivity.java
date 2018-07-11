package com.example.android.myquakereport2;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>>{
    private static final String TAG = "MainActivity";
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    TextView txtvEmptyState;
    RecyclerViewAdapter recyclerViewAdapter ;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Earthquake> earthquakes = new ArrayList<>();
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        setContentView(R.layout.recycler_view);
        txtvEmptyState = findViewById(R.id.txtvEmptyState);
        recyclerView = findViewById(R.id.recyler_view);
        progressBar = findViewById(R.id.loading_spinner);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,earthquakes);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Log.i(TAG, "calling init loader");
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        }else {
            progressBar.setVisibility(View.GONE);
            txtvEmptyState.setText("no internet connection");
        }
//        if(earthquakes.isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//            txtvEmptyState.setVisibility(View.VISIBLE);
//            txtvEmptyState.setText(R.string.empty_state_list);
//        } else {
//            txtvEmptyState.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//        QuakeAsyncTask asyncTask = new QuakeAsyncTask();
//        asyncTask.execute(USGS_REQUEST_URL);
        

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader: ");
        return new EarthquakeLoader(MainActivity.this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.i(TAG, "onLoadFinished: ");
        progressBar.setVisibility(View.GONE);
        if(!data.isEmpty() && data != null){
            recyclerViewAdapter.earthquakes = data;
            recyclerViewAdapter.notifyDataSetChanged();
            txtvEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.GONE);
            txtvEmptyState.setVisibility(View.VISIBLE);
            txtvEmptyState.setText(R.string.empty_state_list);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i(TAG, "onLoaderReset: ");
    }

//    private class QuakeAsyncTask extends AsyncTask<String,Void,ArrayList<Earthquake>>{
//
//        @Override
//        protected ArrayList<Earthquake> doInBackground(String... strings) {
//            if(strings.length < 1 || strings[0] == null)
//                return null;
//            URL url = QueryUtils.createUrl(strings[0]);
//            String jsonResponse = "";
//            try {
//              jsonResponse   = QueryUtils.makeHttpRequest(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ArrayList<Earthquake> arrayList = QueryUtils.extractEarthquakes(jsonResponse);
//            return arrayList;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            recyclerViewAdapter.earthquakes = earthquakes;
//            recyclerViewAdapter.notifyDataSetChanged();
//        }
//    }

}
