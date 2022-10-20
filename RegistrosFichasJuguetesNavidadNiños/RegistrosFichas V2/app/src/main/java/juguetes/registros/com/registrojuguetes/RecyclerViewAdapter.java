package juguetes.registros.com.registrojuguetes;

import android.animation.Animator;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 18-03-18.
 */

class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView ID;
    public TextView FICHA;
    public TextView VECINAL;
    public TextView JUNTAS;
    public TextView SECTOR;
    public TextView DIRECCION;
    public TextView FAMILIA;
    public TextView NINOA;
    public TextView RUT;
    public TextView EDAD;
    public TextView SEXO;
    public TextView ESTADO;
    CardView tarjeta;
    EditText buscador;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ID = itemView.findViewById(R.id.ID);
        FICHA = itemView.findViewById(R.id.FICHA);
        VECINAL = itemView.findViewById(R.id.VECINAL);
        JUNTAS = itemView.findViewById(R.id.JUNTAS);
        SECTOR = itemView.findViewById(R.id.SECTOR);
        DIRECCION = itemView.findViewById(R.id.DIRECCION);
        FAMILIA = itemView.findViewById(R.id.FAMILIA);
        NINOA = itemView.findViewById(R.id.NINOA);
        RUT = itemView.findViewById(R.id.RUT);
        EDAD = itemView.findViewById(R.id.EDAD);
        SEXO = itemView.findViewById(R.id.SEXO);
        ESTADO = itemView.findViewById(R.id.ESTADO);
        tarjeta = itemView.findViewById(R.id.tarjeta);
    }

}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<Data> listadata = new ArrayList<Data>();

    public RecyclerViewAdapter(List<Data> listadata) {
        this.listadata = listadata;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item,parent,false);
        return new RecyclerViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjeta);
        holder.ID.setText(listadata.get(position).getA());
        holder.FICHA.setText(listadata.get(position).getB());
        holder.VECINAL.setText(listadata.get(position).getC());
        holder.JUNTAS.setText(listadata.get(position).getD());
        holder.SECTOR.setText(listadata.get(position).getE());
        holder.DIRECCION.setText(listadata.get(position).getF());
        holder.FAMILIA.setText(listadata.get(position).getG());
        holder.NINOA.setText(listadata.get(position).getH());
        holder.RUT.setText(listadata.get(position).getI());
        holder.EDAD.setText(listadata.get(position).getJ());
        holder.SEXO.setText(listadata.get(position).getK());
        holder.ESTADO.setText(listadata.get(position).getL());
    }

    @Override
    public int getItemCount() {
        return listadata.size();
    }
}