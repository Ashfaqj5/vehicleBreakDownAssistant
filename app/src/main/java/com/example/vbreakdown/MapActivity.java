package com.example.vbreakdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button find;
    double currentLat = 0;
    double currentLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        find = findViewById(R.id.find);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = "PetrolPump";
                String url = "https://maps.googleapi.com/maps/api/place/nearbysearch/json" +
                        "?location=" + currentLat + "," + currentLong +
                        "&radius=5000" +
                        "&types=" + place +
                        "&sensor=true" +
                        "&key" + getResources().getString(R.string.google_map_key);
                new PlaceTask().execute(url);

            }
        });
    }

    private void getCurrentLocation() {

        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLat=location.getLatitude();
                    currentLong=location.getLongitude();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                            map=googleMap;
                            map.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat,currentLong),10)
                            );
                        }
                    });
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if(requestCode ==44){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            String data=null;
            try {
                data= downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        //initializing url
        URL url=new URL(string);
        //Initialize connection
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        //Connection connection
        connection.connect();
        //Initialize input stream
        InputStream stream=connection.getInputStream();
        //Initialize buffer reader
        BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
        //Initialize string builder
        StringBuilder builder=new StringBuilder();
        //Initialize string variable
        String line="";
        while ((line=reader.readLine())!=null){
            builder.append(line);
        }
        String data=builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            Jsonparser jsonparser=new Jsonparser();
            List<HashMap<String,String>>  mapList=null;
            JSONObject object=null;
            try {
                object=new JSONObject(strings[0]);
                mapList=jsonparser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            map.clear();
            for(int i=0;i<hashMaps.size();i++){
                HashMap<String,String> hashMapList=hashMaps.get(i);
                double lat=Double.parseDouble(hashMapList.get("lat"));
                double lng=Double.parseDouble(hashMapList.get("lng"));
                String name=hashMapList.get("name");
                LatLng latLng=new LatLng(lat,lng);
                MarkerOptions options=new MarkerOptions();
                options.position(latLng);
                options.title(name);
                map.addMarker(options);
            }
        }
    }
}