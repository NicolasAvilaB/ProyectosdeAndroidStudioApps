package administracion.admin.com.riego;

import android.app.ProgressDialog;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import underhome.weather.com.underhome.R;

public class DHT11 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progressBar2;
    TextView TEMPAMB, HUMAMB, TEMPAGUA, ESTADOA, ESTADOB, ESTADOC;
    Transition transition;
    CardView tarjeta;
    Snackbar mostrarsnack;
    SwipeRefreshLayout refresco_datos;
    Button ingresar, ingresar2, ingresar3,ingresar4,ingresar8,ingresar9,ingresar10,
            ingresar11,ingresar12;

    public CardView tarjeta_riego, tarjeta_suelo, tarjeta_usuarios, tarjeta_cultivos,
            tarjeta_invernaderos, tarjeta_historiales;

    //Tarjeta Clima
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listadata = new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht11);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerView = findViewById(R.id.recycler);
        tarjeta = findViewById(R.id.tarjeta);

        TEMPAMB = findViewById(R.id.TEMPAMB);
        HUMAMB = findViewById(R.id.HUMAMB);
        TEMPAGUA = findViewById(R.id.TEMPAGUA);
        ESTADOA = findViewById(R.id.ESTADOA);
        ESTADOB = findViewById(R.id.ESTADOB);
        ESTADOC = findViewById(R.id.ESTADOC);


        tarjeta_riego = findViewById(R.id.tarjeta_riego);
        tarjeta_suelo = findViewById(R.id.tarjeta_suelo);
        tarjeta_usuarios = findViewById(R.id.tarjeta_usuarios);
        tarjeta_cultivos = findViewById(R.id.tarjeta_cultivos);
        tarjeta_invernaderos = findViewById(R.id.tarjeta_invernaderos);
        tarjeta_historiales = findViewById(R.id.tarjeta_historiales);

        YoYo.with(Techniques.FadeIn).playOn(tarjeta_riego);
        YoYo.with(Techniques.FadeIn).playOn(tarjeta_suelo);
        YoYo.with(Techniques.FadeIn).playOn(tarjeta_usuarios);
        YoYo.with(Techniques.FadeIn).playOn(tarjeta_cultivos);
        YoYo.with(Techniques.FadeIn).playOn(tarjeta_invernaderos);
        YoYo.with(Techniques.FadeIn).playOn(tarjeta_historiales);

        ingresar = findViewById(R.id.ingresar);
        ingresar2 = findViewById(R.id.ingresar2);
        ingresar3 = findViewById(R.id.ingresar3);
        ingresar4 = findViewById(R.id.ingresar4);
        ingresar8 = findViewById(R.id.ingresar8);
        ingresar9 = findViewById(R.id.ingresar9);
        ingresar10 = findViewById(R.id.ingresar10);
        ingresar11 = findViewById(R.id.ingresar11);
        ingresar12 = findViewById(R.id.ingresar12);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Planes_Riego.class);
                startActivity(i);
            }
        });

        ingresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Suelo.class);
                startActivity(i);
            }
        });

        ingresar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Usuarios.class);
                startActivity(i);
            }
        });

        ingresar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Cultivos_Notificaciones.class);
                startActivity(i);
            }
        });

        ingresar8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Invernaderos.class);
                startActivity(i);
            }
        });

        ingresar9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), H_Riego.class);
                startActivity(i);
            }
        });

        ingresar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), H_Suelo.class);
                startActivity(i);
            }
        });

        ingresar11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), H_Clima.class);
                startActivity(i);
            }
        });

        ingresar12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), H_Sesion.class);
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

       /* final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                    }
                });
            }
        };

        timer.schedule(task, 0, 45000);*/

        refresco_datos = findViewById(R.id.refresco_datos);
        refresco_datos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresco_datos.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresco_datos.setRefreshing(false);
                        mostrarsnack = Snackbar.make(findViewById(R.id.ingresar), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
                        View sbView = mostrarsnack.getView();
                        sbView.setBackgroundColor(Color.rgb(13,112,110));
                        mostrarsnack.show();
                        String consulta2 = "http://192.168.43.250/sistemariego_app/mostrarClima.php";
                        EnviarRecibirDatos(consulta2);
                        String consulta5 = "http://192.168.43.250/sistemariego_app_admin/mostrar_sensores.php";
                        EnviarRecibir_Sensores(consulta5);
                    }
                },300);
            }
        });

        mostrarsnack = Snackbar.make(findViewById(R.id.ingresar), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
        String consulta2 = "http://192.168.43.250/sistemariego_app/mostrarClima.php";
        EnviarRecibirDatos(consulta2);
        String consulta5 = "http://192.168.43.250/sistemariego_app_admin/mostrar_sensores.php";
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

        if (id == R.id.menuu) {

        } else if (id == R.id.planesriego) {
            Intent i = new Intent(getApplicationContext(), Planes_Riego.class);
            startActivity(i);
            //startActivity(new Intent(DHT11.this, DS18B20.class));
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            //finish();
        } else if (id == R.id.suelo) {
            Intent i = new Intent(getApplicationContext(), Suelo.class);
            startActivity(i);
            //startActivity(new Intent(DHT11.this, DS18B20.class));
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            //finish();
        } else if (id == R.id.us) {
            Intent i = new Intent(getApplicationContext(), Usuarios.class);
            startActivity(i);

        } else if (id == R.id.cu) {
            Intent i = new Intent(getApplicationContext(), Cultivos_Notificaciones.class);
            startActivity(i);

        } else if (id == R.id.in) {
            Intent i = new Intent(getApplicationContext(), Invernaderos.class);
            startActivity(i);

        } else if (id == R.id.h1) {
           Intent i = new Intent(getApplicationContext(), H_Riego.class);
            startActivity(i);

        } else if (id == R.id.h2) {
            Intent i = new Intent(getApplicationContext(), H_Suelo.class);
            startActivity(i);

        } else if (id == R.id.h3) {
             Intent i = new Intent(getApplicationContext(), H_Clima.class);
            startActivity(i);

        } else if (id == R.id.h4) {
             Intent i = new Intent(getApplicationContext(), H_Sesion.class);
            startActivity(i);

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
