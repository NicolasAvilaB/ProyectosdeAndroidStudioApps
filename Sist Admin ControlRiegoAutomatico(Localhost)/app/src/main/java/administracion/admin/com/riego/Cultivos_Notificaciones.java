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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Cultivos_Notificaciones extends AppCompatActivity implements DialogoEliminar.FinalizoCuadroDialogo{

    Button nuevo, eliminar, modificar, guardar, cancelar, busqueda;
    Snackbar mostrarsnack;
    EditText ID, TIPOCULTIVO, FECHA, RESUMEN, OPERARIO, BUSCAR;
    TextInputLayout TIPOCULTIVO1, FECHA1, RESUMEN1, OPERARIO1, BUSCAR1;
    ProgressDialog progressBarbuscar;
    SwipeRefreshLayout refresco_datos;
    Spinner ESTACIONANO, MES;
    boolean valor = false;
    Context mensaje_flotante_eliminando;
    CardView tarjetacul;
    private RecyclerView recyclercl;
    private RecyclerViewAdapter_cl adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data5> listadata5 = new ArrayList<Data5>();
    int varseleccion = 0;
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivos__notificaciones);
        setTitle("Gestion Cultivos y Notf.");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TIPOCULTIVO1 = findViewById(R.id.TIPOCULTIVO1);
        FECHA1 = findViewById(R.id.FECHA1);
        RESUMEN1 = findViewById(R.id.RESUMEN1);
        OPERARIO1 = findViewById(R.id.OPERARIO1);
        BUSCAR1 = findViewById(R.id.BUSCAR1);
        ID = findViewById(R.id.ID);
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
        FECHA = findViewById(R.id.FECHA);
        RESUMEN = findViewById(R.id.RESUMEN);
        OPERARIO = findViewById(R.id.OPERARIO);
        OPERARIO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        OPERARIO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                OPERARIO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OPERARIO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }

            @Override
            public void afterTextChanged(Editable s) {
                OPERARIO.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
        });
        ESTACIONANO = findViewById(R.id.ESTACIONANO);
        MES = findViewById(R.id.MES);
        BUSCAR = findViewById(R.id.BUSCAR);
        nuevo = findViewById(R.id.nuevo);
        eliminar = findViewById(R.id.eliminar);
        modificar = findViewById(R.id.modificar);
        guardar = findViewById(R.id.guardar);
        cancelar = findViewById(R.id.cancelar);
        busqueda = findViewById(R.id.busqueda);
        recyclercl = findViewById(R.id.recyclercl);
        tarjetacul = findViewById(R.id.tarjetacul);
        mensaje_flotante_eliminando = this;
        refresco_datos = findViewById(R.id.refresco_datos);
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
                progressBarbuscar= new ProgressDialog(Cultivos_Notificaciones.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Consultando Identificador");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mancultivos/generar_id.php";
                EnviarRecibir_ID(consulta2);

            }});

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                BUSCAR.setText("");
                varseleccion = 2;
                refresco_datos.setEnabled(false);
                TIPOCULTIVO.setFocusable(true);
                TIPOCULTIVO.requestFocus();
                habilitar_desabilitar(true, false);
            }});

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUSCAR1.setError(null);
                new DialogoEliminar(mensaje_flotante_eliminando, Cultivos_Notificaciones.this);
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
        ESTACIONANO.setEnabled(false);
        MES.setEnabled(false);

        FECHA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        c = Calendar.getInstance();
                        int dia =c.get(Calendar.DAY_OF_MONTH);
                        int año = c.get(Calendar.YEAR);
                        int mes = c.get(Calendar.MONTH);
                        dpd = new DatePickerDialog(Cultivos_Notificaciones.this, new DatePickerDialog.OnDateSetListener() {
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

        FECHA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                }else {
                    c = Calendar.getInstance();
                    int dia =c.get(Calendar.DAY_OF_MONTH);
                    int año = c.get(Calendar.YEAR);
                    int mes = c.get(Calendar.MONTH);
                    dpd = new DatePickerDialog(Cultivos_Notificaciones.this, new DatePickerDialog.OnDateSetListener() {
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

    }

    public void limpiar_residuo(){
        TIPOCULTIVO.setText("");
        String[] datostipodocumento = new String[] {"Seleccione una Estacion del Año...","Verano","Otoño","Invierno","Primavera"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
        ESTACIONANO.setAdapter(adapter);
        String[] datostipodocumento2 = new String[] {"Seleccione un Mes...","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento2);
        MES.setAdapter(adapter2);
        FECHA.setText("");
        RESUMEN.setText("");
        OPERARIO.setText("");
        BUSCAR.setText("");

    }

    public void guardar_datos(){
        String valor_seleccionado = ESTACIONANO.getItemAtPosition(ESTACIONANO.getSelectedItemPosition()).toString();
        String valor_seleccionado2 = MES.getItemAtPosition(MES.getSelectedItemPosition()).toString();
        if (TIPOCULTIVO.getText().length() == 0)
        {
            TIPOCULTIVO1.setError("Advertencia: Campo Tipo Cultivo Vacio");
        }
        else{
            TIPOCULTIVO1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z]+$");
            if (!patron.matcher(TIPOCULTIVO.getText()).matches() || TIPOCULTIVO.getText().length() > 45) {
                TIPOCULTIVO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                TIPOCULTIVO1.setError(null);
            }
        }
        if (valor_seleccionado == "Seleccione una Estacion del Año...")
        {
            mostrarsnack.dismiss();
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Advertencia: Seleccione una estacion del año", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }
        if (valor_seleccionado2 == "Seleccione un Mes...")
        {
            mostrarsnack.dismiss();
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Advertencia: Seleccione un Mes", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }
        if (FECHA.getText().length() == 0) {
            FECHA1.setError("Advertencia: Campo Fecha Vacio");
        }
        else{
            FECHA1.setError(null);
        }
        if (RESUMEN.getText().length() == 0) {
            RESUMEN1.setError("Advertencia: Campo Resumen Vacio");
        }
        else{
            RESUMEN1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron2.matcher(RESUMEN.getText()).matches() || RESUMEN.getText().length() > 45) {
                RESUMEN1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                RESUMEN1.setError(null);
            }
        }
        if (OPERARIO.getText().length() == 0){
            OPERARIO1.setError("Advertencia: Campo Operario Vacio");
        }
        else{
            OPERARIO1.setError(null);
            Pattern patron3 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron3.matcher(OPERARIO.getText()).matches() || OPERARIO.getText().length() > 45) {
                OPERARIO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else {
                OPERARIO1.setError(null);
            }
        }
        if(TIPOCULTIVO1.getError() == null && RESUMEN1.getError() == null && OPERARIO1.getError() == null && FECHA1.getError() == null && valor_seleccionado == "Verano" || valor_seleccionado == "Otoño" || valor_seleccionado == "Invierno" || valor_seleccionado == "Primavera" && valor_seleccionado2 == "Enero" || valor_seleccionado2 == "Febrero" || valor_seleccionado2 == "Marzo" || valor_seleccionado2 =="Abril" || valor_seleccionado2 =="Mayo" || valor_seleccionado2 =="Junio" || valor_seleccionado2 =="Julio" || valor_seleccionado2 =="Agosto" || valor_seleccionado2 =="Septiembre" || valor_seleccionado2 == "Octubre" || valor_seleccionado2 =="Noviembre" || valor_seleccionado2 =="Diciembre") {
                TIPOCULTIVO1.setError(null);
                RESUMEN1.setError(null);
                OPERARIO1.setError(null);
                FECHA1.setError(null);
                progressBarbuscar= new ProgressDialog(Cultivos_Notificaciones.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Guardando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String registro = "http://192.168.43.250/sistemariego_app_admin/mancultivos/ingreso_cultivo.php?i=" + ID.getText() + "&n=" + TIPOCULTIVO.getText() + "&cl=" + ESTACIONANO.getItemAtPosition(ESTACIONANO.getSelectedItemPosition()).toString() + "&c=" + MES.getItemAtPosition(MES.getSelectedItemPosition()).toString() + "&e=" + FECHA.getText() + "&t=" + RESUMEN.getText() + "&o=" + OPERARIO.getText()+ "&ess=---";
                registro = registro.replace(" ","%20");
                EnviarRecibirDatos2(registro);
                varseleccion = 0;
                refresco_datos.setEnabled(true);
                habilitar_desabilitar(false, true);
        }
    }

    public void modificar_datos(){
        String valor_seleccionado = ESTACIONANO.getItemAtPosition(ESTACIONANO.getSelectedItemPosition()).toString();
        String valor_seleccionado2 = MES.getItemAtPosition(MES.getSelectedItemPosition()).toString();
        if (TIPOCULTIVO.getText().length() == 0)
        {
            TIPOCULTIVO1.setError("Advertencia: Campo Tipo Cultivo Vacio");
        }
        else{
            TIPOCULTIVO1.setError(null);
            Pattern patron = Pattern.compile("^[a-zA-Z]+$");
            if (!patron.matcher(TIPOCULTIVO.getText()).matches() || TIPOCULTIVO.getText().length() > 45) {
                TIPOCULTIVO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                TIPOCULTIVO1.setError(null);
            }
        }
        if (valor_seleccionado == "Seleccione una Estacion del Año...")
        {
            mostrarsnack.dismiss();
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Advertencia: Seleccione una estacion del año", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }
        if (valor_seleccionado2 == "Seleccione un Mes...")
        {
            mostrarsnack.dismiss();
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Advertencia: Seleccione un Mes", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }
        if (FECHA.getText().length() == 0) {
            FECHA1.setError("Advertencia: Campo Fecha Vacio");
        }
        else{
            FECHA1.setError(null);
        }
        if (RESUMEN.getText().length() == 0) {
            RESUMEN1.setError("Advertencia: Campo Resumen Vacio");
        }
        else{
            RESUMEN1.setError(null);
            Pattern patron2 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron2.matcher(RESUMEN.getText()).matches() || RESUMEN.getText().length() > 45) {
                RESUMEN1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else{
                RESUMEN1.setError(null);
            }
        }
        if (OPERARIO.getText().length() == 0){
            OPERARIO1.setError("Advertencia: Campo Operario Vacio");
        }
        else{
            OPERARIO1.setError(null);
            Pattern patron3 = Pattern.compile("^[a-zA-Z ]+$");
            if (!patron3.matcher(OPERARIO.getText()).matches() || OPERARIO.getText().length() > 45) {
                OPERARIO1.setError("No se permite caracteres especiales ni sobreexcederse de 45 palabras");
            }
            else {
                OPERARIO1.setError(null);
            }
        }
        if(TIPOCULTIVO1.getError() == null && RESUMEN1.getError() == null && OPERARIO1.getError() == null && FECHA1.getError() == null && valor_seleccionado == "Verano" || valor_seleccionado == "Otoño" || valor_seleccionado == "Invierno" || valor_seleccionado == "Primavera" && valor_seleccionado2 == "Enero" || valor_seleccionado2 == "Febrero" || valor_seleccionado2 == "Marzo" || valor_seleccionado2 =="Abril" || valor_seleccionado2 =="Mayo" || valor_seleccionado2 =="Junio" || valor_seleccionado2 =="Julio" || valor_seleccionado2 =="Agosto" || valor_seleccionado2 =="Septiembre" || valor_seleccionado2 == "Octubre" || valor_seleccionado2 =="Noviembre" || valor_seleccionado2 =="Diciembre") {
            TIPOCULTIVO1.setError(null);
            RESUMEN1.setError(null);
            OPERARIO1.setError(null);
            FECHA1.setError(null);
            progressBarbuscar= new ProgressDialog(Cultivos_Notificaciones.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Guardando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro = "http://192.168.43.250/sistemariego_app_admin/mancultivos/modificar_cultivo.php?i=" + ID.getText() + "&n=" + TIPOCULTIVO.getText() + "&cl=" + ESTACIONANO.getItemAtPosition(ESTACIONANO.getSelectedItemPosition()).toString() + "&c=" + MES.getItemAtPosition(MES.getSelectedItemPosition()).toString() + "&e=" + FECHA.getText() + "&t=" + RESUMEN.getText() + "&o=" + OPERARIO.getText();
            registro = registro.replace(" ","%20");
            EnviarRecibirDatos2(registro);
            varseleccion = 0;
            refresco_datos.setEnabled(true);
            habilitar_desabilitar(false, true);
        }
    }

    public void habilitar_desabilitar(boolean a, boolean b){
        TIPOCULTIVO.setEnabled(a);
        ESTACIONANO.setEnabled(a);
        MES.setEnabled(a);
        FECHA.setEnabled(a);
        RESUMEN.setEnabled(a);
        OPERARIO.setEnabled(a);
        nuevo.setEnabled(b);
        modificar.setEnabled(b);
        eliminar.setEnabled(b);
        guardar.setEnabled(a);
        cancelar.setEnabled(a);
        busqueda.setEnabled(b);
        BUSCAR.setEnabled(b);
    }

    public void actualizar_datos(){
        mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Actualizando Datos...", Snackbar.LENGTH_INDEFINITE).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
        String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mostrar_registros_cultivos.php";
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
            String consulta2 = "http://192.168.43.250/sistemariego_app_admin/mancultivos/buscar.php?n=" + BUSCAR.getText();
            consulta2 = consulta2.replace(" ","%20");
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
                        CargarDatos_cl(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mostrarsnack.dismiss();
                    mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "No hay Datos de Cultivos de Notificaciones", Snackbar.LENGTH_LONG).setAction("Action", null);
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

    public void CargarDatos_cl(JSONArray ja){
        try{
            int a = 0;
            ID.setText(""+ja.getString(a));
            TIPOCULTIVO.setText(""+ja.getString(a+1));
            String[] datostipodocumento = new String[] {""+ja.getString(a+2)};
            ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
            ESTACIONANO.setAdapter(adapters);
            String[] datostipodocumento2 = new String[] {""+ja.getString(a+3)};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento2);
            MES.setAdapter(adapter2);
            FECHA.setText(""+ja.getString(a+4));
            RESUMEN.setText(""+ja.getString(a+5));
            OPERARIO.setText(""+ja.getString(a+6));
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata5.clear();
        ejecutar_recyclerview_cl();
        for(int i=0;i<ja.length()/*ja.length()*/;i+=8){
            try{
                listadata5.add(new Data5("ID: "+ja.getString(i),"Tipo Cultivo: "+ja.getString(i+1),"Estacion Año: "+ja.getString(i+2), "Mes: "+ja.getString(i+3), "Fecha: "+ja.getString(i+4), "Resumen: "+ja.getString(i+5) , "Operario: "+ja.getString(i+6), "Estado Notf: "+ja.getString(i+7)));
            }catch (JSONException e){
                e.printStackTrace();
            }
            //animarcardviews_ver();
        }
        mostrarsnack.dismiss();
    }

    public void ejecutar_recyclerview_cl(){
        recyclercl.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Cultivos_Notificaciones.this);
        recyclercl.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter_cl(listadata5);
        recyclercl.setAdapter(adapter);
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
                    TIPOCULTIVO.setFocusable(true);
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
                TIPOCULTIVO.setFocusable(true);
            }
            else{
                int suma = Integer.parseInt(valor) + 1;
                ID.setText(""+suma);
                varseleccion = 1;
                refresco_datos.setEnabled(false);
                limpiar_residuo();
                habilitar_desabilitar(true, false);
                progressBarbuscar.dismiss();
                TIPOCULTIVO.setFocusable(true);
            }
            mostrarsnack = Snackbar.make(findViewById(R.id.nuevo), "Campos con * requeridos", Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = mostrarsnack.getView();
            sbView.setBackgroundColor(Color.rgb(13,112,110));
            mostrarsnack.show();
        }catch (JSONException e){
            e.printStackTrace();
            EnviarRecibir_ID("http://192.168.43.250/sistemariego_app_admin/mancultivos/generar_id.php");
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
                        CargarDatos_Cl2(ja);
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
                    listadata5.clear();
                    ejecutar_recyclerview_cl();
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

    public void CargarDatos_Cl2(JSONArray ja){
        try{
            int a = 0;
            ID.setText(""+ja.getString(a));
            TIPOCULTIVO.setText(""+ja.getString(a+1));
            String[] datostipodocumento = new String[] {""+ja.getString(a+2)};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento);
            ESTACIONANO.setAdapter(adapter);
            String[] datostipodocumento2 = new String[] {""+ja.getString(a+3)};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datostipodocumento2);
            MES.setAdapter(adapter2);
            FECHA.setText(""+ja.getString(a+4));
            RESUMEN.setText(""+ja.getString(a+5));
            OPERARIO.setText(""+ja.getString(a+6));
        }catch (JSONException e){
            e.printStackTrace();
        }
        listadata5.clear();
        ejecutar_recyclerview_cl();
        for(int i=0;i<ja.length()/*ja.length()*/;i+=8){
            try{
                listadata5.add(new Data5("ID: "+ja.getString(i),"Tipo Cultivo: "+ja.getString(i+1),"Estacion Año: "+ja.getString(i+2), "Mes: "+ja.getString(i+3), "Fecha: "+ja.getString(i+4), "Resumen: "+ja.getString(i+5) , "Operario: "+ja.getString(i+6), "Estado Notf: "+ja.getString(i+7)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        progressBarbuscar.dismiss();
        mostrarsnack= Snackbar.make(findViewById(R.id.nuevo), "Datos Manipulados Exitosamente", Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = mostrarsnack.getView();
        sbView.setBackgroundColor(Color.rgb(13,112,110));
        mostrarsnack.show();
    }

    @Override
    public void ResultadoCuadroDialogo(boolean res) {
        valor = res;
        if (valor == true){
            if (BUSCAR.getText().length() > 0)
            {
                BUSCAR.setText("");
            }
            progressBarbuscar= new ProgressDialog(Cultivos_Notificaciones.this);
            progressBarbuscar.dismiss();
            progressBarbuscar.setTitle("Eliminando Datos");
            progressBarbuscar.setMessage("Espere un Momento...");
            progressBarbuscar.setCancelable(false);
            progressBarbuscar.show();
            String registro0 = "http://192.168.43.250/sistemariego_app_admin/mancultivos/eliminar_cultivo.php?i="+ID.getText();
            registro0 = registro0.replace(" ","%20");
            EnviarRecibirDatos2(registro0);
        }
        else if (valor == false){
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
