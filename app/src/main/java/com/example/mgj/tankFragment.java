package com.example.mgj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgj.adapter.TankAdapter;
import com.example.mgj.model.Tank;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class tankFragment extends Fragment {
    RecyclerView recyclerView;
    TankAdapter tankAdapter;
    FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tank, container, false);

        fStore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);


        // Configuración del RecyclerView
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //esto re remplaza por wl wrapcontentlinearlayoutmanager con esto no crasheo el app
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);


        init();
        return view;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void init() {
        Query query = fStore.collection("tanks");

        FirestoreRecyclerOptions<Tank> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Tank>()
                        .setQuery(query, Tank.class)
                        .build();

        tankAdapter = new TankAdapter(firestoreRecyclerOptions, getActivity());
        recyclerView.setAdapter(tankAdapter);

        // Configura el listener
        tankAdapter.setOnItemClickListener(new TankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Tank tank = documentSnapshot.toObject(Tank.class);
                if (tank != null) {
                    // Abre la actividad con más detalles
                    Intent intent = new Intent(getActivity(), DetailsTankActivity.class);
                    intent.putExtra("name", tank.getName());
                    startActivity(intent);
                }
            }
        });

        tankAdapter.notifyDataSetChanged();

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
        tankAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        tankAdapter.stopListening();
    }
}