package org.rest.software.examenrestaurante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class modificarpedido extends AppCompatActivity {
    Button regresar, modificar, guardar, actua, eleccion;
    EditText usuario, elemento, cantidad, buscar;
    ListView listar;
    TextView mesa, tipo, precio, id;
    String comprobar;
    Integer varseleccion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarpedido);
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        comprobar=(String)extras.get("m");
        String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + comprobar;
        mostrar = mostrar.replace(" ","%20");
        EnviarRecibirDatos(mostrar);
        id = (TextView)findViewById(R.id.id);
        mesa = (TextView)findViewById(R.id.mesa);
        tipo = (TextView)findViewById(R.id.tipo);
        precio = (TextView)findViewById(R.id.precio);
        mesa.setText(""+comprobar);
        listar = (ListView)findViewById(R.id.listar);
        usuario = (EditText)findViewById(R.id.usuario);
        elemento = (EditText)findViewById(R.id.elemento);
        elemento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String b = eleccion.getText().toString();
                    if (b.equals("Bebestibles"))
                    {
                        if (elemento.getText().length() == 0)
                        {
                            precio.setText("........");
                            tipo.setText("........");
                        }
                        else {
                            String registross = "http://systemchile.com/AplicacionExamen/pedidos/buscaplatos.php?producto=" + elemento.getText();
                            registross = registross.replace(" ", "%20");
                            EnviarRecibirDatos2(registross);
                        }

                    }
                    else if (b.equals("Platillos")){
                        if (elemento.getText().length() == 0)
                        {
                            precio.setText("........");
                            tipo.setText("........");
                        }
                        else {
                            String registross = "http://systemchile.com/AplicacionExamen/pedidos/buscabebestible.php?producto=" + elemento.getText();
                            registross = registross.replace(" ", "%20");
                            EnviarRecibirDatos3(registross);
                        }
                    }


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cantidad = (EditText)findViewById(R.id.cantidad);

        buscar = (EditText)findViewById(R.id.buscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (buscar.getText().length() == 0){
                    String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + comprobar;
                    mostrar = mostrar.replace(" ","%20");
                    EnviarRecibirDatos(mostrar);
                }
                else{
                    String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/buscarreservas.php?a=" + buscar.getText();
                    mostrar = mostrar.replace(" ","%20");
                    EnviarRecibirDatos(mostrar);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regresar = (Button)findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),menumesas.class);
                startActivity(i);
                finish();
            }
        });


        actua = (Button)findViewById(R.id.actua);
        actua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + mesa.getText().toString();
                mostrar = mostrar.replace(" ","%20");
                EnviarRecibirDatos(mostrar);
            }
        });

        eleccion = (Button)findViewById(R.id.eleccion);
        eleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String boton = eleccion.getText().toString();
                if (boton.equals("Bebestibles")){
                    elemento.setHint("Busque Bebestibles...");
                    eleccion.setText("Platillos");
                    elemento.setText("");
                }
                else if (boton.equals("Platillos")){
                    elemento.setHint("Busque Platillo...");
                    eleccion.setText("Bebestibles");
                    elemento.setText("");
                }
            }
        });


        modificar = (Button)findViewById(R.id.modificar);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = modificar.getText().toString();
                if (n.equals("Modificar")){
                    varseleccion = 1;
                    elemento.setEnabled(true);
                    usuario.setEnabled(true);
                    cantidad.setEnabled(true);
                    actua.setEnabled(false);
                    guardar.setEnabled(true);
                    eleccion.setEnabled(true);
                    modificar.setText("Cancelar");
                }
                else if(n.equals("Cancelar")){
                    varseleccion = 0;
                    actua.setEnabled(true);
                    elemento.setEnabled(false);
                    guardar.setEnabled(false);
                    usuario.setEnabled(false);
                    eleccion.setEnabled(false);
                    cantidad.setEnabled(false);
                    modificar.setText("Modificar");
                    String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + mesa.getText().toString();
                    mostrar = mostrar.replace(" ","%20");
                    EnviarRecibirDatos(mostrar);
                    String boton = eleccion.getText().toString();
                    if (boton.equals("Bebestibles")){
                        elemento.setHint("Busque Bebestibles...");
                        eleccion.setText("Platillos");
                    }
                    else if (boton.equals("Platillos")){
                        elemento.setHint("Busque Platillo...");
                        eleccion.setText("Bebestibles");
                    }
                }
            }
        });
        guardar = (Button)findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   String ejecutar = "http://systemchile.com/AplicacionExamen/pedidos/modificarpedido.php?usuario=" + usuario.getText() + "&producto=" + elemento.getText() + "&precio=" + precio.getText()+ "&cantidad=" +cantidad.getText()+ "&tipo=" +tipo.getText()+ "&id="+id.getText();
                   ejecutar = ejecutar.replace(" ","%20");
                   ejecutaraccion(ejecutar);
                   String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + mesa.getText();
                   mostrar = mostrar.replace(" ","%20");
                   EnviarRecibirDatos(mostrar);
                   varseleccion = 0;
                   actua.setEnabled(true);
                   elemento.setEnabled(false);
                   guardar.setEnabled(false);
                   usuario.setEnabled(false);
                   eleccion.setEnabled(false);
                   cantidad.setEnabled(false);
                   modificar.setText("Modificar");
                   elemento.setHint("Busque Platillo...");
                   eleccion.setText("Bebestibles");
            }
        });
    }

    public void ejecutaraccion(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");

                    try {
                        JSONArray ja = new JSONArray(response);
                        Toast.makeText(getApplicationContext(), "Datos Modificados Correctamente", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    listar.setAdapter(null);
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void EnviarRecibirDatos2(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                int i=0;
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                            precio.setText(ja.getString(i + 5));
                            tipo.setText(ja.getString(i + 4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    precio.setText("........");
                    tipo.setText("........");
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void EnviarRecibirDatos3(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");

                int i = 0;
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                            precio.setText(ja.getString(i + 6));
                            tipo.setText(ja.getString(i + 4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    precio.setText("........");
                    tipo.setText("........");

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=7){

            try {

                lista.add("ID: "+ja.getString(i)+", Usuario: "+ja.getString(i+1)+", Producto: "+ja.getString(i+2)+", Precio: "+ja.getString(i+3)+", Cantidad: "+ja.getString(i+4)+", Tipo: "+ja.getString(i+5)+", Mesa: "+ja.getString(i+6));
                id.setText(ja.getString(i));
                usuario.setText(ja.getString(i+1));
                elemento.setText(ja.getString(i+2));
                precio.setText(ja.getString(i+3));
                cantidad.setText(ja.getString(i+4));
                tipo.setText(ja.getString(i+5));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listar.setAdapter(adaptador);
    }
}
