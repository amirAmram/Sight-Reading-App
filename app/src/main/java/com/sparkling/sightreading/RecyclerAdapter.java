package com.sparkling.sightreading;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    String[][] NOTES_SEQUENCE = new String[][]{
            {
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
            },
            {
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
                    "1 2 3 4 5 6 7 8",
            }
    };


    String[] sArr;
    int[] iArr;
    int gameLevel;
    boolean is_screen_on_enable;
    boolean is_night_mode_enable;
    boolean is_sound_enable;
    Context context;
    LayoutInflater inflater;



    public RecyclerAdapter(Context context, String[] sArr,  int[] iArr, int gameLevel,
                           boolean is_screen_on_enable, boolean is_night_mode_enable, boolean is_sound_enable){
        this.sArr = sArr;
        this.iArr = iArr;
        this.is_screen_on_enable = is_screen_on_enable;
        this.is_night_mode_enable = is_night_mode_enable;
        this.is_sound_enable = is_sound_enable;
        this.gameLevel = gameLevel;
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
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(sArr[position]);
        holder.imageView.setImageResource(iArr[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra("is_screen_on_enable", is_screen_on_enable);
                intent.putExtra("is_night_mode_enable", is_night_mode_enable);
                intent.putExtra("is_sound_enable", is_sound_enable);
                intent.putExtra("game_level", gameLevel );
                intent.putExtra("stage", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sArr.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_text);
            imageView = itemView.findViewById(R.id.card_image);
            cardView = itemView.findViewById(R.id.card_view);

            setNightMode(cardView);
        }

        public void setNightMode(CardView c){
            if (is_night_mode_enable) {
                c.setBackgroundColor(Color.parseColor("#585858"));
            }
            else{
                c.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }




}
