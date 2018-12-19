package com.example.pc.daandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Map_api extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        myMap = googleMap;
        if (isCheck) {
            getLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            myMap.setMyLocationEnabled(true);
            myMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    // cac du lieu  mac dinh
    final private static float ZOOM =15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40,-168),new LatLng(71,136));

    // cac su kien ben xml
    private Button buttonSearchMap;
    private AutoCompleteTextView edittextSearch;
    private ImageView imageviewGPS;

    // cac du lieu cua map
    private boolean isCheck=false;
    private GoogleMap myMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlaceAutocompleteAdapter adapter;
    private GoogleApiClient mGoogleClient;
    private PlaceInfor mPlace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mgeodataclient = Places.getGeoDataClient(Map_api.this,null);
        setContentView(R.layout.activity_map_api);
        // anh xa cac xu kien cua 2 ben voi nhau
        //buttonSearchMap = (Button) findViewById(R.id.buttonSearchMap);
        edittextSearch = (AutoCompleteTextView) findViewById(R.id.edittextSearch);
        imageviewGPS = (ImageView) findViewById(R.id.imageviewGPS);

        getLoacationPermission();

    }

    private void init(){

        mGoogleClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        adapter= new PlaceAutocompleteAdapter(this,mGoogleClient,LAT_LNG_BOUNDS,null);
        edittextSearch.setAdapter(adapter);
        edittextSearch.setOnItemClickListener(mAutocompleteClick);
        edittextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_SEARCH||
                        actionId==EditorInfo.IME_ACTION_DONE||
                        event.getAction()==KeyEvent.ACTION_DOWN||
                        event.getAction()==KeyEvent.KEYCODE_ENTER){
                    // bat dau tiem kiem thong qua api
                    search();
                }
                return false;
            }
        });

        imageviewGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        hintKeybroard();
    }

    private void search(){
        String txtSearch= edittextSearch.getText().toString();
        Geocoder geocoder = new Geocoder(Map_api.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(txtSearch,1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(list.size()>0){
            Address address = list.get(0);
            // lay duoc dia diem
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),ZOOM,address.getAddressLine(0));

        }

    }

    private void getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(isCheck){
                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location curLocation=(Location) task.getResult();
                            moveCamera(new LatLng(curLocation.getLatitude(),curLocation.getLongitude()),ZOOM,"My Location");
                        }else{
                            Toast.makeText(Map_api.this,"not reponse...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch(SecurityException e){
            Log.e("Map_api",""+e.getMessage());
        }
    }



    private void iniMap(){
        final MapFragment mapFragment =(MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(Map_api.this);
    }


    private void getLoacationPermission(){
        String[] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permission[0])==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permission[1])
                    ==PackageManager.PERMISSION_GRANTED){
                    isCheck=true;
                    iniMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permission,
                        1234);
            }
        }else{
            ActivityCompat.requestPermissions(this,permission,1234);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        isCheck=false;
        switch (requestCode){
            case 1234:{
                if(grantResults.length>0){
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            isCheck=false;
                            return;
                        }
                    }
                    isCheck=true;
                    iniMap();
                }
            }
        }
    }

    // ham tat ban phim
    private void hintKeybroard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private AdapterView.OnItemClickListener mAutocompleteClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            hintKeybroard();

            final AutocompletePrediction item = adapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()){
                places.release();
                return;
            }

            final Place place = places.get(0);
            try{
                mPlace= new PlaceInfor();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setId(place.getId());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                mPlace.setLatLng(place.getLatLng());
                mPlace.setRating(place.getRating());
                mPlace.setAttributions(place.getAttributions().toString());

            }catch(NullPointerException e){
            }

            LatLng latLng = new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude);

            //myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,ZOOM));
            moveCamera(latLng, ZOOM,mPlace.getName());
        }
    };

    private void moveCamera(LatLng latLng,float zoom,String title){

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        if(!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().
                    position(latLng).
                    title(title);

            myMap.addMarker(options);
        }
        hintKeybroard();
    }

}
