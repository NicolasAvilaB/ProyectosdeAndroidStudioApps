package org.software.nicolas.ProyBodega;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class mancreaproducto extends AppCompatActivity {
    Button regreso, nuevo,eliminar,modificar,cancelar,guardar;
    EditText codigo, nombre, marca, buscar;
    TextView id;
    Integer varseleccion = 0;
    ListView listar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mancreaproducto);
        setTitle("MUN. DE PERALILLO");
        codigo = (EditText)findViewById(R.id.codigo);
        nombre = (EditText)findViewById(R.id.nombre);
        marca = (EditText)findViewById(R.id.marca);
        buscar = (EditText)findViewById(R.id.buscar);
        id = (TextView)findViewById(R.id.id);
        listar = (ListView) findViewById(R.id.listar);
        String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/mostrarprod.php";
        EnviarRecibirDatosl(consultas);

        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(buscar.getText().length()==0)
                {
                    String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/mostrarprod.php";
                    EnviarRecibirDatosl(consultas);
                }else
                {
                    String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/buscar.php?producto="+buscar.getText().toString();
                    consultas = consultas.replace(" ","%20");
                    EnviarRecibirDatosl(consultas);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nuevo = (Button)findViewById(R.id.nuevo);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varseleccion = 1;
                nuevo.setEnabled(false);
                eliminar.setEnabled(false);
                modificar.setEnabled(false);
                guardar.setEnabled(true);
                cancelar.setEnabled(true);
                codigo.setEnabled(true);
                nombre.setEnabled(true);
                marca.setEnabled(true);
                codigo.requestFocus();
                String consulta = "http://systemchile.com/AplicacionProyBodega/ventanaprod/generarid.php";
                RequestQueue queue = Volley.newRequestQueue(mancreaproducto.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jas = new JSONArray(response);
                            String idd = jas.getString(0);
                            if (idd.equals(id.getText().toString())){
                                int n =Integer.parseInt(id.getText().toString());
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
                codigo.setText("");
                nombre.setText("");
                marca.setText("");
            }
            private void mostrar(int res)
            {
                id.setText(""+res);
            }

        });

        eliminar = (Button)findViewById(R.id.eliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/validararticulostock.php?codigo="+codigo.getText() + "&producto="+nombre.getText();
                consultas = consultas.replace(" ","%20");
                RequestQueue queue = Volley.newRequestQueue(mancreaproducto.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, consultas , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace("][",",");
                        if (response.length()>0){
                            Toast.makeText(getApplicationContext(), "Error: No podras Eliminar el Producto Deseado, Ya que Corresponde a un Stock ya Registrado", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(mancreaproducto.this);
                            dialogo1.setTitle("¿Eliminar Producto?");
                            dialogo1.setMessage("¿Desea Eliminar al Producto: "+ nombre.getText().toString()+ ", del Registro?");
                            dialogo1.setCancelable(false);
                            dialogo1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    aceptar();
                                }
                            });
                            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo1, int id) {
                                    cancelar();
                                }
                            });
                            dialogo1.show();
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
            public void aceptar() {
                String registro = "http://systemchile.com/AplicacionProyBodega/ventanaprod/eliminarprod.php?codigo=" + codigo.getText().toString();
                registro = registro.replace(" ","%20");
                EnviarRecibirDatosl(registro);
                Toast.makeText(getApplicationContext(), "Datos Eliminados Correctamente", Toast.LENGTH_SHORT).show();
            }
            public void cancelar() {
                Toast.makeText(getApplicationContext(), "Se ha Cancelado la Operacion", Toast.LENGTH_SHORT).show();

            }
        });

        modificar = (Button)findViewById(R.id.modificar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/validararticulostock.php?codigo="+codigo.getText() + "&producto="+nombre.getText();
                consultas = consultas.replace(" ","%20");
                RequestQueue queue = Volley.newRequestQueue(mancreaproducto.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, consultas , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace("][",",");
                        if (response.length()>0){
                            Toast.makeText(getApplicationContext(), "Error: No podras Modificar el Producto Deseado, Ya que Corresponde a un Stock ya Registrado", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            varseleccion = 2;
                            nuevo.setEnabled(false);
                            eliminar.setEnabled(false);
                            modificar.setEnabled(false);
                            guardar.setEnabled(true);
                            cancelar.setEnabled(true);
                            codigo.setEnabled(true);
                            nombre.setEnabled(true);
                            marca.setEnabled(true);
                            codigo.requestFocus();
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
        });

        cancelar = (Button)findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevo.setEnabled(true);
                eliminar.setEnabled(true);
                modificar.setEnabled(true);
                guardar.setEnabled(false);
                cancelar.setEnabled(false);
                codigo.setEnabled(false);
                nombre.setEnabled(false);
                marca.setEnabled(false);
                String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/mostrarprod.php";
                EnviarRecibirDatosl(consultas);
                buscar.setText("");
            }
        });

        guardar = (Button)findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (varseleccion == 1) {
                    String registro = "http://systemchile.com/AplicacionProyBodega/ventanaprod/registrarprod.php?id=" + id.getText().toString() + "&codigo=" + codigo.getText().toString() + "&nombre=" + nombre.getText().toString()+ "&marca="+ marca.getText().toString();
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatosl(registro);
                    Toast.makeText(getApplicationContext(), "Datos Ingresados Correctamente", Toast.LENGTH_SHORT).show();
                    codigo.setEnabled(false);
                    nombre.setEnabled(false);
                    marca.setEnabled(false);
                    nuevo.setEnabled(true);
                    modificar.setEnabled(true);
                    eliminar.setEnabled(true);
                    guardar.setEnabled(false);
                    cancelar.setEnabled(false);
                    varseleccion = 0;
                }else if (varseleccion == 2) {
                    String consultas = "http://systemchile.com/AplicacionProyBodega/ventanaprod/validararticulostock.php?codigo="+codigo.getText() + "&producto="+nombre.getText();
                    consultas = consultas.replace(" ","%20");
                    RequestQueue queue = Volley.newRequestQueue(mancreaproducto.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, consultas, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            response = response.replace("][",",");
                            if (response.length()>0){
                                Toast.makeText(getApplicationContext(), "Error: No podras Modificar el Producto Deseado, Ya que Corresponde a un Stock ya Registrado", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "OJO: Para poder Modificarlo Solo debes Dirigirte a la Seccion de Almacenamientos de Productos en Stock", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String registro = "http://systemchile.com/AplicacionProyBodega/ventanaprod/modificarprod.php?codigo="+codigo.getText().toString()+"&nombre=" + nombre.getText().toString() + "&marca=" + marca.getText().toString() + "&id=" + id.getText().toString();
                                registro = registro.replace(" ","%20");
                                EnviarRecibirDatosl(registro);
                                Toast.makeText(getApplicationContext(), "Datos Modificados Correctamente", Toast.LENGTH_SHORT).show();
                                codigo.setEnabled(false);
                                nombre.setEnabled(false);
                                marca.setEnabled(false);
                                nuevo.setEnabled(true);
                                modificar.setEnabled(true);
                                eliminar.setEnabled(true);
                                guardar.setEnabled(false);
                                cancelar.setEnabled(false);
                                varseleccion = 0;
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
    }

    public void EnviarRecibirDatosl(String URL){

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

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=4){

            try {
                lista.add("ID: "+ja.getString(i)+", Codigo: "+ja.getString(i+1)+", Nombre: "+ja.getString(i+2)+", Marca: "+ja.getString(i+3));
                id.setText(ja.getString(i));
                codigo.setText(ja.getString(i+1));
                nombre.setText(ja.getString(i+2));
                marca.setText(ja.getString(i+3));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);

    }


}
