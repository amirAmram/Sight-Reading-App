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

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    TextView tPlay;
    TextView tOption;
    TextView tLearnSighs;
    TextView tInfo;

    TextView tTuner;

    Intent intent;


    public static  final String SHARED_PREFES = "sharedPrefs_2";
    public static  final String SWITCH_1 = "b1";
    public static  final String SWITCH_2 = "b2";
    public static  final String SWITCH_3 = "b3";
    public static  final String SWITCH_4 = "b4";
    public static  final String CLEF_SWITCH = "clef_switch";
    public static  final String AMOUNT_INT = "i4";
    public static  final String SEEK_INT = "i1";
    public static  final String SPINNER_STRING = "s1";
    public static  final String SPINNER_HIGH_INT = "i2";
    public static  final String SPINNER_LOW_INT = "i3";


    RelativeLayout general;
    boolean acknowledge = false;
    boolean play_acknowledge = false;
    boolean b1;
    boolean b2;
    boolean b3;
    boolean b4;
    boolean b5;
    int i1;
    String s1;
    int i2;
    int i3;
    int i4;


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
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    intent = new Intent(StartActivity.this,PlayActivity.class);
                    Log.d(TAG, "seek data    " + i1);
                    intent.putExtra("b1",b1);
                    intent.putExtra("b2",b2);
                    intent.putExtra("b3",b3);
                    intent.putExtra("b4",b4);
                    intent.putExtra("b5",b5);
                    intent.putExtra("i1",i1);
                    intent.putExtra("s1",s1);
                    intent.putExtra("i2",i2);
                    intent.putExtra("i3",i3);
                    intent.putExtra("i4",i4);

                    startActivity(intent);

                }

                playInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,PlayActivity.class);
                        Log.d(TAG, "seek data    " + i1);
                        intent.putExtra("b1",b1);
                        intent.putExtra("b2",b2);
                        intent.putExtra("b3",b3);
                        intent.putExtra("b4",b4);
                        intent.putExtra("b5",b5);
                        intent.putExtra("i1",i1);
                        intent.putExtra("s1",s1);
                        intent.putExtra("i2",i2);
                        intent.putExtra("i3",i3);
                        intent.putExtra("i4",i4);

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
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    intent = new Intent(StartActivity.this,OptionsActivity.class);
                    intent.putExtra("seekInt",i1);
                    startActivity(intent);
                }

                optionsInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,OptionsActivity.class);
                        intent.putExtra("seekInt",i1);
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
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    intent = new Intent(StartActivity.this,InfoActivity.class);
                    intent.putExtra("b1",b1);
                    intent.putExtra("b2",b2);
                    intent.putExtra("b3",b3);
                    startActivity(intent);
                }

                learnInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,InfoActivity.class);
                        intent.putExtra("b1",b1);
                        intent.putExtra("b2",b2);
                        intent.putExtra("b3",b3);
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
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    intent = new Intent(StartActivity.this,AboutActivity.class);
                    intent.putExtra("b1",b1);
                    intent.putExtra("b2",b2);
                    intent.putExtra("b3",b3);
                    startActivity(intent);
                }

                infoInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        intent = new Intent(StartActivity.this,AboutActivity.class);
                        intent.putExtra("b1",b1);
                        intent.putExtra("b2",b2);
                        intent.putExtra("b3",b3);
                        startActivity(intent);
                        infoInterstitialAd.loadAd(new AdRequest.Builder().build());

                    }

                });


            }
        });

        tTuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(StartActivity.this,LevelActivity.class);
                startActivity(intent);
            }
        });

    }


    /*shared prefencece */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.putString(TEXT, s);
        editor.putBoolean(SWITCH_1,b1);
        editor.putBoolean(SWITCH_2,b2);
        editor.putBoolean(SWITCH_3,b3);
        editor.putBoolean(SWITCH_4,b4);
        editor.putBoolean(CLEF_SWITCH,b5);
        editor.putInt(SEEK_INT,i1);
        editor.putString(SPINNER_STRING,s1);
        editor.putInt(SPINNER_HIGH_INT,i2);
        editor.putInt(SPINNER_LOW_INT,i3);
        editor.putInt(AMOUNT_INT,i4);

        editor.apply();
    }
    public void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES,MODE_PRIVATE);

        b1 = sharedPreferences.getBoolean(SWITCH_1,false);
        b2 = sharedPreferences.getBoolean(SWITCH_2,false);
        b3 = sharedPreferences.getBoolean(SWITCH_3,false);
        b4 = sharedPreferences.getBoolean(SWITCH_4,false);
        b5 = sharedPreferences.getBoolean(CLEF_SWITCH,false);
        i1 = sharedPreferences.getInt(SEEK_INT,40);
        s1 = sharedPreferences.getString(SPINNER_STRING,"C");
        i2 = sharedPreferences.getInt(SPINNER_HIGH_INT,0);
        i3 = sharedPreferences.getInt(SPINNER_LOW_INT,20);
        i4 = sharedPreferences.getInt(AMOUNT_INT,15);
    }
    public void updateView(){
        Log.d(TAG, "updateView:" + "\n"
                + "setScreenOn " + b1 + "\n"
                + "setNightMode " +b2 + "\n"
                + "setEnableSound " + b3 + "\n"
                + "setEnableTempOnPlayScreen " + b4 + "\n"
                + "clef " + b5 + "\n"
                + "seekInt " + i1 + "\n"
                + "spinner_string " + s1 + "\n"
                + "spinner_high_Int " + i2 + "\n"
                + "spinner_low_Int " + i3  + "\n"
                + "amount " + i4 + "\n");

    }

    public void getData(){

        loadData();
        updateView();

        Intent intent = getIntent();
        play_acknowledge = intent.getBooleanExtra("play_acknowledge",false);
        if(play_acknowledge){
            i1 = intent.getIntExtra("seekInt", 60);
            saveData();
            play_acknowledge = false;
            loadData();
        }

        acknowledge = intent.getBooleanExtra("acknowledge",false);
        if (acknowledge) {
            b1 = intent.getBooleanExtra("setScreenOn", false);
            b2 = intent.getBooleanExtra("setNightMode", false);
            b3 = intent.getBooleanExtra("setEnableSound", false);
            b4 = intent.getBooleanExtra("setEnableTempOnPlayScreen", false);
            b5 = intent.getBooleanExtra("clef", false);
            i1 = intent.getIntExtra("seekInt", 60);
            s1 = intent.getStringExtra("spinner_string");
            i2 = intent.getIntExtra("spinner_high_Int",0);
            i3 = intent.getIntExtra("spinner_low_Int",20);
            i4 = intent.getIntExtra("amount", 15);

            updateView();

            saveData();
            acknowledge = false;
            loadData();
        }


    }

    public void setScreenOn(){
        if (b1){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
    public void setNightMode(){
        if (b2) {
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
