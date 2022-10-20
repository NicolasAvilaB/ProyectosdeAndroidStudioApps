package com.example.nicolas.serieswhileyfor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WhileyFor extends AppCompatActivity {

    EditText numero, resultado;
    Button whiles, fors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiley_for);

        numero = (EditText)findViewById(R.id.numero);
        resultado = (EditText)findViewById(R.id.resultado);

        whiles = (Button)findViewById(R.id.whiles);
        whiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                int n = Integer.parseInt(numero.getText().toString());
                String cadena="";

                while (i <= n){
                    cadena += i + ", ";
                    i+=1;
                }
                resultado.setText("");
                resultado.invalidate();
                resultado.setText(cadena);
            }
        });

        fors = (Button)findViewById(R.id.fors);
        fors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;
                int n = Integer.parseInt(numero.getText().toString());
                String cadena1="";
                for (i=0; i<=n; i++){
                    cadena1 += i + ", ";
                }
                resultado.setText("");
                resultado.invalidate();
                resultado.setText(cadena1);
            }
        });


    }
}
