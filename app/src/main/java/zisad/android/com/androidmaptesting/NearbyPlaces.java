package zisad.android.com.androidmaptesting;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import zisad.android.com.androidmaptesting.BusinessLogic.CurrentLocation;
import zisad.android.com.androidmaptesting.BusinessLogic.GetNearbyPlaces;

public class NearbyPlaces extends AppCompatActivity implements
        OnMapReadyCallback {

    Button button_hospitals, button_restaurants, button_schools, button_myLocation;
    private GoogleMap gMap;

    Object dataTransfer[];
    String url, type;
    GetNearbyPlaces places;

    Location location;
    FusedLocationProviderClient client;

    int PROXIMITY_RADIUS = 1000;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);

        button_hospitals = (Button) findViewById(R.id.button_hospitals);
        button_restaurants = (Button) findViewById(R.id.button_restaurants);
        button_schools = (Button) findViewById(R.id.button_schools);
        button_myLocation = (Button) findViewById(R.id.button_myLocation);

        client = LocationServices.getFusedLocationProviderClient(this);

        dataTransfer = new Object[2];
        places = new GetNearbyPlaces();

        button_hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchHospitals();
            }
        });

        button_restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchrestaurant();
            }
        });

        button_schools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchSchool();
            }
        });

        button_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLocationinMap();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void searchrestaurant(){
        gMap.clear();
        type = "restaurant";
        url = getUrl(type);
        dataTransfer[0] = gMap;
        dataTransfer[1] = url;

        GetNearbyPlaces places = new GetNearbyPlaces();
        places.execute(dataTransfer);
        Toast.makeText(NearbyPlaces.this, "Searching nearby restaurant", Toast.LENGTH_SHORT).show();

    }

    private void searchHospitals(){
        gMap.clear();
        type = "hospital";
         url = getUrl(type);
        dataTransfer[0] = gMap;
        dataTransfer[1] = url;

        places = new GetNearbyPlaces();
        places.execute(dataTransfer);
        Toast.makeText(NearbyPlaces.this, "Searching nearby hospitals", Toast.LENGTH_SHORT).show();
    }

    private void searchSchool(){
        gMap.clear();
        type = "school";
        url = getUrl(type);
        dataTransfer[0] = gMap;
        dataTransfer[1] = url;

        GetNearbyPlaces places = new GetNearbyPlaces();
        places.execute(dataTransfer);
        Toast.makeText(NearbyPlaces.this, "Searching nearby school", Toast.LENGTH_SHORT).show();
    }

    private String getUrl(String type){
        getCurrentLocation();

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+type);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getResources().getString(R.string.google_maps_key));

        return googlePlaceUrl.toString();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        }

        myLocationinMap();

    }

    void myLocationinMap(){
        getCurrentLocation();

        gMap.clear();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("I am here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        gMap.addMarker(markerOptions);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(18));
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
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
    }

}
