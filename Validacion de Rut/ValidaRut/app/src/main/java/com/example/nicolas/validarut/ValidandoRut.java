package com.example.nicolas.validarut;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ValidandoRut extends AppCompatActivity {
    EditText rute;
    Button comprobar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validando_rut);
        rute = (EditText)findViewById(R.id.rute);
        comprobar = (Button)findViewById(R.id.comprobar);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ValidandoRut.this);
        comprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int i, suma, multiplicador, largo;
                String digito, verificador;
                String rut = rute.getText().toString();
                multiplicador = 2;
                suma = 0;
                largo = rut.length() -2;
                verificador = rut.substring(rut.length()-1,rut.length());
                try {
                    for (i = largo - 1; i >= 0; i--) {
                        suma += (Integer.parseInt(rut.substring(i, i + 1))) * multiplicador;
                        multiplicador += 1;
                        if (multiplicador > 7) {
                            multiplicador = 2;
                        }
                    }
                    digito = String.valueOf(11 - (suma % 11));
                    if (digito == "10") {
                        digito = "k";
                    }
                    if (digito == "11") {
                        digito = "0";
                    }
                    builder.setTitle("Valida Rut");

                    if (Integer.parseInt(digito) == Integer.parseInt(verificador)) {
                        builder.setMessage("Rut Correcto");
                    } else {
                        builder.setMessage("Rut Incorrecto");
                        rute.setText("");
                        rute.invalidate();
                    }
                    builder.setPositiveButton("Aceptar", null);
                    builder.create();
                    builder.show();
                }catch (Exception e){
                    builder.setMessage("Rut Incorrecto");
                }
            }
        });
    }
}
