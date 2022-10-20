package org.fichas.software.registrosfichasjuguetesnavidad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class buscador extends AppCompatActivity {
    EditText buscador;
    ListView listar;
    Button buscar;
    ProgressDialog progressBarbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        setTitle("MUN. DE PERALILLO");
        listar = (ListView)findViewById(R.id.listar);
        buscador = (EditText)findViewById(R.id.buscador);
        buscar =(Button)findViewById(R.id.buscar);
        //String consultas = "http://10.251.151.81/juguetes/mostrar.php";
        //EnviarRecibirDatos(consultas);

        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String ping = "https://systemchile.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buscador.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Error: No Has Digitado Ningun Dato a Buscar", Toast.LENGTH_LONG).show();
                }
                else {
                    progressBarbuscar= new ProgressDialog(buscador.this);
                    progressBarbuscar.setMessage("Buscando, Por Favor Espere un Momento...");
                    progressBarbuscar.show();
                    String consulta2 = "http://systemchile.com/buscar.php?producto=" + buscador.getText().toString();
                    consulta2 = consulta2.replace(" ", "%20");
                    EnviarRecibirDatos(consulta2);
                }
            }
        });



        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(buscador.getText().length()==0){
                    //String consultas = "http://10.251.151.81/juguetes/mostrar.php";
                    //EnviarRecibirDatos(consultas);
                    listar.setAdapter(null);
                }else{
                    //String consulta2 = "http://192.168.20.108/juguetes/buscar.php?producto=" + buscador.getText().toString();
                    //consulta2 = consulta2.replace(" ","%20");
                    //EnviarRecibirDatos(consulta2);
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
                        CargarDatos(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    progressBarbuscar.dismiss();
                    Toast.makeText(getApplicationContext(), "No se ha Encontrado el Dato: "+ buscador.getText() + " ,En el Registro.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "OJO: Puede que el Dato a Buscar este Mal Digitado, Verifica e Intentalo Denuevo", Toast.LENGTH_LONG).show();
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

    public void CargarDatos(JSONArray ja){
       ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=12){
             try{
              lista.add("ID: "+ja.getString(i)+", Numero Ficha: "+ja.getString(i+1)+", Unidad Vecinal: "+ja.getString(i+2)+ ", Juntas Vecinos: "+ja.getString(i+3) +", Sector: "+ja.getString(i+4) +", Direccion: "+ja.getString(i+5)+", Familia: "+ja.getString(i+6)+", Niño/a: "+ja.getString(i+7)+", Rut: "+ja.getString(i+8)+", Edad: "+ja.getString(i+9)+", Sexo: "+ja.getString(i+10)+", Estado: "+ja.getString(i+11));
             }catch (JSONException e){
              e.printStackTrace();
             }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
            listar.setAdapter(adaptador);
        }
        progressBarbuscar.dismiss();
        Toast.makeText(getApplicationContext(), "Datos Encontrados Exitosamente", Toast.LENGTH_LONG).show();
    }
}
