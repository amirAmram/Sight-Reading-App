package com.sparkling.sightreading;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    String[] sArr;
    int[] iArr;
    Context context;
    LayoutInflater inflater;

    public RecyclerAdapter(Context context, String[] sArr,  int[] iArr ){
        this.sArr = sArr;
        this.iArr = iArr;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gird_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.t.setText(sArr[position]);
        holder.i.setImageResource(iArr[position]);
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Tuner.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sArr.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView t;
        ImageView i;
        CardView c;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.card_text);
            i = itemView.findViewById(R.id.card_image);
            c = itemView.findViewById(R.id.card_view);
        }

    }
}
