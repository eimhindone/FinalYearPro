package com.example.vicinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.fonfon.geohash.GeoHash;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayActivity extends AppCompatActivity implements RecylerViewAdapter.OnEventListener {
    private static final String TAG = "DisplayActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mTypes = new ArrayList<>();
    private ArrayList<String> mDistance = new ArrayList<>();
    private ArrayList<String> mID = new ArrayList<>();

    FirebaseFirestore db;
    double myLat;
    double myLon;
    String myGeoHash;
    String start;
    String end;
    String eventName;
    int eventDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        Bundle bundle = getIntent().getExtras();
        eventName = bundle.getString("eventName").toLowerCase();
        eventDistance = (int) bundle.get("eventDistance");

        doASearch(eventName);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecylerViewAdapter adapter = new RecylerViewAdapter(this, mNames, mAddress, mTypes, mDistance, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doASearch(String eventName) {

        db = FirebaseFirestore.getInstance();
        final CollectionReference eventType = db.collection(eventName.toLowerCase());

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
//                            myLat = location.getLatitude();
//                            myLon = location.getLongitude();

                            myLat = 53.347897;
                            myLon = -6.259419;

                            // get current location hashed
                            Location loc = new Location("geohash");
                            loc.setLatitude(myLat);
                            loc.setLongitude(myLon);
                            GeoHash hash = GeoHash.fromLocation(loc, 9);
                            myGeoHash = hash.toString();

                            Log.d(TAG, "XXT My Geohash: " + myGeoHash);
                            start = myGeoHash.substring(0, 3);
                            Log.d(TAG, "XXT start: " + start);
                            end = start + "~";

                            retrieveData(eventType);

                        }
                    }
                });
    }

    public void retrieveData(CollectionReference eventType) {
        eventType.orderBy(FieldPath.of("geohash")).startAt(start).endAt(end).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.get("lon") != null && document.get("lat") != null) {
                            double lon = (double) document.get("lon");
                            double lat = (double) document.get("lat");

                            double dist = Math.round(GetDistance.getDistance(myLat, myLon, lat, lon));

                            if (dist < eventDistance) {
                                if (document.get(FieldPath.of("tags", "address")) != null) {
                                    mNames.add(Namify.namify(document.get(FieldPath.of("tags", "address")).toString()));
                                }
                                else {
                                    mNames.add("Unknown");
                                }
                                if (document.get(FieldPath.of("tags", "address")) != null) {
                                    mAddress.add(document.get(FieldPath.of("tags", "address")).toString());
                                }
                                else mAddress.add("Unknown");

                                mDistance.add(String.valueOf(dist));
                                mID.add(document.get("id").toString());

                                if (document.get(FieldPath.of("tags", "amenity")) != null) {
                                    mTypes.add(document.get(FieldPath.of("tags", "amenity")).toString());
                                }
                                else if (document.get(FieldPath.of("tags", "historic")) != null){
                                    mTypes.add(document.get(FieldPath.of("tags", "historic")).toString());
                                }
                                else if (document.get(FieldPath.of("tags", "leisure")) != null) {
                                    mTypes.add(document.get(FieldPath.of("tags", "leisure")).toString());
                                }
                                else if (document.get(FieldPath.of("tags", "sport")) != null) {
                                    mTypes.add(document.get(FieldPath.of("tags", "sport")).toString());
                                }

                            } else {
                                continue;
                            }
                        }
                    }
                    initRecyclerView();
                } else {
                    Log.d(TAG, "Failed to get documents", task.getException());
                }
            }
        });
    }

    @Override
    public void onEventClick(int position) {
        String id = mID.get(position);
        ArrayList<String> collections = new ArrayList<>();
        collections.add(eventName);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("collections", collections);
        startActivity(intent);
    }
}
