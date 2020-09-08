package com.example.vicinity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import androidx.annotation.NonNull;

import static org.junit.Assert.*;

public class DisplayActivityTest {

    @Test
    public void retrieveData() throws Exception {

        FirebaseFirestore mockDb = Mockito.mock(FirebaseFirestore.class);
        DocumentReference eventType = mockDb.collection("testing").document("retrieveDataTest");

        final String expectedName = "Test Name";
        final String expectedAddress = "Test Address";

        eventType.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            String actualName = doc.get("name").toString();
                            String actualAddress = doc.get("address").toString();

                            assertEquals(expectedName, actualName);
                            assertEquals(expectedAddress, actualAddress);
                        }
                    }
                });

    }
}