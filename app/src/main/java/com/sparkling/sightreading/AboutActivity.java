package com.sparkling.sightreading;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "AboutActivity";
    RelativeLayout general;
    boolean b1;
    boolean b2;
    boolean b3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(TAG, "AboutActivity STARTED ");

        banner();
        find();
        getData();
        setNightMode();
        setScreenOn();
    }


    public void find(){
        general = findViewById(R.id.about_general);
    }

    public void getData() {
        Intent intent = getIntent();
        b1 = intent.getBooleanExtra("b1", false);
        b2 = intent.getBooleanExtra("b2", false);
        b3 = intent.getBooleanExtra("b3", false);

        Log.d(TAG, "get Data" + "\n"
                + "setScreenOn " + b1 + "\n"
                + "setNightMode " +b2 + "\n"
                + "setEnableSound " + b3 + "\n"
               );
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

    public void banner(){
        AdView mAdView;
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //ca-app-pub-2365945636920098/1470910526
       // adView.setAdUnitId("ca-app-pub-2365945636920098/1470910526");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
