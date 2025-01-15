package com.example.mgj.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mgj.R;
import com.example.mgj.model.Tank;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TankAdapter extends FirestoreRecyclerAdapter<Tank, TankAdapter.ViewHolder> {
    FirebaseFirestore fStore;
    Activity activity;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TankAdapter(@NonNull FirestoreRecyclerOptions<Tank> options, Activity activity) {
        super(options);
        fStore = FirebaseFirestore.getInstance();
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position, @NonNull Tank tank) {
        viewHolder.name.setText(tank.getName());
        int currentLevel = tank.getCurrent_level();
        int maxCapacity = 22500;
        int capacityPercentage = (int) ((currentLevel / (double) maxCapacity) * 100);

        viewHolder.current_level.setText(String.valueOf(currentLevel));
        viewHolder.capacity.setText(String.format("%d%%", capacityPercentage));

        // Configura el click listener para el elemento
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclertankfragment, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, capacity, current_level;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tankTitle);
            capacity = itemView.findViewById(R.id.tankProcentaje);
            current_level = itemView.findViewById(R.id.tankCapacity);
        }
    }
}

