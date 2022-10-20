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

class RecyclerViewHolder_usuarios extends RecyclerView.ViewHolder{

    public TextView IDS, NOMBRES, CLAVES, CARGOS, EMAILS, TIPOESTADOS;
    CardView tarjetausuarios;

    public RecyclerViewHolder_usuarios(View itemView) {
        super(itemView);
        IDS = itemView.findViewById(R.id.IDS);
        NOMBRES = itemView.findViewById(R.id.NOMBRES);
        CLAVES = itemView.findViewById(R.id.CLAVES);
        CARGOS = itemView.findViewById(R.id.CARGOS);
        EMAILS = itemView.findViewById(R.id.EMAILS);
        TIPOESTADOS = itemView.findViewById(R.id.TIPOESTADOS);
        tarjetausuarios = itemView.findViewById(R.id.tarjetausuarios);
    }
}

public class RecyclerViewAdapter_usuarios extends RecyclerView.Adapter<RecyclerViewHolder_usuarios>{

    private List<Data3> listadata3 = new ArrayList<Data3>();

    public RecyclerViewAdapter_usuarios(List<Data3> listadata3) {
        this.listadata3 = listadata3;
    }

    @Override
    public RecyclerViewHolder_usuarios onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_usuarios,parent,false);
        return new RecyclerViewHolder_usuarios(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_usuarios holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjetausuarios);
        holder.IDS.setText(listadata3.get(position).getA());
        holder.NOMBRES.setText(listadata3.get(position).getB());
        holder.CLAVES.setText(listadata3.get(position).getC());
        holder.CARGOS.setText(listadata3.get(position).getD());
        holder.EMAILS.setText(listadata3.get(position).getE());
        holder.TIPOESTADOS.setText(listadata3.get(position).getF());

    }

    @Override
    public int getItemCount() {
        return listadata3.size();
    }
}