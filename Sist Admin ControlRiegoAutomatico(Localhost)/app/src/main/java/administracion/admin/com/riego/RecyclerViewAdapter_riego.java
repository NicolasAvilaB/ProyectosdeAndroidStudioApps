package administracion.admin.com.riego;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import underhome.weather.com.underhome.R;

class RecyclerViewHolder_riego extends RecyclerView.ViewHolder{

    public TextView IDS, TIPOPLANS, TIPOCULTIVOS, TIEMPOS, HORAS, FECHAS;
    CardView tarjetariego;

    public RecyclerViewHolder_riego(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        TIPOPLANS = itemView.findViewById(R.id.TIPOPLANS);
        TIPOCULTIVOS = itemView.findViewById(R.id.TIPOCULTIVOS);
        TIEMPOS = itemView.findViewById(R.id.TIEMPOS);
        HORAS = itemView.findViewById(R.id.HORAS);
        FECHAS = itemView.findViewById(R.id.FECHAS);
        tarjetariego = itemView.findViewById(R.id.tarjetariego);
    }

}

public class RecyclerViewAdapter_riego extends RecyclerView.Adapter<RecyclerViewHolder_riego>{

    private List<Data1> listadata1 = new ArrayList<Data1>();

    public RecyclerViewAdapter_riego(List<Data1> listadata1) {
        this.listadata1 = listadata1;
    }

    @Override
    public RecyclerViewHolder_riego onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_plan_riego,parent,false);
        return new RecyclerViewHolder_riego(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_riego holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetariego);
        holder.IDS.setText(listadata1.get(position).getA());
        holder.TIPOPLANS.setText(listadata1.get(position).getB());
        holder.TIPOCULTIVOS.setText(listadata1.get(position).getC());
        holder.TIEMPOS.setText(listadata1.get(position).getD());
        holder.HORAS.setText(listadata1.get(position).getE());
        holder.FECHAS.setText(listadata1.get(position).getF());

    }

    @Override
    public int getItemCount() {
        return listadata1.size();
    }
}