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
class RecyclerViewHolder_HSUELO extends RecyclerView.ViewHolder{

    public TextView IDS, CULTIVO, SUELO, ABONO;
    CardView tarjetah;

    public RecyclerViewHolder_HSUELO(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        CULTIVO = itemView.findViewById(R.id.CULTIVO);
        SUELO = itemView.findViewById(R.id.SUELO);
        ABONO = itemView.findViewById(R.id.ABONO);
        tarjetah = itemView.findViewById(R.id.tarjetah);
    }

}
public class RecyclerViewAdapter_HSUELO  extends RecyclerView.Adapter<RecyclerViewHolder_HSUELO>{

    private List<Datah> listadatah = new ArrayList<Datah>();

    public RecyclerViewAdapter_HSUELO(List<Datah> listadatah) {
        this.listadatah = listadatah;
    }

    @Override
    public RecyclerViewHolder_HSUELO onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_hsuelo,parent,false);
        return new RecyclerViewHolder_HSUELO(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_HSUELO holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetah);
        holder.IDS.setText(listadatah.get(position).getA());
        holder.CULTIVO.setText(listadatah.get(position).getB());
        holder.SUELO.setText(listadatah.get(position).getC());
        holder.ABONO.setText(listadatah.get(position).getD());

    }

    @Override
    public int getItemCount() {
        return listadatah.size();
    }


}
