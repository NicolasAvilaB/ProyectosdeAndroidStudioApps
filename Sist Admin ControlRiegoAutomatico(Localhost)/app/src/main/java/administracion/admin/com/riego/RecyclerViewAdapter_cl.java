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

class RecyclerViewHolder_cl extends RecyclerView.ViewHolder{

    public TextView IDS, TIPOCULTIVOS, ESTACIONANOS, MESS, FECHAS, RESUMENS, OPERARIOS, ESTADONOTFS;
    CardView tarjetacul;

    public RecyclerViewHolder_cl(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        TIPOCULTIVOS = itemView.findViewById(R.id.TIPOCULTIVOS);
        ESTACIONANOS = itemView.findViewById(R.id.ESTACIONANOS);
        MESS = itemView.findViewById(R.id.MESS);
        FECHAS = itemView.findViewById(R.id.FECHAS);
        RESUMENS = itemView.findViewById(R.id.RESUMENS);
        OPERARIOS = itemView.findViewById(R.id.OPERARIOS);
        ESTADONOTFS = itemView.findViewById(R.id.ESTADONOTFS);
        tarjetacul = itemView.findViewById(R.id.tarjetacul);
    }

}

public class RecyclerViewAdapter_cl extends RecyclerView.Adapter<RecyclerViewHolder_cl>{

    private List<Data5> listadata5 = new ArrayList<Data5>();

    public RecyclerViewAdapter_cl(List<Data5> listadata5) {
        this.listadata5 = listadata5;
    }

    @Override
    public RecyclerViewHolder_cl onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_cl,parent,false);
        return new RecyclerViewHolder_cl(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_cl holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetacul);
        holder.IDS.setText(listadata5.get(position).getA());
        holder.TIPOCULTIVOS.setText(listadata5.get(position).getB());
        holder.ESTACIONANOS.setText(listadata5.get(position).getC());
        holder.MESS.setText(listadata5.get(position).getD());
        holder.FECHAS.setText(listadata5.get(position).getE());
        holder.RESUMENS.setText(listadata5.get(position).getF());
        holder.OPERARIOS.setText(listadata5.get(position).getG());
        holder.ESTADONOTFS.setText(listadata5.get(position).getH());

    }

    @Override
    public int getItemCount() {
        return listadata5.size();
    }
}