package underhome.weather.com.underhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import java.util.Timer;
import java.util.TimerTask;

public class DHT11 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView temp,hum;
    ProgressDialog progressBar2;
    Button actualizar;
    Transition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht11);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        temp = (TextView)findViewById(R.id.temp);
        hum = (TextView)findViewById(R.id.hum);
        actualizar = (Button)findViewById(R.id.actualizar);
        setTitle("Sensor DHT11");
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        String datotemperatura=(String)extras.get("temperatura");
        String datohumedad=(String)extras.get("humedad");
        temp.setText(datotemperatura);
        hum.setText(datohumedad);

        String consulta = "http://systemchile.com/sensorDHT11/mostrar.php";
        EnviarRecibirDatos(consulta);

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            String consulta = "http://systemchile.com/sensorDHT11/mostrar.php";
                            EnviarRecibirDatos(consulta);
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta = "http://systemchile.com/sensorDHT11/mostrar.php";
                EnviarRecibirDatos2(consulta);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            startActivity(new Intent(DHT11.this, DS18B20.class));
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

    public void EnviarRecibirDatos2(String URL){
        progressBar2= new ProgressDialog(DHT11.this);
        progressBar2.setTitle("Actualizar Datos");
        progressBar2.setMessage("Espere un Momento...");
        progressBar2.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        progressBar2.dismiss();
                        Cargar(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    temp.setText("Actualizando...");
                    hum.setText("Actualizando...");
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }


    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                //if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Cargar(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                //}
                /*else {
                    temp.setText("Esperando Datos...");
                    hum.setText("Esperando Datos...");
                }*/

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void Cargar(JSONArray ja){

        for(int i=0;i<ja.length();i+=2){

            try {
                temp.setText(""+ja.getString(i+0)+"° C.");
                hum.setText(""+ja.getString(i+1)+" %");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
