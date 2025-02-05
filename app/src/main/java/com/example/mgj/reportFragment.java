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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class reportFragment extends Fragment {

    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    FirebaseFirestore fStore;
    FloatingActionButton fabAdd;
    ImageButton Button;
    private int selectedTankId = R.id.all; // Guardará el ID del ítem seleccionado


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        fStore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);
        Button = view.findViewById(R.id.menuButton);


        // Configuración del RecyclerView
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //esto re remplaza por wl wrapcontentlinearlayoutmanager con esto no crasheo el app
        recyclerView.setLayoutManager(new reportFragment.WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), formReport.class);
                startActivity(i);

            }
        });
        Button.setOnClickListener(v -> showPopupMenu());


        init();
        return view;
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), Button, Gravity.START, 0, R.style.Popup_Menu);
        popupMenu.getMenuInflater().inflate(R.menu.tank_menu, popupMenu.getMenu());

        // Mantener el ítem seleccionado
        popupMenu.getMenu().findItem(selectedTankId).setChecked(true);

        popupMenu.setOnMenuItemClickListener(item -> {
            selectedTankId = item.getItemId(); // Guarda la selección
            item.setChecked(true); // Marca como seleccionado

            switch (item.getItemId()) {
                case R.id.all:
                    Toast.makeText(getActivity(), "Todos los tanques seleccionados", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.tanque1:
                    Toast.makeText(getActivity(), "Tanque 1 seleccionado", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.tanque2:
                    Toast.makeText(getActivity(), "Tanque 2 seleccionado", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init() {
        // Acceder a la subcolección "reports" dentro de "tank1" en "tanks"
        String tankId = "tank1"; // ID dinámico
            Query query = fStore.collection("tanks")
                .document(tankId)
                .collection("reports");



        FirestoreRecyclerOptions<Report> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Report>()
                        .setQuery(query, Report.class)
                        .build();

        reportAdapter = new ReportAdapter(firestoreRecyclerOptions, getActivity());
        recyclerView.setAdapter(reportAdapter);

        // Configura el listener
        reportAdapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

            }
        });


        reportAdapter.notifyDataSetChanged();

    }
    public void PopupMenu(@NonNull Context context, @NonNull View anchor, int gravity,
                     @AttrRes int popupStyleAttr, @StyleRes int popupStyleRes){

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