package com.sparkling.sightreading;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * This class is the first screen after the logo
 * This class presents the main menu
 * There 4 option of navigation:
 *  1. move to PlayActivity
 *  2. move to OptionActivity
 *  3. move to InfoActivity
 *  4. move to AboutActivity
 *
 *  */


public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    TextView tPlay;
    TextView tOption;
    TextView tLearnSighs;
    TextView tInfo;

    TextView tTuner;

    Intent intent;


    public static  final String SHARED_PREFES = "sharedPrefs_2";
    public static  final String SWITCH_ENABLE_SCREEN_ON = "b1";
    public static  final String SWITCH_ENABLE_NIGHT_MOD = "b2";
    public static  final String SWITCH_ENABLE_SOUND = "b3";
    public static  final String SWITCH_ENABLE_TEMPO_ON_SCREEN = "b4";
    public static  final String CLEF_SWITCH = "clef_switch";
    public static  final String AMOUNT_INT = "i4";
    public static  final String SEEK_INT = "i1";
    public static  final String SPINNER_STRING = "s1";
    public static  final String SPINNER_HIGH_INT = "i2";
    public static  final String SPINNER_LOW_INT = "i3";
    public static  final String LAST_STAGE_NUMBER = "last_stage_number";
    public static  final String STAR_SCORE = "star_score";


    RelativeLayout general;
    boolean options_acknowledge = false;
    boolean play_acknowledge = false;
    boolean enable_screen_on;
    boolean enable_night_mode;
    boolean enable_sound;
    boolean enable_tempo_on_play_screen;
    boolean enable_f_clef;
    int seek_int;
    String spinner_string;
    int spinner_high_int;
    int spinner_low_int;
    int amount_of_notes_per_game;
    int last_stage_number;
    int stars_score;


    private InterstitialAd optionsInterstitialAd;
    private InterstitialAd learnInterstitialAd;
    private InterstitialAd infoInterstitialAd;
    private InterstitialAd playInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(TAG, "StartActivity STARTED ");


        getData();
        find();

        setInterstitialAdds();

        setScreenOn();
        setNightMode();


        tPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playInterstitialAd.isLoaded()) {
                    playInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn'textView loaded yet.");
                    intent = new Intent(StartActivity.this,LevelActivity.class);
                    intent.putExtra("is_screen_on_enable",enable_screen_on);
                    intent.putExtra("is_night_mode_enable",enable_night_mode);
                    intent.putExtra("is_sound_enable",enable_sound);
                    intent.putExtra("last_stage_that_played", last_stage_number);
                    intent.putExtra("star_score",stars_score);
                    startActivity(intent);

                }

//        tPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (playInterstitialAd.isLoaded()) {
//                    playInterstitialAd.show();
//                } else {
//                    Log.d("TAG", "The interstitial wasn'textView loaded yet.");
//                    intent = new Intent(StartActivity.this,PracticeActivity.class);
//                    Log.d(TAG, "seek data    " + i1);
//                    intent.putExtra("b1",b1);
//                    intent.putExtra("b2",b2);
//                    intent.putExtra("b3",b3);
//                    intent.putExtra("b4",b4);
//                    intent.putExtra("b5",b5);
//                    intent.putExtra("i1",i1);
//                    intent.putExtra("s1",s1);
//                    intent.putExtra("i2",i2);
//                    intent.putExtra("i3",i3);
//                    intent.putExtra("i4",i4);
//
//                    startActivity(intent);
//
//                }

                playInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,PlayActivity.class);
                        Log.d(TAG, "seek data    " + seek_int);
                        intent.putExtra("b1",enable_screen_on);
                        intent.putExtra("b2",enable_night_mode);
                        intent.putExtra("b3",enable_sound);
                        intent.putExtra("b4",enable_tempo_on_play_screen);
                        intent.putExtra("b5",enable_f_clef);
                        intent.putExtra("i1",seek_int);
                        intent.putExtra("s1",spinner_string);
                        intent.putExtra("i2",spinner_high_int);
                        intent.putExtra("i3",spinner_low_int);
                        intent.putExtra("i4",amount_of_notes_per_game);

                        startActivity(intent);

                        playInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });

            }
        });



        tOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionsInterstitialAd.isLoaded()) {
                    optionsInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn'textView loaded yet.");
                    intent = new Intent(StartActivity.this,OptionsActivity.class);
                    intent.putExtra("seek_int",seek_int);
                    startActivity(intent);
                }

                optionsInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,OptionsActivity.class);
                        intent.putExtra("seekInt",seek_int);
                        startActivity(intent);
                        optionsInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });
            }
        });

        tLearnSighs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (learnInterstitialAd.isLoaded()) {
                    learnInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't textView loaded yet.");
                    intent = new Intent(StartActivity.this,InfoActivity.class);
                    intent.putExtra("b1",enable_screen_on);
                    intent.putExtra("b2",enable_night_mode);
                    intent.putExtra("b3",enable_sound);
                    startActivity(intent);
                }

                learnInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,InfoActivity.class);
                        intent.putExtra("b1",enable_screen_on);
                        intent.putExtra("b2",enable_night_mode);
                        intent.putExtra("b3",enable_sound);
                        startActivity(intent);
                        learnInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });

            }
        });

        tInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (infoInterstitialAd.isLoaded()) {
                    infoInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn'textView loaded yet.");
                    intent = new Intent(StartActivity.this,AboutActivity.class);
                    intent.putExtra("b1",enable_screen_on);
                    intent.putExtra("b2",enable_night_mode);
                    intent.putExtra("b3",enable_sound);
                    startActivity(intent);
                }

                infoInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,AboutActivity.class);
                        intent.putExtra("b1",enable_screen_on);
                        intent.putExtra("b2",enable_night_mode);
                        intent.putExtra("b3",enable_sound);
                        startActivity(intent);
                        infoInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });


            }
        });

        tTuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(StartActivity.this, Tuner.class);
                intent.putExtra("b1",enable_screen_on);
                intent.putExtra("b2",enable_night_mode);
                intent.putExtra("b3",enable_sound);
                intent.putExtra("b4",enable_tempo_on_play_screen);
                intent.putExtra("b5",enable_f_clef);
                intent.putExtra("i1",seek_int);
                intent.putExtra("s1",spinner_string);
                intent.putExtra("i2",spinner_high_int);
                intent.putExtra("i3",spinner_low_int);
                intent.putExtra("i4",amount_of_notes_per_game);

                startActivity(intent);
            }
        });

    }


    /*shared prefencece */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.putString(TEXT, s);
        editor.putBoolean(SWITCH_ENABLE_SCREEN_ON,enable_screen_on);
        editor.putBoolean(SWITCH_ENABLE_NIGHT_MOD,enable_night_mode);
        editor.putBoolean(SWITCH_ENABLE_SOUND,enable_sound);
        editor.putBoolean(SWITCH_ENABLE_TEMPO_ON_SCREEN,enable_tempo_on_play_screen);
        editor.putBoolean(CLEF_SWITCH,enable_f_clef);
        editor.putInt(SEEK_INT,seek_int);
        editor.putString(SPINNER_STRING,spinner_string);
        editor.putInt(SPINNER_HIGH_INT,spinner_high_int);
        editor.putInt(SPINNER_LOW_INT,spinner_low_int);
        editor.putInt(AMOUNT_INT,amount_of_notes_per_game);
        editor.putInt(LAST_STAGE_NUMBER,last_stage_number);
        editor.putInt(STAR_SCORE,stars_score);

        editor.apply();
    }
    public void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES,MODE_PRIVATE);

        enable_screen_on = sharedPreferences.getBoolean(SWITCH_ENABLE_SCREEN_ON,false);
        enable_night_mode = sharedPreferences.getBoolean(SWITCH_ENABLE_NIGHT_MOD,false);
        enable_sound = sharedPreferences.getBoolean(SWITCH_ENABLE_SOUND,false);
        enable_tempo_on_play_screen = sharedPreferences.getBoolean(SWITCH_ENABLE_TEMPO_ON_SCREEN,false);
        enable_f_clef = sharedPreferences.getBoolean(CLEF_SWITCH,false);
        seek_int = sharedPreferences.getInt(SEEK_INT,40);
        spinner_string = sharedPreferences.getString(SPINNER_STRING,"C");
        spinner_high_int = sharedPreferences.getInt(SPINNER_HIGH_INT,0);
        spinner_low_int = sharedPreferences.getInt(SPINNER_LOW_INT,20);
        amount_of_notes_per_game = sharedPreferences.getInt(AMOUNT_INT,15);
        last_stage_number = sharedPreferences.getInt(LAST_STAGE_NUMBER,1);
        stars_score = sharedPreferences.getInt(STAR_SCORE,1);
    }
    public void updateView(){
        Log.d(TAG, "updateView:" + "\n"
                + "setScreenOn " + enable_screen_on + "\n"
                + "setNightMode " +enable_night_mode + "\n"
                + "setEnableSound " + enable_sound + "\n"
                + "setEnableTempOnPlayScreen " + enable_tempo_on_play_screen + "\n"
                + "clef " + enable_f_clef + "\n"
                + "seekInt " + seek_int + "\n"
                + "spinner_string " + spinner_string + "\n"
                + "spinner_high_Int " + spinner_high_int + "\n"
                + "spinner_low_Int " + spinner_low_int  + "\n"
                + "amount " + amount_of_notes_per_game + "\n"
                + "last_stage_number " + last_stage_number + "\n"
                + "star_scor " + stars_score + "\n"
        );

    }

    public void getData(){

        loadData();
        updateView();

        Intent intent = getIntent();
        play_acknowledge = intent.getBooleanExtra("play_acknowledge",false);
        if(play_acknowledge){
            seek_int = intent.getIntExtra("seekInt", 60);
            last_stage_number = intent.getIntExtra("last_stage_that_played",1);
            stars_score = intent.getIntExtra("star_score",0);
            saveData();
            play_acknowledge = false;
            loadData();
        }

        options_acknowledge = intent.getBooleanExtra("options_acknowledge",false);
        if (options_acknowledge) {
            enable_screen_on = intent.getBooleanExtra("is_screen_on", false);
            enable_night_mode = intent.getBooleanExtra("is_night_mode", false);
            enable_sound = intent.getBooleanExtra("enable_sound", false);
            enable_tempo_on_play_screen = intent.getBooleanExtra("is_temp_on_play_screen_enable", false);
            enable_f_clef = intent.getBooleanExtra("clef", false);
            seek_int = intent.getIntExtra("seek_int", 60);
            spinner_string = intent.getStringExtra("spinner_string");
            spinner_high_int = intent.getIntExtra("spinner_high_Int",0);
            spinner_low_int = intent.getIntExtra("spinner_low_Int",20);
            amount_of_notes_per_game = intent.getIntExtra("amount", 15);

            updateView();

            saveData();
            options_acknowledge = false;
            loadData();
        }


    }

    public void setScreenOn(){
        if (enable_screen_on){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
    public void setNightMode(){
        if (enable_night_mode) {
            general.setBackgroundColor(Color.parseColor("#585858"));
            tPlay.setTextColor(Color.parseColor("#1B1A1A"));
            tPlay.setTextColor(Color.parseColor("#FF2E2B2B"));
            tOption.setTextColor(Color.parseColor("#FF2E2B2B"));
            tLearnSighs.setTextColor(Color.parseColor("#FF2E2B2B"));
        }else{
            general.setBackgroundColor(Color.parseColor("#ffffff"));
            tPlay.setTextColor(Color.parseColor("#423D3D"));
            tOption.setTextColor(Color.parseColor("#474343"));
            tLearnSighs.setTextColor(Color.parseColor("#474343"));

        }
    }


    public void find(){
        general = findViewById(R.id.start_general);

        tPlay = findViewById(R.id.title_play);
        tOption = findViewById(R.id.title_options);
        tLearnSighs = findViewById(R.id.title_learn);
        tInfo = findViewById(R.id.title_info);

        tTuner = findViewById(R.id.tuner);
    }

    @Override
    public void onBackPressed() {

         Intent intent = new Intent(Intent.ACTION_MAIN);
         intent.addCategory(Intent.CATEGORY_HOME);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(intent);
    }

    public void setInterstitialAdds(){

        MobileAds.initialize(StartActivity.this,"ca-app-pub-2365945636920098~1583548056");

        playInterstitialAd = new InterstitialAd(this);
        //ca-app-pub-2365945636920098/8855876293
        playInterstitialAd.setAdUnitId("ca-app-pub-2365945636920098/8855876293");
        playInterstitialAd.loadAd(new AdRequest.Builder().build());

        optionsInterstitialAd = new InterstitialAd(this);
        //ca-app-pub-2365945636920098/6952684954
        optionsInterstitialAd.setAdUnitId("ca-app-pub-2365945636920098/6952684954");
        optionsInterstitialAd.loadAd(new AdRequest.Builder().build());

        learnInterstitialAd = new InterstitialAd(this);
        //ca-app-pub-2365945636920098/2191312867
        learnInterstitialAd.setAdUnitId("ca-app-pub-2365945636920098/2191312867");
        learnInterstitialAd.loadAd(new AdRequest.Builder().build());

        infoInterstitialAd = new InterstitialAd(this);
        //ca-app-pub-2365945636920098/5793298915
        infoInterstitialAd.setAdUnitId("ca-app-pub-2365945636920098/5793298915");
        infoInterstitialAd.loadAd(new AdRequest.Builder().build());

    }


}
