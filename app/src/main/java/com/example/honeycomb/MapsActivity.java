    package com.example.honeycomb;

    import androidx.annotation.NonNull;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;
    import androidx.fragment.app.FragmentActivity;

    import android.Manifest;
    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.location.Address;
    import android.location.Geocoder;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Toast;

    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.SupportMapFragment;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.Marker;
    import com.google.android.gms.maps.model.MarkerOptions;

    import java.util.List;
    import java.util.Locale;

    public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
        private Marker myMarker;
        private LocationManager locationManager;
        private Location location;
        private Double currentLatitude = -0.00000;
        private Double currentLongitude = -0.00000;
        private Geocoder geocoder;
        private List<Address> addresses;
        private GoogleMap mMap;

        @Override
        public void onLocationChanged(Location location) {
            currentLongitude = location.getLongitude();
            currentLatitude = location.getLatitude();
            Log.v("latitude", String.valueOf(currentLatitude));
            Log.v("longitude", String.valueOf(currentLongitude));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        public Boolean checkSelfPermission(){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                onLocationChanged(location);
                return true;}
            else return false;
        }
        public void requestPermissions(){
            if(checkSelfPermission())
                markIt();
            else{
                if (Build.VERSION.SDK_INT >= 23) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } }
        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(checkSelfPermission())
                    markIt();
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
            }
        }

        public void markIt() {
            try {
                    LatLng sydney = new LatLng(currentLatitude, currentLongitude);
                    addresses = geocoder.getFromLocation(currentLatitude, currentLatitude, 1);
                    myMarker = mMap.addMarker(new MarkerOptions().position(sydney).title(addresses.get(0).getAddressLine(0) + ". lat:" + currentLatitude.toString().substring(0, 8) + ", lon: " + currentLongitude.toString().substring(0, 8)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }catch (Exception e){
                Log.v("exception", "exception on address!");
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            geocoder = new Geocoder(this, Locale.getDefault());
            mMap = googleMap;
            requestPermissions();
        }


    }
