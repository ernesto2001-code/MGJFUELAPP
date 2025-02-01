package com.example.mgj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgj.adapter.ReportAdapter;
import com.example.mgj.model.Report;
import com.example.mgj.model.Tank;
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        fStore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);


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


        init();
        return view;
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