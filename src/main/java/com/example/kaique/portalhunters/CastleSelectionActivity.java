package com.example.kaique.portalhunters;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.Manifest;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import game.Singleton;

public class CastleSelectionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] arenas;
    private LatLng llCastle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_castle_selection);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onDestroy(){
        if(Singleton.CASTLE_LOCATION==null)
            Singleton.CASTLE_LOCATION = llCastle;
        super.onDestroy();
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
        // Get Arena Locations from Google Spreadsheets
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetArenaLocations().execute("https://docs.google.com/spreadsheets/d/1EczYnRAMLuE99iMaizpUdOC5BZPkFOcBLiFVfzD5WcI/pub?gid=1754791849&single=true&output=tsv");
            }
        });
        // Get player location
        getWorldLocation();

        mMap = googleMap;

        mMap.addMarker(new MarkerOptions()
                .position(llCastle)
                .title("Castle")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_castle))   );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(llCastle));


    }

    public void getWorldLocation(){
        LocationManager lcManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation;
            if(lcManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                lastLocation = lcManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            else if(lcManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                lastLocation = lcManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else{
                lastLocation = lcManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
            llCastle = new LatLng(
                    lastLocation.getLongitude(),
                    lastLocation.getLatitude()  );
            Log.d("GetUserLocation", "Location Founded!");
        }
        else{
            // If can't get the location, set it to Sao Paulo
            llCastle = new LatLng(-23.550989,-46.6328833);
            Log.d("GetUserLocation", "Using Sao Paulo Location...");
        }


    }

    // Arenas Spreadsheet link:
    // https://docs.google.com/spreadsheets/d/1EczYnRAMLuE99iMaizpUdOC5BZPkFOcBLiFVfzD5WcI/pub?gid=1754791849&single=true&output=tsv


    private class GetArenaLocations extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                String res;
                HttpURLConnection httpClient;

                URL url = new URL(params[0]);

                httpClient = (HttpURLConnection)url.openConnection();

                if(httpClient.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader isr = new InputStreamReader(httpClient.getInputStream());
                    BufferedReader br = new BufferedReader((isr));
                    StringBuilder strBuilder = new StringBuilder();

                    String tmp;
                    while((tmp = br.readLine()) != null){
                        strBuilder.append(tmp+"\n");
                    }
                    res = strBuilder.toString();
                    Log.d("GetArenaLocations", "Arenas founded!");
                }
                else{
                    res = null;
                    Log.d("GetArenaLocations", "Access Denied");
                }

                httpClient.disconnect();
                return res;
            }
            catch(Exception ex){
                Log.d("GetArenaLocations", ex.getMessage());
            }
            return null;
        }

        public void onPostExecute(String result){
            if(result!=null){
                Log.d("Arenas.onPostExecute", "Adding arenas");
                arenas = result.split(("\n"));

                // Add a marker for each arena location
                for(int i=1; i<arenas.length; i++){
                    String[] txt = arenas[i].split("\t");

                    String LocationName = txt[0];
                    Float Lat = Float.valueOf(txt[1].split(",")[0]);
                    Float Lng = Float.valueOf(txt[1].split(",")[1]);

                    Log.d("--- Arena ---", LocationName);
                    Log.d("--- Arena ---", txt[1].split(",")[0]);
                    Log.d("--- Arena ---", txt[1].split(",")[1]);


                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Lat, Lng))
                            .title(LocationName)
                            .icon(BitmapDescriptorFactory.defaultMarker((new Random()).nextInt(360) ))
                    );
                }
            }
        }

    }


}
