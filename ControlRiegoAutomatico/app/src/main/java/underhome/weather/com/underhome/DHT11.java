package underhome.weather.com.underhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DHT11 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogoCerrar.FinalizoCuadroDialogo {

    ProgressDialog progressBar2;
    Context mensaje_flotante_cerrando;
    FloatingActionMenu menu_flotante;
    FloatingActionButton boton_flotante1;
    FloatingActionButton boton_flotante2;

    ProgressDialog progressBarbuscar;

    TextView temp2, temp3, operario;
    TextView TEMPAMB, HUMAMB, TEMPAGUA, ESTADOA, ESTADOB, ESTADOC;

    SwipeRefreshLayout refresco_datos;

    Snackbar mostrarsnack;

    Transition transition;
    CardView tarjeta;
    CardView tarjeta2;
    CardView tarjeta3;
    Bundle extras;
    ScrollView scroolees;
    boolean valor = false;

    //Tarjeta Clima
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listadata = new ArrayList<Data>();
    //Tarjeta Riego
    private RecyclerView recyclerView2;
    private RecyclerViewAdapter_riego adapter2;
    private RecyclerView.LayoutManager layoutManager2;
    public List<Data2> listadata2 = new ArrayList<Data2>();
    //Tarjeta Suelo
    private RecyclerView recyclerView3;
    private RecyclerViewAdapter_suelo adapter3;
    private RecyclerView.LayoutManager layoutManager3;
    public List<Data3> listadata3 = new ArrayList<Data3>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht11);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        scroolees = findViewById(R.id.scroolees);

        TEMPAMB = findViewById(R.id.TEMPAMB);
        HUMAMB = findViewById(R.id.HUMAMB);
        TEMPAGUA = findViewById(R.id.TEMPAGUA);
        ESTADOA = findViewById(R.id.ESTADOA);
        ESTADOB = findViewById(R.id.ESTADOB);
        ESTADOC = findViewById(R.id.ESTADOC);
        temp2 = findViewById(R.id.temp2);
        temp3 = findViewById(R.id.temp3);
        operario = findViewById(R.id.operario);
        extras = getIntent().getExtras();
        Usuario u = extras.getParcelable("nombreoperario");
        if (u != null){
            operario.setText(String.format("%s\n", u.getNomb()));
        }
        temp2.setText("Consultando Planes de Riego...");
        temp3.setText("Consultando Tipos de Suelo...");
        recyclerView = findViewById(R.id.recycler);
        tarjeta = findViewById(R.id.tarjeta);
        recyclerView2 = findViewById(R.id.recycler2);
        tarjeta2 = findViewById(R.id.tarjeta2);
        recyclerView3 = findViewById(R.id.recycler3);
        tarjeta3 = findViewById(R.id.tarjeta3);
        refresco_datos = findViewById(R.id.refresco_datos);
        mensaje_flotante_cerrando = this;
        refresco_datos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresco_datos.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresco_datos.setRefreshing(false);
                        mostrarsnack = Snackbar.make(findViewById(R.id.boton_flotante1), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
                        View sbView = mostrarsnack.getView();
                        sbView.setBackgroundColor(Color.rgb(13,112,110));
                        mostrarsnack.show();

                        String consulta2 = "https://systemchile.com/sistemariego_app/mostrarClima.php";
                        EnviarRecibirDatos(consulta2);

                        String consulta3 = "https://systemchile.com/sistemariego_app/mostrar_planes_riego.php";
                        EnviarRecibirDatos3(consulta3);

                        String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos.php";
                        EnviarRecibirDatos4(consulta4);

                        String consulta5 = "https://systemchile.com/sistemariego_app/mostrar_sensores.php";
                        EnviarRecibir_Sensores(consulta5);
                    }
                },300);
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        //recyclerView3.setNestedScrollingEnabled(false);

        menu_flotante = findViewById(R.id.menu_flotante);
        menu_flotante.setClosedOnTouchOutside(true);

        boton_flotante1 = findViewById(R.id.boton_flotante1);
        boton_flotante2 = findViewById(R.id.boton_flotante2);

        boton_flotante1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu_flotante.isOpened()) { //esta abierto?
                    menu_flotante.close(true); //Cierra menú
                }

                ejecutarsnack();

                String consulta2 = "https://systemchile.com/sistemariego_app/mostrarClima.php";
                EnviarRecibirDatos(consulta2);

                String consulta3 = "https://systemchile.com/sistemariego_app/mostrar_planes_riego.php";
                EnviarRecibirDatos3(consulta3);

                String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos.php";
                EnviarRecibirDatos4(consulta4);

                String consulta5 = "https://systemchile.com/sistemariego_app/mostrar_sensores.php";
                EnviarRecibir_Sensores(consulta5);

            }
        });

        boton_flotante2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu_flotante.isOpened()) { //esta abierto?
                    menu_flotante.close(true); //Cierra menú
                }
                Intent i = new Intent(DHT11.this,Notificaciones.class);
                Usuario u = new Usuario(operario.getText().toString());
                i.putExtra("usuario", u);
                startActivity(i);
            }
        });

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

        /*final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        ejecutarsnack();

                        String consulta2 = "https://systemchile.com/sistemariego_app/mostrarClima.php";
                        EnviarRecibirDatos(consulta2);

                        String consulta3 = "https://systemchile.com/sistemariego_app/mostrar_planes_riego.php";
                        EnviarRecibirDatos3(consulta3);

                        String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos.php";
                        EnviarRecibirDatos4(consulta4);

                        String consulta5 = "https://systemchile.com/sistemariego_app/mostrar_sensores.php";
                        EnviarRecibir_Sensores(consulta5);

                    }
                });
            }
        };

        timer.schedule(task, 0, 45000);*/
        ejecutarsnack();

        String consulta2 = "https://systemchile.com/sistemariego_app/mostrarClima.php";
        EnviarRecibirDatos(consulta2);

        String consulta3 = "https://systemchile.com/sistemariego_app/mostrar_planes_riego.php";
        EnviarRecibirDatos3(consulta3);

        String consulta4 = "https://systemchile.com/sistemariego_app/mostrar_suelos.php";
        EnviarRecibirDatos4(consulta4);

        String consulta5 = "https://systemchile.com/sistemariego_app/mostrar_sensores.php";
        EnviarRecibir_Sensores(consulta5);

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

        if (id == R.id.notif) {
            if (menu_flotante.isOpened()) { //esta abierto?
                menu_flotante.close(true); //Cierra menú
            }
            Intent i = new Intent(DHT11.this,Notificaciones.class);
            Usuario u = new Usuario(operario.getText().toString());
            i.putExtra("usuario", u);
            startActivity(i);
        }
        else if (id == R.id.clima) {
            scroolees.scrollTo(0,scroolees.getTop());
        } else if (id == R.id.planesriego) {
            scroolees.scrollTo(0, 200);
            //startActivity(new Intent(DHT11.this, DS18B20.class));
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            //finish();
        } else if (id == R.id.suelo) {
            scroolees.scrollTo(0,scroolees.getBottom());
            //startActivity(new Intent(DHT11.this, DS18B20.class));
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            //finish();
        } else if (id == R.id.salir) {
                new DialogoCerrar(mensaje_flotante_cerrando, DHT11.this);
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

    public void Enviar_Cierre_Sesion(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        progressBarbuscar.dismiss();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        Intent inten = new Intent(Intent.ACTION_MAIN);
                        inten.addCategory(Intent.CATEGORY_HOME);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inten);
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
                        temp2.setText("Planes de Riego");
                        CargarDatos_Riego(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    temp2.setText("Por el momento, no hay planes de riego disponibles...");
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
        for(int i=0;i<ja.length();i+=6){
            try{
                listadata2.add(new Data2("ID: "+ja.getString(i),""+ja.getString(i+1),"Cultivo: "+ja.getString(i+2),"Hora: "+ja.getString(i+3), "Fecha: "+ja.getString(i+4), "Tiempo: "+ja.getString(i+5) + " Minutos"));
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
                        temp3.setText("Suelo");
                        CargarDatos_suelo(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    temp3.setText("Por el momento, no hay tipos de suelos disponibles...");
                    mostrarsnack.dismiss();
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
        for(int i=0;i<ja.length();i+=7){
            try{
                listadata3.add(new Data3("Suelo: "+ja.getString(i),"Estado Suelo: "+ja.getString(i+1),"Abono: "+ja.getString(i+2), "Cultivo: "+ja.getString(i+3), "Invernadero: "+ja.getString(i+4), "Pres. Agua: "+ja.getString(i+5)));
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

    @Override
    public void ResultadoCuadroDialogo (boolean res){
        valor = res;
        int suma, conversion;
        if (valor){
            String datoID = "";
            Identificador ii = extras.getParcelable("identificador");
            if (ii != null){
                datoID = ii.getIdentificador();
            }
            conversion = Integer.parseInt(datoID);
            suma = conversion + 1;
            progressBarbuscar= new ProgressDialog(DHT11.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Cerrando Sesion Sr(a) "+operario.getText());
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro0 = "https://systemchile.com/sistemariego_app/sesiones/ingreso_cierre_sesion.php?ids="+suma+"&nombre="+operario.getText();
            registro0 = registro0.replace(" ","%20");
            Enviar_Cierre_Sesion(registro0);
        }
        else if (valor == false){
        }
    }

    public void ejecutarsnack(){
        mostrarsnack = Snackbar.make(findViewById(R.id.boton_flotante1), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
    }

    public void EnviarRecibir_Sensores(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarDatos_Sensores(ja);
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


    public void CargarDatos_Sensores(JSONArray ja){
        try{
            int i = 0;
            TEMPAMB.setText("Temperatura Ambiente: "+ja.getString(i));
            HUMAMB.setText("Humedad Ambiente: "+ja.getString(i+1));
            TEMPAGUA.setText("Temperatura Agua: "+ja.getString(i+2));
            ESTADOA.setText("Estado S. Ambiente: "+ja.getString(i+3));
            ESTADOB.setText("Estado S. Agua: "+ja.getString(i+4));
            ESTADOC.setText("Estado S. Tierra: "+ja.getString(i+5));
        }catch (JSONException e){
            e.printStackTrace();
        }
        mostrarsnack.dismiss();
    }


}
