package administracion.admin.com.riego;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.regex.Pattern;

import underhome.weather.com.underhome.R;

public class H_Suelo extends AppCompatActivity {

    EditText buscar;
    Button aceptar;
    TextInputLayout til;
    Snackbar mostrarsnack;
    ProgressDialog progressBarbuscar;
    private RecyclerView recyclerhsuelo;
    private RecyclerViewAdapter_HSUELO adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Datah> listadatah = new ArrayList<Datah>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h__suelo);
        setTitle("Historial Suelo");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buscar = findViewById(R.id.buscar);
        aceptar = findViewById(R.id.aceptar);
        recyclerhsuelo = findViewById(R.id.recyclerhsuelo);
        til = findViewById(R.id.til);

        if (Build.VERSION.SDK_INT < 21)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buscar.getText().length() == 0)
                {
                    til.setError("Advertencia: Campo de Busqueda Vacio");
                }
                else{
                    til.setError(null);
                    Pattern patron2 = Pattern.compile("^[a-zA-Z1-9 ]+$");
                    if(!patron2.matcher(buscar.getText()).matches() || buscar.getText().length() > 45){
                        buscar.setError("No se admite caracteres especiales o excederse");
                    }
                    else{
                        til.setError(null);
                        progressBarbuscar= new ProgressDialog(H_Suelo.this);
                        progressBarbuscar.dismiss();
                        progressBarbuscar.setTitle("Consultando Datos");
                        progressBarbuscar.setMessage("Espere un Momento...");
                        progressBarbuscar.setCancelable(false);
                        progressBarbuscar.show();
                        String registro = "http://192.168.43.250/sistemariego_app_admin/buscar_hsuelo.php?a=" + buscar.getText();
                        registro = registro.replace(" ","%20");
                        EnviarRecibirDatos(registro);

                    }
                }
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
                        CargarDatos(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mostrarsnack = Snackbar.make(findViewById(R.id.aceptar), "No se ha encontrado datos", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = mostrarsnack.getView();
                    sbView.setBackgroundColor(Color.rgb(13,112,110));
                    mostrarsnack.show();
                    buscar.setText("");
                    progressBarbuscar.dismiss();
                    listadatah.clear();
                    ejecutar_recyclerview();
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


    public void CargarDatos(JSONArray ja){
        listadatah.clear();
        ejecutar_recyclerview();
        for(int i=0;i<ja.length();i+=4){
            try{
                listadatah.add(new Datah("ID: "+ja.getString(i),"Tipo Cultivo: "+ja.getString(i+1),"Tipo Suelo: "+ja.getString(i+2), "Abono: "+ja.getString(i+3)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
        progressBarbuscar.dismiss();
        buscar.setText("");
    }

    public void ejecutar_recyclerview(){
        recyclerhsuelo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(H_Suelo.this);
        recyclerhsuelo.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_HSUELO(listadatah);
        recyclerhsuelo.setAdapter(adapter);
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
}
