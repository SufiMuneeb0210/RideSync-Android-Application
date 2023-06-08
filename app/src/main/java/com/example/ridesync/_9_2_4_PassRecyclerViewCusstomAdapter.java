package com.example.ridesync;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class _9_2_4_PassRecyclerViewCusstomAdapter extends RecyclerView.Adapter<_9_2_4_PassRecyclerViewCusstomAdapter.PassViewHolder>{

    private DatabaseReference ActivityRef;
    Context context;
    int _balance,_ticketprice;
    ArrayList<_9_ModelPassData> list;
    _9_3_SessionManager sessionManager;
    String _useremail;

    public _9_2_4_PassRecyclerViewCusstomAdapter(Context context, ArrayList<_9_ModelPassData> list) {
        this.context = context;
        sessionManager = new _9_3_SessionManager(context.getApplicationContext());
        this.list = list;
    }
    @NonNull
    @Override
    public _9_2_4_PassRecyclerViewCusstomAdapter.PassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout._recyclerviewpassdatastyle,parent,false);
        return  new _9_2_4_PassRecyclerViewCusstomAdapter.PassViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull _9_2_4_PassRecyclerViewCusstomAdapter.PassViewHolder holder, int position) {

        _9_ModelPassData pass = list.get(position);
        ActivityRef= FirebaseDatabase.getInstance().getReference();
        if (!(context instanceof _3_3_1_PassStatusScreen)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRef.child("Users").child(sessionManager.getJob()).child(_useremail).child("Money").setValue(_balance);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Reject pass");
                    builder.setMessage("Are you sure you want to But he Pass?");
                    builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String key = ActivityRef.child("Pass").child(_useremail).push().getKey();
                            ActivityRef.child("Pass").child(_useremail).child(key).child("Status").setValue("Approved");

                            Toast.makeText(context, "Pass Alloted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String key = ActivityRef.child("Pass").child(_useremail).push().getKey();
                            ActivityRef.child("Pass").child(_useremail).child(key).child("Status").setValue("Rejected");
                            Toast.makeText(context, "Pass Rejected", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }
        else {
            holder.itemView.setClickable(false);
        }

        holder.txtbusnumber.setText(pass.get_busNumber());
        holder.txtname.setText(pass.get_passHoldername());
        holder.txtemail.setText(pass.get_passHolderEmail());
        holder.txtstatus.setText(pass.getStatus());
        holder.txtdated.setText(pass.get_passDate().toString());
        holder.txtpayment.setText(String.valueOf(pass.get_passPayment()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PassViewHolder extends RecyclerView.ViewHolder {

        TextView txtname,txtbusnumber,txtemail,txtstatus,txtpayment,txtdated;

        public PassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbusnumber = itemView.findViewById(R.id.txtbusnumbershow);
            txtname = itemView.findViewById(R.id.txtnameshow);
            txtemail = itemView.findViewById(R.id.txtemailshow);
            txtstatus = itemView.findViewById(R.id.txtstatusshow);
            txtdated = itemView.findViewById(R.id.txtdateshow);
            txtpayment = itemView.findViewById(R.id.txtpaymentshow);

        }
    }

}



