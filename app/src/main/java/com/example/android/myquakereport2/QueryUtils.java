package com.example.android.myquakereport2;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by M on 01/07/2018.
 */

public class QueryUtils {


    private static final String TAG = "QueryUtils";



    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String jsonresponse) {
        Log.i(TAG, "extractEarthquakes: ");
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        if(TextUtils.isEmpty(jsonresponse)) {
            return earthquakes;
        }
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(jsonresponse);
            JSONArray arrFeature = root.getJSONArray("features");
            for(int i = 0,n = arrFeature.length(); i < n; i++ ){
                JSONObject objectFeature = arrFeature.getJSONObject(i);
                JSONObject objectprop = objectFeature.getJSONObject("properties");
                double mag = objectprop.getDouble("mag");
                String place = objectprop.getString("place");
                Long time = objectprop.getLong("time");
                String url = objectprop.getString("url");
                earthquakes.add(new Earthquake(mag,place,time,url));

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static URL createUrl(String url){
        if(TextUtils.isEmpty(url))
            return null;
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url1;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        Log.i(TAG, "makeHttpRequest: ");
        String jsonResponse = "";
        if(url == null)
            return jsonResponse;
        HttpURLConnection  urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection != null)
                 urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();

        }

        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream == null)
            Log.i(TAG, "readFromStream: input stream = null");
        if(inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
                while (line != null){
                    output.append(line);
                    line = bufferedReader.readLine();
                }
        }
        return output.toString();
    }




    /**
     * Return a formated date string from time in milliseconds*/

    public static String formatDate(long timeInMillisec){
        Date date = new Date(timeInMillisec);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(date);
    }

    public static String formatTime(long timeInMillisec){
        Date date = new Date(timeInMillisec);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(date);
    }

    public static String formatMag(double mag){
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(mag);
    }


     public static int getMagnitudeColor(double magnitude, Context context) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }

}
