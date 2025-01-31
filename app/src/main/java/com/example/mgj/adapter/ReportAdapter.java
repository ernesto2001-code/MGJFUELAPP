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
import com.example.mgj.model.Report;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReportAdapter extends FirestoreRecyclerAdapter<Report, ReportAdapter.ViewHolder> {
    FirebaseFirestore fStore;
    Activity activity;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ReportAdapter(@NonNull FirestoreRecyclerOptions<Report> options, Activity activity) {
        super(options);
        fStore = FirebaseFirestore.getInstance();
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position, @NonNull Report report) {
        viewHolder.fuel.setText(report.getLiters_filled());
        viewHolder.plate.setText(report.getLicense_plate());
        viewHolder.operador.setText(report.getOperator_name());
        viewHolder.mgj.setText(report.getUnidad());
        viewHolder.folio.setText(report.getFolio());
        // Formatear el timestamp a una fecha legible
        if (report.getTimestamp() != null) {
            // Convertir el timestamp a una fecha legible
            @SuppressLint("SimpleDateFormat")
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
            String formattedDate = dateFormat.format(report.getTimestamp().toDate());
            viewHolder.date.setText(formattedDate);
        } else {
            viewHolder.date.setText("Sin fecha"); // Si el timestamp es nulo
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclereportfragment, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fuel, plate, operador,mgj, date , folio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fuel = itemView.findViewById(R.id.text_fuel);
            plate = itemView.findViewById(R.id.text_plate);
            operador = itemView.findViewById(R.id.text_operador);
            date = itemView.findViewById(R.id.text_date);
            folio = itemView.findViewById(R.id.txtfolionumber);
            mgj = itemView.findViewById(R.id.text_mgj);
        }
    }
}