package com.example.vicinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CtgrFilterActivity extends AppCompatActivity {

    private static final String TAG = "CtgrFilterActivity";
    private String category;
    private ArrayList<String> targetCategories = new ArrayList<>();
    FirebaseFirestore db;
    private String[] collections;
    private String ctgEventName;
    private int ctgEventDistance;
    private Button ctgFilterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctgr_filter);

        db = FirebaseFirestore.getInstance();
        ctgFilterBtn = findViewById(R.id.ctgFilterBtn);

        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("category");

        collections = getResources().getStringArray(R.array.collections);

        for (final String collection : collections) {
            db.collection(collection).document("catagory").get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                Spinner spinner1 = findViewById(R.id.ctgSpinner1);
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, targetCategories);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter1);
                                spinner1.setOnItemSelectedListener(new CtgSpinner());
                                DocumentSnapshot document = task.getResult();
                                if (document.get(FieldPath.of(category)).equals("t")){
                                    Log.d(TAG, "XXT One matched category");
                                    targetCategories.add(collection);
                                    adapter1.notifyDataSetChanged();
                                }
                                else {
                                    Log.d(TAG, "No matched category on current iteration");
                                }
                            }
                            else {
                                Log.d(TAG, "Get document failed " + task.getException());
                            }
                        }
                    });
        }

        Spinner spinner2 = findViewById(R.id.ctgSpinner2);
        Integer[] items = new Integer[]{5,10,20,30,40};
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new CtgDistanceSpinner());

        ctgFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                intent.putExtra("eventName", ctgEventName);
                intent.putExtra("eventDistance", ctgEventDistance);
                startActivity(intent);
            }
        });
    }


    class CtgSpinner implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ctgEventName = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class CtgDistanceSpinner implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            ctgEventDistance = ((int) parent.getItemAtPosition(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
