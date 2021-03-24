package com.sparkling.sightreading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class LevelActivity extends AppCompatActivity {
    private static final String TAG = "LevelActivity";

    RecyclerView stages_gridView;

    CardView level_1;
    CardView level_2;
    CardView level_3;
    CardView level_4;

    boolean is_screen_on_enable;
    boolean is_night_mode_enable;
    boolean is_sound_enable;

    RelativeLayout level_general_layout;


    final int [] levels_images = new int[] {
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.key_flat_3 ,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef
    };

    final String [] levels_names = new String[] {
            "Level 1",
            "Level 2",
            "Level 3",
            "Level 4",
            "Level 5",
            "Level 6",
            "Level 7",
            "Level 8",
            "Level 9",
            "Level 10",
            "Level 12",
            "Level 13",
            "Level 14",
            "Level 15",
            "Level 16",
            "Level 17",
            "Level 18",
            "Level 19"
    };


    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_level);

        getData();
        find();
        setNightMode();
        setScreenOn();


        level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCardsGone();
                setStageGrid(levels_images, levels_names, 1);
            }
        });
    }

    public void find(){
        level_general_layout = findViewById(R.id.level_general_layout);

        level_1 = findViewById(R.id.level_1);
        level_2 = findViewById(R.id.level_2);
        level_3 = findViewById(R.id.level_3);
        level_4 = findViewById(R.id.level_4);

        stages_gridView = findViewById(R.id.stages_grid_view);

    }

    public void makeCardsGone(){
        level_1.setVisibility(View.GONE);
        level_2.setVisibility(View.GONE);
        level_3.setVisibility(View.GONE);
        level_4.setVisibility(View.GONE);
    }

    public void setStageGrid(int[] images, String[] names, int level){
        stages_gridView.setVisibility(View.VISIBLE);
        adapter = new RecyclerAdapter(LevelActivity.this, names, images,level, is_screen_on_enable, is_night_mode_enable,  is_sound_enable);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL,false );
        stages_gridView.setLayoutManager(gridLayoutManager);
        stages_gridView.setAdapter(adapter);
    }

    public void getData(){

        Intent intent = getIntent();
        is_screen_on_enable = intent.getBooleanExtra("is_screen_on_enable", false);
        is_night_mode_enable = intent.getBooleanExtra("is_night_mode_enable", false);
        is_sound_enable = intent.getBooleanExtra("is_sound_enable", false);

//         gameSeq = intent.getStringExtra("NOTES_SEQUENCE");
//         gameSequens = convertStringToIntArray(gameSeq);
        Log.d(TAG, "getData:  ***********"  + " is_night_mode_enable: " + is_night_mode_enable + " is_sound_enable: " + is_sound_enable + " is_screen_on_enable: " + is_screen_on_enable);


    }


    public void setNightMode(){
        if (is_night_mode_enable) {
            level_general_layout.setBackgroundColor(Color.parseColor("#585858"));
            level_1.setBackgroundColor(Color.parseColor("#585858"));
            level_2.setBackgroundColor(Color.parseColor("#585858"));
            level_3.setBackgroundColor(Color.parseColor("#585858"));
            level_4.setBackgroundColor(Color.parseColor("#585858"));
        }
        else{ level_general_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            level_1.setBackgroundColor(Color.parseColor("#ffffff"));
            level_2.setBackgroundColor(Color.parseColor("#ffffff"));
            level_3.setBackgroundColor(Color.parseColor("#ffffff"));
            level_4.setBackgroundColor(Color.parseColor("#ffffff"));

        }
    }

    public void setScreenOn(){
        if (is_screen_on_enable){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }



}
