package com.example.mgj;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;


import com.example.mgj.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityFragment extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
     perfilFragment perfilFragment = new perfilFragment();
     reportFragment reportFragment = new reportFragment();
     tankFragment tankFragment = new tankFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        Log.d("MainActivityFragment", "onCreate: Iniciando actividad principal");

        // Inicializa BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView == null) {
            Log.e("MainActivityFragment", "onCreate: BottomNavigationView no se encontró");
        } else {
            Log.d("MainActivityFragment", "onCreate: BottomNavigationView inicializado");
        }

        // Establece el fragmento inicial
        // Establece el fragmento inicial y selecciona el elemento correspondiente en BottomNavigationView
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, tankFragment)
                .commit();
        bottomNavigationView.setSelectedItemId(R.id.tank); // Selecciona el botón de tanques

        Log.d("MainActivityFragment", "onCreate: Fragmento inicial (tankFragment) cargado");

        // Configura el listener para las selecciones
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            if (item.getItemId() == R.id.report) {
                fragment = new reportFragment();
            } else if (item.getItemId() == R.id.tank) {
                fragment = new tankFragment(); // Aquí debería ser `tankFragment`
            } else if (item.getItemId() == R.id.perfil) {
                fragment = new perfilFragment(); // Aquí debería ser `perfilFragment`
            } else {
                return false;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }
}