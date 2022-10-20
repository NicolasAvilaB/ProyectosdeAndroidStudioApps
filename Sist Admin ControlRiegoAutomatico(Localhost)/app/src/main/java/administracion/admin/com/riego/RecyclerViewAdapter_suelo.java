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

class RecyclerViewHolder_suelo extends RecyclerView.ViewHolder{

    public TextView IDS, TIPOSUELOS, ESTADOSUELOS, ABONOS, TIPOCULTIVOS, INVERNADEROS, PRE;
    CardView tarjetasuelo;

    public RecyclerViewHolder_suelo(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        TIPOSUELOS = itemView.findViewById(R.id.TIPOSUELOS);
        ESTADOSUELOS = itemView.findViewById(R.id.ESTADOSUELOS);
        ABONOS = itemView.findViewById(R.id.ABONOS);
        TIPOCULTIVOS = itemView.findViewById(R.id.TIPOCULTIVOS);
        INVERNADEROS = itemView.findViewById(R.id.INVERNADEROS);
        PRE = itemView.findViewById(R.id.PRE);
        tarjetasuelo = itemView.findViewById(R.id.tarjetasuelo);
    }

}

public class RecyclerViewAdapter_suelo extends RecyclerView.Adapter<RecyclerViewHolder_suelo>{

    private List<Data2> listadata2 = new ArrayList<Data2>();

    public RecyclerViewAdapter_suelo(List<Data2> listadata2) {
        this.listadata2 = listadata2;
    }

    @Override
    public RecyclerViewHolder_suelo onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_suelo,parent,false);
        return new RecyclerViewHolder_suelo(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_suelo holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetasuelo);
        holder.IDS.setText(listadata2.get(position).getA());
        holder.TIPOSUELOS.setText(listadata2.get(position).getB());
        holder.ESTADOSUELOS.setText(listadata2.get(position).getC());
        holder.ABONOS.setText(listadata2.get(position).getD());
        holder.TIPOCULTIVOS.setText(listadata2.get(position).getE());
        holder.INVERNADEROS.setText(listadata2.get(position).getF());
        holder.PRE.setText(listadata2.get(position).getG());

    }

    @Override
    public int getItemCount() {
        return listadata2.size();
    }
}