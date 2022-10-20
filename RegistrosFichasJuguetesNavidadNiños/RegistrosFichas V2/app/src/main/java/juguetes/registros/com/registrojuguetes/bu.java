package juguetes.registros.com.registrojuguetes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class bu extends AppCompatActivity {
    EditText buscador;
    Button buscar;
    ProgressDialog progressBarbuscar;
    LinearLayout root_layout;
    StringRequest stringRequest;
    RequestQueue queue;
    Snackbar mostrarmensaje;
    CardView tarjeta;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listadata = new ArrayList<Data>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu);
        setTitle("MUN. DE PERALILLO");
        getSupportActionBar().setElevation(0);
        root_layout = findViewById(R.id.root_layout);
        if(Build.VERSION.SDK_INT >= 21){
            overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
            if (savedInstanceState == null){
                root_layout.setVisibility(View.INVISIBLE);
                ViewTreeObserver viewTreeObserver = root_layout.getViewTreeObserver();
                if(viewTreeObserver.isAlive()){
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            animacioncircular_activity();
                            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                                root_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }else{
                                root_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            }
                        }
                    });
                }
            }
        }else{
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
        recyclerView = findViewById(R.id.recycler);
        tarjeta = findViewById(R.id.tarjeta);
        buscador = findViewById(R.id.buscador);
        buscar =findViewById(R.id.buscar);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarbuscador();
            }
        });



        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(buscador.getText().length()==0){
                    //String consultas = "http://10.251.151.81/juguetes/mostrar.php";
                    //EnviarRecibirDatos(consultas);
                    if(Build.VERSION.SDK_INT >= 21){
                        listadata.clear();
                        ejecutar_recyclerview();
                    }
                    else{
                        listadata.clear();
                        ejecutar_recyclerview();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void EnviarRecibirDatos(String URL){

        queue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarDatos(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    progressBarbuscar.dismiss();
                    mostrarmensaje = Snackbar.make(findViewById(R.id.buscar), "Error: No se encontro el usuario",3000).setAction("Action", null);
                    View sbView = mostrarmensaje.getView();
                    sbView.setBackgroundColor(Color.rgb(43,62,95));
                    mostrarmensaje.show();
                    if(Build.VERSION.SDK_INT >= 21){
                        listadata.clear();
                        ejecutar_recyclerview();
                    }
                    else{
                        listadata.clear();
                        ejecutar_recyclerview();
                    }
                    //listar.setAdapter(null);
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    public void CargarDatos(JSONArray ja){
        listadata.clear();
        ejecutar_recyclerview();
        for(int i=0;i<ja.length();i+=12){
            try{
                listadata.add(new Data("ID: "+ja.getString(i),"Numero Ficha: "+ja.getString(i+1),"Unidad Vecinal: "+ja.getString(i+2), "Juntas Vecinos: "+ja.getString(i+3),"Sector: "+ja.getString(i+4),"Direccion: "+ja.getString(i+5),"Familia: "+ja.getString(i+6),"Niño/a: "+ja.getString(i+7),"Rut: "+ja.getString(i+8),"Edad: "+ja.getString(i+9),"Sexo: "+ja.getString(i+10),"Estado: "+ja.getString(i+11)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        if(Build.VERSION.SDK_INT >= 21){
            //animarcardviews_ver();
            progressBarbuscar.dismiss();
           mostrarmensaje = Snackbar.make(findViewById(R.id.buscar), "Datos Encontrados Exitosamente",3000).setAction("Action", null);
           View sbView = mostrarmensaje.getView();
           sbView.setBackgroundColor(Color.rgb(43,62,95));
           mostrarmensaje.show();
        }
        else{
            //recyclerView.setVisibility(View.VISIBLE);
            progressBarbuscar.dismiss();
            mostrarmensaje = Snackbar.make(findViewById(R.id.buscar), "Datos Encontrados Exitosamente",3000).setAction("Action", null);
            View sbView = mostrarmensaje.getView();
            sbView.setBackgroundColor(Color.rgb(43,62,95));
            mostrarmensaje.show();

        }
    }

    public void ejecutar_recyclerview(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(bu.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(listadata);
        recyclerView.setAdapter(adapter);
    }

    /*public void animarcardviews_ver(){
        if (Build.VERSION.SDK_INT >= 21) {

         // create the animator for this view (the start radius is zero)
        //if (Build.VERSION.SDK_INT >= 21){
            View myView = findViewById(R.id.tarjeta);

            // get the center for the clipping circle
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            anim.setDuration(800);
            myView.setVisibility(View.VISIBLE);
            anim.start();
        }
         // make the view visible and start the animation

    }

    public void animarcardviews_ocultar(){
        // previously visible view
        if (Build.VERSION.SDK_INT >= 21) {
            final View myView = findViewById(R.id.tarjeta);

            // get the center for the clipping circle
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;

            // get the initial radius for the clipping circle
            int initialRadius = myView.getWidth();

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
            anim.setDuration(800);
            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            });
            // start the animation
            anim.start();
        }

    }*/

    public void animacioncircular_activity(){

        // create the animator for this view (the start radius is zero)
        if (Build.VERSION.SDK_INT >= 21){
            // get the center for the clipping circle
            int cx = (root_layout.getLeft() + root_layout.getRight()) / 2;
            int cy = (root_layout.getTop() + root_layout.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(root_layout.getWidth(), root_layout.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(root_layout, cx, cy, 0, finalRadius);
            anim.setDuration(800);
            root_layout.setVisibility(View.VISIBLE);
            anim.start();
        }
        // make the view visible and start the animation

    }

    public void ejecutarbuscador(){
        if (buscador.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Error: No Has Digitado Ningun Dato a Buscar", Toast.LENGTH_LONG).show();
        }
        else {
            InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(buscar.getWindowToken(), 0);
            if(Build.VERSION.SDK_INT >= 21){
                //animarcardviews_ocultar();
                progressBarbuscar= new ProgressDialog(bu.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Buscando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar Operacion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listadata.clear();
                        ejecutar_recyclerview();
                        stringRequest.cancel();
                        //recyclerView.setVisibility(View.VISIBLE);
                        progressBarbuscar.dismiss();
                        mostrarmensaje = Snackbar.make(findViewById(R.id.buscar), "Busqueda Cancelada",3000).setAction("Action", null);
                        View sbView = mostrarmensaje.getView();
                        sbView.setBackgroundColor(Color.rgb(43,62,95));
                        mostrarmensaje.show();
                    }
                });
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String consulta2 = "https://systemchile.com/buscar.php?producto=" + buscador.getText().toString();
                consulta2 = consulta2.replace(" ", "%20");
                EnviarRecibirDatos(consulta2);
            }
            else{
                progressBarbuscar= new ProgressDialog(bu.this);
                progressBarbuscar.dismiss();
                progressBarbuscar.setTitle("Buscando Datos");
                progressBarbuscar.setMessage("Espere un Momento...");
                progressBarbuscar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar Operacion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listadata.clear();
                        ejecutar_recyclerview();
                        stringRequest.cancel();
                        //recyclerView.setVisibility(View.VISIBLE);
                        progressBarbuscar.dismiss();
                        mostrarmensaje = Snackbar.make(findViewById(R.id.buscar), "Busqueda Cancelada",3000).setAction("Action", null);
                        View sbView = mostrarmensaje.getView();
                        sbView.setBackgroundColor(Color.rgb(43,62,95));
                        mostrarmensaje.show();
                    }
                });
                progressBarbuscar.setCancelable(false);
                progressBarbuscar.show();
                String consulta2 = "https://systemchile.com/buscar.php?producto=" + buscador.getText().toString();
                consulta2 = consulta2.replace(" ", "%20");
                EnviarRecibirDatos(consulta2);
            }
        }
    }
}
