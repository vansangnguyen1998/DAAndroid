package com.example.pc.daandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private BottomNavigationView bottomNavigationViewNearby;
    private Marker makerSeter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_api);
        // anh xa cac xu kien cua 2 ben voi nhau
        edittextSearch = (AutoCompleteTextView) findViewById(R.id.edittextSearch);
        imageviewGPS = (ImageView) findViewById(R.id.imageviewGPS);

        bottomNavigationViewNearby = (BottomNavigationView) findViewById(R.id.bottomNavigationViewNearby);
        bottomNavigationViewNearby.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.atm:
                        ShowNearby(new LatLng(10.865971, 106.788353),"atm"); // truyen vao chữ thường tiếng anh
                }
            }
        });

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
                    String txtSearch= edittextSearch.getText().toString();
                    search(txtSearch);
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

    private void search(String txtSearch){
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
                            // set location bi loi o 1 so thiet bi nen chuyen sang set co dinh 1 dia diem
                            //moveCamera(new LatLng(curLocation.getLatitude(),curLocation.getLongitude()),ZOOM,"My Location");
                            moveCamera(new LatLng(10.865985,106.788356),ZOOM,"My Location");
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
            moveCamera(latLng, ZOOM,mPlace.getName());
        }
    };


    private void clearCamera()
    {
        myMap.clear();
    }

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


    private void ShowNearby(LatLng latLng, String typePlace){

        moveCamera(latLng,15f,"");

        String LocationPlace = String.valueOf(latLng.latitude)+ "," +String.valueOf(latLng.longitude);
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+LocationPlace+"&radius=5000&type="+typePlace+"&sensor=true&key=AIzaSyAxbKrTQ39T9eoq_YWrlyTkTwN0Dp1M--A";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // du lieu nhan ve dang string
                        // cac du lieu lan can nam trong nay
                        ArrayList<NearbyPlace> data = (ArrayList<NearbyPlace>) DataParse(response);
                        DisplayNearbtPlace(data);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(ActivityDiaDiemChiTiet.this,""+error+"",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }

    private List<NearbyPlace> DataParse(String response){
        ArrayList<NearbyPlace> data=new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(response);

            JSONArray result = jsonObject.getJSONArray("results");
            int len = result.length();
            for(int i=0;i<len;i++){
                JSONObject json = (JSONObject) result.get(i);
                String Name= json.getString("name");
                String Vicinity=json.getString("vicinity");
                String reference = json.getString("reference");

                Double lat=json.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                Double lng =json.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                NearbyPlace nearbyPlace = new NearbyPlace(Name,Vicinity,reference,lat,lng);
                data.add(nearbyPlace);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void DisplayNearbtPlace(List<NearbyPlace> data){
        for(int i=1;i<data.size();i++){
            ///MarkerOptions markerOptions = new MarkerOptions();
            String Name = data.get(i).getName();
            String Vicinity = data.get(i).getVicinity();
            String Reference = data.get(i).getReference();

            Double lat=data.get(i).getLat();
            Double lng=data.get(i).getLng();

            moveCamera(new LatLng(lat,lng),ZOOM,Name+Vicinity);

        }
        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                 //xoa het cac vi tri khac
                clearCamera();
                String locationClick = marker.getTitle();

                search(locationClick);

                //moveCamera();

                return true;
            }
        });
    }
    // class luu du lieu
    private class NearbyPlace{
        String Name;
        String Vicinity;
        String Reference;
        Double lat,lng;

        public NearbyPlace(String name, String vicinity, String reference, Double lat, Double lng) {
            Name = name;
            Vicinity = vicinity;
            Reference = reference;
            this.lat = lat;
            this.lng = lng;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getVicinity() {
            return Vicinity;
        }

        public void setVicinity(String vicinity) {
            Vicinity = vicinity;
        }

        public String getReference() {
            return Reference;
        }

        public void setReference(String reference) {
            Reference = reference;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
    }

}
