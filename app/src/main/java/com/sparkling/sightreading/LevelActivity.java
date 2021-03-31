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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {
    private static final String TAG = "LevelActivity";

    RecyclerView stages_gridView;

    CardView level_1;
    CardView level_2;
    CardView level_3;
    CardView level_4;

    TextView star_counter_text;

    ImageView level_lock_2;
    ImageView level_lock_3;
    ImageView level_lock_4;



    boolean is_screen_on_enable;
    boolean is_night_mode_enable;
    boolean is_sound_enable;
    int last_stage_that_played;
    int star_score;

    RelativeLayout level_general_layout;


    final int [] levels_images_1 = new int[] {
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef
    };

    final int [] levels_images_2 = new int[] {
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef
    };

    final int [] levels_images_3 = new int[] {
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef
    };

    final int [] levels_images_4 = new int[] {
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
            R.drawable.g_clef,
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
            R.drawable.g_clef,
            R.drawable.g_clef,
            R.drawable.g_clef,
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
            R.drawable.g_clef,
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
            "Level 11",
            "Level 12",
            "Level 13",
            "Level 14",
            "Level 15",
            "Level 16",
            "Level 17",
            "Level 18",
            "Level 19",
            "Level 20",
            "Level 21",
            "Level 22",
            "Level 23",
            "Level 24",
            "Level 25",
            "Level 26",
            "Level 27",
            "Level 28",
            "Level 29",
            "Level 30",
            "Level 31",
            "Level 32",
            "Level 33",
            "Level 34",
            "Level 35",
            "Level 36",
            "Level 37",
            "Level 38",
            "Level 39",
            "Level 40",
            "Level 41",
            "Level 42",
            "Level 43",
            "Level 44",
            "Level 45",
            "Level 46",
            "Level 47",
            "Level 48",
            "Level 49",
            "Level 50",
            "Level 51",
            "Level 52",
            "Level 53",
            "Level 54",
            "Level 55",
            "Level 56",
            "Level 57",
            "Level 58",
            "Level 59",
            "Level 60",
            "Level 61",
            "Level 62",
            "Level 63",
            "Level 64",
            "Level 65",
            "Level 66",
            "Level 67",
            "Level 68",
            "Level 69",
            "Level 70",
            "Level 71",
            "Level 72",
            "Level 73",
            "Level 74",
            "Level 75",
            "Level 76",
            "Level 77",
            "Level 78",
            "Level 79"
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
        startTranslation();

        star_counter_text.setText(star_score + "");

        level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCardsGone();
                setStageGrid(levels_images_1, levels_names, 1);
                startTranslation();
            }
        });

        if (star_score >= 250){
            level_lock_2.setVisibility(View.GONE);
            level_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeCardsGone();
                    setStageGrid(levels_images_2, levels_names, 2);
                    startTranslation();
                }
            });
        }
        else if (star_score >= 550){
            level_lock_3.setVisibility(View.GONE);
            level_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeCardsGone();
                    setStageGrid(levels_images_3, levels_names, 3);
                    startTranslation();
                }
            });
        }
        else if (star_score >= 890){
            level_lock_4.setVisibility(View.GONE);
            level_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeCardsGone();
                    setStageGrid(levels_images_3, levels_names, 3);
                    startTranslation();
                }
            });
        }


    }

    public void find(){
        level_general_layout = findViewById(R.id.level_general_layout);

        level_1 = findViewById(R.id.level_1);
        level_2 = findViewById(R.id.level_2);
        level_3 = findViewById(R.id.level_3);
        level_4 = findViewById(R.id.level_4);

        star_counter_text = findViewById(R.id.star_counter);
        stages_gridView = findViewById(R.id.stages_grid_view);

        level_lock_2 = findViewById(R.id.level_lock_2);
        level_lock_3 = findViewById(R.id.level_lock_3);
        level_lock_4 = findViewById(R.id.level_lock_4);

    }

    public void makeCardsGone(){
        level_1.setVisibility(View.GONE);
        level_2.setVisibility(View.GONE);
        level_3.setVisibility(View.GONE);
        level_4.setVisibility(View.GONE);
    }

    public void setStageGrid(int[] images, String[] names, int level){
        stages_gridView.setVisibility(View.VISIBLE);
        adapter = new RecyclerAdapter(LevelActivity.this, names, images,level, is_screen_on_enable, is_night_mode_enable,  is_sound_enable, last_stage_that_played);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL,false );
        stages_gridView.setLayoutManager(gridLayoutManager);
        stages_gridView.setAdapter(adapter);
    }

    public void getData(){

        Intent intent = getIntent();
        is_screen_on_enable = intent.getBooleanExtra("is_screen_on_enable", false);
        is_night_mode_enable = intent.getBooleanExtra("is_night_mode_enable", false);
        is_sound_enable = intent.getBooleanExtra("is_sound_enable", false);
        last_stage_that_played = intent.getIntExtra("last_stage_that_played", 1);
        star_score = intent.getIntExtra("star_score",star_score);

//         gameSeq = intent.getStringExtra("NOTES_SEQUENCE");
//         gameSequens = convertStringToIntArray(gameSeq);
        Log.d(TAG, "getData:  ***********"  + " is_night_mode_enable: " + is_night_mode_enable + " is_sound_enable: " + is_sound_enable + " is_screen_on_enable: " + is_screen_on_enable);


    }


    public void setNightMode(){
        if (is_night_mode_enable) {
            level_general_layout.setBackgroundColor(Color.parseColor("#585858"));
            level_1.setCardBackgroundColor(Color.parseColor("#585858"));
            level_2.setCardBackgroundColor(Color.parseColor("#585858"));
            level_3.setCardBackgroundColor(Color.parseColor("#585858"));
            level_4.setCardBackgroundColor(Color.parseColor("#585858"));
        }
        else{
            level_general_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            level_1.setCardBackgroundColor(Color.parseColor("#ffffff"));
            level_2.setCardBackgroundColor(Color.parseColor("#ffffff"));
            level_3.setCardBackgroundColor(Color.parseColor("#ffffff"));
            level_4.setCardBackgroundColor(Color.parseColor("#ffffff"));

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

    public void startTranslation(){

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(2000);
        rotateAnimation.setRepeatCount(Animation.RESTART);

        findViewById(R.id.star_counter_image).startAnimation(rotateAnimation);
    }

}
