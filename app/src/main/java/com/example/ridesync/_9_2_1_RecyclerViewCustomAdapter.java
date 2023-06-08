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

public class _9_2_1_RecyclerViewCustomAdapter extends RecyclerView.Adapter<_9_2_1_RecyclerViewCustomAdapter.MyViewHolder> {

    Context context;

    ArrayList<_9_ModelUserData> list;


    public _9_2_1_RecyclerViewCustomAdapter(Context context, ArrayList<_9_ModelUserData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout._recyclerviewdatastyle,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        _9_ModelUserData user = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the item
                Gson gson = new Gson();
                String json = gson.toJson(user);
                Intent intent = new Intent(context, _2_1_3_ShowActivity.class);
                intent.putExtra("data", json);
                context.startActivity(intent);
            }
        });

        // Bind your data to the views as before
        holder.txtName.setText(user.getName());
        holder.txtGender.setText(user.getGender());
        holder.txtEmail.setText(user.getEmail());
        holder.txtContact.setText(user.getPhone());
        holder.txtpassword.setText(user.getPassword());
        holder.txtjob.setText(user.getJob());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtGender,txtEmail,txtContact,txtpassword,txtjob;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtnameshow);
            txtGender = itemView.findViewById(R.id.txtgendershow);
            txtEmail = itemView.findViewById(R.id.txtemailshow);
            txtContact = itemView.findViewById(R.id.txtcontactshow);
            txtpassword = itemView.findViewById(R.id.txtpasswordshow);
            txtjob = itemView.findViewById(R.id.txtjobshow);
        }
    }

}
