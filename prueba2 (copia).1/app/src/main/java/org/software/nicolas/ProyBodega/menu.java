package org.software.nicolas.ProyBodega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {
    Button volver,user,bodega,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("MUN. DE PERALILLO");
    }
    public void entrarusuario (View v) {
        user=(Button)findViewById(R.id.user);
        Intent i = new Intent(getApplicationContext(),manusuario.class);
        startActivity(i);
        finish();


    }
    public void entrarbodega (View v) {
        bodega=(Button)findViewById(R.id.bodega);
        Intent i = new Intent(getApplicationContext(),manbodega.class);
        startActivity(i);
        finish();


    }
    public void entraradmin (View v) {
        admin=(Button)findViewById(R.id.admin);
        Intent i = new Intent(getApplicationContext(),manadmin.class);
        startActivity(i);
        finish();


    }
    public void vovlerapantallaanterior (View v) {
        volver=(Button)findViewById(R.id.volver);
        Intent i = new Intent(getApplicationContext(),mysql.class);
        startActivity(i);
        finish();

    }
}
