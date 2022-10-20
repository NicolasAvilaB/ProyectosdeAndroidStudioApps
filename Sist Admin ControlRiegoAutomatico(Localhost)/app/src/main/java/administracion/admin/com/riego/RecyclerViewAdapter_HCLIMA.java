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

class RecyclerViewHolder_HCLIMA extends RecyclerView.ViewHolder{

    public TextView IDS, TIPO, RES, TE, HU, HO, FE;
    CardView tarjetahclima;

    public RecyclerViewHolder_HCLIMA(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        TIPO = itemView.findViewById(R.id.TIPO);
        RES = itemView.findViewById(R.id.RES);
        TE = itemView.findViewById(R.id.TE);
        HU = itemView.findViewById(R.id.HU);
        HO = itemView.findViewById(R.id.HO);
        FE = itemView.findViewById(R.id.FE);

        tarjetahclima = itemView.findViewById(R.id.tarjetahclima);
    }

}


public class RecyclerViewAdapter_HCLIMA extends RecyclerView.Adapter<RecyclerViewHolder_HCLIMA>{
    private List<Datahclima> listadatahclima = new ArrayList<Datahclima>();

    public RecyclerViewAdapter_HCLIMA(List<Datahclima> listadatahclima) {
        this.listadatahclima = listadatahclima;
    }

    @Override
    public RecyclerViewHolder_HCLIMA onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_hclima,parent,false);
        return new RecyclerViewHolder_HCLIMA(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_HCLIMA holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetahclima);
        holder.IDS.setText(listadatahclima.get(position).getA());
        holder.TIPO.setText(listadatahclima.get(position).getB());
        holder.RES.setText(listadatahclima.get(position).getC());
        holder.TE.setText(listadatahclima.get(position).getD());
        holder.HU.setText(listadatahclima.get(position).getE());
        holder.HO.setText(listadatahclima.get(position).getF());
        holder.FE.setText(listadatahclima.get(position).getG());


    }

    @Override
    public int getItemCount() {
        return listadatahclima.size();
    }




}
