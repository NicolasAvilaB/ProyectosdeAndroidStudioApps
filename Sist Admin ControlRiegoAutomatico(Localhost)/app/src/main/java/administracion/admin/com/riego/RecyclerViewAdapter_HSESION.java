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

class RecyclerViewHolder_HSESION extends RecyclerView.ViewHolder{

    public TextView IDS, NOMBRES, CARGOS, HORA1, HORA2, FECHA1, FECHA2;
    CardView tarjetahsesion;

    public RecyclerViewHolder_HSESION(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        NOMBRES = itemView.findViewById(R.id.NOMBRES);
        CARGOS = itemView.findViewById(R.id.CARGOS);
        HORA1 = itemView.findViewById(R.id.HORA1);
        HORA2 = itemView.findViewById(R.id.HORA2);
        FECHA1 = itemView.findViewById(R.id.FECHA1);
        FECHA2 = itemView.findViewById(R.id.FECHA2);

        tarjetahsesion = itemView.findViewById(R.id.tarjetahsesion);
    }

}

public class RecyclerViewAdapter_HSESION extends RecyclerView.Adapter<RecyclerViewHolder_HSESION>{
    private List<Datahsesion> listadatahsesion = new ArrayList<Datahsesion>();

    public RecyclerViewAdapter_HSESION(List<Datahsesion> listadatahsesion) {
        this.listadatahsesion = listadatahsesion;
    }

    @Override
    public RecyclerViewHolder_HSESION onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_hrsesion,parent,false);
        return new RecyclerViewHolder_HSESION(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_HSESION holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetahsesion);
        holder.IDS.setText(listadatahsesion.get(position).getA());
        holder.NOMBRES.setText(listadatahsesion.get(position).getB());
        holder.CARGOS.setText(listadatahsesion.get(position).getC());
        holder.HORA1.setText(listadatahsesion.get(position).getD());
        holder.HORA2.setText(listadatahsesion.get(position).getE());
        holder.FECHA1.setText(listadatahsesion.get(position).getF());
        holder.FECHA2.setText(listadatahsesion.get(position).getG());

    }

    @Override
    public int getItemCount() {
        return listadatahsesion.size();
    }




}
