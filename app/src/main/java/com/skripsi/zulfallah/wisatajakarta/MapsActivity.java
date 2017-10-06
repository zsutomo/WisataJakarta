package com.skripsi.zulfallah.wisatajakarta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    public LatLng userlocation, destination;
    com.google.android.gms.location.LocationListener mLocationListener;
    private String serverKey = "AIzaSyDe33e2T-gBg9GdZxs7C_SYAEZA2ouggDc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        MarkerPoints = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView imageView = findViewById(R.id.iv_back_maps);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Koordinat();


    }

    public void Koordinat() {

        Bundle bundle = getIntent().getExtras();
        Double latitude = bundle.getDouble("Latitude_tujuan");
        Double longitude = bundle.getDouble("Longitude_tujuan");

        System.out.println("Koordinat : " + latitude + "," + longitude );
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
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        String[] nama_stasiun = {
                "Stasiun Ancol",
                "Stasiun Angke",
                "Stasiun Bojong Indah",
                "Stasiun Buaran",
                "Stasiun Cakung",
                "Stasiun Cawang",
                "Stasiun Cikini",
                "Stasiun Cipinang",
                "Stasiun Duren Kalibata",
                "Stasiun Duri",
                "Stasiun Gambir",
                "Stasiun Gang Sentiong",
                "Stasiun Gondangdia",
                "Stasiun Grogol",
                "Stasiun Jakarta Gudang",
                "Stasiun Jakarta Kota",
                "Stasiun Jatinegara",
                "Stasiun Jayakarta",
                "Stasiun Juanda",
                "Stasiun Kalideres",
                "Stasiun Kampung Bandan",
                "Stasiun Karet",
                "Stasiun Kebayoran",
                "Stasiun Kemayoran",
                "Stasiun Klender",
                "Stasiun Klender Baru",
                "Stasiun Kramat",
                "Stasiun Lenteng Agung",
                "Stasiun Mangga Besar",
                "Stasiun Manggarai",
                "Stasiun Palmerah",
                "Stasiun Pasar Minggu",
                "Stasiun Pasar Minggu Baru",
                "Stasiun Pasar Senen",
                "Stasiun Pesing",
                "Stasiun Rajawali",
                "Stasiun Salemba",
                "Stasiun Sawah Besar",
                "Stasiun Rawa Buaya",
                "Stasiun Sudirman",
                "Stasiun Taman Kota",
                "Stasiun Tanahabang",
                "Stasiun Tanjung Barat",
                "Stasiun Tanjung Priok",
                "Stasiun Tebet",
                "Stasiun Universitas Pancasila",
                "Stasiun Pondok Jati",
                "Bekasi",
                "Kranji",
                "Cakung",
                "Klender Baru",
                "Buaran",
                "Klender",
                "Cipinang",
                "Manggarai",
                "Tebet",
                "Cawang",
                "Duren Kalibata",
                "Pasar Minggu Baru",
                "Pasar Minggu",
                "Tanjung Barat",
                "Lenteng Agung",
                "Universitas Pancasila",
                "Universitas Indonesia",
                "Pondok Cina",
                "Depok Baru",
                "Depok",
                "Citayam",
                "Bojong Gede",
                "Cilebut",
                "Bogor"
        };
        double[] latitude_stasiun = {
                -6.1280,
                -6.1444,
                -6.1602,
                -6.2163,
                -6.2193,
                -6.2427,
                -6.1986,
                -6.2141,
                -6.2553,
                -6.1559,
                -6.1767,
                -6.1859,
                -6.1861,
                -6.1620,
                -6.1346,
                -6.1376,
                -6.2153,
                -6.1413,
                -6.1673,
                -6.1661,
                -6.1329,
                -6.2009,
                -6.2372,
                -6.1623,
                -6.2133,
                -6.2176,
                -6.1938,
                -6.3306,
                -6.1498,
                -6.2099,
                -6.2074,
                -6.2838,
                -6.2628,
                -6.1750,
                -6.1612,
                -6.1450,
                -6.1937,
                -6.1607,
                -6.1627,
                -6.2024,
                -6.1585,
                -6.1858,
                -6.3079,
                -6.1107,
                -6.2261,
                -6.3389,
                -6.2091,
                -6.235714,
                -6.224312,
                -6.219287,
                -6.217625,
                -6.216309,
                -6.213294,
                -6.214099,
                -6.209985,
                -6.226081,
                -6.242681,
                -6.255369,
                -6.262752,
                -6.283708,
                -6.307826,
                -6.330651,
                -6.338589,
                -6.360694,
                -6.368823,
                -6.390956,
                -6.404786,
                -6.448522,
                -6.493006,
                -6.530486,
                -6.595575
        };
        double[] longitude_stasiun = {
                106.8451,
                106.8007,
                106.7363,
                106.9283,
                106.9522,
                106.8588,
                106.8413,
                106.8851,
                106.8550,
                106.8014,
                106.8307,
                106.8507,
                106.8327,
                106.7893,
                106.8196,
                106.8146,
                106.8702,
                106.8231,
                106.8305,
                106.7038,
                106.8283,
                106.8159,
                106.7825,
                106.8415,
                106.8991,
                106.9395,
                106.8565,
                106.8349,
                106.8270,
                106.8502,
                106.7976,
                106.8442,
                106.8518,
                106.8454,
                106.7715,
                106.8367,
                106.8490,
                106.8276,
                106.7236,
                106.8234,
                106.7561,
                106.8108,
                106.8388,
                106.8815,
                106.8583,
                106.8344,
                106.8623,
                107.003144,
                106.979485,
                106.952282,
                106.939593,
                106.928423,
                106.899247,
                106.885467,
                106.850720,
                106.858387,
                106.859161,
                106.855380,
                106.851838,
                106.844216,
                106.838828,
                106.834977,
                106.834393,
                106.831730,
                106.832187,
                106.821655,
                106.817258,
                106.802501,
                106.794895,
                106.800549,
                106.790408
        };
        final MarkerOptions[] markerOptions = {new MarkerOptions()};
        final LatLng[] latLng_stasiun = new LatLng[1];
        Bundle bundle = getIntent().getExtras();
        Double latitude_lokasi = bundle.getDouble("Latitude_tujuan");
        Double longitude_lokasi = bundle.getDouble("Longitude_tujuan");
        final LatLng[] latLng_lokasi = new LatLng[1];
        latLng_lokasi[0] = new LatLng(latitude_lokasi,longitude_lokasi);
        GPSTracker gpsTracker= new GPSTracker(getApplicationContext(), MapsActivity.this);
        final LatLng[] latLng_user = new LatLng[1];
        latLng_user[0] = new LatLng(gpsTracker.latitude,gpsTracker.longitude);
        double temp_jarak=9999999;
        int index=0;
        for(int i=0; i<latitude_stasiun.length; i++){
            latLng_stasiun[0] = new LatLng(latitude_stasiun[i],longitude_stasiun[i]);
            markerOptions[0].position(latLng_stasiun[0]);
            markerOptions[0].title(nama_stasiun[i]);
            markerOptions[0].icon(BitmapDescriptorFactory.fromResource(R.mipmap.stasiun));
            mMap.addMarker(markerOptions[0]);

            Location locationA = new Location("point A");
            locationA.setLatitude(latLng_user[0] .latitude);
            locationA.setLongitude(latLng_user[0] .longitude);
            Location locationB = new Location("point B");
            locationB.setLatitude(latLng_stasiun[0].latitude);
            locationB.setLongitude(latLng_stasiun[0].longitude);
            double distance = locationA.distanceTo(locationB);
            if(distance<temp_jarak){
                temp_jarak=distance;
                index=i;
            }
        }
        latLng_stasiun[0] = new LatLng(latitude_stasiun[index],longitude_stasiun[index]);
        GoogleDirection.withServerKey(serverKey)
                .from(latLng_user[0])
                .to(latLng_stasiun[0])
                .transportMode(TransportMode.WALKING)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        System.out.println("berhasil direksi");
                        System.out.println(direction.getErrorMessage());
//                                    Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
                        if (direction.isOK()) {

                            markerOptions[0].position(latLng_lokasi[0]);
                            markerOptions[0].title("Lokasi Tempat Wisata");
                            markerOptions[0].icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                            mMap.addMarker(markerOptions[0]);



                            markerOptions[0] = new MarkerOptions();
                            markerOptions[0].position(latLng_user[0]);
                            markerOptions[0].title("Lokasi User");
                            markerOptions[0].icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(markerOptions[0]);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng_user[0], 14);
                            mMap.animateCamera(cameraUpdate);

                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            mMap.addPolyline(DirectionConverter.createPolyline(MapsActivity.this, directionPositionList, 5, Color.BLUE));


                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });





    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }

    }


        private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        userlocation = new LatLng(location.getLatitude(), location.getLongitude());

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng
                (userlocation.latitude, userlocation.longitude)).zoom(16).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission(){

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Izin diberikan.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Izin ditolak.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
