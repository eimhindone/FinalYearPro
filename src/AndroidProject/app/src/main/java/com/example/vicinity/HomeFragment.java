package com.example.vicinity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fonfon.geohash.GeoHash;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class HomeFragment extends Fragment implements RecylerViewAdapter.OnEventListener {

    private static final String TAG = "HomeFragment";
    private String uId;
    private String userName;
    private String similarUserId;
    private TextView nameTv;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private double myLat;
    private double myLon;
    private String myGeoHash;
    private String start;
    private String end;
    public int within_distance=10;

    private List<String> invalidCategories = Arrays.asList("competitive", "exercise", "family", "free", "friends", "friend", "individual", "mixedGroup", "over18", "paid", "ticketRequired");

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mTypes = new ArrayList<>();
    private ArrayList<String> mDistance = new ArrayList<>();
    private ArrayList<String> mID = new ArrayList<>();

    ArrayList<String> similarInterests = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        nameTv = view.findViewById(R.id.nameTv);
        Button refineBtn = view.findViewById(R.id.refineBtn);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uId = mAuth.getCurrentUser().getUid();

        db.collection("users").document(uId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            userName = documentSnapshot.get("name").toString();
                            nameTv.setText(userName);
                        }
                    }
                });



        refineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNames.clear();
                mAddress.clear();
                mTypes.clear();
                mDistance.clear();
                mID.clear();
                getSimilarUserInterests(uId);
            }
        });

        Spinner distanceSpinner = view.findViewById(R.id.recommenderSpinner);
        Integer[] items = new Integer[]{5,10,15,20,25,30,35,40,45,50};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(adapter);
        distanceSpinner.setOnItemSelectedListener(new RecDistanceSpinner());

        getSimilarUserInterests(uId);
        initRecyclerView(view);

        return view;
    }

    private void getSimilarUserInterests(final String uId) {

        db.collection("users").document(uId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.get("similar_users") != null) {
                                List<Map<String, Object>> similar_people = (List<Map<String, Object>>) documentSnapshot.get("similar_users");
                                Map<String, Object> most_similar = similar_people.get(0);
                                similarUserId = most_similar.get("id").toString();
                                Log.d(TAG, "XXT: User Found");
                                initSimilarInterests(similarUserId);
                            }
                            else {
                                initSimilarInterests(uId);
                            }
                        }
                        else {
                        }
                    }
                });
    }

    private void initSimilarInterests(String similarUserId) {

        Log.d(TAG, "XXT: Calling initSimilarInterests()");
        db.collection("users").document(similarUserId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            similarInterests = (ArrayList<String>) documentSnapshot.get("interests");
                            Log.d(TAG, "XXT: Retrieved Similar Interests: " + similarInterests);
                            search(similarInterests);
                        }
                    }
                });
    }

    private void search(final ArrayList<String> interest_list) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
//                            myLat = location.getLatitude();
//                            myLon = location.getLongitude();

                            myLat = 53.347897;
                            myLon = -6.259419;

                            Log.d(TAG, "myLat" + myLat + "myLon" + myLon);
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

                        }

                        for (String similar : interest_list) {
                            if (!invalidCategories.contains(similar)) {
                                CollectionReference similarColRef = db.collection(similar);
                                retrieveData(similarColRef, within_distance);
                            }
                        }

                    }
                });
    }

    private void initRecyclerView (View view){
        RecyclerView recyclerView = view.findViewById(R.id.recommenderRecylerView);
        RecylerViewAdapter adapter = new RecylerViewAdapter(getActivity(), mNames, mAddress, mTypes, mDistance, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void retrieveData(CollectionReference colRef, final int max_distance){
        Log.d(TAG, "XXT: Retrieving Data from: " + colRef);
        colRef.orderBy(FieldPath.of("geohash")).startAt(start).endAt(end).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.get("lon") != null && document.get("lat") != null) {
                            double lon = (double) document.get("lon");
                            double lat = (double) document.get("lat");

                            double dist = Math.round(GetDistance.getDistance(myLat, myLon, lat, lon));

                            if (dist <= max_distance) {
                                Log.d(TAG, "XXT: Found event adding to arrays");
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
                                    Log.d(TAG, "XXT: " + mTypes);
                                }
                                else if (document.get(FieldPath.of("tags", "historic")) != null){
                                    mTypes.add(document.get(FieldPath.of("tags", "historic")).toString());
                                    Log.d(TAG, "XXT: " + mTypes);
                                }
                                else if (document.get(FieldPath.of("tags", "leisure")) != null) {
                                    mTypes.add(document.get(FieldPath.of("tags", "leisure")).toString());
                                    Log.d(TAG, "XXT: " + mTypes);
                                }
                                else if (document.get(FieldPath.of("tags", "sport")) != null) {
                                    mTypes.add(document.get(FieldPath.of("tags", "sport")).toString());
                                    Log.d(TAG, "XXT: " + mTypes);
                                }
                                else if (document.get(FieldPath.of("tags", "tourism")) != null) {
                                    mTypes.add(document.get(FieldPath.of("tags", "tourism")).toString());
                                    Log.d(TAG, "XXT: " + mTypes);
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    initRecyclerView(view);
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
        for (String similar : similarInterests){
            if (!invalidCategories.contains(similar)){
                collections.add(similar);
            }
        }
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("collections", collections);
        startActivity(intent);
    }

    class RecDistanceSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            within_distance = ((int) parent.getItemAtPosition(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
