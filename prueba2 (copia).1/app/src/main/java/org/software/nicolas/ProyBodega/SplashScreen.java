package org.software.nicolas.ProyBodega;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SplashScreen extends AppCompatActivity {
    VideoView videoView3;
    ProgressDialog progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        videoView3 = (VideoView)findViewById(R.id.videoView3);
        Uri video = Uri.parse("android.resource://"+ getPackageName() + "/"+R.raw.ui);
        videoView3.setVideoURI(video);
        videoView3.start();
        videoView3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                ConnectivityManager conexion = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = conexion.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo datos = conexion.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (String.valueOf(wifi.getState()).equals("CONNECTED")){
                    Toast.makeText(getApplicationContext(), "Modo WIFI Detectado", Toast.LENGTH_SHORT).show();
                    progressBar2= new ProgressDialog(SplashScreen.this);
                    progressBar2.setMessage("Verificando Conexion a Internet Espere un Momento...");
                    progressBar2.show();
                    RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
                    String ping = "https://www.google.cl";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Conectado al Internet", Toast.LENGTH_SHORT).show();
                            progressBar2.dismiss();
                            Intent intent = new Intent(SplashScreen.this, mysql.class);
                            startActivity(intent);
                            finish();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "No hay Internet", Toast.LENGTH_SHORT).show();
                            progressBar2.dismiss();
                            Intent intent = new Intent(SplashScreen.this, SplashScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    stringRequest.setShouldCache(false);
                    queue.add(stringRequest);

                }
                else{
                    if(String.valueOf(datos.getState()).equals("CONNECTED")){
                        Toast.makeText(getApplicationContext(), "Modo Internet Movil Detectado", Toast.LENGTH_SHORT).show();
                        progressBar2= new ProgressDialog(SplashScreen.this);
                        progressBar2.setMessage("Verificando Conexion a Internet Espere un Momento...");
                        progressBar2.show();
                        RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
                        String ping = "https://www.google.cl";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "Conectado al Internet", Toast.LENGTH_SHORT).show();
                                progressBar2.dismiss();
                                Intent intent = new Intent(SplashScreen.this, mysql.class);
                                startActivity(intent);
                                finish();
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "No hay Internet", Toast.LENGTH_SHORT).show();
                                progressBar2.dismiss();
                                Intent intent = new Intent(SplashScreen.this, SplashScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        stringRequest.setShouldCache(false);
                        queue.add(stringRequest);
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
        });
    }
}
