package com.sparkling.sightreading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

public class LevelActivity extends AppCompatActivity {

    RecyclerView stages_gridView;

    CardView level_1;
    CardView level_2;
    CardView level_3;
    CardView level_4;


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
        setContentView(R.layout.activity_level);

        level_1 = findViewById(R.id.level_1);
        level_2 = findViewById(R.id.level_2);
        level_3 = findViewById(R.id.level_3);
        level_4 = findViewById(R.id.level_4);


        level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCardsGone();
                setStageGrid(levels_images, levels_names);
            }
        });









    }

    public void makeCardsGone(){
        level_1.setVisibility(View.GONE);
        level_2.setVisibility(View.GONE);
        level_3.setVisibility(View.GONE);
        level_4.setVisibility(View.GONE);
    }

    public void setStageGrid(int[] images, String[] names){
        stages_gridView = findViewById(R.id.stages_grid_view);
        stages_gridView.setVisibility(View.VISIBLE);
        adapter = new RecyclerAdapter(LevelActivity.this, names, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL,false );
        stages_gridView.setLayoutManager(gridLayoutManager);
        stages_gridView.setAdapter(adapter);
    }
}
