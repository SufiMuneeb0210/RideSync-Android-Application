package com.example.ridesync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class _9_2_3_TicketRecyclerViewCustomAdapter extends RecyclerView.Adapter<_9_2_3_TicketRecyclerViewCustomAdapter.TicketViewHolder> {

    private DatabaseReference ActivityRef;
    Context context;
    int _balance,_ticketprice;
    ArrayList<_9_ModelBusData> list;
    _9_3_SessionManager sessionManager;
    String _useremail;
    String temp;

    public _9_2_3_TicketRecyclerViewCustomAdapter(Context context, ArrayList<_9_ModelBusData> list) {
        this.context = context;
        sessionManager = new _9_3_SessionManager(context.getApplicationContext());
        this.list = list;
    }

    @NonNull
    @Override
    public _9_2_3_TicketRecyclerViewCustomAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout._recyclerviewbusdatastyle,parent,false);
        return  new _9_2_3_TicketRecyclerViewCustomAdapter.TicketViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull _9_2_3_TicketRecyclerViewCustomAdapter.TicketViewHolder holder, int position) {

        _9_ModelBusData bus = list.get(position);
        ActivityRef= FirebaseDatabase.getInstance().getReference();
        if (context instanceof _3_2_TicketsScreen) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _useremail = sessionManager.getEmail();
                    _ticketprice = Integer.parseInt(bus.getBusFair());
                    _balance = sessionManager.getBalance();
                    if (_balance < _ticketprice) {
                        Toast.makeText(context, "Not enough Balance", Toast.LENGTH_SHORT).show();
                    } else {
                        _balance -= _ticketprice;
                        ActivityRef.child("Users").child(sessionManager.getJob()).child(_useremail).child("Money").setValue(_balance);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Delete Bus");
                        builder.setMessage("Are you sure you want to Buy this Ticket?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String key = ActivityRef.child("Ticket").child(_useremail).push().getKey();
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("BusNumber").setValue(bus.getBusNumber());
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("To").setValue(bus.getTo());
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("From").setValue(bus.getFrom());
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("TotalSeats").setValue(bus.getBusSeats());
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("BusFair").setValue(bus.getBusFair());
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("BusTime").setValue(bus.getBusTime());
                                ActivityRef.child("Ticket").child(_useremail).child(key).child("BookedSeats").setValue(bus.getBookedSeats());

                                Toast.makeText(context, "Tickey Bought Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }
            });
        }
        else {
            holder.itemView.setClickable(false);
        }


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

    public static class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView txtbusnumber,txtfare,txtto,txtfrom,txttime,txtseats,txtcurrentbooked;

        public TicketViewHolder(@NonNull View itemView) {
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

