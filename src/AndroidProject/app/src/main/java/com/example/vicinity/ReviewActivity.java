package com.example.vicinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    TextView noReviews;
    RecyclerView recyclerView;
    ArrayList<String> reviews;
    FirebaseFirestore db;
    EditText addReview;
    Button reviewSubmit;
    private String id;
    private String currentCollection;
    private ArrayList<String> collections;

    private static final String TAG = "ReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Log.d(TAG, "onCreate: Success");

        noReviews = findViewById(R.id.noReviews);

        db = FirebaseFirestore.getInstance();
        addReview = findViewById(R.id.ret);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        currentCollection = bundle.getString("currentCollection");
        collections = bundle.getStringArrayList("collections");

        getReviews(currentCollection);

        reviewSubmit = findViewById(R.id.reviewSubmit);
        reviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newReview = addReview.getText().toString();
                reviews.add(newReview);
                Log.d(TAG, "Updating " + currentCollection + " " + id);
                db.collection(currentCollection).document(id).update(FieldPath.of("tags", "review"), reviews);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void initRecylerView() {
        recyclerView = findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter adapter = new ReviewAdapter(this, reviews);
        recyclerView.setAdapter(adapter);
    }

    public void getReviews(String currentCollection) {
        Log.d(TAG, "XXF getting " + currentCollection + " " + id);
        db.collection(currentCollection).document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Log.d(TAG, "XXF Document exists " + id);
                                if (documentSnapshot.get(FieldPath.of("tags", "review")) != null) {
                                    reviews = (ArrayList<String>) documentSnapshot.get(FieldPath.of("tags", "review"));
                                    Log.d(TAG, "XXF Reviews " + reviews);
                                }
                            } else {
                                Log.d(TAG, "No Such Document");
                            }
                        }
                        if (!reviews.isEmpty()) {
                            initRecylerView();
                        } else {
                            noReviews.setText("No Current Reviews, Feel free to add some!");
                        }

                    }
                });
    }
}
