package co.com.smartgeeks.tutorial34;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etFechaEntrada, etFechaSalida, etHabitacion;
    Button btnGuardar, btnCargar;
    ListView listaResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFechaEntrada = (EditText)findViewById(R.id.etFechaEntrada);
        etFechaSalida = (EditText)findViewById(R.id.etFechaSalida);
        etHabitacion = (EditText)findViewById(R.id.etHabitacion);
        btnGuardar = (Button)findViewById(R.id.btnSave);
        btnCargar = (Button)findViewById(R.id.btnLoad);
        listaResultado = (ListView)findViewById(R.id.lvLista);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String registro = "http://10.0.3.2/hotelejemplo/registrarReserva.php?idr=NULL&NHab="+etHabitacion.getText()+"&fe="+etFechaEntrada.getText()+"&fs="+etFechaSalida.getText();
                EnviarRecibirDatos(registro);

            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String consulta = "http://10.0.3.2/hotelejemplo/consultarReserva.php";
                EnviarRecibirDatos(consulta);

            }
        });


    }

    public void EnviarRecibirDatos(String URL){

        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

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

        for(int i=0;i<ja.length();i+=4){

            try {

                lista.add(ja.getString(i)+" "+ja.getString(i+1)+" "+ja.getString(i+2)+" "+ja.getString(i+3));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listaResultado.setAdapter(adaptador);



    }
}
