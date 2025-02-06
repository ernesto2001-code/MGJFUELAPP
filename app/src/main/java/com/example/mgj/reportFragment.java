package com.example.mgj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mgj.adapter.ReportAdapter;
import com.example.mgj.model.Report;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class reportFragment extends Fragment {

    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    FirebaseFirestore fStore;
    FloatingActionButton fabAdd;
    ImageButton Button;
    private int selectedTankId = R.id.all; // ID del ítem seleccionado en el menú

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        fStore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);
        Button = view.findViewById(R.id.menuButton);

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        fabAdd.setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), formReport.class);
            startActivity(i);
        });

        Button.setOnClickListener(v -> showPopupMenu());

        init("tanks"); // Iniciar mostrando todos los reportes
        return view;
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), Button, Gravity.START, 0, R.style.Popup_Menu);
        popupMenu.getMenuInflater().inflate(R.menu.tank_menu, popupMenu.getMenu());

        popupMenu.getMenu().findItem(selectedTankId).setChecked(true);

        popupMenu.setOnMenuItemClickListener(item -> {
            selectedTankId = item.getItemId(); // Guarda la selección
            item.setChecked(true); // Marca como seleccionado

            String selectedTank;
            if (item.getItemId() == R.id.all) {
                selectedTank = "tanks"; // Mostrar todos los reportes
            } else if (item.getItemId() == R.id.tanque1) {
                selectedTank = "tank1";
            } else if (item.getItemId() == R.id.tanque2) {
                selectedTank = "tank2";
            } else {
                return false;
            }

            updateQuery(selectedTank); // Actualiza la consulta en Firestore
            return true;
        });

        popupMenu.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init(String tankId) {
        Query query;
        if (tankId.equals("tanks")) {
            // Si se selecciona "Todos", obtener todos los reportes de todos los tanques
            query = fStore.collectionGroup("reports");
        } else {
            // Obtener solo los reportes del tanque seleccionado
            query = fStore.collection("tanks")
                    .document(tankId)
                    .collection("reports");
        }

        FirestoreRecyclerOptions<Report> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Report>()
                        .setQuery(query, Report.class)
                        .build();

        reportAdapter = new ReportAdapter(firestoreRecyclerOptions, getActivity());
        recyclerView.setAdapter(reportAdapter);

        reportAdapter.notifyDataSetChanged();
    }

    private void updateQuery(String tankId) {
        Query query;
        if (tankId.equals("tanks")) {
            query = fStore.collectionGroup("reports"); // Obtiene reportes de todos los tanques
        } else {
            query = fStore.collection("tanks")
                    .document(tankId)
                    .collection("reports");
        }

        FirestoreRecyclerOptions<Report> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Report>()
                        .setQuery(query, Report.class)
                        .build();

        reportAdapter.updateAdapterOptions(firestoreRecyclerOptions);// Actualiza la consulta del adaptador
    }

    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        reportAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        reportAdapter.stopListening();
    }
}