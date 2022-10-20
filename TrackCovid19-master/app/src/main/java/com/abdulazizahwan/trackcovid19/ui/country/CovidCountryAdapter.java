package com.abdulazizahwan.trackcovid19.ui.country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulazizahwan.trackcovid19.R;

import java.util.ArrayList;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> implements Filterable {

    private ArrayList<CovidCountry> covidCountries;
    private ArrayList<CovidCountry> covid;
    CustomFilter mFilter;

    public CovidCountryAdapter(ArrayList<CovidCountry> covid){
        this.covid = covid;
        this.covidCountries = new ArrayList<>();
        this.covidCountries.addAll(covid);
        this.mFilter = new CustomFilter(CovidCountryAdapter.this);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, final int position) {
        //CovidCountry covidCountry = covidCountries.get(position);
        viewholder.tvTotalCases.setText(covidCountries.get(position).getmCases());
        viewholder.tvCountryName.setText(covidCountries.get(position).getmCovidCountry());
        viewholder.tvRE.setText(covidCountries.get(position).getmRecovered());
        viewholder.tvFA.setText(covidCountries.get(position).getmDeaths());
        viewholder.tvNowCases.setText(covidCountries.get(position).getmTodayCases());
        viewholder.tvNowDeath.setText(covidCountries.get(position).getmTodayDeaths());
    }

    @Override
    public int getItemCount() {
        return covidCountries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTotalCases, tvCountryName, tvRE, tvFA, tvNowCases, tvNowDeath;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotalCases = itemView.findViewById(R.id.tvTotalCases);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvRE = itemView.findViewById(R.id.tvRE);
            tvFA = itemView.findViewById(R.id.tvFA);
            tvNowCases = itemView.findViewById(R.id.tvNowCases);
            tvNowDeath = itemView.findViewById(R.id.tvNowDeath);
        }
    }

    //FILTRO PAISEEES
    public class CustomFilter extends Filter {
        private CovidCountryAdapter listAdapter;

        private CustomFilter(CovidCountryAdapter listAdapter) {
            super();
            this.listAdapter = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            covidCountries.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                covidCountries.addAll(covid);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final CovidCountry person : covid) {
                    if (person.getmCovidCountry().toLowerCase().contains(filterPattern)) {
                        covidCountries.add(person);
                    }
                }
            }
            results.values = covidCountries;
            results.count = covidCountries.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
