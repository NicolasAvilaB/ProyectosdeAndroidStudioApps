package com.example.nicolas.factorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PrincipalFactorial extends AppCompatActivity {
    EditText numero, resultado;
    Button factorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_factorial);

        numero = (EditText)findViewById(R.id.numero);
        resultado = (EditText)findViewById(R.id.resultado);

        factorial = (Button)findViewById(R.id.factorial);
        factorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int i = 0;
               int n = Integer.parseInt(numero.getText().toString());
               int fact = 1;
               for (i=1;i<=n;i++){
               fact *= i;
               }
               resultado.setText(String.valueOf(fact));
            }
        });

    }
}
