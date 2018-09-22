package zisad.android.com.androidmaptesting.BusinessLogic;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;


import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by MDABURAIHAN on 8/2/2018.
 */
public class CurrentLocation {
    private FusedLocationProviderClient client;
    private Location location;
    Activity activity;

    public CurrentLocation(Activity activity){
        this.activity = activity;

        requestPermission();
    }

    public Location getCurrentLocation(){
        client = LocationServices.getFusedLocationProviderClient(activity);

        //if permission is not granted
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return null;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location loc) {
                location = loc;
            }
        });

        return location;
    }

    //set location service permission
    private void requestPermission(){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }

}
