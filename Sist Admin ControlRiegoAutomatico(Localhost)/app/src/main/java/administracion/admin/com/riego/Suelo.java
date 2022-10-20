package administracion.admin.com.riego;

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
import android.view.View;
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

public class Suelo extends AppCompatActivity implements DialogoEliminar.FinalizoCuadroDialogo{

    Button nuevo, eliminar, modificar, guardar, cancelar, busqueda;
    Snackbar mostrarsnack;
    EditText ID, TIPOSUELO, ESTADOSUELO, ABONO, TIPOCULTIVO, INVERNADERO, BUSCAR;
    TextInputLayout TIPOSUELO1, ESTADOSUELO1, ABONO1, TIPOCULTIVO1, INVERNADERO1, BUSCAR1;
    ProgressDialog progressBarbuscar;
    SwipeRefreshLayout refresco_datos;
    boolean valor = false;
    Context mensaje_flotante_eliminando;
    CardView tarjetasuelo;
    private RecyclerView recyclersuelo;
    private RecyclerViewAdapter_suelo adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data2> listadata2 = new ArrayList<Data2>();
    int varseleccion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suelo);
        setTitle("Gestion Suelos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mensaje_flotante_eliminando = this;
        refresco_datos = findViewById(R.id.refresco_datos);
        TIPOSUELO1 = findViewById(R.id.TIPOSUELO1);
        ESTADOSUELO1 = findViewById(R.id.ESTADOSUELO1);
        ABONO1 = findViewById(R.id.ABONO1);
        TIPOCULTIVO1 = findViewById(R.id.TIPOCULTIVO1);
        INVERNADERO1 = findViewById(R.id.INVERNADERO1);
        BUSCAR1 = findViewById(R.id.BUSCAR1);
        ID = findViewById(R.id.ID);
        TIPOSUELO = findViewById(R.id.TIPOSUELO);
        TIPOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        TIPOSUELO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                TIPOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TIPOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                TIPOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        ESTADOSUELO = findViewById(R.id.ESTADOSUELO);
        ESTADOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        ESTADOSUELO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ESTADOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ESTADOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                ESTADOSUELO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        ABONO = findViewById(R.id.ABONO);
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
        INVERNADERO = findViewById(R.id.INVERNADERO);
        BUSCAR = findViewById(R.id.BUSCAR);
        nuevo = findViewById(R.id.nuevo);
        eliminar = findViewById(R.id.eliminar);
        modificar = findViewById(R.id.modificar);
        guardar = findViewById(R.id.guardar);
        cancelar = findViewById(R.id.cancelar);
        busqueda = findViewById(R.id.busqueda);
        recyclersuelo = findViewById(R.id.recyclersuelo);
        tarjetasuelo = findViewById(R.id.tarjetasuelo);
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
                progressBarbuscar= new ProgressDialog(Suelo.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Consultando Identificador");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mansuelo/generar_id.php";
                EnviarRecibir_ID(consulta2);

            }});

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                BUSCAR.setText("");
                varseleccion = 2;
                refresco_datos.setEnabled(false);
                TIPOSUELO.setFocusable(true);
                TIPOSUELO.requestFocus();
                habilitar_desabilitar(true, false);
            }});

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                new DialogoEliminar(mensaje_flotante_eliminando, Suelo.this);
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
                TIPOSUELO1.setError(null);
                ESTADOSUELO1.setError(null);
                ABONO1.setError(null);
                INVERNADERO1.setError(null);
                TIPOCULTIVO1.setError(null);
            }});

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar_datos();
            }
        });
    }

    public void limpiar_residuo(){
        TIPOSUELO.setText("");
        ESTADOSUELO.setText("");
        ABONO.setText("");
        TIPOCULTIVO.setText("");
        INVERNADERO.setText("");
        BUSCAR.setText("");

    }

    public void guardar_datos() {
        if (TIPOSUELO.getText().length() == 0)
        {
            TIPOSUELO1.setError("Advertencia: Campo Tipo Suelo Vacio");
        }
        else{
            TIPOSUELO1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron.matcher(TIPOSUELO.getText()).matches() ||  TIPOSUELO.getText().length() > 45) {
                TIPOSUELO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else{
                TIPOSUELO1.setError(null);
            }
        }
        if (ESTADOSUELO.getText().length() == 0) {
            ESTADOSUELO1.setError("Advertencia: Campo Estado Suelo Vacio");
        }
        else{
            ESTADOSUELO1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron2.matcher(ESTADOSUELO.getText()).matches() || ESTADOSUELO.getText().length() > 45) {
                ESTADOSUELO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                ESTADOSUELO1.setError(null);
            }
        }
        if (TIPOCULTIVO.getText().length() == 0){
            TIPOCULTIVO1.setError("Advertencia: Campo Tipo Cultivo Vacio");
        }
        else{
            TIPOCULTIVO1.setError(null);
            Pattern patron4 = Pattern.compile("^[a-zA-Z]+$");
            if (!patron4.matcher(TIPOCULTIVO.getText()).matches() || TIPOCULTIVO.getText().length() > 45) {
                TIPOCULTIVO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else{
                TIPOCULTIVO1.setError(null);
            }
        }
        if (ABONO.getText().length() == 0){
            ABONO1.setError(null);
        }
        else {
            Pattern patron3 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron3.matcher(ABONO.getText()).matches() || ABONO.getText().length() > 45) {
                ABONO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            } else {
                ABONO1.setError(null);
            }
        }
        Pattern patron5 = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron5.matcher(INVERNADERO.getText()).matches() || INVERNADERO.getText().length() > 45) {
            INVERNADERO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
        }
        else {
            INVERNADERO1.setError(null);
        }
        INVERNADERO1.setError("Comprobando Invernadero...");
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mansuelo/buscar.php?n=" + INVERNADERO.getText();
        consulta2 = consulta2.replace(" ", "%20");
        VALIDAR_DATOS_INVERNADERO(consulta2);

    }

    public void modificar_datos() {
        if (TIPOSUELO.getText().length() == 0)
        {
            TIPOSUELO1.setError("Advertencia: Campo Tipo Suelo Vacio");
        }
        else{
            TIPOSUELO1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron.matcher(TIPOSUELO.getText()).matches() ||  TIPOSUELO.getText().length() > 45) {
                TIPOSUELO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else{
                TIPOSUELO1.setError(null);
            }
        }
        if (ESTADOSUELO.getText().length() == 0) {
            ESTADOSUELO1.setError("Advertencia: Campo Estado Suelo Vacio");
        }
        else{
            ESTADOSUELO1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron2.matcher(ESTADOSUELO.getText()).matches() || ESTADOSUELO.getText().length() > 45) {
                ESTADOSUELO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                ESTADOSUELO1.setError(null);
            }
        }
        if (TIPOCULTIVO.getText().length() == 0){
            TIPOCULTIVO1.setError("Advertencia: Campo Tipo Cultivo Vacio");
        }
        else{
            TIPOCULTIVO1.setError(null);
            Pattern patron4 = Pattern.compile("^[a-zA-Z]+$");
            if (!patron4.matcher(TIPOCULTIVO.getText()).matches() || TIPOCULTIVO.getText().length() > 45) {
                TIPOCULTIVO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else{
                TIPOCULTIVO1.setError(null);
            }
        }
        if (ABONO.getText().length() == 0){
            ABONO1.setError(null);
        }
        else {
            Pattern patron3 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron3.matcher(ABONO.getText()).matches() || ABONO.getText().length() > 45) {
                ABONO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            } else {
                ABONO1.setError(null);
            }
        }
        Pattern patron5 = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron5.matcher(INVERNADERO.getText()).matches() || INVERNADERO.getText().length() > 45) {
            INVERNADERO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
        }
        else {
            INVERNADERO1.setError(null);
        }
        INVERNADERO1.setError("Comprobando Invernadero...");
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mansuelo/buscar.php?n=" + INVERNADERO.getText();
        consulta2 = consulta2.replace(" ", "%20");
        VALIDAR_DATOS_INVERNADERO(consulta2);
    }

    public void habilitar_desabilitar(boolean a, boolean b){
        TIPOSUELO.setEnabled(a);
        ESTADOSUELO.setEnabled(a);
        ABONO.setEnabled(a);
        TIPOCULTIVO.setEnabled(a);
        INVERNADERO.setEnabled(a);
        nuevo.setEnabled(b);
        modificar.setEnabled(b);
        eliminar.setEnabled(b);
        guardar.setEnabled(a);
        cancelar.setEnabled(a);
        busqueda.setEnabled(b);
        BUSCAR.setEnabled(b);
    }

    public void buscar_datos() {
        if (BUSCAR.getText().length() == 0){
            BUSCAR1.setError("Advertencia: Campo de Busqueda Vacio");
        }else {
        BUSCAR1.setError(null);
        mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13, 112, 110));
        mostrarsnack.show();
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mansuelo/buscar.php?n=" + BUSCAR.getText();
        consulta2 = consulta2.replace(" ", "%20");
        EnviarRecibirDatos(consulta2);
        }
    }

    public void actualizar_datos(){
        mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mostrar_registros_suelo.php";
        EnviarRecibirDatos(consulta2);
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
                        CargarDatos_Suelo(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mostrarsnack.dismiss();
                    mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "No hay Datos de Suelo", Snackbar.LENGTH_LONG).setAction("Action", null);
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

    public void CargarDatos_Suelo(JSONArray ja){
        try{
            int a = 0;
            ID.setText(""+ja.getString(a));
            TIPOSUELO.setText(""+ja.getString(a+1));
            ESTADOSUELO.setText(""+ja.getString(a+2));
            ABONO.setText(""+ja.getString(a+3));
            TIPOCULTIVO.setText(""+ja.getString(a+4));
            INVERNADERO.setText(""+ja.getString(a+5));
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata2.clear();
        ejecutar_recyclerview_suelo();
        for(int i=0;i<ja.length()/*ja.length()*/;i+=7){
            try{
                listadata2.add(new Data2("ID: "+ja.getString(i),"Tipo Suelo: "+ja.getString(i+1),"Estado Suelo: "+ja.getString(i+2), "Abono: "+ja.getString(i+3), "Tipo Cultivo: "+ja.getString(i+4), "Invernadero: "+ja.getString(i+5), "Pres. Agua: "+ja.getString(i+6)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
        mostrarsnack.dismiss();

    }

    public void ejecutar_recyclerview_suelo(){
        recyclersuelo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Suelo.this);
        recyclersuelo.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_suelo(listadata2);
        recyclersuelo.setAdapter(adapter);
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
                    TIPOSUELO.setFocusable(true);
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
                TIPOSUELO.setFocusable(true);
            }
            else{
                int suma = Integer.parseInt(valor) + 1;
                ID.setText(""+suma);
                varseleccion = 1;
                refresco_datos.setEnabled(false);
                limpiar_residuo();
                habilitar_desabilitar(true, false);
                progressBarbuscar.dismiss();
                TIPOSUELO.setFocusable(true);
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
            progressBarbuscar= new ProgressDialog(Suelo.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Eliminando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro0 = "http://192.168.43.250/sistemariego_app_admin/mansuelo/eliminar_suelo.php?i="+ID.getText();
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
                        CargarDatos_Suelo2(ja);
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
                    listadata2.clear();
                    ejecutar_recyclerview_suelo();
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

    public void CargarDatos_Suelo2(JSONArray ja){
        try{
            int a = 0;
            ID.setText(""+ja.getString(a));
            TIPOSUELO.setText(""+ja.getString(a+1));
            ESTADOSUELO.setText(""+ja.getString(a+2));
            ABONO.setText(""+ja.getString(a+3));
            TIPOCULTIVO.setText(""+ja.getString(a+4));
            INVERNADERO.setText(""+ja.getString(a+5));
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata2.clear();
        ejecutar_recyclerview_suelo();
        for(int i=0;i<ja.length()/*ja.length()*/;i+=7){
            try{
                listadata2.add(new Data2("ID: "+ja.getString(i),"Tipo Suelo: "+ja.getString(i+1),"Estado Suelo: "+ja.getString(i+2), "Abono: "+ja.getString(i+3), "Tipo Cultivo: "+ja.getString(i+4), "Invernadero: "+ja.getString(i+5), "Pres. Agua: "+ja.getString(i+6)));
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

    //Validar Invernadero
    public void VALIDAR_DATOS_INVERNADERO(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        String invernaderos = ja.getString(5);
                       if (invernaderos.equals(INVERNADERO.getText().toString())) {
                                INVERNADERO1.setError("Advertencia: Invernadero ya existente en el Registro");
                       } else {
                                INVERNADERO1.setError(null);
                                guardar_modificar();
                       }
                       if (INVERNADERO.getText().length() == 0){
                           INVERNADERO1.setError(null);
                           guardar_modificar();
                       }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                else {
                    INVERNADERO1.setError(null);
                    guardar_modificar();
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

    public void guardar_modificar(){
        if (varseleccion == 1){
            if(TIPOSUELO1.getError() == null && TIPOCULTIVO1.getError() == null && ESTADOSUELO1.getError() == null && ABONO1.getError() == null && INVERNADERO1.getError() == null) {
                TIPOSUELO1.setError(null);
                ESTADOSUELO1.setError(null);
                ABONO1.setError(null);
                TIPOCULTIVO1.setError(null);
                INVERNADERO1.setError(null);
                progressBarbuscar = new ProgressDialog(Suelo.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                if (ABONO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/ingreso_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c=" + "&e=" + TIPOCULTIVO.getText() + "&t=" + INVERNADERO.getText();
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                if (INVERNADERO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/ingreso_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c="+ ABONO.getText() + "&e=" + TIPOCULTIVO.getText() + "&t=---";
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                if (INVERNADERO.getText().length() == 0 && ABONO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/ingreso_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c=" + "&e=" + TIPOCULTIVO.getText() + "&t=---";
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }else{
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/ingreso_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c=" + ABONO.getText() + "&e=" + TIPOCULTIVO.getText() + "&t=" + INVERNADERO.getText();
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
            }
        }else if (varseleccion == 2){
            if(TIPOSUELO1.getError() == null && TIPOCULTIVO1.getError() == null && ESTADOSUELO1.getError() == null && ABONO1.getError() == null && INVERNADERO1.getError() == null) {
                TIPOSUELO1.setError(null);
                ESTADOSUELO1.setError(null);
                ABONO1.setError(null);
                TIPOCULTIVO1.setError(null);
                INVERNADERO1.setError(null);
                progressBarbuscar = new ProgressDialog(Suelo.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                if (ABONO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/modificar_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c=" + "&e=" + TIPOCULTIVO.getText() + "&t=" + INVERNADERO.getText();
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                else if (INVERNADERO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/modificar_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c="+ ABONO.getText() + "&e=" + TIPOCULTIVO.getText() + "&t=---";
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                if (INVERNADERO.getText().length() == 0 && ABONO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/modificar_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c=" + "&e=" + TIPOCULTIVO.getText() + "&t=---";
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                else{
                    String registro = "http://192.168.43.250/sistemariego_app_admin/mansuelo/modificar_suelo.php?i=" + ID.getText() + "&n=" + TIPOSUELO.getText() + "&cl=" + ESTADOSUELO.getText() + "&c="+ ABONO.getText() + "&e=" + TIPOCULTIVO.getText() + "&t=" + INVERNADERO.getText();
                    registro = registro.replace(" ", "%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
            }
        }
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
}
