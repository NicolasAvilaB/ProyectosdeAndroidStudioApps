package com.earthquake.usgs.usgs;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class Principal extends AppCompatActivity {
    StringRequest stringRequest;
    RequestQueue queue;
    TextView textView,textView2,textView3,textView4,textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle("USGS");
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        String consulta = "https://earthquake.usgs.gov/earthquake/feed/v1.0/summary/2.5_day.geojson";
        EnviarRecibirDatos(consulta);

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
        for(int i=0;i<ja.length();i+=5){
            try{
                textView.setText(""+ja.getString(i));
                textView2.setText(""+ja.getString(i+1));
                textView3.setText(""+ja.getString(i+2));
                textView4.setText(""+ja.getString(i+3));
                textView5.setText(""+ja.getString(i+4));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
