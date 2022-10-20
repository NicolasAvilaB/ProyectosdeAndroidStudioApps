package administracion.admin.com.riego;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;
import java.util.regex.Pattern;

import underhome.weather.com.underhome.R;

public class Usuarios extends AppCompatActivity implements DialogoEliminar.FinalizoCuadroDialogo{

    Button nuevo, eliminar, modificar, guardar, cancelar, busqueda;
    Snackbar mostrarsnack;
    TextInputLayout ID1, NOMBRE1, CLAVE1, EMAIL1, TIPOESTADO1, BUSCAR1;
    EditText ID, NOMBRE,CLAVE, EMAIL,TIPOESTADO, BUSCAR;
    Spinner CARGO;
    CardView tarjetausuarios;
    ProgressDialog progressBarbuscar;
    SwipeRefreshLayout refresco_datos;
    JSONArray ja;
    boolean valor = false;
    Context mensaje_flotante_eliminando;
    private RecyclerView recyclerusuarios;
    private RecyclerViewAdapter_usuarios adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data3> listadata3 = new ArrayList<Data3>();
    int varseleccion = 0;
    String da, da2, da3, da4, da5, da6;
    boolean a,b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        setTitle("Gestion Usuarios");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mensaje_flotante_eliminando = this;
        refresco_datos = findViewById(R.id.refresco_datos);
        ID1 = findViewById(R.id.ID1);
        NOMBRE1 = findViewById(R.id.NOMBRE1);
        CLAVE1 = findViewById(R.id.CLAVE1);
        EMAIL1 = findViewById(R.id.EMAIL1);
        TIPOESTADO1 = findViewById(R.id.TIPOESTADO1);
        BUSCAR1 = findViewById(R.id.BUSCAR1);
        ID = findViewById(R.id.ID);
        NOMBRE = findViewById(R.id.NOMBRE);
        NOMBRE.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        NOMBRE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NOMBRE.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NOMBRE.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                NOMBRE.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });

        CLAVE = findViewById(R.id.CLAVE);
        CARGO = findViewById(R.id.CARGO);

        CARGO.setEnabled(false);
        EMAIL = findViewById(R.id.EMAIL);
        TIPOESTADO = findViewById(R.id.TIPOESTADO);
        BUSCAR = findViewById(R.id.BUSCAR);
        nuevo = findViewById(R.id.nuevo);
        eliminar = findViewById(R.id.eliminar);
        modificar = findViewById(R.id.modificar);
        guardar = findViewById(R.id.guardar);
        cancelar = findViewById(R.id.cancelar);
        busqueda = findViewById(R.id.busqueda);
        recyclerusuarios = findViewById(R.id.recyclerusuarios);
        tarjetausuarios = findViewById(R.id.tarjetausuarios);
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
                progressBarbuscar= new ProgressDialog(Usuarios.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Consultando Identificador");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manusuario/generar_id.php";
                EnviarRecibir_ID(consulta2);

        }});

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                BUSCAR.setText("");
                varseleccion = 2;
                refresco_datos.setEnabled(false);
                NOMBRE.setFocusable(true);
                NOMBRE.requestFocus();
                habilitar_desabilitar(true, false);
                adaptador_spinner();
            }});

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                new DialogoEliminar(mensaje_flotante_eliminando, Usuarios.this);
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
                NOMBRE1.setError(null);
                EMAIL1.setError(null);
                CLAVE1.setError(null);
            }});

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar_datos();
            }
        });
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

    public void limpiar_residuo(){
        NOMBRE.setText("");
        CLAVE.setText("");
        String[] datostipodocumento = new String[] {"Seleccione un Cargo...","Operario","Administrador"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
        CARGO.setAdapter(adapter);
        EMAIL.setText("");
        TIPOESTADO.setText("");
        BUSCAR.setText("");

    }

    public void habilitar_desabilitar(boolean a, boolean b){
        NOMBRE.setEnabled(a);
        CLAVE.setEnabled(a);
        CARGO.setEnabled(a);
        EMAIL.setEnabled(a);
        TIPOESTADO.setEnabled(a);
        nuevo.setEnabled(b);
        modificar.setEnabled(b);
        eliminar.setEnabled(b);
        guardar.setEnabled(a);
        cancelar.setEnabled(a);
        busqueda.setEnabled(b);
        BUSCAR.setEnabled(b);
    }

    public void guardar_datos(){
        String valor_seleccionado = CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString();
        if (NOMBRE.getText().length() == 0){
            NOMBRE1.setError("Advertencia: Campo Nombre Vacio");
        }
        else{
            NOMBRE1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron.matcher(NOMBRE.getText()).matches() || NOMBRE.getText().length() > 45) {
                NOMBRE1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else{
                NOMBRE1.setError(null);
            }
            NOMBRE1.setError("Comprobando Nombre...");
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manusuario/buscar.php?n=" + NOMBRE.getText();
            consulta2 = consulta2.replace(" ", "%20");
            VALIDAR_DATOS_NOMBRE(consulta2);
        }
        if (CLAVE.getText().length() == 0) {
            CLAVE1.setError("Advertencia: Campo Clave Vacio");
        }else{
            CLAVE1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z1-9]+$");
            if (!patron2.matcher(CLAVE.getText()).matches() || CLAVE.getText().length() > 45) {
                CLAVE1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                CLAVE1.setError(null);
            }
        }
        if (valor_seleccionado == "Seleccione un Cargo...") {
                mostrarsnack.dismiss();
                mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Advertencia: Seleccione un Cargo", Snackbar.LENGTH_LONG).setAction("Action", null);
                View sbView = mostrarsnack.getView();
                sbView.setBackgroundColor(Color.rgb(13,112,110));
                mostrarsnack.show();
        }
        if (EMAIL.getText().length() == 0){
            EMAIL1.setError("Advertencia: Campo Email Vacio");
        }
        else{
            EMAIL1.setError(null);
            Pattern patron3 = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");
            if (!patron3.matcher(EMAIL.getText()).matches() || EMAIL.getText().length() > 45) {
                EMAIL1.setError("Advertencia: Formato de Email no valido, no se permite sobreexcederse de 45 caracteres");
            }
            else{
                EMAIL1.setError(null);
            }
            EMAIL1.setError("Comprobando Email...");
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manusuario/buscar.php?n=" + EMAIL.getText();
            consulta2 = consulta2.replace(" ", "%20");
            VALIDAR_DATOS_EMAIL(consulta2);
        }
    }

    public void modificar_datos(){
        String valor_seleccionado = CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString();
        if (NOMBRE.getText().length() == 0){
            NOMBRE1.setError("Advertencia: Campo Nombre Vacio");
        }
        else{
            NOMBRE1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron.matcher(NOMBRE.getText()).matches() || NOMBRE.getText().length() > 45) {
                NOMBRE1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }else{
                NOMBRE1.setError(null);
            }
            NOMBRE1.setError("Comprobando Nombre...");
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manusuario/buscar.php?n=" + NOMBRE.getText();
            consulta2 = consulta2.replace(" ", "%20");
            VALIDAR_DATOS_NOMBRE(consulta2);
        }
        if (CLAVE.getText().length() == 0) {
            CLAVE1.setError("Advertencia: Campo Clave Vacio");
        }else{
            CLAVE1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z1-9]+$");
            if (!patron2.matcher(CLAVE.getText()).matches() || CLAVE.getText().length() > 45) {
                CLAVE1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                CLAVE1.setError(null);
            }
        }
        if (valor_seleccionado == "Seleccione un Cargo...") {
            mostrarsnack.dismiss();
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Advertencia: Seleccione un Cargo", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }
        if (EMAIL.getText().length() == 0){
            EMAIL1.setError("Advertencia: Campo Email Vacio");
        }
        else {
            EMAIL1.setError(null);
            Pattern patron3 = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");
            if (!patron3.matcher(EMAIL.getText()).matches() || EMAIL.getText().length() > 45) {
                EMAIL1.setError("Advertencia: Formato de Email no valido, no se permite sobreexcederse de 45 caracteres");
            } else {
                EMAIL1.setError(null);
            }
            EMAIL1.setError("Comprobando Email...");
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manusuario/buscar.php?n=" + EMAIL.getText();
            consulta2 = consulta2.replace(" ", "%20");
            VALIDAR_DATOS_EMAIL(consulta2);
        }
    }

    public void actualizar_datos(){
        mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mostrar_registros_usuarios.php";
        EnviarRecibirDatos(consulta2);
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
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/manusuario/buscar.php?n=" + BUSCAR.getText();
            consulta2 = consulta2.replace(" ", "%20");
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
                        CargarDatos_Usuarios(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                        mostrarsnack.dismiss();
                        mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "No hay Datos de Usuarios", Snackbar.LENGTH_LONG).setAction("Action", null);
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

    public void CargarDatos_Usuarios(JSONArray ja){
        try{
            int a = 0;
            da = ja.getString(a);
            da2 = ja.getString(a+1);
                    da3 = ja.getString(a+2);
                            da4 = ja.getString(a+4);
                            da5 = ja.getString(a+5);

            ID.setText(""+da);
            NOMBRE.setText(""+da2);
            CLAVE.setText(""+da3);
            String[] datostipodocumento = new String[] {""+ja.getString(a+3)};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
            CARGO.setAdapter(adapter);
            EMAIL.setText(""+da4);
            TIPOESTADO.setText(""+da5);
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata3.clear();
        ejecutar_recyclerview_usuarios();
        for(int i=0;i<ja.length();i+=6){
            try{
                listadata3.add(new Data3("ID: "+ja.getString(i),"Nombre: "+ja.getString(i+1),"Clave: "+ja.getString(i+2), "Cargo: "+ja.getString(i+3), "Email: "+ja.getString(i+4), "Tipo Estado: "+ja.getString(i+5)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }

        mostrarsnack.dismiss();

    }

    public void ejecutar_recyclerview_usuarios(){
        recyclerusuarios.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Usuarios.this);
        recyclerusuarios.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_usuarios(listadata3);
        recyclerusuarios.setAdapter(adapter);
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
                        CargarDatos_Usuarios2(ja);
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
                    listadata3.clear();
                    ejecutar_recyclerview_usuarios();
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

    public void CargarDatos_Usuarios2(JSONArray ja){
        try{
            int a = 0;
            da = ja.getString(a);
            da2 = ja.getString(a+1);
            da3 = ja.getString(a+2);
            da4 = ja.getString(a+4);
            da5 = ja.getString(a+5);
            ID.setText(""+da);
            NOMBRE.setText(""+da2);
            CLAVE.setText(""+da3);
            String[] datostipodocumento = new String[] {""+ja.getString(a+3)};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
            CARGO.setAdapter(adapter);
            EMAIL.setText(""+da4);
            TIPOESTADO.setText(""+da5);
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata3.clear();
        ejecutar_recyclerview_usuarios();
        for(int i=0;i<ja.length();i+=6){
            try{
                listadata3.add(new Data3("ID: "+ja.getString(i),"Nombre: "+ja.getString(i+1),"Clave: "+ja.getString(i+2), "Cargo: "+ja.getString(i+3), "Email: "+ja.getString(i+4), "Tipo Estado: "+ja.getString(i+5)));
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
                    NOMBRE.setFocusable(true);
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
                NOMBRE.setFocusable(true);
            }
            else{
                int suma = Integer.parseInt(valor) + 1;
                ID.setText(""+suma);
                varseleccion = 1;
                refresco_datos.setEnabled(false);
                limpiar_residuo();
                habilitar_desabilitar(true, false);
                progressBarbuscar.dismiss();
                NOMBRE.setFocusable(true);
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
            progressBarbuscar= new ProgressDialog(Usuarios.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Eliminando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro0 = "http://192.168.43.250/sistemariego_app_admin/manusuario/eliminar_usuario.php?i="+ID.getText();
            registro0 = registro0.replace(" ","%20");
            EnviarRecibirDatos2(registro0);
        }
        else if (valor == false){
        }
    }

    //Validar Nombre
    public void VALIDAR_DATOS_NOMBRE(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0) {
                    try {
                        ja = new JSONArray(response);
                        String nombres = ja.getString(1);
                        if (nombres.equals(NOMBRE.getText().toString())) {
                            NOMBRE1.setError("Advertencia: Nombre ya existente en el Registro");
                        } else {
                            NOMBRE1.setError(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }else{
                    NOMBRE1.setError(null);
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
    //Validar Email
    public void VALIDAR_DATOS_EMAIL(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0) {
                    try {
                        ja = new JSONArray(response);
                        String email = ja.getString(4);
                        if (email.equals(EMAIL.getText().toString())) {
                            EMAIL1.setError("Advertencia: Email ya existente en el Registro");
                        } else {
                            EMAIL1.setError(null);
                            guardar_modificar();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }else{
                    EMAIL1.setError(null);
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
        String valor_seleccionado = CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString();
        if (varseleccion == 1){
            if(NOMBRE1.getError() == null && CLAVE1.getError() == null && EMAIL1.getError() == null && valor_seleccionado == "Administrador"){
                progressBarbuscar= new ProgressDialog(Usuarios.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                if (TIPOESTADO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/ingreso_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=Ninguno";
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }else{
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/ingreso_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=" + TIPOESTADO.getText();
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
            }else if (NOMBRE1.getError() == null && CLAVE1.getError() == null && EMAIL1.getError() == null && valor_seleccionado == "Operario"){
                progressBarbuscar= new ProgressDialog(Usuarios.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                if (TIPOESTADO.getText().length() == 0){
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/ingreso_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=Ninguno";
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }else{
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/ingreso_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=" + TIPOESTADO.getText();
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
            }
        }else if (varseleccion == 2){
            if(NOMBRE1.getError() == null && CLAVE1.getError() == null && EMAIL1.getError() == null && valor_seleccionado == "Administrador"){
                progressBarbuscar= new ProgressDialog(Usuarios.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                if (TIPOESTADO.getText().length() == 0) {
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/modificar_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=Ninguno";
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                else{
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/modificar_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=" + TIPOESTADO.getText();
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);

                }
            }else if (NOMBRE1.getError() == null && CLAVE1.getError() == null && EMAIL1.getError() == null && valor_seleccionado == "Operario"){
                progressBarbuscar= new ProgressDialog(Usuarios.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                if (TIPOESTADO.getText().length() == 0) {
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/modificar_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=Ninguno";
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);
                }
                else{
                    String registro = "http://192.168.43.250/sistemariego_app_admin/manusuario/modificar_usuario.php?i=" + ID.getText() + "&n=" + NOMBRE.getText() + "&cl=" + CLAVE.getText() + "&c=" + CARGO.getItemAtPosition(CARGO.getSelectedItemPosition()).toString() + "&e=" + EMAIL.getText() + "&t=" + TIPOESTADO.getText();
                    registro = registro.replace(" ","%20");
                    EnviarRecibirDatos2(registro);
                    varseleccion = 0;
                    refresco_datos.setEnabled(true);
                    habilitar_desabilitar(false, true);

                }
            }
        }

    }

    public void adaptador_spinner(){
        String[] datostipodocumento = new String[] {"Seleccione un Cargo...","Operario","Administrador"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
        CARGO.setAdapter(adapter);
    }
}
