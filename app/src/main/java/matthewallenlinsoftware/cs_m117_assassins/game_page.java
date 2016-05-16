package matthewallenlinsoftware.cs_m117_assassins;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class game_page extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap; //Might be null if Google Play services APK is not available
    Button killButton;
    TextView targetLabel;
    LocationManager locationManager;
    String provider;
    public String [] active_users = {"","","","","",""};
    public long Occupied;
    Firebase myFirebaseRef;
    double target_lng = -1;
    double target_lat = -1;
    public int targetNumber = -1;


    LatLng lastEnemyLocation = new LatLng(34.0689, -118.4451);
    LatLng lastUserLocation = new LatLng(34.0689, -118.4452);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        killButton = (Button) findViewById(R.id.kill_button);
        targetLabel = (TextView) findViewById(R.id.game_target);
        killButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastEnemyLocation == lastUserLocation){

                }
            }
        });

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://assassinsm1117.firebaseio.com/");
        myFirebaseRef.child("Lobby").child("User1").child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                active_users[0] = (String) snapshot.getValue();
                myFirebaseRef.child("Lobby").child("User2").child("Name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        active_users[1] = (String) snapshot.getValue();
                        myFirebaseRef.child("Lobby").child("User3").child("Name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                active_users[2] = (String) snapshot.getValue();
                                myFirebaseRef.child("Lobby").child("User4").child("Name").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        active_users[3] = (String) snapshot.getValue();
                                        myFirebaseRef.child("Lobby").child("User5").child("Name").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                active_users[4] = (String) snapshot.getValue();
                                                myFirebaseRef.child("Lobby").child("User6").child("Name").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot snapshot) {
                                                        active_users[5] = (String) snapshot.getValue();
                                                        myFirebaseRef.child("Lobby").child("Occupied").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot snapshot) {
                                                                Occupied = (long) snapshot.getValue();
                                                                int usernumber = PreferenceManager.getDefaultSharedPreferences(game_page.this).getInt("UserNumber", -1);
                                                                boolean gameover = false;
                                                                for(long i = (usernumber+1)%Occupied; i == usernumber; i= (i+1)%Occupied){
                                                                    if (active_users[(int)i] == ""){
                                                                        continue;
                                                                    }
                                                                    else if(active_users[(int)i] != ""){
                                                                        if(usernumber == i){
                                                                            //game over
                                                                            gameover = true;
                                                                            break;
                                                                        }
                                                                        else {
                                                                            targetNumber = (int) i;
                                                                            gameover = false;
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                                if (gameover){
                                                                    showAlertDialogue("Game has ended");
                                                                    myFirebaseRef.child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User1").child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User1").child("Lat").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User1").child("Long").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User1").child("Name").setValue("");

                                                                    myFirebaseRef.child("Lobby").child("User2").child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User2").child("Lat").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User2").child("Long").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User2").child("Name").setValue("");

                                                                    myFirebaseRef.child("Lobby").child("User3").child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User3").child("Lat").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User3").child("Long").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User3").child("Name").setValue("");

                                                                    myFirebaseRef.child("Lobby").child("User4").child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User4").child("Lat").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User4").child("Long").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User4").child("Name").setValue("");

                                                                    myFirebaseRef.child("Lobby").child("User5").child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User5").child("Lat").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User5").child("Long").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User5").child("Name").setValue("");

                                                                    myFirebaseRef.child("Lobby").child("User6").child("Active").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User6").child("Lat").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User6").child("Long").setValue(0);
                                                                    myFirebaseRef.child("Lobby").child("User6").child("Name").setValue("");

                                                                    Intent startMain = new Intent(getApplicationContext(), selection_page.class);
                                                                    startMain.addCategory(Intent.CATEGORY_HOME);
                                                                    startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(startMain);
                                                                }
                                                                else {
                                                                    //set target
                                                                    if(targetNumber != Occupied){
                                                                        targetLabel.setText(active_users[targetNumber]);
                                                                        myFirebaseRef.child("Lobby").child("User" + (targetNumber + 1)).child("Lat").addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot snapshot) {
                                                                                System.out.println("LAT");
                                                                                System.out.println(snapshot.getValue());
                                                                                System.out.println("LAT");
                                                                                target_lat = (double) snapshot.getValue();
                                                                                myFirebaseRef.child("Lobby").child("User" + (targetNumber + 1)).child("Long").addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(DataSnapshot snapshot) {
                                                                                        target_lng = (double) snapshot.getValue();
                                                                                        LatLng latLng = new LatLng(target_lat, target_lng);
                                                                                        mMap.clear();
                                                                                        setMarker(lastUserLocation);
                                                                                        setEnemyMarker(latLng);
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(FirebaseError error) {
                                                                                    }
                                                                                });
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(FirebaseError error) {
                                                                            }
                                                                        });
                                                                    }
                                                                    else {
                                                                        targetLabel.setText(active_users[0]);
                                                                        myFirebaseRef.child("Lobby").child("User1").child("Lat").addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot snapshot) {
                                                                                System.out.println("LAT");
                                                                                System.out.println(snapshot.getValue());
                                                                                System.out.println("LAT");
                                                                                target_lat = (double) snapshot.getValue();
                                                                                myFirebaseRef.child("Lobby").child("User1").child("Long").addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(DataSnapshot snapshot) {
                                                                                        target_lng = (double) snapshot.getValue();
                                                                                        LatLng latLng = new LatLng(target_lat, target_lng);
                                                                                        mMap.clear();
                                                                                        setMarker(lastUserLocation);
                                                                                        setEnemyMarker(latLng);
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(FirebaseError error) {
                                                                                    }
                                                                                });
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(FirebaseError error) {
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(FirebaseError error) {
                                                            }
                                                        });
                                                    }
                                                    @Override
                                                    public void onCancelled(FirebaseError error) {}
                                                });
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError error) {
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError error) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(FirebaseError error) {}
                        });
                    }
                    @Override
                    public void onCancelled(FirebaseError error) {}
                });
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //2nd parameter: Check at a minimum of 15 second intervals
        //3rd parameter: Check 3 meters (10 feet) away
        locationManager.requestLocationUpdates(provider, 15000, 3, this);
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
    }

    //Used to scale the icons
    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public void setMarker(LatLng latLng){
        Double lat = latLng.latitude;
        Double lng = latLng.longitude;

        int usernumber = PreferenceManager.getDefaultSharedPreferences(this).getInt("UserNumber", -1);
        myFirebaseRef.child("Lobby").child("User"+usernumber).child("Lat").setValue(lat);
        myFirebaseRef.child("Lobby").child("User"+usernumber).child("Long").setValue(lng);


        Log.i("Latitude", lat.toString());
        Log.i("Longitude", lng.toString());

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng))
                .title("Your Location")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("user_marker", 100, 100))); //Resizes the icon to look good
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));    //Zooms in pretty deep into the map
        lastUserLocation = latLng;
    }

    public void setEnemyMarker(LatLng latLng){
        Double lat = latLng.latitude;
        Double lng = latLng.longitude;

        Log.i("Latitude", lat.toString());
        Log.i("Longitude", lng.toString());

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng))
                .title("Target Location")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("enemy_marker", 100, 100))); //Resizes the icon to look good
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));    //Zooms in pretty deep into the map
        lastEnemyLocation = latLng;
    }


    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        setEnemyMarker(lastEnemyLocation);
        setMarker(latLng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void showAlertDialogue(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Invalid");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
