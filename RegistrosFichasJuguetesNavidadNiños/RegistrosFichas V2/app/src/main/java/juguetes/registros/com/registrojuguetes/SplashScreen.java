package juguetes.registros.com.registrojuguetes;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    ProgressBar proceso;
    TextView compr;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        proceso = findViewById(R.id.proceso);
        compr = findViewById(R.id.compr);
        videoView3 = findViewById(R.id.videoView3);
        Uri video = Uri.parse("android.resource://"+ getPackageName() + "/"+R.raw.ui);
        videoView3.setVideoURI(video);
        videoView3.start();
        videoView3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation m = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.transicionsplash);
                        Animation m2 = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.transicionsplash);
                        proceso.startAnimation(m2);
                        compr.startAnimation(m);

                        ConnectivityManager conexion = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo wifi = conexion.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        NetworkInfo datos = conexion.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                        if (String.valueOf(wifi.getState()).equals("CONNECTED")) {
                            proceso.setProgress(40);
                            compr.setText("Conexion desde Wifi");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    proceso.setProgress(75);
                                    compr.setText("Verificando Conexion a Internet Espere...");
                                    RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
                                    String ping = "https://www.google.cl";
                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                                            //progressBar2.dismiss();
                                            proceso.setProgress(100);
                                            compr.setText("Conectado al Internet");
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    startActivity(new Intent(SplashScreen.this, bu.class));
                                                    finish();

                                                }
                                            }, 2500);
                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                            /*Toast.makeText(getApplicationContext(), "No hay Internet ", Toast.LENGTH_SHORT).show();
                            progressBar2.dismiss();*/
                                            compr.setText("Error: Tiempo de  Respuesta Agotado, Reintentando");
                                            Toast.makeText(getApplicationContext(), "Error: Tiempo de  Respuesta Agotado, Reintentando", Toast.LENGTH_LONG).show();
                                            proceso.setProgress(0);
                                            startActivity(new Intent(SplashScreen.this, SplashScreen.class));
                                            finish();
                                        }
                                    });
                                    stringRequest.setShouldCache(false);
                                    queue.add(stringRequest);
                                }
                            }, 3000);
                    /*progressBar2= new ProgressDialog(Inicio.this);
                    progressBar2.setMessage();
                    progressBar2.show();
                    progressBar2.setCancelable(false);*/
                        } else {
                            if (String.valueOf(datos.getState()).equals("CONNECTED")) {
                                proceso.setProgress(40);
                                compr.setText("Conexion desde Internet Movil");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        proceso.setProgress(75);
                                        compr.setText("Verificando Conexion a Internet Espere...");
                                        RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
                                        String ping = "https://www.google.cl";
                                        StringRequest stringRequest = new StringRequest(Request.Method.GET, ping, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                proceso.setProgress(100);
                                                compr.setText("Conectado al Internet");
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        Intent i2 = new Intent(SplashScreen.this, bu.class);
                                                        startActivity(i2);
                                                        finish();

                                                    }
                                                }, 2500);
                                            }

                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                compr.setText("Error: Tiempo de  Respuesta Agotado, Reintentando");
                                                Toast.makeText(getApplicationContext(), "Error: Tiempo de  Respuesta Agotado, Reintentando", Toast.LENGTH_LONG).show();
                                                proceso.setProgress(0);
                                                startActivity(new Intent(SplashScreen.this, SplashScreen.class));
                                                finish();
                                            }
                                        });
                                        stringRequest.setShouldCache(false);
                                        queue.add(stringRequest);
                                    }
                                }, 3000);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: No tienes Activado Ningun Modo de Conexion a Internet (Wifi, Internet Movil)", Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }
                }, 1000);

            }
        });

    }


    public void animar_ver(final View myView){

        // create the animator for this view (the start radius is zero)
        if (Build.VERSION.SDK_INT >= 21){
            // get the center for the clipping circle
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            myView.setVisibility(View.VISIBLE);
            anim.start();
        }
        // make the view visible and start the animation

    }

}
