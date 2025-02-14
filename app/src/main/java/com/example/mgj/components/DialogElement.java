package com.example.mgj.components;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.mgj.MainActivityFragment;

import com.example.mgj.R;
;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DialogElement {

    Activity activity;
    SnackBarElement snackbar;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;


    public DialogElement(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        snackbar = new SnackBarElement(activity);;
    }

    //----------Metodos para mostrar los diferentes Dialogs----------

    //-----Dialog para editar la Edad-----



    public void showDialogWarningExit(){
        Dialog d_edit = new Dialog(activity);

        d_edit.setContentView(R.layout.warning_exit_dialog);
        d_edit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d_edit.setCanceledOnTouchOutside(true);
        d_edit.show();

        //-------Boton para borrar el viaje-------
        Button btnSalir = d_edit.findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, MainActivityFragment.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(i);
                activity.finish();
                d_edit.dismiss();
            }
        });

        //-------Boton para cerrar el dialog-------
        Button btnCancelar = d_edit.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d_edit.dismiss();

            }
        });
    }
    public void showDialogConfirmPredeterminado(String operador, String numeroplaca, String departede, String litros, String folio, String empresa, String unidad, String creadopor){
        Dialog d_edit = new Dialog(activity);


        d_edit.setContentView(R.layout.dialog_create_form);
        d_edit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d_edit.setCanceledOnTouchOutside(true);
        d_edit.show();

        //-------Boton para borrar el viaje-------
        Button btnSi = d_edit.findViewById(R.id.btnSi);
        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear mapa de datos para Firestore
                Map<String, Object> reporte = new HashMap<>();
                reporte.put("operator_name", operador);
                reporte.put("license_plate", numeroplaca);
                reporte.put("chargefromby", departede);
                reporte.put("liters_filled", litros);
                reporte.put("folio", folio);
                reporte.put("company", empresa);
                reporte.put("created_by", creadopor);// se cammbiara cuando inicie sesion  fAuth.getUid()
                reporte.put("unidad", unidad);
                reporte.put("timestamp", FieldValue.serverTimestamp()); // Agregar timestamp

                // 🔹 Guardar en tanks/tank1/reports
                fStore.collection("tanks")
                        .document("tank1") // Documento específico (puedes cambiarlo si necesitas otro tanque)
                        .collection("reports") // Subcolección donde se guardarán los reportes
                        .add(reporte) // Firestore generará automáticamente un ID para cada reporte
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                fStore.collection("tanks")
                                        .document("tank1") // Documento específico (puedes cambiarlo si necesitas otro tanque)
                                        .collection("reports").document(documentReference.getId()).update("id", documentReference.getId());
                                snackbar.showSnackBar(activity.getResources().getColor(R.color.green),"Publicacion creada Correctamente");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(activity, MainActivityFragment.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        activity.startActivity(i);
                                        activity.finish();
                                    }
                                },1200);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                snackbar.showSnackBar(activity.getResources().getColor(R.color.red),"Error al crear la Publicacion");
                            }
                        });

            }
        });

        //-------Boton para cerrar el dialog-------
        Button btnNo = d_edit.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d_edit.dismiss();
            }
        });
    }



}
