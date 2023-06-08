package com.example.ridesync;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class _9_2_2_BusRecyclerViewCustomAdapter extends RecyclerView.Adapter<_9_2_2_BusRecyclerViewCustomAdapter.BusViewHolder> {

    Context context;

    ArrayList<_9_ModelBusData> list;



    public _9_2_2_BusRecyclerViewCustomAdapter(Context context, ArrayList<_9_ModelBusData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public _9_2_2_BusRecyclerViewCustomAdapter.BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout._recyclerviewbusdatastyle,parent,false);
        return  new _9_2_2_BusRecyclerViewCustomAdapter.BusViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull _9_2_2_BusRecyclerViewCustomAdapter.BusViewHolder holder, int position) {

        _9_ModelBusData bus = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(bus);
                Intent intent = new Intent(context, _2_2_3_BusShowActivty.class);
                intent.putExtra("data", json);
                context.startActivity(intent);
            }
        });


        holder.txtbusnumber.setText(bus.getBusNumber());
        holder.txtfare.setText(bus.getBusFair());
        holder.txtto.setText(bus.getTo());
        holder.txtfrom.setText(bus.getFrom());
        holder.txttime.setText(bus.getBusTime());
        holder.txtseats.setText(bus.getBusSeats());
        holder.txtcurrentbooked.setText(bus.getBookedSeats());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {

        TextView txtbusnumber,txtfare,txtto,txtfrom,txttime,txtseats,txtcurrentbooked;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbusnumber = itemView.findViewById(R.id.txtbusnumbershow);
            txtfare = itemView.findViewById(R.id.txtfareshow);
            txtto = itemView.findViewById(R.id.txttoshow);
            txtfrom = itemView.findViewById(R.id.txtfromshow);
            txttime = itemView.findViewById(R.id.txttimeshow);
            txtseats = itemView.findViewById(R.id.txttotalseatsshow);
            txtcurrentbooked = itemView.findViewById(R.id.txtcapacityshow);

        }
    }

}

