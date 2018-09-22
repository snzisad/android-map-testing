package zisad.android.com.androidmaptesting.BusinessLogic;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.maps.android.PolyUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by MDABURAIHAN on 8/6/2018.
 */
public class GetDirectionData extends AsyncTask<Object, String, String> {
    String googleDirectionData;
    GoogleMap gMap;
    String url;
    String duration, distance;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        gMap = (GoogleMap)objects[0];
        url = (String) objects[1];
        latLng = (LatLng) objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return googleDirectionData;
    }

    @Override
    protected void onPostExecute(String s) {
        HashMap<String, String> directionList = null;
        String[] polylineList;

        Log.d("ZISAD",s);

        DataParser parser = new DataParser();
        directionList = parser.parseDirection(s);

        polylineList = parser.parseRoute(s);

        //set total distance and duration
        setDistanceDuration(directionList);

        //set direction
        displayDirection(polylineList);

    }

    private void setDistanceDuration(HashMap<String, String> directionList){

        duration = directionList.get("duration");
        distance = directionList.get("distance");

        gMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Duration: "+duration);
        markerOptions.snippet("Distance: " + distance);
        gMap.addMarker(markerOptions).showInfoWindow();
    }

    private void displayDirection(String[] polylineList){
        int count = polylineList.length;

        for(int i=0; i<count; i++){
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
//            options.addAll(PolyUtil.decode(polylineList[i]));
//            options.addAll(decodePol)

            gMap.addPolyline(options);
        }
    }
}
