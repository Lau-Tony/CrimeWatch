package com.example.crimewatch;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CrimeMapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = CrimeMapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    float zoomLevel = 15.0f;
    private ArrayList<crime> crimes = new ArrayList<>();
    private static String URL = "https://gis.mapleridge.ca/arcgis/rest/services/OpenData/PublicSafety/MapServer/7/query?where=1%3D1&outFields=*&orderByFields=OBJECTID%20DESC&outSR=4326&f=json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new getCrime().execute();
        setContentView(R.layout.activity_crime_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
        }
        for(int i = 0; i < crimes.size(); i++) {
            LatLng currentCrime = new LatLng(crimes.get(i).getY(),crimes.get(i).getX());
            mMap.addMarker(new MarkerOptions().position(currentCrime).title(crimes.get(i).getOffense()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentCrime));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCrime, zoomLevel));
        }


    }


    private class getCrime extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... arg0) {

            FetchCrimeData sh = new FetchCrimeData();

            String jsonStr = sh.makeServiceCall(URL);
            if (jsonStr != null) {
                try {
                    JSONObject crimeJsonObject = new JSONObject(jsonStr);
                    JSONArray crimeJsonArray = crimeJsonObject.getJSONArray("features");

                    for (int i = 0; i < crimeJsonArray.length(); i++) {
                        JSONObject c = crimeJsonArray.getJSONObject(i);
                        String attribute = c.getString("attributes");
                        String geometry = c.getString("geometry");
                        JSONObject crimeobj = new JSONObject(attribute);
                        JSONObject coorObj = new JSONObject(geometry);

                        String offense = crimeobj.getString("Offense");
                        double xcoor = coorObj.getDouble("x");
                        double ycoor = coorObj.getDouble("y");

                        crime crime = new crime();
                        crime.setOffense(offense);
                        crime.setX(xcoor);
                        crime.setY(ycoor);
                        crimes.add(crime);
                        //Log.d("list", crimes.toString());

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }
    }

}
