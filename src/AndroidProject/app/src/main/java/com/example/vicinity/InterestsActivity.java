package com.example.vicinity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestsActivity extends AppCompatActivity {

    private static final String TAG = "InterestsActivity";
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    String uID;
    Button submitBtn;
    CardView competitiveBtn, exerciseBtn, familyBtn,
             freeBtn, friendBtn, individualBtn, mixedGroupBtn,
             over18Btn, paidBtn, ticketBtn;
    ArrayList<String> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        competitiveBtn = findViewById(R.id.competitiveBtn);
        exerciseBtn = findViewById(R.id.exerciseBtn);
        familyBtn = findViewById(R.id.familyBtn);
        freeBtn = findViewById(R.id.freeBtn);
        friendBtn = findViewById(R.id.friendBtn);
        individualBtn = findViewById(R.id.individualBtn);
        mixedGroupBtn = findViewById(R.id.mixedGroupBtn);
        over18Btn = findViewById(R.id.over18Btn);
        paidBtn = findViewById(R.id.paidBtn);
        ticketBtn = findViewById(R.id.ticketBtn);
        submitBtn = findViewById(R.id.submitBtn);


        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        competitiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("competitive")){
                    interests.add("competitive");
                    competitiveBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("competitive");
                    competitiveBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("exercise")){
                    interests.add("exercise");
                    exerciseBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("exercise");
                    exerciseBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        familyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("family")){
                    interests.add("family");
                    familyBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("family");
                    familyBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        freeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("free")){
                    interests.add("free");
                    freeBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("free");
                    freeBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("friends")){
                    interests.add("friends");
                    friendBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("friends");
                    friendBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        individualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("individual")){
                    interests.add("individual");
                    individualBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("individual");
                    individualBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        mixedGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("mixedGroup")){
                    interests.add("mixedGroup");
                    mixedGroupBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("mixedGroup");
                    mixedGroupBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        over18Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("over18")){
                    interests.add("over18");
                    over18Btn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("over18");
                    over18Btn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        paidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("paid")){
                    interests.add("paid");
                    paidBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("paid");
                    paidBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        ticketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!interests.contains("ticket")){
                    interests.add("ticket");
                    ticketBtn.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    interests.remove("ticket");
                    ticketBtn.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubInterestsActivity.class);
                intent.putExtra("interests", interests);
                startActivity(intent);
            }
        });
    }
}
