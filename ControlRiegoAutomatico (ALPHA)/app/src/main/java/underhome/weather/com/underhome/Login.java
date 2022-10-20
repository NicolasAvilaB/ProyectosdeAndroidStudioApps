package underhome.weather.com.underhome;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class Login extends AppCompatActivity {

    Button aceptar, salir;
    EditText nombre, clave;
    JSONArray ja;
    ProgressDialog progressBarbuscar;
    Snackbar mostrarmensaje;
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        aceptar = findViewById(R.id.aceptar);
        salir = findViewById(R.id.salir);
        nombre = findViewById(R.id.nombre);
        clave = findViewById(R.id.clave);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarbuscar= new ProgressDialog(Login.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Consultando Usuario");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBarbuscar.dismiss();
                        mostrarmensaje = Snackbar.make(findViewById(R.id.aceptar), "Operacion Cancelada",3000).setAction("Action", null);
                        View sbView = mostrarmensaje.getView();
                        sbView.setBackgroundColor(Color.rgb(43,62,95));
                        mostrarmensaje.show();
                    }
                });
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String registro = "https://systemchile.com/sistemariego_app/login.php?nx=" + nombre.getText() + "&cx=" +clave.getText();
                registro = registro.replace(" ","%20");
                EnviarRecibirDatos(registro);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ja = new JSONArray(response);
                    String nombres = ja.getString(0);
                    String claves = ja.getString(1);
                    if (nombres.equals(nombre.getText().toString()) && claves.equals(clave.getText().toString())){
                        progressBarbuscar.dismiss();
                        Intent i = new Intent(getApplicationContext(), DHT11.class);
                        startActivity(i);
                        nombre.setText("");
                        nombre.invalidate();
                        clave.setText("");
                        clave.invalidate();

                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Nombre de Usuario y/o Contraseña Son Incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Nombre de Usuario y/o Contraseña Son Incorrectos", Toast.LENGTH_SHORT).show();
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
}
