package org.rest.software.examenrestaurante;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Login extends AppCompatActivity {
    EditText nombre,clave;
    Button ingresar, salir;
    JSONArray ja;
    ProgressDialog progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("La Picada de Yepeto");

        RequestQueue queue2 = Volley.newRequestQueue(this);
        String ping2 = "http://systemchile.com";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, ping2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Conexion al Servidor", Toast.LENGTH_SHORT).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: Tiempo de Respuesta Agotado con la Conexion al Servidor", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest2.setShouldCache(false);
        queue2.add(stringRequest2);

        nombre=(EditText)findViewById(R.id.nombre);
        clave=(EditText)findViewById(R.id.clave);
        ingresar=(Button)findViewById(R.id.ingresar);
        salir = (Button)findViewById(R.id.salir);
        nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                nombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String registro = "http://systemchile.com/AplicacionExamen/login.php?nx=" + nombre.getText() + "&cx=" +clave.getText();
                registro = registro.replace(" ","%20");
                EnviarRecibirDatos(registro);

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });




    }


    public void EnviarRecibirDatos(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ja = new JSONArray(response);
                    String nom = ja.getString(2);
                    String cla = ja.getString(3);
                    if (nom.equals(nombre.getText().toString()) && cla.equals(clave.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Bienvenido Garzon: " + nombre.getText(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), menumesas.class);
                        startActivity(i);
                        nombre.setText("");
                        nombre.invalidate();
                        clave.setText("");
                        clave.invalidate();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Nombre de Usuario y/o Contraseña Son Incorrectos", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Nombre de Usuario y/o Contraseña Son Incorrectos", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }



}
