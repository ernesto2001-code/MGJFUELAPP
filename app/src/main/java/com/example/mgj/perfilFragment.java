package com.example.mgj;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class perfilFragment extends Fragment {
    View vista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("perfilFragment", "pitoe");
        vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        return vista;
    }

}
