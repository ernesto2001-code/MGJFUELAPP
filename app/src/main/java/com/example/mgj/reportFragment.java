package com.example.mgj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
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
import android.widget.SearchView;

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
    SearchView searchview;
    private int selectedTankId = R.id.all;
    private final Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        fStore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);
        Button = view.findViewById(R.id.menuButton);
        searchview = view.findViewById(R.id.searchview);

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        fabAdd.setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), formReport.class);
            startActivity(i);
        });
        Button.setOnClickListener(v -> showPopupMenu());

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> filterReports(newText);
                searchHandler.postDelayed(searchRunnable, 500);
                return true;
            }
        });

        init("tanks");
        return view;
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), Button, Gravity.START, 0, R.style.Popup_Menu);
        popupMenu.getMenuInflater().inflate(R.menu.tank_menu, popupMenu.getMenu());
        popupMenu.getMenu().findItem(selectedTankId).setChecked(true);

        popupMenu.setOnMenuItemClickListener(item -> {
            selectedTankId = item.getItemId();
            item.setChecked(true);

            String selectedTank = (item.getItemId() == R.id.all) ? "tanks" : (item.getItemId() == R.id.tanque1) ? "tank1" : "tank2";
            updateQuery(selectedTank);
            return true;
        });

        popupMenu.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void init(String tankId) {
        Query query = tankId.equals("tanks") ? fStore.collectionGroup("reports") : fStore.collection("tanks").document(tankId).collection("reports");

        FirestoreRecyclerOptions<Report> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Report>()
                        .setQuery(query, Report.class)
                        .build();

        reportAdapter = new ReportAdapter(firestoreRecyclerOptions, getActivity());
        recyclerView.setAdapter(reportAdapter);
    }

    private void updateQuery(String tankId) {
        Query query = tankId.equals("tanks") ? fStore.collectionGroup("reports") : fStore.collection("tanks").document(tankId).collection("reports");

        FirestoreRecyclerOptions<Report> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Report>()
                        .setQuery(query, Report.class)
                        .build();

        reportAdapter.updateAdapterOptions(firestoreRecyclerOptions);
        reportAdapter.notifyDataSetChanged();
    }

    private void filterReports(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            updateQuery("tanks");
            return;
        }

        Query query = fStore.collectionGroup("reports")
                .whereArrayContains("searchkeywords", searchText.toLowerCase());

        FirestoreRecyclerOptions<Report> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Report>()
                        .setQuery(query, Report.class)
                        .build();

        reportAdapter.updateAdapterOptions(firestoreRecyclerOptions);
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
        if (reportAdapter != null) {
            reportAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (reportAdapter != null) {
            reportAdapter.stopListening();
        }
    }
}
