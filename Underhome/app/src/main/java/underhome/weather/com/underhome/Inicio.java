package underhome.weather.com.underhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class Inicio extends AppCompatActivity {
    ProgressDialog progressBar2;
    ProgressBar proceso;
    TextView compr;
    String a,b = "";
    private ImageView imageView2;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        compr = (TextView)findViewById(R.id.compr);
        proceso = (ProgressBar) findViewById(R.id.proceso);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        Animation m = AnimationUtils.loadAnimation(this, R.anim.transicionsplash);
        imageView2.startAnimation(m);

        View decorview = getWindow().getDecorView();
        int ui = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //| View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorview.setSystemUiVisibility(ui);

        EnviarRecibirDatos("http://systemchile.com/sensorDHT11/mostrar.php");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation m = AnimationUtils.loadAnimation(Inicio.this, R.anim.transicionsplash);
                Animation m2 = AnimationUtils.loadAnimation(Inicio.this, R.anim.transicionsplash);
                proceso.startAnimation(m);
                compr.startAnimation(m2);

                ConnectivityManager conexion = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = conexion.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo datos = conexion.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (String.valueOf(wifi.getState()).equals("CONNECTED")){
                    proceso.setProgress(40);
                    compr.setText("Conexion desde Wifi");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            proceso.setProgress(75);
                            compr.setText("Verificando Conexion a Internet Espere...");
                            RequestQueue queue = Volley.newRequestQueue(Inicio.this);
                            String ping = "https://www.google.cl";
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                                    //progressBar2.dismiss();
                                    proceso.setProgress(100);
                                    compr.setText("Conectado al Internet");
                                    new Handler().postDelayed(new Runnable() {
                                        public void run(){

                                            Intent i2 = new Intent(Inicio.this,DHT11.class);
                                            i2.putExtra("temperatura", a);
                                            i2.putExtra("humedad", b);
                                            startActivity(i2);
                                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                            finish();

                                        }
                                    },2500);
                                }

                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                            /*Toast.makeText(getApplicationContext(), "No hay Internet ", Toast.LENGTH_SHORT).show();
                            progressBar2.dismiss();*/
                                    compr.setText("Error: Tiempo de  Respuesta Agotado, Reintentando");
                                    Toast.makeText(getApplicationContext(), "Error: Tiempo de  Respuesta Agotado, Reintentando", Toast.LENGTH_LONG).show();
                                    proceso.setProgress(0);
                                    startActivity(new Intent(Inicio.this, Inicio.class));
                                    finish();
                                }
                            });
                            stringRequest.setShouldCache(false);
                            queue.add(stringRequest);
                        }
                    },3000);
                    /*progressBar2= new ProgressDialog(Inicio.this);
                    progressBar2.setMessage();
                    progressBar2.show();
                    progressBar2.setCancelable(false);*/
                }
                else{
                    if(String.valueOf(datos.getState()).equals("CONNECTED")){
                        proceso.setProgress(40);
                        compr.setText("Conexion desde Internet Movil");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                proceso.setProgress(75);
                                compr.setText("Verificando Conexion a Internet Espere...");
                                RequestQueue queue = Volley.newRequestQueue(Inicio.this);
                                String ping = "https://www.google.cl";
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        proceso.setProgress(100);
                                        compr.setText("Conectado al Internet");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                Intent i2 = new Intent(Inicio.this,DHT11.class);
                                                i2.putExtra("temperatura", a);
                                                i2.putExtra("humedad", b);
                                                startActivity(i2);
                                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                                finish();

                                            }
                                        },2500);
                                    }

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        compr.setText("Error: Tiempo de  Respuesta Agotado, Reintentando");
                                        Toast.makeText(getApplicationContext(), "Error: Tiempo de  Respuesta Agotado, Reintentando", Toast.LENGTH_LONG).show();
                                        proceso.setProgress(0);
                                        startActivity(new Intent(Inicio.this, Inicio.class));
                                        finish();
                                    }
                                });
                                stringRequest.setShouldCache(false);
                                queue.add(stringRequest);
                            }
                        },3000);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error: No tienes Activado Ningun Modo de Conexion a Internet (Wifi, Internet Movil)", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
        },3000);
    }


    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                try {
                    JSONArray ja = new JSONArray(response);
                    Log.i("sizejson",""+ja.length());
                    Cargar(ja);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                a = "Actualizando...";
                b = "Actualizando...";
            }
        });
        queue.add(stringRequest);
    }

    public void Cargar(JSONArray ja){

        for(int i=0;i<ja.length();i+=2){

            try {
                a = ja.getString(i+0) + "° C.";
                b = ja.getString(i+1) + " %";

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
