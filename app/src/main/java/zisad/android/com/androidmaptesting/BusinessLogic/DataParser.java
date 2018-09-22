package zisad.android.com.androidmaptesting.BusinessLogic;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJSON){
        HashMap<String,String> googlePlaceMap = new HashMap<>();

        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {

            if (!googlePlaceJSON.isNull("name")) {
                placeName = googlePlaceJSON.getString("name");
            }

            if (!googlePlaceJSON.isNull("vicinity")) {
                vicinity = googlePlaceJSON.getString("vicinity");
            }

            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        }
        catch (Exception e){}

        return googlePlaceMap;
    }

    private HashMap<String, String> getDistanceDuration(JSONArray googleDirectionJSON){
        HashMap<String,String> googleDirectionMap  = new HashMap<>();

        String duration = "";
        String distance = "";

        try {

            duration = googleDirectionJSON.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionJSON.getJSONObject(0).getJSONObject("distance").getString("text");


            googleDirectionMap.put("duration", duration);
            googleDirectionMap.put("distance", distance);
        }
        catch (Exception e){}

        return googleDirectionMap;
    }

    private List<HashMap<String, String>> getAllPlaces(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String, String>> placeList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i=0; i<count; i++){
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placeList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placeList;

    }

    public List<HashMap<String, String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (Exception e){}

        return getAllPlaces(jsonArray);
    }

    public HashMap<String, String> parseDirection(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
        }
        catch (Exception e){}

        return getDistanceDuration(jsonArray);
    }

    public String[] parseRoute(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        }
        catch (Exception e){}

        return getPaths(jsonArray);
    }

    public String[] getPaths(JSONArray googleStepsJSON){
        int count = googleStepsJSON.length();
        String[] polylines = new String[count];

        for(int i=0 ; i<count; i++){
            try {
                polylines[i] = getPath(googleStepsJSON.getJSONObject(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return polylines;
    }

    public String getPath(JSONObject googlePathJSON){
        String polyline = "";

        try {
            polyline = googlePathJSON.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return polyline;
    }

//    private List<LatLng> decodePolyline(String poly){
//
//    }
}
