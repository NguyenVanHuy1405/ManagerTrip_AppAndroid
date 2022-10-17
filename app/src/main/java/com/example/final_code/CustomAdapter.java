package com.example.final_code;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList id, name, destination, date, risk, description;
    Animation translate_anim;
    CustomAdapter(
            Activity activity,
            Context context,
            ArrayList id,
            ArrayList name,
            ArrayList destination,
            ArrayList date,
            ArrayList risk,
            ArrayList description){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.risk = risk;
        this.description = description;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.manager_trip, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.out_name.setText(String.valueOf(name.get(position)));
        holder.out_destination.setText(String.valueOf(destination.get(position)));
        holder.out_date.setText(String.valueOf(date.get(position)));
        if(risk.get(position).equals("1")){
            holder.out_risk.setText("Require Assessment:YES");
        }
        else if(risk.get(position).equals("0")){
            holder.out_risk.setText("Require Assessment:NO");
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id",String.valueOf(id.get(position)));
                intent.putExtra("name",String.valueOf(name.get(position)));
                intent.putExtra("destination",String.valueOf(destination.get(position)));
                intent.putExtra("date",String.valueOf(date.get(position)));
                intent.putExtra("risks",String.valueOf(risk.get(position)));
                intent.putExtra("description",String.valueOf(description.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {

        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_txt, out_name, out_destination, out_date, out_risk;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            out_name = itemView.findViewById(R.id.out_name);
            out_destination = itemView.findViewById(R.id.out_destination);
            out_date = itemView.findViewById(R.id.out_date);
            out_risk = itemView.findViewById(R.id.out_risk);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
