package com.example.vicinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    private Button reviewBtn, mapsBtn;
    private FirebaseFirestore mFirestore;
    private ArrayList<String> collections;
    private TextView name, address;
    private String currentCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.detailsName);
        address = findViewById(R.id.detailsAddress);

        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("id");
        collections = (ArrayList<String>) getIntent().getSerializableExtra("collections");

        Log.d(TAG, "XXT Collection on create:" + collections);

        Log.d(TAG, "XXT: " + id);
        Log.d(TAG, "XXT: " + collections);

        mFirestore = FirebaseFirestore.getInstance();

        for (final String collection : collections) {
            mFirestore.collection(collection).document(id).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    currentCollection = collection;
                                    name.setText(Namify.namify(documentSnapshot.get(FieldPath.of("tags", "address")).toString()));
                                    address.setText(documentSnapshot.get(FieldPath.of("tags", "address")).toString());
                                }
                                else {
                                    Log.d(TAG, "No Such Document");
                                }
                            }
                        }
                    });
        }

        mapsBtn = findViewById(R.id.mapsBtn);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("currentCollection", currentCollection);
                startActivity(intent);
            }
        });

        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("currentCollection", currentCollection);
                intent.putExtra("collections", collections);
                startActivity(intent);
            }
        });

    }
}
