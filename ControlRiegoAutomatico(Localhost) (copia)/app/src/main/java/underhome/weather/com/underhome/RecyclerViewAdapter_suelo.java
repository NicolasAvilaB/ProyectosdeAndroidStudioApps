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

class RecyclerViewHolder_suelo extends RecyclerView.ViewHolder{

    public TextView TIPOSUELO, ESTADOSUELO, ABONO, TIPOCULTIVOS, INVERNADERO, TEMPERATURA;
    CardView tarjeta3;

    public RecyclerViewHolder_suelo(View itemView) {
        super(itemView);
        TIPOSUELO = itemView.findViewById(R.id.TIPOSUELO);
        ESTADOSUELO = itemView.findViewById(R.id.ESTADOSUELO);
        ABONO = itemView.findViewById(R.id.ABONO);
        TIPOCULTIVOS = itemView.findViewById(R.id.TIPOCULTIVOS);
        INVERNADERO = itemView.findViewById(R.id.INVERNADERO);
        TEMPERATURA = itemView.findViewById(R.id.TEMPERATURA);
        tarjeta3 = itemView.findViewById(R.id.tarjeta3);
    }

}

public class RecyclerViewAdapter_suelo extends RecyclerView.Adapter<RecyclerViewHolder_suelo>{

    private List<Data3> listadata3 = new ArrayList<Data3>();

    public RecyclerViewAdapter_suelo(List<Data3> listadata3) {
        this.listadata3 = listadata3;
    }

    @Override
    public RecyclerViewHolder_suelo onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_suelo,parent,false);
        return new RecyclerViewHolder_suelo(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_suelo holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjeta3);
        holder.TIPOSUELO.setText(listadata3.get(position).getA());
        holder.ESTADOSUELO.setText(listadata3.get(position).getB());
        holder.ABONO.setText(listadata3.get(position).getC());
        holder.TIPOCULTIVOS.setText(listadata3.get(position).getD());
        holder.INVERNADERO.setText(listadata3.get(position).getE());
        holder.TEMPERATURA.setText(listadata3.get(position).getF());

    }

    @Override
    public int getItemCount() {
        return listadata3.size();
    }
}