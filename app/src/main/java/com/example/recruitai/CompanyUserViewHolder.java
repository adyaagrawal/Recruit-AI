package com.example.recruitai;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompanyUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView uname, status;
    private ItemClickListener itemClickListener;
    public CompanyUserViewHolder(@NonNull View itemView) {
        super(itemView);
        uname=(TextView)itemView.findViewById(R.id.txtuaname);
        status=(TextView)itemView.findViewById(R.id.txtustatus);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
