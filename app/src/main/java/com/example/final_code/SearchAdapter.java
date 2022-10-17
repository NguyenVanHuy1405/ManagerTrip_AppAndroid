package com.example.final_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<dataSearch> dataSearches= new ArrayList<>();
    private List<dataSearch> dataSearchList = new ArrayList<>();

    SearchAdapter(Context context, ArrayList<dataSearch> dataSearches){
        this.context=context;
        this.dataSearches = dataSearches;
        this.dataSearchList = new ArrayList<>();
    }
    @Override
    public Filter getFilter() {
        return null;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<dataSearch> dataSearches = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                dataSearches.addAll(dataSearchList);
            }else{
                for(dataSearch data : dataSearchList){
                    if(data.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        dataSearches.add(data);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = dataSearches;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dataSearches.clear();
            dataSearches.addAll((Collection<? extends dataSearch>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.manager_trip, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dataSearch list = dataSearches.get(position);
        holder.id_txt.setText(String.valueOf(list.getId()));
        holder.out_name.setText(String.valueOf(list.getName()));
        holder.out_destination.setText(String.valueOf(list.getDestination()));
        holder.out_date.setText(String.valueOf(list.getDate()));
        if(list.getRisks().equals("1")){
            holder.out_risk.setText("Require Assessment:YES");
        }
        else if(list.getRisks().equals("0")){
            holder.out_risk.setText("Require Assessment:NO");
        }
    }

    @Override
    public int getItemCount() {
        return dataSearches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_txt, out_name, out_destination, out_date, out_risk;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            out_name = itemView.findViewById(R.id.out_name);
            out_destination = itemView.findViewById(R.id.out_destination);
            out_date = itemView.findViewById(R.id.out_date);
            out_risk = itemView.findViewById(R.id.out_risk);
        }
    }
}
