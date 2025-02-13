package com.example.mgj;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class formReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button SaveReport;
    TextView mgjyempresa, textounidad, txtnumberuniad;
    EditText EditTextOperador, EditTextNumeroPlaca, EditTextLitros, EditTextFolio, EditTextmgjyempresa;
    Spinner Spinerpartede;
    String[] opciones = {"-", "mgj", "kristel", "lucy", "ulises"};
    ImageView btnsalir, imageunidad;
    ImageButton Buttonimage;
    private int selectedForm = R.id.unidad_externa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_report);

        Spinerpartede = findViewById(R.id.Spinerpartede);
        SaveReport = findViewById(R.id.btn_image);
        btnsalir = findViewById(R.id.salir);
        mgjyempresa = findViewById(R.id.mgjyempresa);
        EditTextOperador = findViewById(R.id.EditTextOperador);
        EditTextNumeroPlaca = findViewById(R.id.EditTextNumeroPlaca);
        EditTextLitros = findViewById(R.id.EditTextLitros);
        EditTextFolio = findViewById(R.id.EditTextFolio);
        EditTextmgjyempresa = findViewById(R.id.EditTextmgjyempresa);
        Buttonimage = findViewById(R.id.menuButton);
        imageunidad = findViewById(R.id.imageunidad);
        textounidad = findViewById(R.id.textounidad);
        txtnumberuniad = findViewById(R.id.txtnumberuniad);

        // Inicialmente ocultamos los elementos
        imageunidad.setVisibility(View.INVISIBLE);
        textounidad.setVisibility(View.INVISIBLE);
        txtnumberuniad.setVisibility(View.INVISIBLE);

        // Configurar spinner de tipo de vehículo
        ArrayAdapter<String> aa = new ArrayAdapter<>(formReport.this, R.layout.listviewresours, opciones);
        Spinerpartede.setAdapter(aa);
        Spinerpartede.setOnItemSelectedListener(this);

        Buttonimage.setOnClickListener(v -> showPopupMenu());
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(formReport.this, Buttonimage, Gravity.START, 0, R.style.Popup_Menu);
        popupMenu.getMenuInflater().inflate(R.menu.form_menu, popupMenu.getMenu());
        popupMenu.getMenu().findItem(selectedForm).setChecked(true);

        popupMenu.setOnMenuItemClickListener(item -> {
            selectedForm = item.getItemId();
            item.setChecked(true);

            String selectedUnidad = (item.getItemId() == R.id.unidad_externa) ? "externo"
                    : (item.getItemId() == R.id.mgj01) ? "mgj01"
                    : (item.getItemId() == R.id.mgj02) ? "mgj02"
                    : (item.getItemId() == R.id.mgj04) ? "mgj04"
                    : (item.getItemId() == R.id.mgj05) ? "mgj05"
                    : (item.getItemId() == R.id.mgj06) ? "mgj06"
                    : (item.getItemId() == R.id.mgj07) ? "mgj07"
                    : (item.getItemId() == R.id.mgj08) ? "mgj08"
                    : (item.getItemId() == R.id.mgj09) ? "mgj09"
                    : (item.getItemId() == R.id.mgj10) ? "mgj10"
                    : (item.getItemId() == R.id.mgj11) ? "mgj11"
                    : "mgj12";

            // Mostrar Toast con la unidad seleccionada
            //Toast.makeText(formReport.this, "Unidad seleccionada: " + selectedUnidad, Toast.LENGTH_SHORT).show();

            if (selectedUnidad.equals("externo")) {
                // Ocultar elementos
                imageunidad.setVisibility(View.INVISIBLE);
                textounidad.setVisibility(View.INVISIBLE);
                txtnumberuniad.setVisibility(View.INVISIBLE);

                // Limpiar campos
                EditTextOperador.setText("");
                EditTextNumeroPlaca.setText("");
                EditTextmgjyempresa.setText("");

                // Seleccionar la opción "-" en el Spinner
                int position = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("-");
                Spinerpartede.setSelection(position);

            } else {
                // Mostrar elementos
                imageunidad.setVisibility(View.VISIBLE);
                textounidad.setVisibility(View.VISIBLE);
                txtnumberuniad.setVisibility(View.VISIBLE);

                // Extraer solo el número de la unidad
                String number = selectedUnidad.replace("mgj", "");
                txtnumberuniad.setText(number);

                // Llenar datos según la unidad seleccionada
                switch (selectedUnidad) {
                    case "mgj07":
                        EditTextOperador.setText("Martin Gammon");
                        EditTextNumeroPlaca.setText("31-AZ-6Z");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position);
                        break;

                    case "mgj01":
                        EditTextOperador.setText("");
                        EditTextNumeroPlaca.setText("23-AL-5V");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position2 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position2);
                        break;

                    case "mgj02":
                        EditTextOperador.setText("Cherokee");
                        EditTextNumeroPlaca.setText("86-BF-7C");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position3 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position3);
                        break;
                    case "mgj04":
                        EditTextOperador.setText("");
                        EditTextNumeroPlaca.setText("43-AW-7Y");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position4 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position4);
                        break;
                    case "mgj05":
                        EditTextOperador.setText("");
                        EditTextNumeroPlaca.setText("27-AU-8M");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position5 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position5);
                        break;
                    case "mgj06":
                        EditTextOperador.setText("Juan Ballesteros");
                        EditTextNumeroPlaca.setText("32-AZ-6Z");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position6 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position6);
                        break;
                    case "mgj08":
                        EditTextOperador.setText("");
                        EditTextNumeroPlaca.setText("78-BA-5A");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position7 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position7);
                        break;
                    case "mgj09":
                        EditTextOperador.setText("Brandon Geovanni");
                        EditTextNumeroPlaca.setText("54-BF-7B");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position8 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position8);
                        break;
                    case "mgj10":
                        EditTextOperador.setText("Guaracha");
                        EditTextNumeroPlaca.setText("55-BF-7B");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position9 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position9);
                        break;
                    case "mgj11":
                        EditTextOperador.setText("Oscar Magdaleno");
                        EditTextNumeroPlaca.setText("05-AS-8V");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position10 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position10);
                        break;
                    case "mgj12":
                        EditTextOperador.setText("Martin Eduardo");
                        EditTextNumeroPlaca.setText("85-BF-7C");
                        EditTextmgjyempresa.setText("MGJ");

                        // Seleccionar "mgj" en el Spinner
                        int position11 = ((ArrayAdapter<String>) Spinerpartede.getAdapter()).getPosition("mgj");
                        Spinerpartede.setSelection(position11);
                        break;
                    // Agrega más casos según las unidades
                }
            }

            return true;
        });

        popupMenu.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Método necesario para el Spinner, aunque no lo estés usando ahora
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Método necesario para el Spinner
    }
}
