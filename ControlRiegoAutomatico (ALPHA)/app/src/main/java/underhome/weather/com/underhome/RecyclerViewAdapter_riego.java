package underhome.weather.com.underhome;

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

class RecyclerViewHolder_riego extends RecyclerView.ViewHolder{

    public TextView TIPOPLAN;
    public TextView TIPOCULTIVO;
    public TextView HORA;
    public TextView FECHA;
    CardView tarjeta2;

    public RecyclerViewHolder_riego(View itemView) {
        super(itemView);
        TIPOPLAN = itemView.findViewById(R.id.TIPOPLAN);
        TIPOCULTIVO = itemView.findViewById(R.id.TIPOCULTIVO);
        HORA = itemView.findViewById(R.id.HORA);
        FECHA = itemView.findViewById(R.id.FECHA);
        tarjeta2 = itemView.findViewById(R.id.tarjeta2);
    }

}

public class RecyclerViewAdapter_riego extends RecyclerView.Adapter<RecyclerViewHolder_riego>{

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
        holder.TIPOPLAN.setText(listadata2.get(position).getA());
        holder.TIPOCULTIVO.setText(listadata2.get(position).getB());
        holder.HORA.setText(listadata2.get(position).getC());
        holder.FECHA.setText(listadata2.get(position).getD());

    }

    @Override
    public int getItemCount() {
        return listadata2.size();
    }
}