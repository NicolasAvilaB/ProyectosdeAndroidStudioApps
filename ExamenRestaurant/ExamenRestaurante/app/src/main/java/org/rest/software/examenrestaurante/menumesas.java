package org.rest.software.examenrestaurante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class menumesas extends AppCompatActivity {
    Button volver, actualizar;
    ListView listar;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menumesas);
        setTitle("Menu de Mesas");
        volver = (Button)findViewById(R.id.volver);
        actualizar = (Button)findViewById(R.id.actualizar);
        listar = (ListView)findViewById(R.id.listar);
        textView2 = (TextView)findViewById(R.id.textView2);
        String consulta = "http://systemchile.com/AplicacionExamen/menumesas/mostrar.php";
        EnviarRecibirDatos(consulta);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta = "http://systemchile.com/AplicacionExamen/menumesas/mostrar.php";
                EnviarRecibirDatos(consulta);
            }
        });


        listar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String listChoice = (listar.getItemAtPosition(position).toString());

               textView2.setText(listChoice);

               Intent i = new Intent(menumesas.this,transaccion.class);
               i.putExtra("nombremesa", textView2.getText().toString());
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

        for(int i=0;i<ja.length();i+=5){

            try {

                lista.add(ja.getString(i+1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);
    }

}
