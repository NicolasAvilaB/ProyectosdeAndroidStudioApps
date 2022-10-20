package administracion.admin.com.riego;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

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

public class Planes_Riego extends AppCompatActivity implements DialogoEliminar.FinalizoCuadroDialogo, DialogoTiempo.FinalizoCuadroDialogo2, DialogoHora.FinalizoCuadroDialogo3{

    Button nuevo, eliminar, modificar, guardar, cancelar, busqueda;
    Snackbar mostrarsnack;
    EditText ID, TIPOPLAN, TIPOCULTIVO, TIEMPOREGADIO, HORA, FECHA, BUSCAR;
    TextInputLayout TIPOPLAN1, TIPOCULTIVO1, TIEMPOREGADIO1, HORA1, FECHA1, BUSCAR1;
    CardView tarjetariego;
    ProgressDialog progressBarbuscar;
    SwipeRefreshLayout refresco_datos;
    boolean valor = false;
    String valor_tiempo = "";
    String valor_hora = "";
    Context mensaje_flotante_eliminando;
    Context mensaje_flotante_tiempo;
    Context mensaje_flotante_hora;
    private RecyclerView recyclerriego;
    private RecyclerViewAdapter_riego adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data1> listadata1 = new ArrayList<Data1>();
    int varseleccion = 0;
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes__riego);
        setTitle("Gestion Planes de Riego");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mensaje_flotante_eliminando = this;
        mensaje_flotante_tiempo = this;
        mensaje_flotante_hora = this;
        refresco_datos = findViewById(R.id.refresco_datos);
        TIPOPLAN1 = findViewById(R.id.TIPOPLAN1);
        TIPOCULTIVO1 = findViewById(R.id.TIPOCULTIVO1);
        TIEMPOREGADIO1 = findViewById(R.id.TIEMPOREGADIO1);
        HORA1 = findViewById(R.id.HORA1);
        FECHA1 = findViewById(R.id.FECHA1);
        BUSCAR1 = findViewById(R.id.BUSCAR1);
        ID = findViewById(R.id.ID);
        TIPOPLAN = findViewById(R.id.TIPOPLAN);
        TIPOPLAN.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        TIPOPLAN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                TIPOPLAN.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TIPOPLAN.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                TIPOPLAN.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        TIPOCULTIVO = findViewById(R.id.TIPOCULTIVO);
        TIPOCULTIVO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        TIPOCULTIVO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                TIPOCULTIVO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TIPOCULTIVO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                TIPOCULTIVO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        TIEMPOREGADIO = findViewById(R.id.TIEMPOREGADIO);
        HORA = findViewById(R.id.HORA);
        FECHA = findViewById(R.id.FECHA);
        BUSCAR = findViewById(R.id.BUSCAR);
        nuevo = findViewById(R.id.nuevo);
        eliminar = findViewById(R.id.eliminar);
        modificar = findViewById(R.id.modificar);
        guardar = findViewById(R.id.guardar);
        cancelar = findViewById(R.id.cancelar);
        busqueda = findViewById(R.id.busqueda);
        recyclerriego = findViewById(R.id.recyclerriego);
        tarjetariego = findViewById(R.id.tarjetariego);
        actualizar_datos();
        refresco_datos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresco_datos.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresco_datos.setRefreshing(false);
                        actualizar_datos();
                    }
                },300);
            }
        });

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                BUSCAR.setText("");
                progressBarbuscar= new ProgressDialog(Planes_Riego.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Consultando Identificador");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manriego/generar_id.php";
                EnviarRecibir_ID(consulta2);

            }});

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                BUSCAR.setText("");
                varseleccion = 2;
                refresco_datos.setEnabled(false);
                TIPOPLAN.setFocusable(true);
                TIPOPLAN.requestFocus();
                habilitar_desabilitar(true, false);
            }});

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                new DialogoEliminar(mensaje_flotante_eliminando, Planes_Riego.this);
            }});

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (varseleccion == 1){
                    guardar_datos();
                }
                else if (varseleccion == 2){
                    modificar_datos();
                }
            }});

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varseleccion = 0;
                refresco_datos.setEnabled(true);
                actualizar_datos();
                habilitar_desabilitar(false, true);
            }});

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar_datos();
            }
        });

        TIEMPOREGADIO.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                }else{
                    new DialogoTiempo(mensaje_flotante_tiempo, Planes_Riego.this);
                }
            }
        });

        TIEMPOREGADIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogoTiempo(mensaje_flotante_tiempo, Planes_Riego.this);
            }
        });

        HORA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                }else{
                    new DialogoHora(mensaje_flotante_hora, Planes_Riego.this);
                }
            }
        });

        HORA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogoHora(mensaje_flotante_hora, Planes_Riego.this);
            }
        });

        FECHA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                }else{
                    c = Calendar.getInstance();
                    int dia =c.get(Calendar.DAY_OF_MONTH);
                    int año = c.get(Calendar.YEAR);
                    int mes = c.get(Calendar.MONTH);
                    dpd = new DatePickerDialog(Planes_Riego.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            int v = i1;
                            int v2 = i2;
                            v +=1;
                            if (v > 9){
                                if (v2 <= 9){
                                    FECHA.setText(""+i+"-"+v+"-0"+i2);
                                }else if (v2 > 9){
                                    FECHA.setText(""+i+"-"+v+"-"+i2);
                                }
                            }else if (v <= 9){
                                if (v2 <= 9){
                                    FECHA.setText(""+i+"-0"+v+"-0"+i2);
                                }else if (v2 > 9){
                                    FECHA.setText(""+i+"-0"+v+"-"+i2);
                                }
                            }
                        }
                    }, dia, mes, año);
                    dpd.show();
                }
            }
        });

        FECHA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int dia =c.get(Calendar.DAY_OF_MONTH);
                int año = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                dpd = new DatePickerDialog(Planes_Riego.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int v = i1;
                        int v2 = i2;
                        v +=1;
                        if (v > 9){
                            if (v2 <= 9){
                                FECHA.setText(""+i+"-"+v+"-0"+i2);
                            }else if (v2 > 9){
                                FECHA.setText(""+i+"-"+v+"-"+i2);
                            }
                        }else if (v <= 9){
                            if (v2 <= 9){
                                FECHA.setText(""+i+"-0"+v+"-0"+i2);
                            }else if (v2 > 9){
                                FECHA.setText(""+i+"-0"+v+"-"+i2);
                            }
                        }
                    }
                }, dia, mes, año);
                dpd.show();
            }
        });
    }

    public void limpiar_residuo(){
        TIPOPLAN.setText("");
        TIPOCULTIVO.setText("");
        TIEMPOREGADIO.setText("");
        HORA.setText("");
        FECHA.setText("");
        BUSCAR.setText("");

    }

    public void habilitar_desabilitar(boolean a, boolean b){
        TIPOPLAN.setEnabled(a);
        TIPOCULTIVO.setEnabled(a);
        TIEMPOREGADIO.setEnabled(a);
        HORA.setEnabled(a);
        FECHA.setEnabled(a);
        nuevo.setEnabled(b);
        modificar.setEnabled(b);
        eliminar.setEnabled(b);
        guardar.setEnabled(a);
        cancelar.setEnabled(a);
        busqueda.setEnabled(b);
        BUSCAR.setEnabled(b);
    }

    public void guardar_datos(){
        if (TIPOPLAN.getText().length() == 0)
        {
            TIPOPLAN1.setError("Advertencia: Campo Tipo Plan Vacio");
        }
        else{
            TIPOPLAN1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron.matcher(TIPOPLAN.getText()).matches() || TIPOPLAN.getText().length() > 45) {
                TIPOPLAN1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else {
                TIPOPLAN1.setError(null);
            }
        }
        if (TIPOCULTIVO.getText().length() == 0) {
            TIPOCULTIVO1.setError("Advertencia: Campo Tipo Cultivo Vacio");
        }
        else{
            TIPOCULTIVO1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z]+$");
            if (!patron2.matcher(TIPOCULTIVO.getText()).matches() || TIPOCULTIVO.getText().length() > 45){
                TIPOCULTIVO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else {
                TIPOCULTIVO1.setError(null);
            }
        }

        if (TIEMPOREGADIO.getText().length() == 0) {
            TIEMPOREGADIO1.setError("Advertencia: Campo Tiempo Riego Vacio");
        }
        else{
            TIEMPOREGADIO1.setError(null);
            Pattern patron3 = Pattern.compile("^[1-9]+$");
            if (!patron3.matcher(TIEMPOREGADIO.getText()).matches() || TIEMPOREGADIO.getText().length() > 3) {
                TIEMPOREGADIO1.setError("No se permite caracteres especiales ni sobreexcederse de 3 numeros");
            }
            else {
                TIEMPOREGADIO1.setError(null);
            }
        }
        if (HORA.getText().length() == 0){

            HORA1.setError("Advertencia: Campo Hora Vacio");
        }else{
            HORA1.setError(null);
        }
        if (FECHA.getText().length() == 0){
            FECHA1.setError("Advertencia: Campo Fecha Vacio");
        }
        else {
            FECHA1.setError(null);
        }
            /*else if (!patron.matcher(HORA.getText()).matches() || HORA.getText().length() > 45){
                HORA1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else if (!patron.matcher(FECHA.getText()).matches() || FECHA.getText().length() > 45){
                FECHA1.s.etError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }*/
        if (TIPOPLAN1.getError() == null && TIPOCULTIVO1.getError() == null && TIEMPOREGADIO1.getError() == null && HORA1.getError() == null && FECHA1.getError() == null) {
            TIPOPLAN1.setError(null);
            TIPOCULTIVO1.setError(null);
            TIEMPOREGADIO1.setError(null);
            HORA1.setError(null);
            FECHA1.setError(null);
            progressBarbuscar= new ProgressDialog(Planes_Riego.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Guardando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro = "http://192.168.43.250/sistemariego_app_admin/manriego/ingreso_riego.php?i=" + ID.getText() + "&n=" + TIPOPLAN.getText() + "&cl=" + TIPOCULTIVO.getText() + "&c=" + TIEMPOREGADIO.getText() + "&e=" + HORA.getText() + "&t=" + FECHA.getText();
            registro = registro.replace(" ","%20");
            EnviarRecibirDatos2(registro);
            varseleccion = 0;
            refresco_datos.setEnabled(true);
            habilitar_desabilitar(false, true);

        }

    }

    public void modificar_datos(){
        if (TIPOPLAN.getText().length() == 0)
        {
            TIPOPLAN1.setError("Advertencia: Campo Tipo Plan Vacio");
        }
        else{
            TIPOPLAN1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron.matcher(TIPOPLAN.getText()).matches() || TIPOPLAN.getText().length() > 45) {
                TIPOPLAN1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else {
                TIPOPLAN1.setError(null);
            }
        }
        if (TIPOCULTIVO.getText().length() == 0) {
            TIPOCULTIVO1.setError("Advertencia: Campo Tipo Cultivo Vacio");
        }
        else{
            TIPOCULTIVO1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z]+$");
            if (!patron2.matcher(TIPOCULTIVO.getText()).matches() || TIPOCULTIVO.getText().length() > 45){
                TIPOCULTIVO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else {
                TIPOCULTIVO1.setError(null);
            }
        }

        if (TIEMPOREGADIO.getText().length() == 0) {
            TIEMPOREGADIO1.setError("Advertencia: Campo Tiempo Riego Vacio");
        }
        else{
            TIEMPOREGADIO1.setError(null);
            Pattern patron3 = Pattern.compile("^[1-9]+$");
            if (!patron3.matcher(TIEMPOREGADIO.getText()).matches() || TIEMPOREGADIO.getText().length() > 45) {
                TIEMPOREGADIO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else {
                TIEMPOREGADIO1.setError(null);
            }
        }
        if (HORA.getText().length() == 0){

            HORA1.setError("Advertencia: Campo Hora Vacio");
        }else{
            HORA1.setError(null);
        }
        if (FECHA.getText().length() == 0){
            FECHA1.setError("Advertencia: Campo Fecha Vacio");
        }
        else {
            FECHA1.setError(null);
        }
        if (TIPOPLAN1.getError() == null && TIPOCULTIVO1.getError() == null && TIEMPOREGADIO1.getError() == null && HORA1.getError() == null && FECHA1.getError() == null) {
            TIPOPLAN1.setError(null);
            TIPOCULTIVO1.setError(null);
            TIEMPOREGADIO1.setError(null);
            HORA1.setError(null);
            FECHA1.setError(null);
            progressBarbuscar = new ProgressDialog(Planes_Riego.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Guardando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro = "http://192.168.43.250/sistemariego_app_admin/manriego/modificar_riego.php?i=" + ID.getText() + "&n=" + TIPOPLAN.getText() + "&cl=" + TIPOCULTIVO.getText() + "&c=" + TIEMPOREGADIO.getText() + "&e=" + HORA.getText() + "&t=" + FECHA.getText();
            registro = registro.replace(" ", "%20");
            EnviarRecibirDatos2(registro);
            varseleccion = 0;
            refresco_datos.setEnabled(true);
            habilitar_desabilitar(false, true);
        }
    }

    public void buscar_datos(){
        if (BUSCAR.getText().length() == 0){
            BUSCAR1.setError("Advertencia: Campo de Busqueda Vacio");
        }else {
            BUSCAR1.setError(null);
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13, 112, 110));
            mostrarsnack.show();
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manriego/buscar.php?n=" + BUSCAR.getText();
            EnviarRecibirDatos(consulta2);
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
                        CargarDatos_Riego(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mostrarsnack.dismiss();
                    mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "No hay Datos de Planes de Riego", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = mostrarsnack.getView();
                    sbView.setBackgroundColor(Color.rgb(13,112,110));
                    mostrarsnack.show();
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
        try{
            int a = 0;
            ID.setText(""+ja.getString(a));
            TIPOPLAN.setText(""+ja.getString(a+1));
            TIPOCULTIVO.setText(""+ja.getString(a+2));
            TIEMPOREGADIO.setText(""+ja.getString(a+3));
            HORA.setText(""+ja.getString(a+4));
            FECHA.setText(""+ja.getString(a+5));
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata1.clear();
        ejecutar_recyclerview_riego();
        for(int i=0;i<ja.length();i+=6){
            try{
                listadata1.add(new Data1("ID: "+ja.getString(i),"Tipo Plan: "+ja.getString(i+1),"Tipo Cultivo: "+ja.getString(i+2), "Tiempo Regadio: "+ja.getString(i+3), "Hora: "+ja.getString(i+4), "Fecha: "+ja.getString(i+5)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
        mostrarsnack.dismiss();

    }

    public void ejecutar_recyclerview_riego(){
        recyclerriego.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Planes_Riego.this);
        recyclerriego.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_riego(listadata1);
        recyclerriego.setAdapter(adapter);
    }

    public void actualizar_datos(){
        mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mostrar_registros_planes.php";
        EnviarRecibirDatos(consulta2);
    }

    public void EnviarRecibir_ID(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarID(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    ID.setText("1");
                    varseleccion = 1;
                    refresco_datos.setEnabled(false);
                    limpiar_residuo();
                    habilitar_desabilitar(true, false);
                    progressBarbuscar.dismiss();
                    TIPOPLAN.setFocusable(true);
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

    public void CargarID(JSONArray ja){
        try{
            int a = 0;
            String valor = ja.getString(a);
            if (valor == "" || valor == "null"){
                ID.setText("1");
                varseleccion = 1;
                refresco_datos.setEnabled(false);
                limpiar_residuo();
                habilitar_desabilitar(true, false);
                progressBarbuscar.dismiss();
                TIPOPLAN.setFocusable(true);
            }
            else{
                int suma = Integer.parseInt(valor) + 1;
                ID.setText(""+suma);
                varseleccion = 1;
                refresco_datos.setEnabled(false);
                limpiar_residuo();
                habilitar_desabilitar(true, false);
                progressBarbuscar.dismiss();
                TIPOPLAN.setFocusable(true);
            }
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Campos con * requeridos", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void ResultadoCuadroDialogo(boolean res) {
        valor = res;
        if (valor == true){
            if (BUSCAR.getText().length() > 0)
            {
                BUSCAR.setText("");
            }
            progressBarbuscar= new ProgressDialog(Planes_Riego.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Eliminando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro0 = "http://192.168.43.250/sistemariego_app_admin/manriego/eliminar_riego.php?i="+ID.getText();
            registro0 = registro0.replace(" ","%20");
            EnviarRecibirDatos2(registro0);
        }
        else if (valor == false){
        }
    }

    public void EnviarRecibirDatos2(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarDatos_Riego2(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    progressBarbuscar.dismiss();
                    mostrarsnack= Snackbar.make(findViewById(R.id.nuevo), "Datos Manipulados Exitosamente", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = mostrarsnack.getView();
                    sbView.setBackgroundColor(Color.rgb(13,112,110));
                    mostrarsnack.show();
                    ID.setText("");
                    limpiar_residuo();
                    listadata1.clear();
                    ejecutar_recyclerview_riego();
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

    public void CargarDatos_Riego2(JSONArray ja){
        try{
            int a = 0;
            ID.setText(""+ja.getString(a));
            TIPOPLAN.setText(""+ja.getString(a+1));
            TIPOCULTIVO.setText(""+ja.getString(a+2));
            TIEMPOREGADIO.setText(""+ja.getString(a+3));
            HORA.setText(""+ja.getString(a+4));
            FECHA.setText(""+ja.getString(a+5));
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata1.clear();
        ejecutar_recyclerview_riego();
        for(int i=0;i<ja.length();i+=6){
            try{
                listadata1.add(new Data1("ID: "+ja.getString(i),"Tipo Plan: "+ja.getString(i+1),"Tipo Cultivo: "+ja.getString(i+2), "Tiempo Regadio: "+ja.getString(i+3), "Hora: "+ja.getString(i+4), "Fecha: "+ja.getString(i+5)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }

        progressBarbuscar.dismiss();
        mostrarsnack= Snackbar.make(findViewById(R.id.nuevo), "Datos Manipulados Exitosamente", Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Log.i("ActionBar","Atras!");
                varseleccion = 0;
                valor = false;
                refresco_datos.setEnabled(true);
                habilitar_desabilitar(false, true);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void ResultadoCuadroDialogo2(String ress) {
        valor_tiempo = ress;
        TIEMPOREGADIO.setText(""+valor_tiempo);
    }

    @Override
    public void ResultadoCuadroDialogo3(String ress) {
        valor_hora = ress;
        HORA.setText(""+valor_hora);
    }
}
