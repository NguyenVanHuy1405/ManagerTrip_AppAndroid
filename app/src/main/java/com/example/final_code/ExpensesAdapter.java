package com.example.final_code;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList id,name,date,amount,id_trip;
    ExpensesAdapter(
            Context context,
            ArrayList id,
            ArrayList name,
            ArrayList amount,
            ArrayList date,
            ArrayList id_trip){

        this.context = context;
        this.id = id;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.id_trip = id_trip;
    }
    @NonNull
    @Override
    public ExpensesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expenses_trip, parent,false);
        return new ExpensesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.MyViewHolder holder, int position) {
        holder.out_name.setText(String.valueOf(name.get(position)));
        holder.out_amount.setText(String.valueOf(amount.get(position)));
        holder.out_date.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView out_name, out_amount, out_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            out_name = itemView.findViewById(R.id.id_expense);
            out_amount = itemView.findViewById(R.id.out_amount);
            out_date = itemView.findViewById(R.id.out_dateExpense);
        }
    }
}
