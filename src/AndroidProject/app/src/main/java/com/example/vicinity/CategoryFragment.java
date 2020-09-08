package com.example.vicinity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    FirebaseFirestore db;
    String category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        db = FirebaseFirestore.getInstance();
        CardView card1 = view.findViewById(R.id.CompetitiveFilter);
        CardView card2 = view.findViewById(R.id.ExerciseFilter);
        CardView card3 = view.findViewById(R.id.FamilyFilter);
        CardView card4 = view.findViewById(R.id.FreeFilter);
        CardView card5 = view.findViewById(R.id.FriendFilter);
        CardView card6 = view.findViewById(R.id.IndividualFilter);
        CardView card7 = view.findViewById(R.id.MixedGroupFilter);
        CardView card8 = view.findViewById(R.id.Over18Filter);
        CardView card9 = view.findViewById(R.id.PaidFilter);
        CardView card10 = view.findViewById(R.id.TicketFilter);


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "competetive";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "exercise";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "family";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "free";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "friends";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "individual";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "mixedGroup";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "over18";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "paid";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "ticketRequired";
                Intent intent = new Intent(getActivity(), CtgrFilterActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        return view;
    }
}
