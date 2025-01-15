package com.example.mgj;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.skydoves.progressview.ProgressView;

public class DetailsTankActivity extends AppCompatActivity {
    TextView nameTextView ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_tank);
        nameTextView = findViewById(R.id.nameTextView);

        // Obtén los datos del Intent
        String name = getIntent().getStringExtra("name");

        // Muestra los datos
        nameTextView.setText(name);


    }
}