package underhome.weather.com.underhome;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView TIPOCLIMA;
    public TextView RESUMEN;
    public TextView TEMPERATURA;
    public TextView HUMEDAD;
    CardView tarjeta;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        TIPOCLIMA = itemView.findViewById(R.id.TIPOCLIMA);
        RESUMEN = itemView.findViewById(R.id.RESUMEN);
        TEMPERATURA = itemView.findViewById(R.id.TEMPERATURA);
        HUMEDAD = itemView.findViewById(R.id.HUMEDAD);
        tarjeta = itemView.findViewById(R.id.tarjeta);
    }

}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<Data> listadata = new ArrayList<Data>();

    public RecyclerViewAdapter(List<Data> listadata) {
        this.listadata = listadata;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item,parent,false);
        return new RecyclerViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjeta);
        holder.TIPOCLIMA.setText(listadata.get(position).getA());
        holder.RESUMEN.setText(listadata.get(position).getB());
        holder.TEMPERATURA.setText(listadata.get(position).getC());
        holder.HUMEDAD.setText(listadata.get(position).getD());

    }

    @Override
    public int getItemCount() {
        return listadata.size();
    }
}