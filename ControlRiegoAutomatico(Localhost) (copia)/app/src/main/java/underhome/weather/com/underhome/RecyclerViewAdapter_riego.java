package underhome.weather.com.underhome;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewHolder_riego extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView I, TIPOPLAN, TIPOCULTIVO, HORA, FECHA, TIEMPO;
    public Button aceptar_riego;
    CardView tarjeta2;
    Context ventana_riego;


    public RecyclerViewHolder_riego(View itemView) {
        super(itemView);
        ventana_riego = itemView.getContext();
        I = itemView.findViewById(R.id.I);
        TIPOPLAN = itemView.findViewById(R.id.TIPOPLAN);
        TIPOCULTIVO = itemView.findViewById(R.id.TIPOCULTIVO);
        HORA = itemView.findViewById(R.id.HORA);
        FECHA = itemView.findViewById(R.id.FECHA);
        TIEMPO = itemView.findViewById(R.id.TIEMPO);
        aceptar_riego = itemView.findViewById(R.id.aceptar_riego);
        tarjeta2 = itemView.findViewById(R.id.tarjeta2);
    }

    void setOnClickListeners() {
        aceptar_riego.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aceptar_riego:
            Intent intent = new Intent(ventana_riego, Planes_Riego.class);
            intent.putExtra("i", I.getText().toString());
            intent.putExtra("tipoplan", TIPOPLAN.getText().toString());
            intent.putExtra("tipocultivo", TIPOCULTIVO.getText().toString());
            intent.putExtra("hora", HORA.getText().toString());
            intent.putExtra("fecha", FECHA.getText().toString());
            intent.putExtra("tiempo", TIEMPO.getText().toString());
            ventana_riego.startActivity(intent);

            break;
        }
    }
}

public class RecyclerViewAdapter_riego extends RecyclerView.Adapter<RecyclerViewHolder_riego> {

    private List<Data2> listadata2 = new ArrayList<Data2>();

    public RecyclerViewAdapter_riego(List<Data2> listadata2) {
        this.listadata2 = listadata2;
    }

    @Override
    public RecyclerViewHolder_riego onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_riego,parent,false);
        return new RecyclerViewHolder_riego(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_riego holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjeta2);
        holder.I.setText(listadata2.get(position).getA());
        holder.TIPOPLAN.setText(listadata2.get(position).getB());
        holder.TIPOCULTIVO.setText(listadata2.get(position).getC());
        holder.HORA.setText(listadata2.get(position).getD());
        holder.FECHA.setText(listadata2.get(position).getE());
        holder.TIEMPO.setText(listadata2.get(position).getF());
        holder.setOnClickListeners();
    }



    @Override
    public int getItemCount() {
        return listadata2.size();
    }

}