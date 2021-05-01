package com.example.infocom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<UserModal> userModalArrayList;
    private Context context;

    public Adapter(ArrayList<UserModal> userModalArrayList, Context context){
        this.userModalArrayList = userModalArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserModal modal = userModalArrayList.get(position);
        holder.emailTV.setText(modal.getEmail());
        holder.numberTV.setText(modal.getNumber());
    }



    @Override
    public int getItemCount() {

        return userModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView emailTV, numberTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emailTV = itemView.findViewById(R.id.idTVemail);
            numberTV = itemView.findViewById(R.id.idTVnumber);
        }
    }
}
