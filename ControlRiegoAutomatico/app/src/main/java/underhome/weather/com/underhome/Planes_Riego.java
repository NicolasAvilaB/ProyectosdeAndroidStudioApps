package underhome.weather.com.underhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Planes_Riego extends AppCompatActivity implements DialogoPlanRiego.FinalizoCuadroDialogo {

    Button volver, aceptarplan;
    TextView I2, TIPOPLAN2, TIPOCULTIVO2, HORA2, FECHA2, TIEMPO2;
    Context mensaje_flotante_riego;
    String su2, ab2 = "";
    Snackbar mostrarsnack;
    ProgressDialog progressBarbuscar;
    String com = "";
    String ll, valor_agua = "";

    CardView tarjeta3;

    boolean valor = false;

    private RecyclerView recy3;
    private RecyclerViewAdapter_suelo adapter3;
    private RecyclerView.LayoutManager layoutManager3;
    private List<Data3> listadata3 = new ArrayList<Data3>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes__riego);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar !=null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Comprobar Plan de Riego");
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recy3 = findViewById(R.id.recy5);
        tarjeta3 = findViewById(R.id.tarjeta3);
        I2 = findViewById(R.id.I2);
        TIPOPLAN2 = findViewById(R.id.TIPOPLAN2);
        TIPOCULTIVO2 = findViewById(R.id.TIPOCULTIVO2);
        HORA2 = findViewById(R.id.HORA2);
        FECHA2 = findViewById(R.id.FECHA2);
        TIEMPO2 = findViewById(R.id.TIEMPO2);
        mensaje_flotante_riego = this;
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aceptarplan = findViewById(R.id.aceptarplan);
        aceptarplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarsnack = Snackbar.make(findViewById(R.id.volver), "Consultando Datos de Clima y Cultivo...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
                View sbView = mostrarsnack.getView();
                sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                mostrarsnack.show();
                String consulta2 = "https://systemchile.com/sistemariego_app/mostrarClima.php";
                EnviarRecibirDatos(consulta2);
            }
        });

        mostrarsnack = Snackbar.make(findViewById(R.id.volver), "Consultando Datos, Espere un Momento...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13, 112, 110));
        mostrarsnack.show();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String valor = (String) extras.get("i");
        I2.setText(valor);
        TIPOPLAN2.setText((String) extras.get("tipoplan"));
        TIPOCULTIVO2.setText((String) extras.get("tipocultivo"));
        HORA2.setText((String) extras.get("hora"));
        FECHA2.setText((String) extras.get("fecha"));
        TIEMPO2.setText((String) extras.get("tiempo"));
        valor = valor.replace("ID: ", "");
        valor_agua = valor;
        String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos_comp.php?t=" + valor;
        consulta4 = consulta4.replace(" ", "%20");
        EnviarRecibirDatos4(consulta4);

    }


    public void EnviarRecibirDatos4(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarDatos_suelo(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }


    public void CargarDatos_suelo(JSONArray ja) {
        listadata3.clear();
        ejecutar_recyclerview_suelo();
        int i = 0;
        try {
            listadata3.add(new Data3("Suelo: " + ja.getString(i), "Estado Suelo: " + ja.getString(i + 1), "Abono: " + ja.getString(i + 2), "Cultivo: " + ja.getString(i + 3), "Invernadero: " + ja.getString(i + 4), "Pres. Agua: " + ja.getString(i + 5)));
            com = ja.getString(i + 5);
            su2 = ja.getString(i);
            ab2 = ja.getString(i + 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //animarcardviews_ver();
        mostrarsnack.dismiss();
    }

    public void ejecutar_recyclerview_suelo() {
        recy3.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(Planes_Riego.this);
        recy3.setLayoutManager(layoutManager3);
        adapter3 = new RecyclerViewAdapter_suelo(listadata3);
        recy3.setAdapter(adapter3);
    }


    @Override
    public void ResultadoCuadroDialogo(boolean res) {
        valor = res;
        String idees2 = I2.getText().toString();
        String cu2 = TIPOCULTIVO2.getText().toString();
        String pl2 = TIPOPLAN2.getText().toString();
        String ho2 = HORA2.getText().toString();
        String fe2 = FECHA2.getText().toString();
        String ti2 = TIEMPO2.getText().toString();
        if (valor){
            progressBarbuscar= new ProgressDialog(Planes_Riego.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Ejecutando Riego");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            idees2 = idees2.replace("ID: ", "");
            cu2 = cu2.replace("Cultivo: ", "");
            ho2 = ho2.replace("Hora: ", "");
            fe2 = fe2.replace("Fecha: ", "");
            ti2 = ti2.replace("Tiempo: ", "");
            ti2 = ti2.replace(" Minutos", "");
            String registro0 = "https://systemchile.com/sistemariego_app/ingreso_historial_riego_suelo.php?t=" + idees2 + "&t2=" + cu2 + "&t3=" + pl2 + "&t4=" + ho2 + "&t5=" + fe2 + "&t6=" + su2 + "&t7=" + ab2 + "&t8=" + ti2;
            registro0 = registro0.replace(" ", "%20");
            EnviarDatos_Historiales(registro0);
        } else if(valor == false){
        }
    }

    public void EnviarDatos_Historiales(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                DHT11 h = new DHT11();
                h.ejecutarsnack();
                String consulta3 = "https://systemchile.com/sistemariego_app/mostrar_planes_riego.php";
                h.EnviarRecibirDatos3(consulta3);
                String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos.php";
                h.EnviarRecibirDatos4(consulta4);
                finish();
                progressBarbuscar.dismiss();
                //Intent i = new Intent(Planes_Riego.this,DHT11.class);
                //startActivity(i);
                //finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Log.i("ActionBar","Atras!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                        CargarDatos_Clima(ja);
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


    public void CargarDatos_Clima(JSONArray ja){
            try{
                int i = 0;
                ll = ja.getString(i);
                if (ll.equals("Lluvia")){
                    mostrarsnack = Snackbar.make(findViewById(R.id.volver), "Advertencia: Probabilidad de Lluvia es Alta, No se podra regar", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = mostrarsnack.getView();
                    sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                    mostrarsnack.show();
                }
                if (ll.equals("Llovisna")){
                    mostrarsnack = Snackbar.make(findViewById(R.id.volver), "Advertencia: Probabilidad de Llovisna, No se podra regar", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = mostrarsnack.getView();
                    sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                    mostrarsnack.show();
                }

                    String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos_comp.php?t=" + valor_agua;
                    consulta4 = consulta4.replace(" ", "%20");
                    EnviarRecibirDatos_AGUA(consulta4);

            }catch (JSONException e){
                e.printStackTrace();
            }
    }

    public void EnviarRecibirDatos_AGUA(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        mostrarsnack.dismiss();
                        int i = 0;
                        String agua = ja.getString(i+5);
                        if(agua.equals("REGADA")){
                            mostrarsnack = Snackbar.make(findViewById(R.id.volver), "Advertencia: Cultivo ya contiene agua", Snackbar.LENGTH_LONG).setAction("Action", null);
                            View sbView = mostrarsnack.getView();
                            sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                            mostrarsnack.show();
                        }
                        else {
                            new DialogoPlanRiego(mensaje_flotante_riego, Planes_Riego.this);
                        }
                    } catch (JSONException e) {
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
}