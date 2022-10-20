package underhome.weather.com.underhome;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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

public class Notificaciones extends AppCompatActivity {

    CardView tarjeta4;
    Button volver, conf;
    Snackbar mostrarsnack;
    SwipeRefreshLayout refresco_not;
    Context mensaje_datos;

    boolean valor = false;

    private RecyclerView recyclernotificaciones;
    private RecyclerViewAdapter_notf adapter4;
    private RecyclerView.LayoutManager layoutManager4;
    private List<Data4> listadata4 = new ArrayList<Data4>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar !=null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Notificaciones");
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mensaje_datos = this;
        volver = findViewById(R.id.volver);
        tarjeta4 = findViewById(R.id.tarjeta4);
        recyclernotificaciones = findViewById(R.id.recyclernotificaciones);

        refresco_not = findViewById(R.id.refresco_not);


        refresco_not.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresco_not.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresco_not.setRefreshing(false);
                        consulta_notificaciones();

                    }
                },300);
            }
        });

        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT < 21)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        /*final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        mostrarsnack = Snackbar.make(findViewById(R.id.refresco_not), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
                        View sbView = mostrarsnack.getView();
                        sbView.setBackgroundColor(Color.rgb(13,112,110));
                        mostrarsnack.show();
                        Bundle extras = getIntent().getExtras();
                        Usuario u = extras.getParcelable("usuario");
                        if (u != null) {
                            String consulta5 = "https://systemchile.com/sistemariego_app/mostrar_notf.php?nombre="+String.format("%s\n", u.getNomb());
                            EnviarRecibirDatos5(consulta5);
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 45000);*/

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        consulta_notificaciones();
    }

    public void EnviarRecibirDatos5(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarDatos_Notificaciones(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mostrarsnack.dismiss();
                    new DialogoDatosnoenc(mensaje_datos);
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


    public void CargarDatos_Notificaciones(JSONArray ja){
        listadata4.clear();
        ejecutar_recyclerview_notificaciones();
        for(int i=0;i<ja.length();i+=5){
            try{
                listadata4.add(new Data4("Notificacion: "+ja.getString(i), "Estacion del Año: "+ja.getString(i+1) , "Mes: "+ja.getString(i+2) , "Fecha: "+ja.getString(i+3) , ""+ja.getString(i+4)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
        mostrarsnack.dismiss();
    }

    public void ejecutar_recyclerview_notificaciones(){
        recyclernotificaciones.setHasFixedSize(true);
        layoutManager4 = new LinearLayoutManager(Notificaciones.this);
        recyclernotificaciones.setLayoutManager(layoutManager4);
        adapter4 = new RecyclerViewAdapter_notf(listadata4);
        recyclernotificaciones.setAdapter(adapter4);
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

    public void consulta_notificaciones(){
        mostrarsnack = Snackbar.make(findViewById(R.id.refresco_not), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
        Bundle extras = getIntent().getExtras();
        Usuario u = extras.getParcelable("usuario");
        if (u != null) {
            String consulta5 = "http://192.168.43.250/sistemariego_app/mostrar_notf.php?nombre="+String.format("%s\n", u.getNomb());
            EnviarRecibirDatos5(consulta5);
        }
    }


}
