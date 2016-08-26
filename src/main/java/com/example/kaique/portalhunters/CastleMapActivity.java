package com.example.kaique.portalhunters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by kaique on 23/08/16.
 */
public class CastleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_castle);

        FrameLayout mapContainer = (FrameLayout)findViewById(R.id.maps_fragment);
        mapFragment = genDefaultMap();
        mapFragment.getMapAsync(this);
        getFragmentManager().beginTransaction().add(R.id.map_container, mapFragment).commit();
    }

    private MapFragment genDefaultMap() {
        GoogleMapOptions mapOptions = new GoogleMapOptions();
        mapOptions.camera(new CameraPosition(new LatLng(-23.5426820, -46.7630890), 15f, 0f, 0f));
        mapOptions.zoomControlsEnabled(true);
        mapOptions.mapType(GoogleMap.MAP_TYPE_NORMAL);
        return MapFragment.newInstance(mapOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions myMarker = new MarkerOptions();
        myMarker.position(new LatLng(-23.5426820, -46.7630890));
        myMarker.title("My Map");

        MarkerOptions mkShopMorumbi = new MarkerOptions();
        mkShopMorumbi.position(new LatLng(-23.621043023, -46.6985790));
        mkShopMorumbi.title("Shopping Morumbi");

        googleMap.addMarker(myMarker);
        googleMap.addMarker(mkShopMorumbi);
    }
}
