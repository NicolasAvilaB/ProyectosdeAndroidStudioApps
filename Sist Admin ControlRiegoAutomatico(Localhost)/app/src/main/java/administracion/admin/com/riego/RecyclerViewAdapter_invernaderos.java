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

class RecyclerViewHolder_invernaderos extends RecyclerView.ViewHolder{

    public TextView IDS, INVERNADEROS, TIPOCULTIVOS, TIPOSUELOS, ABONOS;
    CardView tarjetainvernaderos;

    public RecyclerViewHolder_invernaderos(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        INVERNADEROS = itemView.findViewById(R.id.INVERNADEROS);
        TIPOCULTIVOS = itemView.findViewById(R.id.TIPOCULTIVOS);
        TIPOSUELOS = itemView.findViewById(R.id.TIPOSUELOS);
        ABONOS = itemView.findViewById(R.id.ABONOS);
        tarjetainvernaderos = itemView.findViewById(R.id.tarjetainvernaderos);
    }

}

public class RecyclerViewAdapter_invernaderos extends RecyclerView.Adapter<RecyclerViewHolder_invernaderos>{

    private List<Data4> listadata4 = new ArrayList<Data4>();

    public RecyclerViewAdapter_invernaderos(List<Data4> listadata4) {
        this.listadata4 = listadata4;
    }

    @Override
    public RecyclerViewHolder_invernaderos onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_invernaderos,parent,false);
        return new RecyclerViewHolder_invernaderos(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_invernaderos holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetainvernaderos);
        holder.IDS.setText(listadata4.get(position).getA());
        holder.INVERNADEROS.setText(listadata4.get(position).getB());
        holder.TIPOCULTIVOS.setText(listadata4.get(position).getC());
        holder.TIPOSUELOS.setText(listadata4.get(position).getD());
        holder.ABONOS.setText(listadata4.get(position).getE());

    }

    @Override
    public int getItemCount() {
        return listadata4.size();
    }
}