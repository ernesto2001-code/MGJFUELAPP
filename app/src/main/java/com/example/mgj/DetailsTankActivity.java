package com.example.mgj;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
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
import com.orbitalsonic.waterwave.WaterWaveView;
import com.skydoves.progressview.ProgressView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsTankActivity extends AppCompatActivity {
    TextView nameTextView, litrosTextView, txtUltimaCargaFecha , txtUltimaCargaResponsable ;
    FirebaseFirestore fStore;
    LinearLayout bottomviewcharger,bottoncreatereport;
    WaterWaveView waterWaveView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_tank);
        nameTextView = findViewById(R.id.txtTanques);
        litrosTextView = findViewById(R.id.txtLitros);
        txtUltimaCargaFecha = findViewById(R.id.txtUltimaCargaFecha);
        txtUltimaCargaResponsable = findViewById(R.id.txtUltimaCargaResponsable);
        bottomviewcharger = findViewById(R.id.bottomviewcharger);
        bottoncreatereport = findViewById(R.id.bottoncreatereport);
        waterWaveView = findViewById(R.id.waterWaveView);
        fStore = FirebaseFirestore.getInstance();


        // Obtén los datos del Intent
        final String name = getIntent().getStringExtra("name");

        // Muestra los datos
        nameTextView.setText(name);
        setInformation(name);


    }

    private void setInformation(String name) {
        fStore.collection("tanks").whereEqualTo("name", name).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("FirestoreError", error.getMessage());
                    return;
                }

                if (value != null && !value.isEmpty()) {
                    for (DocumentSnapshot document : value.getDocuments()) {
                        String lastUpdate = document.getString("last_updated_by");
                        long capacity = document.getLong("capacity");
                        long currentLevel = document.getLong("current_level");

                        // Obtener el Timestamp
                        Timestamp timestamp = document.getTimestamp("last_updated_at");
                        String lastUpdatedAt = null;
                        if (timestamp != null) {
                            // Convertir a Date y luego a String
                            Date date = timestamp.toDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                            lastUpdatedAt = sdf.format(date);
                        }

                        // Mostrar currentLevel en litrosTextView con "Lts"
                        litrosTextView.setText(currentLevel + " Lts");

                        // Mostrar "Fecha: " seguido de lastUpdatedAt
                        if (lastUpdatedAt != null) {
                            txtUltimaCargaFecha.setText("Fecha: " + lastUpdatedAt);
                        }

                        // Mostrar "Responsable: " seguido de lastUpdate
                        txtUltimaCargaResponsable.setText("Responsable: " + lastUpdate);

                        // Calcular y asignar el porcentaje al WaterWaveView
                        if (capacity > 0) { // Evitar división por 0
                            int progress = (int) ((currentLevel * 100) / capacity);
                            waterWaveView.setProgress(progress);
                        }
                    }
                } else {
                    Log.d("Firestore", "No matching documents found.");
                }
            }
        });
    }

}