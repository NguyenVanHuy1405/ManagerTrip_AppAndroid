package com.example.final_code;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.HomeViewHolder> {
    private List<dataSearch> list;
    private Context context;
    public SearchAdapter(Context context){
        list = new ArrayList<>();
        this.context = context;
    }

    public void setList(List<dataSearch>list){
        this.list = list;
        notifyDataSetChanged();
    }

    public dataSearch getItem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.manager_trip, parent,false);
        return new SearchAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        dataSearch item = list.get(position);
        holder.id.setText(item.getId());
        holder.name.setText(item.getName());
        holder.destination.setText(item.getDestination());
        holder.date.setText(item.getDate());
//        holder.risk.setText(item.getRisks());
        if(item.getRisks().equals("1")){
            holder.risk.setText("Require Assessment:YES");
        }
        else if(item.getRisks().equals("0")){
            holder.risk.setText("Require Assessment:NO");
        }
//        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        private TextView id, name, destination, date, risk, description;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            id = view.findViewById(R.id.id_txt);
            name = view.findViewById(R.id.out_name);
            destination = view.findViewById(R.id.out_date);
            date = view.findViewById(R.id.out_destination);
            risk = view.findViewById(R.id.out_risk);
        }


    }
}