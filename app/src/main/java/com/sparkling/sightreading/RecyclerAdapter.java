package com.sparkling.sightreading;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Vibrator;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;


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


    private String[] sArr;
    private int[] iArr;
    private  int gameLevel;
    private  boolean is_screen_on_enable;
    private boolean is_night_mode_enable;
    private  boolean is_sound_enable;
    private int last_stage_that_played;
    private Context context;
    private LayoutInflater inflater;

    private int night_mode_color;

    public RecyclerAdapter(Context context, String[] sArr,  int[] iArr, int gameLevel,
                           boolean is_screen_on_enable, boolean is_night_mode_enable, boolean is_sound_enable, int last_stage_that_played){
        this.sArr = sArr;
        this.iArr = iArr;
        this.is_screen_on_enable = is_screen_on_enable;
        this.is_night_mode_enable = is_night_mode_enable;
        this.is_sound_enable = is_sound_enable;
        this.last_stage_that_played = last_stage_that_played;
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
    public void onBindViewHolder(@NonNull final RecyclerAdapter.ViewHolder holder, final int position) {

        /**Because the position give wrong values*/

        holder.textView.setText(sArr[position]);
        holder.imageView.setImageResource(iArr[position]);


         if(last_stage_that_played < position){
            holder.imageView.setImageResource(R.drawable.lock_ic);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vibration(200);
                     }
            });

         }else if (last_stage_that_played == position) {
                 holder.cardView.setCardBackgroundColor(Color.parseColor("#f31fffff"));
         }


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.putExtra("is_screen_on_enable", is_screen_on_enable);
                    intent.putExtra("is_night_mode_enable", is_night_mode_enable);
                    intent.putExtra("is_sound_enable", is_sound_enable);
                    intent.putExtra("game_level", gameLevel);
                    intent.putExtra("stage", position);
                    context.startActivity(intent);
                }
            });
    }


    @Override
    public int getItemCount() {
        return sArr.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
                night_mode_color = Color.parseColor("#585858");
                c.setCardBackgroundColor(night_mode_color);
            }
            else{
                night_mode_color = Color.parseColor("#ffffff");
                c.setCardBackgroundColor(night_mode_color);
            }
        }
    }

    public void vibration(int duration){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(duration);
        }
    }



}
