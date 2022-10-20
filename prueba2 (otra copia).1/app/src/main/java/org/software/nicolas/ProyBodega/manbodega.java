package org.software.nicolas.ProyBodega;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
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

public class manbodega extends AppCompatActivity{

        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listar;
    EditText buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manbodega);
        buscar=(EditText)findViewById(R.id.buscar);
        listar=(ListView)findViewById(R.id.listaegreso);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("MUN. DE PERALILLO");
        //String consulta = "http://10.251.151.81/ProyBodega/ventanahistorialegreso/mostrarhistorialegreso.php";
        //EnviarRecibirDatos(consulta);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                    listar.setAdapter(null);
                }else {
                    String consultas = "http://10.251.151.81/ProyBodega/ventanahistorialegreso/buscar.php?user=" + buscar.getText().toString();
                    consultas = consultas.replace(" ", "%20");
                    EnviarRecibirDatos(consultas);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manbodega, menu);
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
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }



    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=11){

            try {
                lista.add("ID: "+ja.getString(i)+", Cod. Producto: "+ja.getString(i+1)+", Producto: "+ja.getString(i+2)+", Rut: "+ja.getString(i+3)+", Nombre: "+ja.getString(i+4)+", Departamento: "+ja.getString(i+5)+", Cantidad: "+ja.getString(i+6)+", Hora: "+ja.getString(i+7)+", Fecha: "+ja.getString(i+8) + ", Origen: "+ja.getString(i+9)+", Descripcion: "+ja.getString(i+10));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(getApplicationContext(),manhistorialingresos.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(getApplicationContext(),mancreaproducto.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(getApplicationContext(),mancreastock.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(getApplicationContext(),menu.class);
            startActivity(i);
            finish();
        } else if (id == R.id.historialiniciosesion){
            Intent i = new Intent(getApplicationContext(),maniniciosesion.class);
            startActivity(i);
            finish();

        } else if (id == R.id.historialcierresesion){
            Intent i = new Intent(getApplicationContext(),mancierresesion.class);
            startActivity(i);
            finish();
        } else if (id == R.id.almacenstock){
            Intent i = new Intent(getApplicationContext(),manalmacenstock.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
}