package cardview.com.cardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView textoa;
    public TextView textob;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        textoa = itemView.findViewById(R.id.textoa);
        textob = itemView.findViewById(R.id.textob);
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
        holder.textoa.setText(listadata.get(position).getA());
        holder.textob.setText(listadata.get(position).getB());

    }

    @Override
    public int getItemCount() {
        return listadata.size();
    }
}
