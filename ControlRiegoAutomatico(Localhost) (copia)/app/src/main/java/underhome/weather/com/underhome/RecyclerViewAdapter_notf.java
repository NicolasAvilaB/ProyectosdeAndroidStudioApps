package underhome.weather.com.underhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

class RecyclerViewHolder_notf extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView IDES, ESTACION, MES, FECHA, DESCRIPCION;
    CardView tarjeta4;
    Button conf;
    Context mensaje_flotante, ventana_riego;
    Snackbar mostrarsnack;

    public RecyclerViewHolder_notf(View itemView) {
        super(itemView);
        ventana_riego = itemView.getContext();
        IDES = itemView.findViewById(R.id.IDES);
        ESTACION = itemView.findViewById(R.id.ESTACION);
        MES = itemView.findViewById(R.id.MES);
        FECHA = itemView.findViewById(R.id.FECHA);
        DESCRIPCION = itemView.findViewById(R.id.DESCRIPCION);
        tarjeta4 = itemView.findViewById(R.id.tarjeta4);
        conf = itemView.findViewById(R.id.conf);
        mensaje_flotante = itemView.getContext();
    }

    void setOnClickListeners() {
        conf.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.conf:
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(mensaje_flotante);
                dialogo1.setTitle("Notificaciones");
                dialogo1.setMessage("¿Desea confirmar la Notificacion?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        String idd = IDES.getText().toString();
                        idd = idd.replace("Notificacion: ", "");
                        String registro0 = "http://192.168.43.250/sistemariego_app/confirmacion_notificacion.php?t=" + idd;
                        Enviar_Confirmacion(registro0);
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        mostrarsnack = Snackbar.make(itemView.findViewById(R.id.conf), "Operacion Cancelada", Snackbar.LENGTH_LONG).setAction("Action", null);
                        View sbView = mostrarsnack.getView();
                        sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                        mostrarsnack.show();
                    }
                });
                dialogo1.show();
            break;
        }
    }
    public void Enviar_Confirmacion(String URL) {
        RequestQueue queue = Volley.newRequestQueue(mensaje_flotante);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");

                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        mostrarsnack = Snackbar.make(itemView.findViewById(R.id.conf), "Confirmacion Enviada Exitosamente", Snackbar.LENGTH_LONG).setAction("Action", null);
                        View sbView = mostrarsnack.getView();
                        sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                        mostrarsnack.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarsnack = Snackbar.make(itemView.findViewById(R.id.conf), "Error de Confirmacion"+ error, Snackbar.LENGTH_LONG).setAction("Action", null);
                View sbView = mostrarsnack.getView();
                sbView.setBackgroundColor(Color.rgb(13, 112, 110));
                mostrarsnack.show();
                Log.i("Error: ",error.toString());

            }
        });
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }
}

public class RecyclerViewAdapter_notf extends RecyclerView.Adapter<RecyclerViewHolder_notf>{

    private List<Data4> listadata4 = new ArrayList<Data4>();

    public RecyclerViewAdapter_notf(List<Data4> listadata4) {
        this.listadata4 = listadata4;
    }

    @Override
    public RecyclerViewHolder_notf onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_notificaciones,parent,false);
        return new RecyclerViewHolder_notf(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_notf holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjeta4);
        holder.IDES.setText(listadata4.get(position).getA());
        holder.ESTACION.setText(listadata4.get(position).getB());
        holder.MES.setText(listadata4.get(position).getC());
        holder.FECHA.setText(listadata4.get(position).getD());
        holder.DESCRIPCION.setText(listadata4.get(position).getE());
        holder.setOnClickListeners();

    }

    @Override
    public int getItemCount() {
        return listadata4.size();
    }

}