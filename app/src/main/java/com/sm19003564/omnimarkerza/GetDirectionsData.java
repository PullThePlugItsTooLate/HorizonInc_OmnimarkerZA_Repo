package com.sm19003564.omnimarkerza;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    private String googleDirectionsData;
    private GoogleMap mMap;
    String url, duration, distance;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];

        DownloadUrl downloadURL = new DownloadUrl();
        try {
            googleDirectionsData = downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s){

        mMap.clear();
        String[] directionsList;
        HashMap<String,String> directionDetailList = null;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        directionDetailList = parser.parseDirectionDetail(s);
        displayDirection(directionsList);

        duration = directionDetailList.get("duration");
        distance = directionDetailList.get("distance");


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Duration = "+ duration);
        markerOptions.snippet("Distance = "+ distance);

        mMap.addMarker(markerOptions);
    }

    public void displayDirection(String[] directionsList){
        int count = directionsList.length;
        for(int i = 0; i < count; i++){
            PolylineOptions options = new PolylineOptions();
            options.color(Color.CYAN);
            options.width(10);
            options.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(options);

        }
    }
}
