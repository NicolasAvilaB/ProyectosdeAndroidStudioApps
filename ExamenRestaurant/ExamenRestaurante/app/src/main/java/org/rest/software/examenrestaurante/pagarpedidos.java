package org.rest.software.examenrestaurante;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class pagarpedidos extends AppCompatActivity {
     Button regresar, concretar, actua;
    ListView listar;
    String comprobar;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagarpedidos);
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        comprobar=(String)extras.get("p");
        String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + comprobar;
        mostrar = mostrar.replace(" ","%20");
        EnviarRecibirDatos(mostrar);
        total = (TextView)findViewById(R.id.total);
        listar = (ListView)findViewById(R.id.listar);
        concretar = (Button)findViewById(R.id.concretar);
        concretar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(pagarpedidos.this);
                dialogo1.setTitle("Pagar Pedidos");
                dialogo1.setMessage("¿Deseas Pagar los Pedidos Correspondientes a la "+ comprobar + " ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        positivo();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        negativo();
                    }
                });
                dialogo1.show();
            }
        });

        actua = (Button)findViewById(R.id.actua);
        actua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + comprobar;
                mostrar = mostrar.replace(" ","%20");
                EnviarRecibirDatos(mostrar);
            }
        });

        regresar = (Button)findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),menumesas.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void positivo(){
        if (listar.getCount()==0){
            Toast.makeText(getApplicationContext(), "Error: Para Pagar un Pedido debe Asegurarse de Agregarlos a la Lista", Toast.LENGTH_SHORT).show();
        }
        else {
            String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + comprobar;
            mostrar = mostrar.replace(" ","%20");
            EnviarRecibirDatos332(mostrar);


            String reg = "http://systemchile.com/AplicacionExamen/transaccion/estadodisponible.php?producto=" + comprobar;
            reg = reg.replace(" ","%20");
            EstadoDisponible(reg);
            String r5 = "http://systemchile.com/AplicacionExamen/transaccion/borrarreservas.php?mesa=" + comprobar;
            r5 = r5.replace(" ","%20");
            BorrarReservas(r5);
            Toast.makeText(getApplicationContext(), "Pedidos Pagados de Forma Exitosa", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),menumesas.class);
            startActivity(i);
            finish();
        }
    }

    public void negativo(){
        Toast.makeText(getApplicationContext(), "Se ha Cancelado la Operación", Toast.LENGTH_SHORT).show();
    }

    public void EstadoDisponible(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void BorrarReservas(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    listar.setAdapter(null);
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }


    public void CargarListView(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        Integer suma = 0;
        for(int i=0;i<ja.length();i+=7){
            try {
                lista.add("ID: "+ja.getString(i)+", Usuario: "+ja.getString(i+1)+", Producto: "+ja.getString(i+2)+", Precio: "+ja.getString(i+3)+", Cantidad: "+ja.getString(i+4)+", Tipo: "+ja.getString(i+5)+", Mesa: "+ja.getString(i+6));
                suma += ja.getInt(i+3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);
        total.setText(""+suma);
    }

    public void EnviarRecibirDatos332(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView22(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }


    public void CargarListView22(JSONArray ja){
        for(int i=0;i<ja.length();i+=7){
            try {
                RequestQueue queue2 = Volley.newRequestQueue(this);
                String ping2 = "http://systemchile.com/AplicacionExamen/historial/registrohistorial.php?user=" + ja.getString(i+1)+ "&producto=" + ja.getString(i+2) + "&precio="+ja.getString(i+3)+"&tipo="+ ja.getString(i+5) +"&mesa="+ja.getString(i+6);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, ping2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                stringRequest2.setShouldCache(false);
                queue2.add(stringRequest2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
