package underhome.weather.com.underhome;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DHT11 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progressBar2;

    Transition transition;
    CardView tarjeta;
    CardView tarjeta2;
    CardView tarjeta3;

    //Tarjeta Clima
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listadata = new ArrayList<Data>();
    //Tarjeta Riego
    private RecyclerView recyclerView2;
    private RecyclerViewAdapter_riego adapter2;
    private RecyclerView.LayoutManager layoutManager2;
    private List<Data2> listadata2 = new ArrayList<Data2>();
    //Tarjeta Suelo
    private RecyclerView recyclerView3;
    private RecyclerViewAdapter_suelo adapter3;
    private RecyclerView.LayoutManager layoutManager3;
    private List<Data3> listadata3 = new ArrayList<Data3>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht11);
        recyclerView = findViewById(R.id.recycler);
        tarjeta = findViewById(R.id.tarjeta);
        recyclerView2 = findViewById(R.id.recycler2);
        tarjeta2 = findViewById(R.id.tarjeta2);
        recyclerView3 = findViewById(R.id.recycler3);
        tarjeta3 = findViewById(R.id.tarjeta3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT < 21)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setTitle("Menu");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String consulta2 = "https://systemchile.com/sistemariego_app/mostrarClima.php";
        EnviarRecibirDatos(consulta2);

        String consulta3 = "https://systemchile.com/sistemariego_app/mostrar_planes_riego.php";
        EnviarRecibirDatos3(consulta3);

        String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos.php";
        EnviarRecibirDatos4(consulta4);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dht11, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sensordht11) {

        } else if (id == R.id.sensords18b20) {
            //startActivity(new Intent(DHT11.this, DS18B20.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else if (id == R.id.salir) {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*public void animarcardviews_ver(){
        if (Build.VERSION.SDK_INT >= 21) {

            // create the animator for this view (the start radius is zero)
            //if (Build.VERSION.SDK_INT >= 21){
            View myView = findViewById(R.id.tarjeta);

            // get the center for the clipping circle
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            anim.setDuration(800);
            myView.setVisibility(View.VISIBLE);
            anim.start();
        }
        // make the view visible and start the animation

    }*/

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
        listadata.clear();
        ejecutar_recyclerview_clima();
        for(int i=0;i<ja.length();i+=4){
            try{
                listadata.add(new Data(""+ja.getString(i),""+ja.getString(i+1),"Temperatura: "+ja.getString(i+2), "Humedad: "+ja.getString(i+3)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
    }

    public void ejecutar_recyclerview_clima(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DHT11.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(listadata);
        recyclerView.setAdapter(adapter);
    }

    public void EnviarRecibirDatos3(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarDatos_Riego(ja);
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


    public void CargarDatos_Riego(JSONArray ja){
        listadata2.clear();
        ejecutar_recyclerview_riego();
        for(int i=0;i<ja.length();i+=4){
            try{
                listadata2.add(new Data2(""+ja.getString(i),"Cultivo: "+ja.getString(i+1),"Hora: "+ja.getString(i+2), "Fecha: "+ja.getString(i+3)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
    }

    public void ejecutar_recyclerview_riego(){
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(DHT11.this);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new RecyclerViewAdapter_riego(listadata2);
        recyclerView2.setAdapter(adapter2);
    }

    public void EnviarRecibirDatos4(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarDatos_suelo(ja);
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


    public void CargarDatos_suelo(JSONArray ja){
        listadata3.clear();
        ejecutar_recyclerview_suelo();
        for(int i=0;i<ja.length();i+=4){
            try{
                listadata3.add(new Data3("Suelo: "+ja.getString(i),"Estado Suelo: "+ja.getString(i+1),"Abono: "+ja.getString(i+2), "Cultivo: "+ja.getString(i+3)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
    }

    public void ejecutar_recyclerview_suelo(){
        recyclerView3.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(DHT11.this);
        recyclerView3.setLayoutManager(layoutManager3);
        adapter3 = new RecyclerViewAdapter_suelo(listadata3);
        recyclerView3.setAdapter(adapter3);
    }

}
