package org.software.nicolas.ProyBodega;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class mysql extends AppCompatActivity {

    EditText txtnombre,txtclave;
    Button ingresar, salir;
    JSONArray ja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql);
        setTitle("MUN. DE PERALILLO");
        RequestQueue queue = Volley.newRequestQueue(this);
        String ping = "http://systemchile.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        txtnombre=(EditText)findViewById(R.id.txtnombre);
        txtclave=(EditText)findViewById(R.id.txtclave);
        ingresar=(Button)findViewById(R.id.ingresar);
        txtnombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtnombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtnombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtnombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtnombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String registro = "http://systemchile.com/AplicacionProyBodega/login.php?nx=" + txtnombre.getText() + "&cx=" +txtclave.getText();
                registro = registro.replace(" ","%20");
                EnviarRecibirDatos(registro);

            }
        });
    }


    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        ja = new JSONArray(response);
                        String nombre = ja.getString(0);
                        String clave = ja.getString(1);
                        if (nombre.equals(txtnombre.getText().toString()) && clave.equals(txtclave.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Bienvenido Alcalde: " + txtnombre.getText(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), menu.class);
                            startActivity(i);
                            txtnombre.setText("");
                            txtnombre.invalidate();
                            txtclave.setText("");
                            txtclave.invalidate();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: Nombre de Usuario y/o Contraseña Son Incorrectos", Toast.LENGTH_SHORT).show();

                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error: Nombre de Usuario y/o Contraseña Son Incorrectos", Toast.LENGTH_SHORT).show();
                    }

                }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }



    public void salgo (View v) {
        salir=(Button)findViewById(R.id.salir);
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
}
