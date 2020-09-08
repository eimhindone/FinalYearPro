package com.example.vicinity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private static final String TAG = "RecylerViewAdapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mTypes = new ArrayList<>();
    private ArrayList<String> mDistance = new ArrayList<>();
    private Context mContext;
    private OnEventListener mOnEventListener;

    public RecylerViewAdapter(Context mContext, ArrayList<String> mNames, ArrayList<String> mAddress, ArrayList<String> mTypes, ArrayList<String> mDistance, OnEventListener mOnEventListener) {
        this.mNames = mNames;
        this.mAddress = mAddress;
        this.mTypes = mTypes;
        this.mDistance = mDistance;
        this.mContext = mContext;
        this.mOnEventListener = mOnEventListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        ViewHolder holder = new ViewHolder(view, mOnEventListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.customRowName.setText(mNames.get(position));
        holder.customRowAddress.setText(mAddress.get(position));
        holder.customRowType.setText(mTypes.get(position));
        holder.customRowDistance.setText("Dist: " + mDistance.get(position) + " Km");
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView customRowName, customRowAddress, customRowType, customRowDistance;
        OnEventListener onEventListener;

        public ViewHolder(@NonNull View itemView, OnEventListener onEventListener) {
            super(itemView);
            customRowName = itemView.findViewById(R.id.customRowName);
            customRowAddress = itemView.findViewById(R.id.customRowAddress);
            customRowType = itemView.findViewById(R.id.customRowType);
            customRowDistance = itemView.findViewById(R.id.customRowDistance);
            this.onEventListener = onEventListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener{
        void onEventClick(int position);
    }
}
