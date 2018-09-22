package zisad.android.com.androidmaptesting;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import zisad.android.com.androidmaptesting.BusinessLogic.CurrentLocation;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class CurrentLocationInfo extends AppCompatActivity {
    private FusedLocationProviderClient client;

    CurrentLocation myLocation;
    TextView textView;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location_info);

        myLocation= new CurrentLocation(this);
        textView = (TextView)findViewById(R.id.location);

        getCurrentLocation();

        Button button = (Button) findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });
    }

    private void getMyLocation(){
        location=myLocation.getCurrentLocation();
        if(location!= null){
            textView.setText("Latitude: "+location.getLatitude()+"\nLongitude: "+location.getLongitude());
        }
        else{
            textView.setText("Error");
        }
    }

    public void getCurrentLocation(){
        client = LocationServices.getFusedLocationProviderClient(this);

        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);

        //if permission is not granted
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!= null){
                    textView.setText("Latitude: "+location.getLatitude()+"\nLongitude: "+location.getLongitude());
                }
            }
        });
    }

}
