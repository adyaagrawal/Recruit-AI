package com.example.recruitai;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView cname, pos, loc;
    private ItemClickListener itemClickListener;
    public JobUserViewHolder(@NonNull View itemView) {
        super(itemView);
        cname=(TextView)itemView.findViewById(R.id.txtcname);
        pos=(TextView)itemView.findViewById(R.id.txtposition);
        loc=(TextView)itemView.findViewById(R.id.txtloc);
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
