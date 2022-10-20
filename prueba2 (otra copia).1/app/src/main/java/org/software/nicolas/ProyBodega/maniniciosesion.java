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

public class maniniciosesion extends AppCompatActivity {
    Button regreso;
    ListView listainiciosesion;
    EditText buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maniniciosesion);
        setTitle("MUN. DE PERALILLO");
        regreso = (Button)findViewById(R.id.regreso);
        buscar =(EditText)findViewById(R.id.buscar);
        listainiciosesion = (ListView)findViewById(R.id.listainiciosesion);

        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(buscar.getText().length()==0)
                {
                    // String consulta = "http://10.251.151.81/ProyBodega/ventanahistorialegreso/mostrarhistorialegreso.php";
                    //   EnviarRecibirDatos(consulta);
                    listainiciosesion.setAdapter(null);
                }else {
                    String consultas = "http://10.251.151.81/ProyBodega/ventanainiciosesion/buscar.php?user=" + buscar.getText().toString();
                    consultas = consultas.replace(" ", "%20");
                    EnviarRecibirDatos(consultas);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),manbodega.class);
                startActivity(i);
                finish();
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

        for(int i=0;i<ja.length();i+=6){

            try {

                lista.add("ID: "+ja.getString(i)+", Nombre: "+ja.getString(i+1)+", IP Equipo: "+ja.getString(i+2)+", Nombre PC: "+ja.getString(i+3)+", Hora: "+ja.getString(i+4)+", Fecha: "+ja.getString(i+5));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listainiciosesion.setAdapter(adaptador);
    }
}
