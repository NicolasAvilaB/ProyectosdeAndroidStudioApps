package org.software.nicolas.ProyBodega;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class mancreastock extends AppCompatActivity {
    TextView codigo, marca, id;
    Spinner tipodocumento;
    Spinner listarproductos;
    Button regreso, nuevo, cancelar, guardar;
    EditText ndocumento, cantidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mancreastock);
        setTitle("MUN. DE PERALILLO");
        String consulta = "http://systemchile.com/AplicacionProyBodega/ventanastock/mostrarproducto.php";
        EnviarRecibirDatos(consulta);
        ndocumento = (EditText)findViewById(R.id.ndocumento);
        cantidad = (EditText)findViewById(R.id.cantidad);
        codigo = (TextView)findViewById(R.id.codigo);
        marca = (TextView)findViewById(R.id.marca);
        id = (TextView)findViewById(R.id.id);
        String visualid = "http://systemchile.com/AplicacionProyBodega/ventanastock/generarid.php";
        EnviarRecibirIDYVisual(visualid);
        listarproductos = (Spinner)findViewById(R.id.listarproductos);
        listarproductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String consulta2 = "http://systemchile.com/AplicacionProyBodega/ventanastock/buscarproducto.php?producto=" + listarproductos.getItemAtPosition(listarproductos.getSelectedItemPosition()).toString();
                consulta2 = consulta2.replace(" ","%20");
                EnviarRecibirDatos2ConsultaSpinner(consulta2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        regreso = (Button)findViewById(R.id.regreso);
        regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),manbodega.class);
                startActivity(i);
                finish();
            }
        });

        nuevo = (Button)findViewById(R.id.nuevo);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevo.setEnabled(false);
                guardar.setEnabled(true);
                cancelar.setEnabled(true);
                ndocumento.setEnabled(true);
                cantidad.setEnabled(true);
                tipodocumento.setEnabled(true);
                listarproductos.setEnabled(true);
                ndocumento.setText("");
                cantidad.setText("");
                listarproductos.clearFocus();
                String consulta = "http://systemchile.com/AplicacionProyBodega/ventanastock/generarid.php";
                EnviarRecibirID(consulta);



        }});

        cancelar = (Button)findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevo.setEnabled(true);
                guardar.setEnabled(false);
                cancelar.setEnabled(false);
                ndocumento.setEnabled(false);
                cantidad.setEnabled(false);
                tipodocumento.setEnabled(false);
                listarproductos.setEnabled(false);

            }
        });

        guardar = (Button)findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta2 = "http://systemchile.com/AplicacionProyBodega/ventanastock/registrarstock.php?id=" + id.getText() + "&codigo="+codigo.getText()+"&nombre="+listarproductos.getItemAtPosition(listarproductos.getSelectedItemPosition()).toString()+"&marca="+marca.getText()+"&td="+tipodocumento.getItemAtPosition(tipodocumento.getSelectedItemPosition()).toString()+"&nd="+ndocumento.getText()+"&cant="+cantidad.getText();
                consulta2 = consulta2.replace(" ","%20");
                EnviarRecibirDatos2ConsultaSpinner(consulta2);
                String consulta3 = "http://systemchile.com/AplicacionProyBodega/ventanastock/registrarhistorialstock.php?id=" + id.getText() + "&codigo="+codigo.getText()+"&nombre="+listarproductos.getItemAtPosition(listarproductos.getSelectedItemPosition()).toString()+"&marca="+marca.getText()+"&td="+tipodocumento.getItemAtPosition(tipodocumento.getSelectedItemPosition()).toString()+"&nd="+ndocumento.getText()+"&cant="+cantidad.getText();
                consulta3 = consulta3.replace(" ","%20");
                EnviarRecibirDatos111(consulta3);
                nuevo.setEnabled(true);
                guardar.setEnabled(false);
                cancelar.setEnabled(false);
                ndocumento.setEnabled(false);
                cantidad.setEnabled(false);
                tipodocumento.setEnabled(false);
                listarproductos.setEnabled(false);
                ndocumento.setText("");
                cantidad.setText("");
                Toast.makeText(getApplicationContext(), "Datos Ingresados Correctamente", Toast.LENGTH_LONG).show();
            }
        });

        tipodocumento = (Spinner)findViewById(R.id.tipodocumento);
        listarproductos = (Spinner)findViewById(R.id.listarproductos);
        String[] datostipodocumento = new String[] {"Seleccion Tipo Documento...","Factura","Boleta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
        tipodocumento.setAdapter(adapter);
        tipodocumento.setEnabled(false);
        listarproductos.setEnabled(false);
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
                        CargarSpinnerNombreProducto(ja);
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

    public void EnviarRecibirDatos111(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
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

    public void CargarSpinnerNombreProducto(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Seleccione Producto...");
        for(int i=0;i<ja.length();i+=4){
            try {
                lista.add(ja.getString(i+2));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mancreastock.this, android.R.layout.simple_dropdown_item_1line, lista);
                listarproductos.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void EnviarRecibirDatos2ConsultaSpinner(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());

                        CargarDatosConsultaSpinnner(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    codigo.setText(".........");
                    marca.setText(".........");
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void CargarDatosConsultaSpinnner(JSONArray ja){
       // ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=9){
            try {
                codigo.setText(""+ja.getString(i+1));
                marca.setText(""+ja.getString(i+3));
             } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
    public void EnviarRecibirID(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jas = new JSONArray(response);
                    String idd = jas.getString(0);
                    if (idd.equals(id.getText().toString())){
                        int n =Integer.parseInt(jas.getString(0));
                        int i = n + 1;
                        id.setText("");
                        id.invalidate();
                        mostrar(i);

                    }else{
                        id.setText("1");
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
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }



    private void mostrar(int res) {
        id.setText("" + res);
    }

    public void EnviarRecibirIDYVisual(String URL){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        cargarID(ja);
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

    public void cargarID(JSONArray ja){
        // ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=9){
            try {
                if (ja.getString(i) == "null"){
                    id.setText("0");
                }
                else{
                    id.setText(ja.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
