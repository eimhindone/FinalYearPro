package com.example.vicinity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestViewHolder> {

    private ArrayList<String> mNumbers = new ArrayList<>();
    private  ArrayList<String> mInterests = new ArrayList<>();
    private Context mContext;

    public InterestAdapter(Context mContext, ArrayList<String> mNumbers, ArrayList<String> mInterests) {
        this.mNumbers = mNumbers;
        this.mInterests = mInterests;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_layout, parent, false);
        InterestViewHolder interestViewHolder = new InterestViewHolder(view);
        return interestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        holder.rowNumber.setText(mNumbers.get(position));
        holder.rowInterest.setText(mInterests.get(position));
    }

    @Override
    public int getItemCount() {
        return mInterests.size();
    }

    public class InterestViewHolder extends RecyclerView.ViewHolder{

        TextView rowNumber, rowInterest;

        public InterestViewHolder(@NonNull View itemView){
            super(itemView);
            rowNumber = itemView.findViewById(R.id.number);
            rowInterest = itemView.findViewById(R.id.rowInterest);
        }
    }

}
