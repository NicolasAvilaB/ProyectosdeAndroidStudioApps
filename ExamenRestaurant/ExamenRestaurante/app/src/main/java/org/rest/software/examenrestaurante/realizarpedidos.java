package org.rest.software.examenrestaurante;

import android.app.ProgressDialog;
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

public class realizarpedidos extends AppCompatActivity {
    Button regresar, aceptar, eleccion, actualizacion;
    ListView trans;
    EditText elemento, usuario;
    String datoNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizarpedidos);
        setTitle("Pedidos Mesa");
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        datoNombre=(String)extras.get("mesa");
        String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + datoNombre;
        mostrar = mostrar.replace(" ","%20");
        MostrarPedidos(mostrar);
        trans = (ListView)findViewById(R.id.trans);
        elemento = (EditText)findViewById(R.id.elemento);
        usuario = (EditText)findViewById(R.id.usuario);

        actualizacion = (Button)findViewById(R.id.actualizacion);
        actualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + datoNombre;
                mostrar = mostrar.replace(" ","%20");
                MostrarPedidos(mostrar);
            }
        });
        regresar = (Button) findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),menumesas.class);
                startActivity(i);
                finish();
            }
        });
        aceptar = (Button) findViewById(R.id.aceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Error: No Has Digitado al Cliente", Toast.LENGTH_SHORT).show();
                }
                else if (elemento.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Error: No Has Digitado el Producto", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String b = eleccion.getText().toString();
                    if (b.equals("Bebestibles"))
                    {
                        String registross = "http://systemchile.com/AplicacionExamen/pedidos/buscaplatos.php?producto=" + elemento.getText();
                        registross = registross.replace(" ","%20");
                        EnviarRecibirDatos(registross);
                    }
                    else if (b.equals("Platillos")){
                        String registross = "http://systemchile.com/AplicacionExamen/pedidos/buscabebestible.php?producto=" + elemento.getText();
                        registross = registross.replace(" ","%20");
                        EnviarRecibirDatos2(registross);
                    }
                }
            }
        });
        eleccion = (Button) findViewById(R.id.eleccion);
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
    }
    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    String nom = ja.getString(2);
                    String precio = ja.getString(5);
                    String tipo = ja.getString(4);

                    if (nom.equals(elemento.getText().toString())){
                        String pedido = "http://systemchile.com/AplicacionExamen/pedidos/registroreserva.php?user="+usuario.getText()+"&producto="+elemento.getText()+"&precio="+precio+"&tipo="+tipo+"&mesa="+datoNombre;
                        pedido = pedido.replace(" ","%20");
                        MostrarPedidos(pedido);
                        String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + datoNombre;
                        mostrar = mostrar.replace(" ","%20");
                        MostrarPedidos(mostrar);
                        Toast.makeText(getApplicationContext(), "Pedido Registrado con Exito",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Producto no Existente", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "OJO: Puede que Hayas Digitado un Producto Bebestible o un Plato de Comida", Toast.LENGTH_SHORT).show();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Producto no Existente", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "OJO: Puede que Hayas Digitado un Producto Bebestible o un Plato de Comida", Toast.LENGTH_SHORT).show();

                }
                usuario.setText("");
                elemento.setText("");
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }

    public void EnviarRecibirDatos2(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    String nom = ja.getString(2);
                    String precio = ja.getString(6);
                    String tipo = ja.getString(4);
                    if (nom.equals(elemento.getText().toString())){
                        String pedido = "http://systemchile.com/AplicacionExamen/pedidos/registroreserva.php?user="+usuario.getText()+"&producto="+elemento.getText()+"&precio="+precio+"&tipo="+tipo+"&mesa="+datoNombre;
                        pedido = pedido.replace(" ","%20");
                        MostrarPedidos(pedido);
                        String mostrar = "http://systemchile.com/AplicacionExamen/pedidos/mostrarreservas.php?mesa=" + datoNombre;
                        mostrar = mostrar.replace(" ","%20");
                        MostrarPedidos(mostrar);
                        Toast.makeText(getApplicationContext(), "Pedido Registrado con Exito",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Producto no Existente", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "OJO: Puede que Hayas Digitado un Producto Bebestible o un Plato de Comida", Toast.LENGTH_SHORT).show();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Producto no Existente", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "OJO: Puede que Hayas Digitado un Producto Bebestible o un Plato de Comida", Toast.LENGTH_SHORT).show();

                }
                usuario.setText("");
                elemento.setText("");
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }


    public void MostrarPedidos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarLista(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    trans.setAdapter(null);
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void CargarLista(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=7){

            try {

                lista.add("ID: "+ja.getString(i)+", Usuario: "+ja.getString(i+1)+", Producto: "+ja.getString(i+2)+", Precio: "+ja.getString(i+3)+", Cantidad: "+ja.getString(i+4)+", Tipo: "+ja.getString(i+5)+", Mesa: "+ja.getString(i+6));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        trans.setAdapter(adaptador);
    }


}
