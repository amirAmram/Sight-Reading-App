package com.sparkling.sightreading;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";
    RelativeLayout general;
    boolean b1;
    boolean b2;
    boolean b3;


    ScrollView scrollView;
    ImageView menu;
    TextView intro;
    TextView lines;
    TextView clef;
    TextView notesAndRests;

    TextView vIntro;
    TextView vLines;
    TextView vClef;
    TextView vNotesAndRests;

    AlertDialog alertDialog;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(TAG, "InfoActivity STARTED ");


        banner();
        find();
        getData();
        setNightMode();
        setScreenOn();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });


    }


    public void find(){
        general = findViewById(R.id.info_general);
        scrollView = findViewById(R.id.info_scroll);
        menu = findViewById(R.id.info_menu);
        intro = findViewById(R.id.info_introduction);
        lines = findViewById(R.id.info_lines);
        clef = findViewById(R.id.info_Clef);
        notesAndRests = findViewById(R.id.info_notes_and_rests);
    }


    public void getData() {
        Intent intent = getIntent();
        b1 = intent.getBooleanExtra("b1", false);
        b2 = intent.getBooleanExtra("b2", false);
        b3 = intent.getBooleanExtra("b3", false);
        Log.d(TAG, "get Data:" + "\n"
                + "setScreenOn " + b1 + "\n"
                + "setNightMode " +b2 + "\n"
                + "setEnableSound " + b3 + "\n");
    }

    public void setNightMode(){
        if (b2) { general.setBackgroundColor(Color.parseColor("#585858")); }
        else{ general.setBackgroundColor(Color.parseColor("#ffffff")); }
    }
    public void setScreenOn(){
        if (b1){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public void alertDialog (){

        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.menu_info_layout,null);

        alertDialog = new AlertDialog.Builder(InfoActivity.this).create();
        alertDialog.setView(v);

        alertDialog.show();
        menuClick();

    }



    public void menuClick(){
        vIntro = v.findViewById(R.id.item_intro);
        vLines = v.findViewById(R.id.item_lines);
        vClef = v.findViewById(R.id.item_clef);
        vNotesAndRests = v.findViewById(R.id.item_notes_and_rests);

        vIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScrollLocation(intro);
                alertDialog.dismiss();
            }
        });

        vLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScrollLocation(lines);
                alertDialog.dismiss();
            }
        });

        vClef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScrollLocation(clef);
                alertDialog.dismiss();
            }
        });

        vNotesAndRests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScrollLocation(notesAndRests   );
                alertDialog.dismiss();
            }
        });

    }

    public void setScrollLocation(final TextView t){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,t.getTop());
            }
        });
    }

    public void banner(){
        AdView mAdView;
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //ca-app-pub-2365945636920098/1242205036
        adView.setAdUnitId("ca-app-pub-2365945636920098/1242205036");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


}
