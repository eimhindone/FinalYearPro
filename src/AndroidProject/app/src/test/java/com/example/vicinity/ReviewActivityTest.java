package com.example.vicinity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import androidx.annotation.NonNull;

import static org.junit.Assert.*;

public class ReviewActivityTest {

    @Test
    public void getReviews() throws Exception {
        FirebaseFirestore mockDb = Mockito.mock(FirebaseFirestore.class);

        String currentCollection = "testing";
        final ArrayList<String> expectedReviews = new ArrayList<>();
        expectedReviews.add("test good");
        expectedReviews.add("test great");
        expectedReviews.add("test amazing");

        mockDb.collection(currentCollection).document("getReviewsTest").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            ArrayList<String> actualReviews = (ArrayList<String>) doc.get("reviews");

                            assertEquals(expectedReviews, actualReviews);
                        }
                    }
                });


    }
}