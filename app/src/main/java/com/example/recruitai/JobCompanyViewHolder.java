package com.example.recruitai;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobCompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView jname, status, num;
    private ItemClickListener itemClickListener;
    public JobCompanyViewHolder(@NonNull View itemView) {
        super(itemView);
        jname=(TextView)itemView.findViewById(R.id.txtpositionc);
        status=(TextView)itemView.findViewById(R.id.txtstatus);
        num=(TextView)itemView.findViewById(R.id.txtnum);
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
