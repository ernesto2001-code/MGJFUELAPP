package com.example.mgj;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsReport extends AppCompatActivity {
    FirebaseFirestore fStore;
    TextView litros, folio, departede, nombrecargo, nummgj, operador, placas, empresa, cargo;
    String idacargo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_report);
        litros = findViewById(R.id.txt_liters);
        folio = findViewById(R.id.txt_folio);
        departede = findViewById(R.id.txt_departede);
        nombrecargo = findViewById(R.id.txt_name);
        nummgj = findViewById(R.id.txt_numberunidad);
        operador = findViewById(R.id.txt_operatorname);
        placas = findViewById(R.id.txt_numeroplacas);
        empresa = findViewById(R.id.txt_empresa);
        cargo = findViewById(R.id.txt_cargo);

        fStore = FirebaseFirestore.getInstance();
        // Obtén los datos del Intent
        final String litrostexto = getIntent().getStringExtra("litros");
        final String foliotexto = getIntent().getStringExtra("folio");
        final String departedetexto = getIntent().getStringExtra("departede");
        idacargo = getIntent().getStringExtra("creadopor");
        final String nummgjtexto = getIntent().getStringExtra("unidad");
        final String operadortexto = getIntent().getStringExtra("operator");
        final String placastexto = getIntent().getStringExtra("placas");
        final String empresatexto = getIntent().getStringExtra("compañia");

        litros.setText(litrostexto);
        folio.setText(foliotexto);
        departede.setText(departedetexto);
        nummgj.setText(nummgjtexto);
        operador.setText(operadortexto);
        empresa.setText(empresatexto);
        placas.setText(placastexto);
        setInformation(idacargo);
    }

    private void setInformation(String idacargo) {
        fStore.collection("users").whereEqualTo("Id", idacargo).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("FirestoreError", error.getMessage());
                    return;
                }

                if (value != null && !value.isEmpty()) {
                    for (DocumentSnapshot document : value.getDocuments()) {
                        String cargostore = document.getString("Rol");
                        String nombre = document.getString("username");

                        // Mostrar currentLevel en litrosTextView con "Lts"
                        nombrecargo.setText(nombre);
                        cargo.setText(cargostore);

                    }
                } else {
                    Log.d("Firestore", "No matching documents found.");
                }
            }
        });
    }


}