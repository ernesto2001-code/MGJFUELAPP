package com.example.mgj;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class formReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button SaveReport;
    TextView mgjyempresa;
    EditText EditTextOperador, EditTextNumeroPlaca, EditTextLitros,EditTextFolio,EditTextmgjyempresa;
    Spinner Spinerpartede;
    String [] opciones= {"-", "MGJ", "Kristel", "Lucy"};
    ImageView btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_report);
        Spinerpartede = (Spinner)findViewById(R.id.Spinerpartede);
        SaveReport = (Button) findViewById(R.id.btn_image);
        btnsalir = (ImageView)findViewById(R.id.salir);
        mgjyempresa = (TextView) findViewById(R.id.mgjyempresa);
        EditTextOperador = (EditText) findViewById(R.id.EditTextOperador);
        EditTextNumeroPlaca = (EditText) findViewById(R.id.EditTextNumeroPlaca);
        EditTextLitros = (EditText) findViewById(R.id.EditTextLitros);
        EditTextFolio = (EditText) findViewById(R.id.EditTextFolio);
        EditTextmgjyempresa = (EditText) findViewById(R.id.EditTextmgjyempresa);


        // Configurar spinner de tipo de vehículo
        ArrayAdapter<String> aa = new ArrayAdapter<String>(formReport.this,
                R.layout.listviewresours, opciones);
        Spinerpartede.setAdapter(aa);
        Spinerpartede.setOnItemSelectedListener( this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}