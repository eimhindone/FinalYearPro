package com.example.vicinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.AEADBadTagException;

public class SubInterestsActivity extends AppCompatActivity {

    private static final String TAG = "SubInterestsActivity";
    private ArrayList<String> new_interests = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> main_interests;
    FirebaseFirestore db;
    private String[] collections;
    int check = 0;
    String uId;
    FirebaseAuth mAuth;
    private ArrayList<String> mNumbers = new ArrayList<>();
    private ArrayList<String> mInterests = new ArrayList<>();
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_interests);

        number = 1;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uId = mAuth.getCurrentUser().getUid();

        main_interests = (ArrayList<String>) getIntent().getSerializableExtra("interests");

        collections = getResources().getStringArray(R.array.collections);

        for (final String collection : collections) {
            for (final String interest : main_interests) {
                db.collection(collection).document("catagory").get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Spinner spinner = findViewById(R.id.SubInterestSpinner);
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner.setAdapter(adapter1);
                                    spinner.setSelection(0,false);
                                    spinner.setOnItemSelectedListener(new SubInterestSpinner());
                                    DocumentSnapshot document = task.getResult();
                                    if (document.get(FieldPath.of(interest)) != null) {
                                        if (document.get(FieldPath.of(interest)).equals("t")) {
                                            if (!categories.contains(collection)){
                                                categories.add(collection);
                                                adapter1.notifyDataSetChanged();
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "No matched category on current iteration");
                                    }
                                } else {
                                    Log.d(TAG, "Get document failed " + task.getException());
                                }
                            }
                        });
            }

            Button nextPage = findViewById(R.id.nextPageBtn);
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference docRef = db.collection("users").document(uId);
                    Map<String, Object> interestList = new HashMap<>();
                    main_interests.addAll(new_interests);
                    interestList.put("interests", main_interests);
                    docRef.update(interestList);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void initRecyclerView() {
        RecyclerView interestRecyclerView = findViewById(R.id.interestRecyclerView);
        InterestAdapter adapter = new InterestAdapter(this, mNumbers, mInterests);
        interestRecyclerView.setAdapter(adapter);
        interestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class SubInterestSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Add to list
            if (++check > 2) {
                new_interests.add(parent.getItemAtPosition(position).toString());
                mInterests.add(parent.getItemAtPosition(position).toString());
                mNumbers.add(String.valueOf(number));
                number++;
                initRecyclerView();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
