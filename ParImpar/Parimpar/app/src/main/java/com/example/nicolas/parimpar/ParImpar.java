package com.example.nicolas.parimpar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ParImpar extends AppCompatActivity {
    EditText numero, resultado;
    Button verificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_impar);

        numero = (EditText)findViewById(R.id.numero);
        resultado = (EditText)findViewById(R.id.resultado);

        verificacion = (Button)findViewById(R.id.verificacion);
        verificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n1 = Integer.parseInt(numero.getText().toString());
                String respuesta;
                if(n1 %2 == 0)
                {
                    respuesta = "El Numero es Par";
                    resultado.setText(respuesta);
                }
                else
                {
                    respuesta = "El Numero es Impar";
                    resultado.setText(respuesta);
                }
            }
        });
    }
}
