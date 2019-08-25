package com.soft.angelcanturamirez.servsocialapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng ute = new LatLng(25.828639, -100.276220);
        LatLng casa = new LatLng(25.767401, -100.253144);
        //BARES
        LatLng mama = new LatLng(25.692839, -100.243956);
        LatLng majadera = new LatLng(25.632150, -100.272250);
        LatLng punto_medio = new LatLng(25.716014, -100.261396);
        //mMap.addMarker(new MarkerOptions().position(ute).title("UNIVERSIDAD TECNOLOGICA GRAL. MARIANO ESCOBEDO").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        mMap.addMarker(new MarkerOptions().position(casa).title("MI CASA").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addMarker(new MarkerOptions().position(mama).title("MAMA LINDA VISTA").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.addMarker(new MarkerOptions().position(majadera).title("MAJADERA SOCIAL CLUB").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        //locacionBar(26.208693, -98.179060, "Casa de McAllen");
        //locacionBar();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto_medio, 10f));
    }
}
