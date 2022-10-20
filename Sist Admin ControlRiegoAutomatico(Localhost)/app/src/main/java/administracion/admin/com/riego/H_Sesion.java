package administracion.admin.com.riego;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import underhome.weather.com.underhome.R;

public class H_Sesion extends AppCompatActivity {

    EditText buscar, fecha1, fecha2;
    Button aceptar;
    TextInputLayout til, til2, til3;
    Snackbar mostrarsnack;
    Spinner opcion;
    ProgressDialog progressBarbuscar;
    private RecyclerView recyclerhsesion;
    private RecyclerViewAdapter_HSESION adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Datahsesion> listadatahsesion = new ArrayList<Datahsesion>();
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h__sesion);
        setTitle("Historial Sesion");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buscar = findViewById(R.id.buscar);
        fecha1 = findViewById(R.id.fecha1);
        fecha2 = findViewById(R.id.fecha2);
        aceptar = findViewById(R.id.aceptar);
        recyclerhsesion = findViewById(R.id.recyclerhsesion);
        til = findViewById(R.id.til);
        til2 = findViewById(R.id.til2);
        til3 = findViewById(R.id.til3);
        opcion = findViewById(R.id.opcion);
        String[] datostipodocumento = new String[] {"Seleccione un Filtro...","Filtro Normal","Filtro Solo Fechas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
        opcion.setAdapter(adapter);

        fecha1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                }else{
                    c = Calendar.getInstance();
                    int dia =c.get(Calendar.DAY_OF_MONTH);
                    int año = c.get(Calendar.YEAR);
                    int mes = c.get(Calendar.MONTH);
                    dpd = new DatePickerDialog(H_Sesion.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            int v = i1;
                            int v2 = i2;
                            v +=1;
                            if (v > 9){
                                if (v2 <= 9){
                                    fecha1.setText(""+i+"-"+v+"-0"+i2);
                                }else if (v2 > 9){
                                    fecha1.setText(""+i+"-"+v+"-"+i2);
                                }
                            }else if (v <= 9){
                                if (v2 <= 9){
                                    fecha1.setText(""+i+"-0"+v+"-0"+i2);
                                }else if (v2 > 9){
                                    fecha1.setText(""+i+"-0"+v+"-"+i2);
                                }
                            }
                        }
                    }, dia, mes, año);
                    dpd.show();
                }
            }
        });

        fecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int dia =c.get(Calendar.DAY_OF_MONTH);
                int año = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                dpd = new DatePickerDialog(H_Sesion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int v = i1;
                        int v2 = i2;
                        v +=1;
                        if (v > 9){
                            if (v2 <= 9){
                                fecha1.setText(""+i+"-"+v+"-0"+i2);
                            }else if (v2 > 9){
                                fecha1.setText(""+i+"-"+v+"-"+i2);
                            }
                        }else if (v <= 9){
                            if (v2 <= 9){
                                fecha1.setText(""+i+"-0"+v+"-0"+i2);
                            }else if (v2 > 9){
                                fecha1.setText(""+i+"-0"+v+"-"+i2);
                            }
                        }
                    }
                }, dia, mes, año);
                dpd.show();
            }
        });

        fecha2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                }else{
                    c = Calendar.getInstance();
                    int dia =c.get(Calendar.DAY_OF_MONTH);
                    int año = c.get(Calendar.YEAR);
                    int mes = c.get(Calendar.MONTH);
                    dpd = new DatePickerDialog(H_Sesion.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            int v = i1;
                            int v2 = i2;
                            v +=1;
                            if (v > 9){
                                if (v2 <= 9){
                                    fecha2.setText(""+i+"-"+v+"-0"+i2);
                                }else if (v2 > 9){
                                    fecha2.setText(""+i+"-"+v+"-"+i2);
                                }
                            }else if (v <= 9){
                                if (v2 <= 9){
                                    fecha2.setText(""+i+"-0"+v+"-0"+i2);
                                }else if (v2 > 9){
                                    fecha2.setText(""+i+"-0"+v+"-"+i2);
                                }
                            }
                        }
                    }, dia, mes, año);
                    dpd.show();
                }
            }
        });

        fecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int dia =c.get(Calendar.DAY_OF_MONTH);
                int año = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                dpd = new DatePickerDialog(H_Sesion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int v = i1;
                        int v2 = i2;
                        v +=1;
                        if (v > 9){
                            if (v2 <= 9){
                                fecha2.setText(""+i+"-"+v+"-0"+i2);
                            }else if (v2 > 9){
                                fecha2.setText(""+i+"-"+v+"-"+i2);
                            }
                        }else if (v <= 9){
                            if (v2 <= 9){
                                fecha2.setText(""+i+"-0"+v+"-0"+i2);
                            }else if (v2 > 9){
                                fecha2.setText(""+i+"-0"+v+"-"+i2);
                            }
                        }
                    }
                }, dia, mes, año);
                dpd.show();
            }
        });

        if (Build.VERSION.SDK_INT < 21)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern patron = Pattern.compile("^[a-zA-Z1-9 ]+$");
                String valor_seleccionado = opcion.getItemAtPosition(opcion.getSelectedItemPosition()).toString();
                if (buscar.getText().length() == 0 && buscar.isEnabled())
                {
                    til.setError("Advertencia: Campo de Busqueda Vacio");
                }
                else if (fecha1.getText().length() == 0 && fecha1.isEnabled()){
                    til2.setError("Advertencia: Campo de Fecha Origen Vacio");
                }
                else if (fecha2.getText().length() == 0 && fecha2.isEnabled()){
                    til3.setError("Advertencia: Campo de Fecha Termino Vacio");
                }
                else if (buscar.getText().length() > 45 && buscar.isEnabled()){
                    til.setError("Advertencia: No puedes excederte mas de 45 caracteres");
                }
                else if(!patron.matcher(buscar.getText()).matches() && buscar.isEnabled()){
                    til.setError("Advertencia: No puedes usar caracteres especiales");
                }
                else{
                    if (valor_seleccionado == "Seleccione un Filtro..." && buscar.isEnabled()){
                        mostrarsnack = Snackbar.make(findViewById(R.id.aceptar), "No has seleccionado el tipo de filtro", Snackbar.LENGTH_LONG).setAction("Action", null);
                        View sbView = mostrarsnack.getView();
                        sbView.setBackgroundColor(Color.rgb(13,112,110));
                        mostrarsnack.show();
                    }
                    else if (valor_seleccionado == "Filtro Normal" && buscar.isEnabled())
                    {
                        til.setError(null);
                        til2.setError(null);
                        til3.setError(null);
                        progressBarbuscar = new ProgressDialog(H_Sesion.this);
                        progressBarbuscar.dismiss();
                        progressBarbuscar.setTitle("Consultando Datos");
                        progressBarbuscar.setMessage("Espere un Momento...");
                        progressBarbuscar.setCancelable(false);
                        progressBarbuscar.show();
                        String registro = "http://192.168.43.250/sistemariego_app_admin/buscar_hsesion_normal.php?a=" + buscar.getText();
                        registro = registro.replace(" ", "%20");
                        EnviarRecibirDatos(registro);
                    }
                    else if (valor_seleccionado == "Filtro Solo Fechas" && fecha1.isEnabled() && fecha2.isEnabled()) {
                        til.setError(null);
                        til2.setError(null);
                        til3.setError(null);
                        progressBarbuscar= new ProgressDialog(H_Sesion.this);
                        progressBarbuscar.dismiss();
                        progressBarbuscar.setTitle("Consultando Datos");
                        progressBarbuscar.setMessage("Espere un Momento...");
                        progressBarbuscar.setCancelable(false);
                        progressBarbuscar.show();
                        String registro = "http://192.168.43.250/sistemariego_app_admin/buscar_hsesion_fecha.php?a=" + fecha1.getText() +"&b=" + fecha2.getText();
                        registro = registro.replace(" ","%20");
                        EnviarRecibirDatos(registro);
                    }
                }
            }
        });

        opcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = opcion.getItemAtPosition(opcion.getSelectedItemPosition()).toString();
                if (item == "Filtro Normal"){
                    habilitar_desabilitar_controles(true, false);
                    til.setError(null);
                    til2.setError(null);
                    til3.setError(null);
                    buscar.setFocusable(true);
                    buscar.requestFocus();
                    buscar.setText("");
                    fecha1.setText("");
                    fecha2.setText("");
                }else if (item == "Filtro Solo Fechas"){
                    habilitar_desabilitar_controles(false, true);
                    til.setError(null);
                    til2.setError(null);
                    til3.setError(null);
                    fecha1.setFocusable(true);
                    fecha1.requestFocus();
                    buscar.setText("");
                    fecha1.setText("");
                    fecha2.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void habilitar_desabilitar_controles(boolean a, boolean b){
        buscar.setEnabled(a);
        fecha1.setEnabled(b);
        fecha2.setEnabled(b);
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
                    fecha1.setText("");
                    fecha2.setText("");
                    progressBarbuscar.dismiss();
                    listadatahsesion.clear();
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
        listadatahsesion.clear();
        ejecutar_recyclerview();
        for(int i=0;i<ja.length();i+=7){
            try{
                listadatahsesion.add(new Datahsesion("ID: "+ja.getString(i),"Nombre: "+ja.getString(i+1),"Cargo: "+ja.getString(i+2), "Hora Inicio: "+ja.getString(i+3), "Hora Cierre: "+ja.getString(i+4), "Fecha Inicio: "+ja.getString(i+5), "Fecha Cierre: "+ja.getString(i+6)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
        progressBarbuscar.dismiss();
        buscar.setText("");
        fecha1.setText("");
        fecha2.setText("");
    }

    public void ejecutar_recyclerview(){
        recyclerhsesion.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(H_Sesion.this);
        recyclerhsesion.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_HSESION(listadatahsesion);
        recyclerhsesion.setAdapter(adapter);
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
