package org.rest.software.examenrestaurante;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class transaccion extends AppCompatActivity {
    Button regresar, crearpedido, cancelarpedido, cambiarmesa, pagar, modificar, actualizar;
    EditText mesa;
    TextView medirestado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        setTitle("Transaccion");
        mesa = (EditText)findViewById(R.id.mesa);
        medirestado = (TextView)findViewById(R.id.medirestado);

        actualizar = (Button)findViewById(R.id.actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registross = "http://systemchile.com/AplicacionExamen/transaccion/validamesa.php?producto=" + mesa.getText();
                registross = registross.replace(" ","%20");
                EnviarRecibirDatos(registross);
            }
        });

        pagar = (Button)findViewById(R.id.pagar);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(transaccion.this,pagarpedidos.class);
                i.putExtra("p", mesa.getText().toString());
                startActivity(i);
                finish();
            }
        });

        modificar = (Button)findViewById(R.id.modificar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(transaccion.this,modificarpedido.class);
                i.putExtra("m", mesa.getText().toString());
                startActivity(i);
                finish();
            }
        });

        cambiarmesa = (Button)findViewById(R.id.cambiarmesa);
        cambiarmesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = cambiarmesa.getText().toString();
                if (n.equals("Cambiar"))
               {
                   mesa.setEnabled(true);
                   cambiarmesa.setText("Aceptar");
               }
               else if (n.equals("Aceptar")) {
                    String r = "http://systemchile.com/AplicacionExamen/transaccion/verificamesa.php?producto=" + mesa.getText();
                    r = r.replace(" ","%20");
                    EnviarRecibirMesa(r);
                }
            }
        });

        regresar = (Button)findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(transaccion.this,menumesas.class);
                startActivity(i);
                finish();
            }
        });

        crearpedido = (Button)findViewById(R.id.crearpedido);
        crearpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg = "http://systemchile.com/AplicacionExamen/transaccion/estadoocupado.php?producto=" + mesa.getText();
                reg = reg.replace(" ","%20");
                EnviarRecibirDatos(reg);
                cancelarpedido.setEnabled(true);
                modificar.setEnabled(true);
                pagar.setEnabled(true);
                crearpedido.setEnabled(false);
                medirestado.setText("LA MESA ESTA OCUPADA");
                Intent i = new Intent(getApplicationContext(),realizarpedidos.class);
                i.putExtra("mesa", mesa.getText().toString());
                startActivity(i);
                finish();

            }
        });

        cancelarpedido = (Button)findViewById(R.id.cancelarpedido);
        cancelarpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(transaccion.this);
                dialogo1.setTitle("Opcion Antes de Cancelar");
                dialogo1.setMessage("¿Que Deseas Hacer?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Ir a Pedidos Pendientes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        pedidospendientes();
                    }
                });
                dialogo1.setNeutralButton("Cancelar Sesion", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.setNegativeButton("Salir del Mensaje", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        salir();
                    }
                });
                dialogo1.show();
            }
        });
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        String datoNombre=(String)extras.get("nombremesa");
        mesa.setText(datoNombre);
        String registross = "http://systemchile.com/AplicacionExamen/transaccion/validamesa.php?producto=" + mesa.getText();
        registross = registross.replace(" ","%20");
        EnviarRecibirDatos(registross);
    }

    public void pedidospendientes() {
        Intent i = new Intent(getApplicationContext(),realizarpedidos.class);
        i.putExtra("mesa", mesa.getText().toString());
        startActivity(i);
        finish();
    }
    public void cancelar() {
        String reg = "http://systemchile.com/AplicacionExamen/transaccion/estadodisponible.php?producto=" + mesa.getText();
        reg = reg.replace(" ","%20");
        EnviarRecibirDatos(reg);
        String r5 = "http://systemchile.com/AplicacionExamen/transaccion/borrarreservas.php?mesa=" + mesa.getText();
        r5 = r5.replace(" ","%20");
        EjecutarAcciones(r5);
        Toast.makeText(getApplicationContext(), "Has Cancelado la Mesa", Toast.LENGTH_SHORT).show();
        cancelarpedido.setEnabled(false);
        crearpedido.setEnabled(true);
        modificar.setEnabled(false);
        pagar.setEnabled(false);
        cambiarmesa.setEnabled(false);
        medirestado.setText("LA MESA ESTA DISPONIBLE");
    }
    public void salir(){

    }

    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray ja = new JSONArray(response);
                    String estado = ja.getString(3);
                    if (estado.equals("Disponible")){
                        cancelarpedido.setEnabled(false);
                        crearpedido.setEnabled(true);
                        medirestado.setText("LA MESA ESTA DISPONIBLE");
                        cambiarmesa.setEnabled(false);
                        modificar.setEnabled(false);
                        pagar.setEnabled(false);
                    } else if (estado.equals("Ocupado")) {
                        cancelarpedido.setEnabled(true);
                        crearpedido.setEnabled(false);
                        medirestado.setText("LA MESA ESTA OCUPADA");
                        cambiarmesa.setEnabled(true);
                        modificar.setEnabled(true);
                        pagar.setEnabled(true);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void EnviarRecibirMesa(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray ja = new JSONArray(response);
                    String nombremesa = ja.getString(1);
                    if (nombremesa.equals(mesa.getText().toString())){
                        String r2 = "http://systemchile.com/AplicacionExamen/transaccion/verificamesaocupada.php?producto=" + mesa.getText();
                        r2 = r2.replace(" ","%20");
                        EnviarRecibirMesa2(r2);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error: Mesa no Existente en el Mantenedor", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Mesa no Existente en el Mantenedor", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void EnviarRecibirMesa2(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray ja = new JSONArray(response);
                    String nomb = ja.getString(3);
                    if (nomb.equals("Ocupado")){
                        Toast.makeText(getApplicationContext(), "Error: Mesa Seleccionada Ocupada", Toast.LENGTH_SHORT).show();
                    }
                    else if (nomb.equals("")) {
                        String r3 = "http://systemchile.com/AplicacionExamen/transaccion/estadoocupado.php?producto=" + mesa.getText();
                        r3 = r3.replace(" ", "%20");
                        EjecutarAcciones(r3);
                        Intent intent = getIntent();
                        Bundle extras = intent.getExtras();
                        String datoNombre = (String) extras.get("nombremesa");
                        String r4 = "http://systemchile.com/AplicacionExamen/transaccion/estadodisponible.php?producto=" + datoNombre;
                        r4 = r4.replace(" ", "%20");
                        EjecutarAcciones(r4);
                        String rx = "http://systemchile.com/AplicacionExamen/transaccion/cambiarmesa.php?mesa2=" + mesa.getText()+ "&mesa1="+datoNombre;
                        rx = rx.replace(" ", "%20");
                        EjecutarAcciones(rx);
                        mesa.setEnabled(false);
                        cambiarmesa.setText("Cambiar");


                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    String r3 = "http://systemchile.com/AplicacionExamen/transaccion/estadoocupado.php?producto=" + mesa.getText();
                    r3 = r3.replace(" ","%20");
                    EjecutarAcciones(r3);
                    Intent intent=getIntent();
                    Bundle extras =intent.getExtras();
                    String datoNombre=(String)extras.get("nombremesa");
                    String r4 = "http://systemchile.com/AplicacionExamen/transaccion/estadodisponible.php?producto=" + datoNombre;
                    r4 = r4.replace(" ","%20");
                    EjecutarAcciones(r4);
                    String rx = "http://systemchile.com/AplicacionExamen/transaccion/cambiarmesa.php?mesa2=" +datoNombre+ "&mesa1="+mesa.getText();
                    rx = rx.replace(" ", "%20");
                    EjecutarAcciones(rx);
                    mesa.setEnabled(false);
                    cambiarmesa.setText("Cambiar");
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void EjecutarAcciones(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray ja = new JSONArray(response);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
}
