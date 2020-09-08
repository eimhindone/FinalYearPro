package com.example.vicinity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FilterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SearchFragment";
    FirebaseFirestore db;
    ArrayList<String> text = new ArrayList<>();
    private String eventName;
    private int eventDistance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        // Search button
        Button searchButton = view.findViewById(R.id.search_button);

        // filter array
        final ArrayList<String> filter = new ArrayList<>();

        // drop down menu for distance
        Spinner spinner1 = view.findViewById(R.id.spinner1);
        Integer[] items = new Integer[]{5,10,15,20,25,30,35,40,45,50};
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new DistanceSpinner());

        // drop down menu for event
        Spinner spinner2 = view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.collections, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new EventSpinner());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DisplayActivity.class);
                intent.putExtra("eventName", eventName);
                intent.putExtra("eventDistance", eventDistance);
                startActivity(intent);
                Log.d(TAG, "XXT Starting Display Activity");
            }
        });

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class DistanceSpinner implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            eventDistance = ((int) parent.getItemAtPosition(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class EventSpinner implements AdapterView.OnItemSelectedListener
    {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            eventName = (parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
