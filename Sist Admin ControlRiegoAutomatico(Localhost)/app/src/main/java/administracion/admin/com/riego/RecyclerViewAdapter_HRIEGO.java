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

class RecyclerViewHolder_HRIEGO extends RecyclerView.ViewHolder{

    public TextView IDS, CULTIVO, PLAN, HORAH, FECHAH, TIEMP;
    CardView tarjetahriego;

    public RecyclerViewHolder_HRIEGO(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        CULTIVO = itemView.findViewById(R.id.CULTIVO);
        PLAN = itemView.findViewById(R.id.PLAN);
        HORAH = itemView.findViewById(R.id.HORAH);
        FECHAH = itemView.findViewById(R.id.FECHAH);
        TIEMP = itemView.findViewById(R.id.TIEMP);
        tarjetahriego = itemView.findViewById(R.id.tarjetahriego);
    }

}

public class RecyclerViewAdapter_HRIEGO extends RecyclerView.Adapter<RecyclerViewHolder_HRIEGO>{

    private List<Datahriego> listadatahriego = new ArrayList<Datahriego>();

    public RecyclerViewAdapter_HRIEGO(List<Datahriego> listadatahriego) {
        this.listadatahriego = listadatahriego;
    }

    @Override
    public RecyclerViewHolder_HRIEGO onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_hriego,parent,false);
        return new RecyclerViewHolder_HRIEGO(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_HRIEGO holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetahriego);
        holder.IDS.setText(listadatahriego.get(position).getA());
        holder.CULTIVO.setText(listadatahriego.get(position).getB());
        holder.PLAN.setText(listadatahriego.get(position).getC());
        holder.HORAH.setText(listadatahriego.get(position).getD());
        holder.FECHAH.setText(listadatahriego.get(position).getE());
        holder.TIEMP.setText(listadatahriego.get(position).getF());
    }

    @Override
    public int getItemCount() {
        return listadatahriego.size();
    }


}
