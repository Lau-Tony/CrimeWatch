package com.example.crimewatch;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CrimeMapsActivity extends AppCompatActivity implements OnMapReadyCallback {
            
    private GoogleMap mMap;
    private FetchCrimeData fetch;
    float zoomLevel = 15.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public List<crime> fetchCrime() throws IOException, JSONException {
        String url = "https://gis.mapleridge.ca/arcgis/rest/services/OpenData/PublicSafety/MapServer/7/query?where=1%3D1&outFields=OccuranceYear,Offense,OffenseCategory,HouseNumber,City,SHAPE,StreetName&returnGeometry=false&outSR=4326&f=json";
        String request = fetch.request(url);

        List<crime> crime = new ArrayList<crime>();
        JSONObject root = new JSONObject(request);
        JSONArray c = root.getJSONArray("features");
        JSONObject attribute;
        JSONObject geometry;

        for(int i = 0; i < c.length(); i++){
            JSONObject crimeObject = c.getJSONObject(i);
            attribute = (JSONObject) crimeObject.get("attributes");
            System.out.println(attribute);
            System.out.println("Hello");

        }
        return crime;
    }



   /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(49.2193, -122.6015);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Maple Ridge"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }
}
