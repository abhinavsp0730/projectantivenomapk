package com.xiken.projectantivenom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int i =3;

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
//        LatLng sydney = new LatLng(-34, 151);
        FirebaseDatabase.getInstance().getReference().child("/HospitalList")
                .push().setValue(new CustomLocation(20.2000,85.7302));
        FirebaseDatabase.getInstance().getReference().child("/HospitalList")
                .push().setValue(new CustomLocation(20.2300,85.730342));
        FirebaseDatabase.getInstance().getReference().child("/HospitalList")
                .push().setValue(new CustomLocation(20.2400,85.730544));
//        FirebaseDatabase.getInstance().getReference().child("/HospitalList")
//                .push().setValue(new CustomLocation(20.2500,85.73656));
//        FirebaseDatabase.getInstance().getReference().child("/HospitalList")
//                .push().setValue(new CustomLocation(20.2600,85.7334));
//        FirebaseDatabase.getInstance().getReference().child("/HospitalList")
//                .push().setValue(new CustomLocation(20.2700,85.73310));
//        LatLng newPlace = new LatLng(20.236959, 85.722434);
        LatLng cvraman = new LatLng(20.219245, 85.736002);
        LatLng cvraman_two = new LatLng(20.219245, 85.736002);
//        mMap.addMarker(new MarkerOptions().position(newPlace).title("Hospital 1"));
//        mMap.addMarker(new MarkerOptions().position(cvraman).title("Hospital 2"));
        FirebaseDatabase.getInstance().getReference().child("/HospitalList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    CustomLocation customLocation = snapshot.getValue(CustomLocation.class);
                    double latitude = customLocation.getLatiutude();
                    double longitude = customLocation.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Hospital"+ i++));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mMap.clear();
            }
        });
        mMap.addMarker(new MarkerOptions().position(cvraman_two).title("hospital 7"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cvraman));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f));

    }
}
