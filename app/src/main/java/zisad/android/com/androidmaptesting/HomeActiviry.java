package zisad.android.com.androidmaptesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActiviry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activiry);
    }

    public void myLocationInfo(View v){
        startActivity(new Intent(this, CurrentLocationInfo.class));
    }

    public void searchLocation(View v){
        startActivity(new Intent(this, SearchResults.class));
    }

    public void nearbyPlaces(View v){
        startActivity(new Intent(this, NearbyPlaces.class));
    }

    public void getRoute(View v){
        startActivity(new Intent(this, DistanceDuration.class));
    }

    public void myLocationinMap(View v){
        startActivity(new Intent(this, MapsActivity.class));
    }
}
