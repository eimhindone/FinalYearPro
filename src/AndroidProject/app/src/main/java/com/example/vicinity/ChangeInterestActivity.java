package com.example.vicinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeInterestActivity extends AppCompatActivity {

    private static final String TAG = "ChangeInterestActivity";
    CardView updCompet, updExer, updFam, updFree, updFren, updInd, updMix, updOver, updPaid, updTick;
    private List<String> validCategories = Arrays.asList("competitive", "exercise", "family", "free", "friends", "friend", "individual", "mixedGroup", "over18", "paid", "ticketRequired");
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String uId;
    ArrayList<String> allUserInterests = new ArrayList<>();
    ArrayList<String> validUserInterests = new ArrayList<>();
    Button updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_interest);

        updCompet = findViewById(R.id.updCompet);
        updExer = findViewById(R.id.updExer);
        updFam = findViewById(R.id.updFam);
        updFree = findViewById(R.id.updFree);
        updFren = findViewById(R.id.updFren);
        updInd = findViewById(R.id.updInd);
        updMix = findViewById(R.id.updMix);
        updOver = findViewById(R.id.updOver);
        updPaid = findViewById(R.id.updPaid);
        updTick = findViewById(R.id.updTick);
        updateBtn = findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubInterestsActivity.class);
                intent.putExtra("interests", validUserInterests);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uId = mAuth.getCurrentUser().getUid();

        DocumentReference userRef = db.collection("users").document(uId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    allUserInterests = (ArrayList<String>) documentSnapshot.get("interests");
                    for (String interest : allUserInterests){
                        if (validCategories.contains(interest)){
                            validUserInterests.add(interest);
                        }
                    }
                    for (String validInterest : validUserInterests) {
                        if (validInterest.equals("competitive")){
                            updCompet.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("exercise")){
                            updExer.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("family")){
                            updFam.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("friend") || validInterest.equals("friends")){
                            updFren.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("individual")){
                            updInd.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("mixedGroup")){
                            updMix.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("Over18")){
                            updOver.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("paid")){
                            updPaid.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("ticketRequired")){
                            updTick.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                        if (validInterest.equals("free")){
                            updFree.setBackgroundColor(getColor(R.color.colorAccent));
                        }
                    }
                }
            }
        });





        updCompet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("competitive")){
                    validUserInterests.add("competitive");
                    updCompet.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("competitive");
                    updCompet.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updExer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("exercise")){
                    validUserInterests.add("exercise");
                    updExer.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("exercise");
                    updExer.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updFam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("family")){
                    validUserInterests.add("family");
                    updFam.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("family");
                    updFam.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("free")){
                    validUserInterests.add("free");
                    updFree.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("free");
                    updFree.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updFren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("friends")){
                    validUserInterests.add("friends");
                    updFren.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("friends");
                    updFren.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("individual")){
                    validUserInterests.add("individual");
                    updInd.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("individual");
                    updInd.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updMix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("mixedGroup")){
                    validUserInterests.add("mixedGroup");
                    updMix.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("mixedGroup");
                    updMix.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("over18")){
                    validUserInterests.add("over18");
                    updOver.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("over18");
                    updOver.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("paid")){
                    validUserInterests.add("paid");
                    updPaid.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("paid");
                    updPaid.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });

        updTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUserInterests.contains("ticket")){
                    validUserInterests.add("ticket");
                    updTick.setBackgroundColor(getColor(R.color.colorAccent));
                }
                else {
                    validUserInterests.remove("ticket");
                    updTick.setBackgroundColor(getColor(R.color.cardview_light_background));
                }
            }
        });
    }
}
