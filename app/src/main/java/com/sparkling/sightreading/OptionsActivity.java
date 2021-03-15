package com.sparkling.sightreading;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/**
 * This class represents preference
 * This class include 10 different options
 *  1. set screen on
 *  2. set night mode
 *  3. sound enable
 *  4. tempo on play screen enable
 *  5. tempo rate
 *  6. set scale
 *  7. set highest note plays
 *  8. set lowest note plays
 *  9. amount of note every game
 *  10. clef (F or C)
 *  */

public class OptionsActivity extends AppCompatActivity {
    private static final String TAG = "OptionsActivity";

    public static  final String SHARED_PREFES = "sharedPrefs";
    public static  final String SWITCH_SCREEN_ON = "SWITCH_SCREEN_ON";
    public static  final String SWITCH_NIGHT_MODE = "SWITCH_NIGHT_MODE";
    public static  final String SWITCH_ENABLE_SOUND = "SWITCH_ENABLE_SOUND";
    public static  final String SWITCH_TEMPO_ON_PLAY_SCREEN = "SWITCH_TEMPO_ON_PLAY_SCREEN";
    public static  final String SEEK_INT = "seekint";
    public static  final String AMOUNT_INT = "amount_int";
    public static  final String CLEF_SWITCH = "clef_switch";
    public static  final String SPINNER_INT = "spinnerint";
    public static  final String SPINNER_LOW_INT = "spinner_low_int";
    public static  final String SPINNER_HIGH_INT = "spinner_hight_int";


    TextView keyText;
    Spinner spinnerKey;
    Spinner spinnerHigh;
    Spinner spinnerLow;

    String[] Keys = {"C","D","E","F","G","A","B"};
    String[] Keys_Extension = {"C / Am" , "D / Bm" , "E / C#m" , "F / Dm" , "G / Em" , "A / F#m" , "B / G#m"};
    String[] RangeArray_G = {"F0","G0","A0","B0","C1","D1","E1","F1","G1","A1","B1","C2","D2","E2","F2","G2","A2","B2","C3","D3","E3"};
    String[] RangeArray_F = {"A0","B0","C1","D1","E1","F1","G1","A1","B1","C2","D2","E2","F2","G2","A2","B2","C3","D3","E3","F3","G3"};

    RelativeLayout options_general_layout;

    Switch setNightMode;
    Switch setScreenOn;
    Switch setEnableSound;
    Switch setEnableTempOnPlayScreen;

    ImageView g_clef;
    ImageView f_clef;
    TextView amount_15;
    TextView amount_30;
    TextView amount_50;


    SeekBar seek ;
    TextView seekText;
    int seekInt = 0;


    boolean on_off_screen_on_switch;
    boolean on_off_night_mode_switch;
    boolean on_off_sound_enable_switch;
    boolean on_off_tempo_on_play_screen_switch;
    boolean clef_switch = false;
    int spinnerKeyInt;
    int spinnerHighInt;
    int spinnerLowInt;
    int amountOfNotesInt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  setTheme(R.style.Theme_1);
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_options);
        Log.d(TAG, "OptionsActivity STARTED ");
        options_general_layout = findViewById(R.id.general_layout_options);

        banner();
        find();
        setNightMode();
        chooseClef();
        chooseAmount();
        setEnableTempOnPlayScreen();
        setScreenOn();
        setMute();

        setSpinner();
        setSeekBar();

    }

    public void getData(){
        Intent intent = getIntent();
        seekInt = intent.getIntExtra("seek_int", 60);
        saveData();
    }

    public void mute (boolean b){
        AudioManager manager= (AudioManager)getSystemService(AUDIO_SERVICE);
        manager.setStreamMute(AudioManager.STREAM_MUSIC,b);
    }

    public void setSeekBar(){
        seek = findViewById(R.id.seekBar);
        seekText = findViewById(R.id.seekText);

        if (on_off_tempo_on_play_screen_switch){ getData(); }

        loadData();
        updateView();
        seek.setProgress(seekInt);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekText.setText("" + progress);
                seekInt = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekText.setVisibility(View.INVISIBLE);
                saveData();
            }
        });
    }

    public void setSpinner(){
        keyText = findViewById(R.id.key_text);
        spinnerKey = (Spinner) findViewById(R.id.Key_Spinner);
        spinnerHigh = (Spinner) findViewById(R.id.high_Spinner);
        spinnerLow = (Spinner) findViewById(R.id.low_Spinner);

        loadData();
        updateView();

        spinnerKey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerKeyInt = position;
                saveData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHigh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerHighInt = position;
                saveData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerLowInt = position;
                saveData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setSpinnerAdapter();
    }

    public void setSpinnerAdapter(){

        ArrayAdapter spinnerKeyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Keys_Extension);
        spinnerKeyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKey.setAdapter(spinnerKeyAdapter);
        if (spinnerKeyInt < 7) { spinnerKey.setSelection(spinnerKeyInt); }
        else{  spinnerKeyInt = 0;
            spinnerKey.setSelection(spinnerKeyInt); }


        String[] adapterArr = null;
        if (clef_switch){
            adapterArr = RangeArray_F;
            spinnerKey.setSelection(0);
            spinnerKey.setEnabled(false);
        }else{
            adapterArr = RangeArray_G;
            spinnerKey.setEnabled(true);
        }

        ArrayAdapter spinnerRangeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,adapterArr);
        spinnerRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHigh.setAdapter(spinnerRangeAdapter);
        spinnerHigh.setSelection(spinnerHighInt);

        spinnerLow.setAdapter(spinnerRangeAdapter);
        spinnerLow.setSelection(spinnerLowInt);
    }

    public void setNightMode(){
        loadData();
        updateView();

        if (on_off_night_mode_switch) {
            options_general_layout.setBackgroundColor(Color.parseColor("#585858"));
        }else{
            options_general_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        setNightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Log.d(TAG, "night mode clicked");
                if (setNightMode.isChecked()) {
                    options_general_layout.setBackgroundColor(Color.parseColor("#585858"));
                }else{
                    options_general_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });
    }

    public void setEnableTempOnPlayScreen(){

        loadData();
        updateView();

        setEnableTempOnPlayScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { saveData(); }
        });
    }

    public void setScreenOn(){
        loadData();
        updateView();
        setScreenOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                if (setScreenOn.isChecked()){
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                else{
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        });
    }

    public void setMute(){
        loadData();
        updateView();
        setEnableSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                mute(setEnableSound.isChecked());
            }
        });
    }

    /*shared preference */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH_SCREEN_ON,setScreenOn.isChecked());
        editor.putBoolean(SWITCH_NIGHT_MODE,setNightMode.isChecked());
        editor.putBoolean(SWITCH_ENABLE_SOUND,setEnableSound.isChecked());
        editor.putBoolean(SWITCH_TEMPO_ON_PLAY_SCREEN,setEnableTempOnPlayScreen.isChecked());
        editor.putBoolean(CLEF_SWITCH,clef_switch);
        editor.putInt(SEEK_INT,seekInt);
        editor.putInt(SPINNER_INT,spinnerKeyInt);
        editor.putInt(SPINNER_HIGH_INT,spinnerHighInt);
        editor.putInt(SPINNER_LOW_INT,spinnerLowInt);
        editor.putInt(AMOUNT_INT,amountOfNotesInt);


        editor.apply();
    }
    public void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES,MODE_PRIVATE);

        on_off_screen_on_switch = sharedPreferences.getBoolean(SWITCH_SCREEN_ON,false);
        on_off_night_mode_switch = sharedPreferences.getBoolean(SWITCH_NIGHT_MODE,false);
        on_off_sound_enable_switch = sharedPreferences.getBoolean(SWITCH_ENABLE_SOUND,false);
        on_off_tempo_on_play_screen_switch = sharedPreferences.getBoolean(SWITCH_TEMPO_ON_PLAY_SCREEN,false);
        clef_switch = sharedPreferences.getBoolean(CLEF_SWITCH,false);
        seekInt = sharedPreferences.getInt(SEEK_INT,60);
        spinnerKeyInt = sharedPreferences.getInt(SPINNER_INT,0);
        spinnerHighInt = sharedPreferences.getInt(SPINNER_HIGH_INT,0);
        spinnerLowInt = sharedPreferences.getInt(SPINNER_LOW_INT,0);
        amountOfNotesInt = sharedPreferences.getInt(AMOUNT_INT,15);
    }
    public void updateView(){
        setScreenOn.setChecked(on_off_screen_on_switch);
        setNightMode.setChecked(on_off_night_mode_switch);
        setEnableSound.setChecked(on_off_sound_enable_switch);
        setEnableTempOnPlayScreen.setChecked(on_off_tempo_on_play_screen_switch);
        Log.d(TAG, "updateView:" + "\n"
                + "setScreenOn " + on_off_screen_on_switch + "\n"
                + "setNightMode " +on_off_night_mode_switch + "\n"
                + "setEnableSound " + on_off_sound_enable_switch + "\n"
                + "setEnableTempOnPlayScreen " + on_off_tempo_on_play_screen_switch + "\n"
                + "clef " + clef_switch + "\n"
                + "seekInt " + seekInt + "\n"
                + "spinner_int " + spinnerKeyInt + "\n"
                + "spinner_high_Int " + spinnerHighInt + "\n"
                + "spinner_low_Int " + spinnerLowInt  + "\n"
                + "amount " + amountOfNotesInt + "\n");

    }

    public void find (){
        setNightMode = findViewById(R.id.set_night_mode);
        setScreenOn = findViewById(R.id.set_screen_on);
        setEnableSound = findViewById(R.id.set_enable_sound);
        setEnableTempOnPlayScreen = findViewById(R.id.set_enable_temp_on_play_screen);

        g_clef = findViewById(R.id.g_clef);
        f_clef = findViewById(R.id.f_clef);
        amount_15 = findViewById(R.id.amount_15);
        amount_30 = findViewById(R.id.amount_30);
        amount_50 = findViewById(R.id.amount_50);
    }

    public void passData(){

        Intent intent = new Intent(this,StartActivity.class);
        intent.putExtra("options_acknowledge",true);
        intent.putExtra("is_screen_on",setScreenOn.isChecked());
        intent.putExtra("is_night_mode",setNightMode.isChecked());
        intent.putExtra("enable_sound",setEnableSound.isChecked());
        intent.putExtra("is_temp_on_play_screen_enable",setEnableTempOnPlayScreen.isChecked());
        intent.putExtra("seek_int",seekInt);
        intent.putExtra("spinner_string",Keys[spinnerKeyInt]);//scale name
        intent.putExtra("spinner_high_Int",spinnerHighInt);
        intent.putExtra("spinner_low_Int",spinnerLowInt);
        intent.putExtra("amount",amountOfNotesInt);
        intent.putExtra("clef",clef_switch);

        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        passData();
    }



    public void chooseClef(){

        loadData();
        updateView();

        if(clef_switch){
            g_clef.getBackground().setAlpha(0);
            f_clef.getBackground().setAlpha(250);
        }

        if (!clef_switch){
            g_clef.getBackground().setAlpha(250);
            f_clef.getBackground().setAlpha(0);
        }


        g_clef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_clef.getBackground().setAlpha(250);
                f_clef.getBackground().setAlpha(0);
                clef_switch = false;
                setSpinnerAdapter();
                saveData();
                Log.d(TAG, "clef switch clicked ");
            }
        });

        f_clef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_clef.getBackground().setAlpha(0);
                f_clef.getBackground().setAlpha(250);
                clef_switch = true;
                setSpinnerAdapter();
                saveData();
                Log.d(TAG, "clef switch clicked ");
            }
        });

    }

    public void chooseAmount(){



        amount_15.getBackground().setAlpha(0);
        amount_30.getBackground().setAlpha(0);
        amount_50.getBackground().setAlpha(0);

        if (amountOfNotesInt == 15){
            amount_15.getBackground().setAlpha(250);
            amount_30.getBackground().setAlpha(0);
            amount_50.getBackground().setAlpha(0);
        }

        if (amountOfNotesInt == 30){
            amount_15.getBackground().setAlpha(0);
            amount_30.getBackground().setAlpha(250);
            amount_50.getBackground().setAlpha(0);
        }

        if (amountOfNotesInt == 50){
            amount_15.getBackground().setAlpha(0);
            amount_30.getBackground().setAlpha(0);
            amount_50.getBackground().setAlpha(250);
        }



        amount_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount_15.getBackground().setAlpha(250);
                amount_30.getBackground().setAlpha(0);
                amount_50.getBackground().setAlpha(0);
                amountOfNotesInt = 15;
                saveData();
            }
        });

        amount_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount_15.getBackground().setAlpha(0);
                amount_30.getBackground().setAlpha(250);
                amount_50.getBackground().setAlpha(0);
                amountOfNotesInt = 30;
                saveData();
            }
        });

        amount_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount_15.getBackground().setAlpha(0);
                amount_30.getBackground().setAlpha(0);
                amount_50.getBackground().setAlpha(250);
                amountOfNotesInt = 50;
                saveData();
            }
        });

    }

    public void banner(){
        AdView mAdView;
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        //ca-app-pub-2365945636920098/1534683223
        adView.setAdUnitId("ca-app-pub-2365945636920098/1534683223");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
