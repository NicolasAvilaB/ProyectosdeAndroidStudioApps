package org.software.nicolas.ProyBodega;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class manadmin extends AppCompatActivity {
    Button volver,nuevo,modificar,eliminar,guardar,cancelar,crea_producto,valida_stock;
    ListView listar;
    EditText  nombre_alias, clave_acceso, buscar;
    Integer varseleccion = 0;
    TextView id;
    JSONArray jas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manadmin);
        setTitle("MUN. DE PERALILLO");
        listar=(ListView)findViewById(R.id.listaadmins);
        buscar=(EditText)findViewById(R.id.buscar);
        id=(TextView)findViewById(R.id.id);
        nombre_alias=(EditText)findViewById(R.id.nombre_alias);
        clave_acceso=(EditText)findViewById(R.id.clave_acceso);
        nuevo=(Button)findViewById(R.id.nuevo);
        modificar=(Button)findViewById(R.id.modificar);
        eliminar=(Button)findViewById(R.id.eliminar);
        guardar=(Button)findViewById(R.id.guardar);
        cancelar=(Button)findViewById(R.id.cancelar);
        String consulta = "http://10.251.151.81/ProyBodega/ventanaadmins/mostraradmins.php";
        EnviarRecibirDatos(consulta);
        nombre_alias.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        buscar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        nombre_alias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nombre_alias.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nombre_alias.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            }

            @Override
            public void afterTextChanged(Editable s) {
                nombre_alias.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            }
        });
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buscar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

                if(buscar.getText().length()==0){
                    String consulta = "http://10.251.151.81/ProyBodega/ventanaadmins/mostraradmins.php";
                    EnviarRecibirDatos(consulta);
                }
                else{
                    String consulta = "http://10.251.151.81/ProyBodega/ventanaadmins/buscar.php?alias=" + buscar.getText().toString();
                    consulta = consulta.replace(" ","%20");
                    EnviarRecibirDatos(consulta);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                buscar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });

        listar.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

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

        for(int i=0;i<ja.length();i+=3){

            try {
                lista.add("ID: "+ja.getString(i)+", Alias: "+ja.getString(i+1)+", Acceso: "+ja.getString(i+2));
                id.setText(ja.getString(i));
                nombre_alias.setText(ja.getString(i+1));
                clave_acceso.setText(ja.getString(i+2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);

    }



    public void clicknuevo (View v) {
        varseleccion = 1;
        nombre_alias.setEnabled(true);
        clave_acceso.setEnabled(true);
        nuevo.setEnabled(false);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        guardar.setEnabled(true);
        cancelar.setEnabled(true);
        nombre_alias.setText("");
        clave_acceso.setText("");
        nombre_alias.requestFocus();
        String consulta = "http://10.251.151.81/ProyBodega/ventanaadmins/generarid.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jas = new JSONArray(response);
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



    }
    private void mostrar(int res)
    {
        id.setText(""+res);
    }
    public void clickmodificar (View v) {
        varseleccion = 2;
        nombre_alias.setEnabled(true);
        clave_acceso.setEnabled(true);
        nuevo.setEnabled(false);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        guardar.setEnabled(true);
        cancelar.setEnabled(true);
    }

    public void clickeliminar (View v) {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("¿Eliminar Usuario?");
        dialogo1.setMessage("¿Desea Eliminar al Usuario: "+ nombre_alias.getText().toString()+ ", del Registro?");
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

    public void aceptar() {
        String registro = "http://10.251.151.81/ProyBodega/ventanaadmins/eliminaradmin.php?alias=" + nombre_alias.getText().toString();
        registro = registro.replace(" ","%20");
        EnviarRecibirDatos(registro);
        Toast.makeText(getApplicationContext(), "Datos Eliminados Correctamente", Toast.LENGTH_SHORT).show();

    }

    public void cancelar() {
        Toast.makeText(getApplicationContext(), "Se ha Cancelado la Operacion", Toast.LENGTH_SHORT).show();

    }


    public void clickguardar (View v) {
        if (varseleccion == 1) {
            String registro = "http://10.251.151.81/ProyBodega/ventanaadmins/registraradmin.php?id=" + id.getText().toString() + "&alias=" + nombre_alias.getText().toString() + "&clave=" + clave_acceso.getText().toString();
            registro = registro.replace(" ","%20");
            EnviarRecibirDatos(registro);
            Toast.makeText(getApplicationContext(), "Datos Ingresados Correctamente", Toast.LENGTH_SHORT).show();
            nombre_alias.setEnabled(false);
            clave_acceso.setEnabled(false);
            nuevo.setEnabled(true);
            modificar.setEnabled(true);
            eliminar.setEnabled(true);
            guardar.setEnabled(false);
            cancelar.setEnabled(false);
            varseleccion = 0;
        }else if (varseleccion == 2) {
            String registro = "http://10.251.151.81/ProyBodega/ventanaadmins/modificaradmin.php?alias=" + nombre_alias.getText().toString() + "&clave=" + clave_acceso.getText().toString() + "&id=" + id.getText().toString();
            registro = registro.replace(" ","%20");
            EnviarRecibirDatos(registro);
            Toast.makeText(getApplicationContext(), "Datos Modificados Correctamente", Toast.LENGTH_SHORT).show();
            nombre_alias.setEnabled(false);
            clave_acceso.setEnabled(false);
            nuevo.setEnabled(true);
            modificar.setEnabled(true);
            eliminar.setEnabled(true);
            guardar.setEnabled(false);
            cancelar.setEnabled(false);
            varseleccion = 0;
        }

    }
    public void clickcancelar (View v) {
        nombre_alias.setEnabled(false);
        clave_acceso.setEnabled(false);
        nuevo.setEnabled(true);
        modificar.setEnabled(true);
        eliminar.setEnabled(true);
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
        id.setText("");
        nombre_alias.setText("");
        clave_acceso.setText("");
        String consulta = "http://10.251.151.81/ProyBodega/ventanaadmins/mostraradmins.php";
        EnviarRecibirDatos(consulta);
        varseleccion = 0;
    }


    public void volveralapantallaanterior (View v) {
        volver=(Button)findViewById(R.id.volver);
        Intent i = new Intent(getApplicationContext(),menu.class);
        startActivity(i);
        finish();

    }




}
