package org.software.nicolas.ProyBodega;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class manusuario extends AppCompatActivity {
    Button volver;
    ListView listar;
    EditText buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manusuario);
        setTitle("MUN. DE PERALILLO");
        listar=(ListView)findViewById(R.id.listausuarios);
        //String consulta = "http://10.251.151.81/ProyBodega/ventanausuarios/mostrarusuarios.php";
        //EnviarRecibirDatos(consulta);
        buscar = (EditText)findViewById(R.id.buscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(buscar.getText().length()==0){
                   // String consulta = "http://10.251.151.81/ProyBodega/ventanausuarios/mostrarusuarios.php";
                    //EnviarRecibirDatos(consulta);
                    listar.setAdapter(null);
                }
                else{
                    String consulta = "http://10.251.151.81/ProyBodega/ventanausuarios/buscar.php?user=" + buscar.getText().toString();
                    consulta = consulta.replace(" ","%20");
                    EnviarRecibirDatos(consulta);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=5){

            try {

                lista.add("ID: "+ja.getString(i)+", Rut:"+ja.getString(i+1)+", Nombre:"+ja.getString(i+2)+", Cargo:"+ja.getString(i+3)+", Departamento:"+ja.getString(i+4));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);
    }

    public void volveralapantallaanterior (View v) {
        volver=(Button)findViewById(R.id.volver);
        Intent i = new Intent(getApplicationContext(),menu.class);
        startActivity(i);
        finish();

    }
}
