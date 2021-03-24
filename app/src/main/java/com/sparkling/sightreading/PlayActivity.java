package com.sparkling.sightreading;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.CountDownTimer;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = "PlayActivity";

    public static  final String SHARED_PREFES = "sharedPrefs";
    public static  final String GRAPH_SCORE_ARRY = "graph_score";
    public static  final String GRAPH_INDEX = "graph_index";

    String s1 = "C";
    boolean key = false; // false = C, true = F.
    int percent = 0;
    int amount = 0;


    int game_level;
    int game_stage;
    int gameSequens[];
    int gameSequensIndex = 0;
    int [] freq ;
    Button [] notes_button_on_screen_array = new Button[7];


    LinearLayout doReMi_buttons_on_play_screen_layout;




    AlertDialog alertDialog;
    TextView performance;
    TextView stageNum;
    TextView graphTitle;
    ImageView star1;
    ImageView star2;
    ImageView star3;


    TextView title_tv;
    TextView time_tv;
    ImageView note_image;
    RelativeLayout note_layout;
    ImageView layoutImage;
    Button start_play_button;
    ImageView back;


    Random r = new Random();

    int [] layout_images = new int[20];

    final int [] layout_images_redundancy_0 = new int[] {
                  R.id.f0, //1
            R.id.g0, //2
            R.id.a0, //3
            R.id.b0, //4
            R.id.c1, //5
            R.id.d1, //6
            R.id.e1, //7
            R.id.f1, //8
            R.id.g1, //9
            R.id.a1, //10
            R.id.b1, //11
            R.id.c2, //12
            R.id.d2, //13
            R.id.e2, //14
            R.id.f2, //15
            R.id.g2, //16
            R.id.a2, //17
            R.id.b2, //18
            R.id.c3, //19
            R.id.d3, //20
            R.id.e3  //21
    };
    final int [] layout_images_redundancy_1 = new int[] {
            R.id.f0_backup_1, //1
            R.id.g0_backup_1, //2
            R.id.a0_backup_1, //3
            R.id.b0_backup_1, //4
            R.id.c1_backup_1, //5
            R.id.d1_backup_1, //6
            R.id.e1_backup_1, //7
            R.id.f1_backup_1, //8
            R.id.g1_backup_1, //9
            R.id.a1_backup_1, //10
            R.id.b1_backup_1, //11
            R.id.c2_backup_1, //12
            R.id.d2_backup_1, //13
            R.id.e2_backup_1, //14
            R.id.f2_backup_1, //15
            R.id.g2_backup_1, //16
            R.id.a2_backup_1, //17
            R.id.b2_backup_1, //18
            R.id.c3_backup_1, //19
            R.id.d3_backup_1, //20
            R.id.e3_backup_1  //21
    };
    final int [] layout_images_redundancy_2 = new int[] {
            R.id.f0_backup_2, //1
            R.id.g0_backup_2, //2
            R.id.a0_backup_2, //3
            R.id.b0_backup_2, //4
            R.id.c1_backup_2, //5
            R.id.d1_backup_2, //6
            R.id.e1_backup_2, //7
            R.id.f1_backup_2, //8
            R.id.g1_backup_2, //9
            R.id.a1_backup_2, //10
            R.id.b1_backup_2, //11
            R.id.c2_backup_2, //12
            R.id.d2_backup_2, //13
            R.id.e2_backup_2, //14
            R.id.f2_backup_2, //15
            R.id.g2_backup_2, //16
            R.id.a2_backup_2, //17
            R.id.b2_backup_2, //18
            R.id.c3_backup_2, //19
            R.id.d3_backup_2, //20
            R.id.e3_backup_2  //21
    };
    final int [] layout_images_redundancy_3 = new int[] {
            R.id.f0_backup_3, //1
            R.id.g0_backup_3, //2
            R.id.a0_backup_3, //3
            R.id.b0_backup_3, //4
            R.id.c1_backup_3, //5
            R.id.d1_backup_3, //6
            R.id.e1_backup_3, //7
            R.id.f1_backup_3, //8
            R.id.g1_backup_3, //9
            R.id.a1_backup_3, //10
            R.id.b1_backup_3, //11
            R.id.c2_backup_3, //12
            R.id.d2_backup_3, //13
            R.id.e2_backup_3, //14
            R.id.f2_backup_3, //15
            R.id.g2_backup_3, //16
            R.id.a2_backup_3, //17
            R.id.b2_backup_3, //18
            R.id.c3_backup_3, //19
            R.id.d3_backup_3, //20
            R.id.e3_backup_3  //21
    };
    final int [] layout_images_redundancy_4 = new int[] {
            R.id.f0_backup_4, //1
            R.id.g0_backup_4, //2
            R.id.a0_backup_4, //3
            R.id.b0_backup_4, //4
            R.id.c1_backup_4, //5
            R.id.d1_backup_4, //6
            R.id.e1_backup_4, //7
            R.id.f1_backup_4, //8
            R.id.g1_backup_4, //9
            R.id.a1_backup_4, //10
            R.id.b1_backup_4, //11
            R.id.c2_backup_4, //12
            R.id.d2_backup_4, //13
            R.id.e2_backup_4, //14
            R.id.f2_backup_4, //15
            R.id.g2_backup_4, //16
            R.id.a2_backup_4, //17
            R.id.b2_backup_4, //18
            R.id.c3_backup_4, //19
            R.id.d3_backup_4, //20
            R.id.e3_backup_4  //21
    };

    int [] images = new int[20];

    final int [] images_redundancy_0 = new int[] {
                  R.id.f0_image,
            R.id.g0_image,
            R.id.a0_image,
            R.id.b0_image,
            R.id.c1_image, //5
            R.id.d1, //6
            R.id.e1, //7
            R.id.f1, //8
            R.id.g1, //9
            R.id.a1, //10
            R.id.b1, //11
            R.id.c2, //12
            R.id.d2, //13
            R.id.e2, //14
            R.id.f2, //15
            R.id.g2, //16
            R.id.a2_image,
            R.id.b2_image,
            R.id.c3_image,
            R.id.d3_image,
            R.id.e3_image,
    };
    final int [] images_redundancy_1 = new int[] {
            R.id.f0_image_backup_1,
            R.id.g0_image_backup_1,
            R.id.a0_image_backup_1,
            R.id.b0_image_backup_1,
            R.id.c1_image_backup_1, //5
            R.id.d1_backup_1, //6
            R.id.e1_backup_1, //7
            R.id.f1_backup_1, //8
            R.id.g1_backup_1, //9
            R.id.a1_backup_1, //10
            R.id.b1_backup_1, //11
            R.id.c2_backup_1, //12
            R.id.d2_backup_1, //13
            R.id.e2_backup_1, //14
            R.id.f2_backup_1, //15
            R.id.g2_backup_1, //16
            R.id.a2_image_backup_1,
            R.id.b2_image_backup_1,
            R.id.c3_image_backup_1,
            R.id.d3_image_backup_1,
            R.id.e3_image_backup_1,
    };
    final int [] images_redundancy_2 = new int[] {
            R.id.f0_image_backup_2,
            R.id.g0_image_backup_2,
            R.id.a0_image_backup_2,
            R.id.b0_image_backup_2,
            R.id.c1_image_backup_2, //5
            R.id.d1_backup_2, //6
            R.id.e1_backup_2, //7
            R.id.f1_backup_2, //8
            R.id.g1_backup_2, //9
            R.id.a1_backup_2, //10
            R.id.b1_backup_2, //11
            R.id.c2_backup_2, //12
            R.id.d2_backup_2, //13
            R.id.e2_backup_2, //14
            R.id.f2_backup_2, //15
            R.id.g2_backup_2, //16
            R.id.a2_image_backup_2,
            R.id.b2_image_backup_2,
            R.id.c3_image_backup_2,
            R.id.d3_image_backup_2,
            R.id.e3_image_backup_2,
    };
    final int [] images_redundancy_3 = new int[] {
            R.id.f0_image_backup_3,
            R.id.g0_image_backup_3,
            R.id.a0_image_backup_3,
            R.id.b0_image_backup_3,
            R.id.c1_image_backup_3, //5
            R.id.d1_backup_3, //6
            R.id.e1_backup_3, //7
            R.id.f1_backup_3, //8
            R.id.g1_backup_3, //9
            R.id.a1_backup_3, //10
            R.id.b1_backup_3, //11
            R.id.c2_backup_3, //12
            R.id.d2_backup_3, //13
            R.id.e2_backup_3, //14
            R.id.f2_backup_3, //15
            R.id.g2_backup_3, //16
            R.id.a2_image_backup_3,
            R.id.b2_image_backup_3,
            R.id.c3_image_backup_3,
            R.id.d3_image_backup_3,
            R.id.e3_image_backup_3,
    };
    final int [] images_redundancy_4 = new int[] {
            R.id.f0_image_backup_4,
            R.id.g0_image_backup_4,
            R.id.a0_image_backup_4,
            R.id.b0_image_backup_4,
            R.id.c1_image_backup_4, //5
            R.id.d1_backup_4, //6
            R.id.e1_backup_4, //7
            R.id.f1_backup_4, //8
            R.id.g1_backup_4, //9
            R.id.a1_backup_4, //10
            R.id.b1_backup_4, //11
            R.id.c2_backup_4, //12
            R.id.d2_backup_4, //13
            R.id.e2_backup_4, //14
            R.id.f2_backup_4, //15
            R.id.g2_backup_4, //16
            R.id.a2_image_backup_4,
            R.id.b2_image_backup_4,
            R.id.c3_image_backup_4,
            R.id.d3_image_backup_4,
            R.id.e3_image_backup_4,
    };


    final int [] freq_F = new int[] {
            174,  //f0
            196,  //g0
            220,  //a0
            233,  //bb0
            261,  //c1
            293,  //d1
            330,  //e1
            350,  //f1
            392,  //g1
            440,  //a1
            466,  //bb1
            523,  //c2
            587,  //d2
            659,  //e2
            698,  //f2
            784,  //g2
            880,  //a2
            932,  //bb2
            1047, //c3
            1175, //d3
            1319, //e3
    };
    final int [] freq_Clef_F = new int[] {
            55,// a
            61,
            65,
            73,
            82,
            87,
            98,
            110,
            123,
            130,
            146,
            164,
            174,  //f0
            196,  //g0
            220,  //a0
            246,  //b0
            261,  //c1
            293,  //d1
            330,  //e1
            350,  //f1
            392,  //g1
    };

    /**
     *             174,  //f0
     *             185,
     *             196,  //g0
     *             207,
     *             220,  //a0
     *             233,
     *             246,  //b0
     *             261,  //c1
     *             277,
     *             293,  //d1
     *             311,
     *             330,  //e1
     *             350,  //f1
     *             369,
     *             392,  //g1
     *             415,
     *             440,  //a1
     *             466,
     *             494,  //b1
     *             523,  //c2
     *             544,
     *             587,  //d2
     *             622,
     *             659,  //e2
     *              ,  //f2
                                                            ************             740,
     *             784,  //g2
     *             830,
     *             880,  //a2
     *             932,
     *             987,  //b2
     *             1047, //c3
     *             1108,
     *             1175, //d3
     *             1244,
     *             1319, //e3 */
    final int [] freq_C = new int[] {
            174,  //f0
            196,  //g0
            220,  //a0
            246,  //b0
            261,  //c1
            293,  //d1
            330,  //e1
            350,  //f1
            392,  //g1
            440,  //a1
            494,  //b1
            523,  //c2
            587,  //d2
            659,  //e2
            698,  //f2
            784,  //g2
            880,  //a2
            987,  //b2
            1047, //c3
            1175, //d3
            1319, //e3
    };

    final int [] freq_G = new int[]{
            185,  //f#0
            196,  //g0
            220,  //a0
            246,  //b0
            261,  //c1
            293,  //d1
            330,  //e1
            369,  //f#1
            392,  //g1
            440,  //a1
            494,  //b1
            523,  //c2
            587,  //d2
            659,  //e2
            740,  //f2
            784,  //g2
            880,  //a2
            987,  //b2
            1047, //c3
            1175, //d3
            1319, //e3
    };

    final int [] freq_D = new int[]{
            185,  //f#0
            196,  //g0
            220,  //a0
            246,  //b0
            277,  //cardView#1
            293,  //d1
            330,  //e1
            369,  //f#1
            392,  //g1
            440,  //a1
            494,  //b1
            544,  //cardView#2
            587,  //d2
            659,  //e2
            740,  //f2
            784,  //g2
            880,  //a2
            987,  //b2
            1047, //cardView#3
            1175, //d3
            1319, //e3
    };

    final int [] freq_A = new int[]{
            185,  //f#0
            207,  //g#0
            220,  //a0
            246,  //b0
            277,  //cardView#1
            293,  //d1
            330,  //e1
            369,  //f#1
            415,  //g#1
            440,  //a1
            494,  //b1
            544,  //cardView#2
            587,  //d2
            659,  //e2
            740,  //f2
            830,  //g#2
            880,  //a2
            987,  //b2
            1047, //cardView#3
            1175, //d3
            1319, //e3
    };

    final int [] freq_E = new int[]{
            185,  //f#0
            207,  //g#0
            220,  //a0
            246,  //b0
            277,  //cardView#1
            311,  //d#1
            330,  //e1
            369,  //f#1
            415,  //g#1
            440,  //a1
            494,  //b1
            544,  //cardView#2
            622,  //d#2
            659,  //e2
            740,  //f2
            830,  //g#2
            880,  //a2
            987,  //b2
            1047, //cardView#3
            1244, //d#3
            1319, //e3
    };

    final int [] freq_B = new int[]{
            185,  //f#0
            207,  //g#0
            233,  //a#0
            246,  //b0
            277,  //cardView#1
            311,  //d#1
            330,  //e1
            369,  //f#1
            415,  //g#1
            466,  //a#1
            494,  //b1
            544,  //cardView#2
            622,  //d#2
            659,  //e2
            740,  //f2
            830,  //g#2
            932,  //a#2
            987,  //b2
            1047, //cardView#3
            1244, //d#3
            1319, //e3
    };

    /**
     *             174,  //f0
     *             185,
     *             196,  //g0
     *             207,
     *             220,  //a0
     *             233,
     *             246,  //b0
     *             261,  //c1
     *             277,
     *             293,  //d1
     *             311,
     *             330,  //e1
     *             350,  //f1
     *             369,
     *             392,  //g1
     *             415,
     *             440,  //a1
     *             466,
     *             494,  //b1
     *             523,  //c2
     *             544,
     *             587,  //d2
     *             622,
     *             659,  //e2
     *             698,  //f2
     *             740,
     *             784,  //g2
     *             830,
     *             880,  //a2
     *             932,
     *             987,  //b2
     *             1047, //c3
     *             1108,
     *             1175, //d3
     *             1244,
     *             1319, //e3 */

    int note_playing_counter = 0;
    int sum = 0;
    boolean is_star_happen = false; //this not give the user


    RelativeLayout play_general_layout;
    boolean is_screen_on_enable;
    boolean is_night_mode_enable;
    boolean is_sound_enable;


    ImageView image_key;

    int note_rate;
    int tempo_of_stage = 50;

    DataPoint[] points = new DataPoint[1000];
    int graph_index = -1;
    View v;

    boolean out_is_clicked = false;

    int current_freq = -10;
    //     ^
    // I put -10 because the default is -1 and I wanna include
    //      range of 3 both side --> ( -1 + 3, -1 - 3 ) so it could be -5 but I took extra

    boolean VOICE_FLAGE = false;
    boolean START_SOUND_DETECT = false;


    char redundancy_usage [] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};



    String musicNotesData[] = {
        //   note_name-note_frequency-octave|
             //check
            "Do-0.5-4, Re-0.25-4, Mi-0.125-4, Fa-0.0625-4, Sol-0.03125-4, La-0.0625-4, Si-0.125-4, Do-0.25-4, ",
/*stage 1*/ "Do-0.25-4, Do-0.25-4, Do-0.25-4, Do-0.25-4, ",
            "Do-0.25-4, Do-0.25-4, Re-0.25-4, Re-0.25-4, Do-0.25-4, Do-0.25-4, Do-0.25-4, Do-0.25-4, ",
            "Do-0.25-4, Re-0.25-4, Mi-0.25-4, Re-0.25-4, Do-0.25-4, Do-0.25-4, Do-0.5-4, ",
            "Do-0.5-4, Do-0.5-4, Re-0.25-4, Re-0.25-4, Re-0.25-4, Re-0.25-4, Do-0.5-4, Do-0.5-4, Re-0.25-4, Re-0.25-4, Re-0.25-4, Re-0.25-4, ",
            "Do-0.25-4, Mi-0.25-4, Do-0.25-4, Mi-0.25-4, Re-0.5-4, Re-0.5-4, Do-0.25-4, Mi-0.25-4, Do-0.25-4, Mi-0.25-4, Fa-0.5-4, Mi-0.5-4, ",
            "Mi-0.25-4, Re-0.25-4, Do-0.25-4, Re-0.25-4, Mi-0.25-4, Mi-0.25-4, Mi-0.5-4, Re-0.25-4, Re-0.25-4, Re-0.5-4, Mi-0.25-4, Mi-0.25-4, Mi-0.25-4, ",
            "Sol-0.25-4, Mi-0.25-4, Mi-0.50-4, Fa-0.25-4, Re-0.25-4, Re-0.50-4, Do-0.25-4, Re-0.25-4, Mi-0.25-4, Fa-0.25-4, Sol-0.25-4, Sol-0.25-4, Sol-0.25-4, ",
            "Fa-0.25-4, Sol-0.50-4, Sol-0.25-4, ",
            "Sol-0.25-4, Sol-0.125-4, La-0.125-4, Si-0.25-4, Sol-0.25-4, Do-0.25-5, Mi-0.25-5, Re-0.125-5, Do-0.125-5, Si-0.125-4, La-0.125-4, Sol-0.125-4, ",
};

    int id_of_playing_note = -1;
    int note_image_res;
    double note_duration;
    double note_frequency;


    ArrayList<MusicNote> musicNoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        setContentView(R.layout.activity_play);
        Log.d(TAG, "PlayActivity STARTED ");
        find();
        back();
        getData();
        loadData();
        setNightMode();
        setScreenOn();
        setKey();
        musicNoteSetup(game_stage); // this _0_ need to be change to the stage number

        findNotes();

        start_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_tv.setVisibility(View.INVISIBLE);
                time_tv.setVisibility(View.VISIBLE);
                start_play_button.setVisibility(View.GONE);
                note_playing_counter = 0;
                time_tv.setText(note_playing_counter + "/" + amount);

                permission();
                tuner();

                delay(1000);
                time_tv.setVisibility(View.VISIBLE);
                graph_index++;
            }
        });
    }

    public void find(){
        play_general_layout = findViewById(R.id.play_general);
        doReMi_buttons_on_play_screen_layout = findViewById(R.id.do_re_mi);
        back = findViewById(R.id.back);

        notes_button_on_screen_array[5] = findViewById(R.id.text_la);
        notes_button_on_screen_array[6] = findViewById(R.id.text_si);
        notes_button_on_screen_array[0] = findViewById(R.id.text_do);
        notes_button_on_screen_array[1] = findViewById(R.id.text_re);
        notes_button_on_screen_array[2] = findViewById(R.id.text_mi);
        notes_button_on_screen_array[3] = findViewById(R.id.text_fa);
        notes_button_on_screen_array[4] = findViewById(R.id.text_sul);

        image_key = findViewById(R.id.g_key);

        title_tv = findViewById(R.id.title);
        time_tv = findViewById(R.id.time_left);
        start_play_button = findViewById(R.id.btn);

    }

    public void start_notes(){
        if(!out_is_clicked) {


            id_of_playing_note = musicNoteList.get(note_playing_counter).getNote_display_id();
            note_image_res = musicNoteList.get(note_playing_counter).getNote_image();
            note_duration = musicNoteList.get(note_playing_counter).getNote_duration();
            note_frequency = musicNoteList.get(note_playing_counter).getNote_frequency();

            VOICE_FLAGE = false;

             note_rate = (int)(bitToMs(tempo_of_stage)* 4 * note_duration);  //determent the tempo of the game(cast the tempo to millisecond)
                                                                                // I multiple by 4 because the basic temp is for quoter and I wont 1

                if (16 > id_of_playing_note && id_of_playing_note > 4) {
                    set_redundancy_image_array(id_of_playing_note);
                    note_image = findViewById(images[id_of_playing_note]);
                    note_image.setImageResource(note_image_res);
                    if (!is_sound_enable){ setFrequency(2, note_frequency); } // because it is sound disable and not enable
                    note_image.setVisibility(View.VISIBLE);
                    startTranslation(note_image);
                } else {

                    Log.d(TAG, " \n  start_notes:  id = " + id_of_playing_note
                            + "\n  note_image_res = " + note_image_res
                            + "\n  note_duration = " + note_duration + "\n"
                            + "\n  redundancy_usage = " + redundancy_usage[id_of_playing_note]);

                    set_redundancy_image_layout_array(id_of_playing_note);
                    note_layout = findViewById(layout_images[id_of_playing_note]);
                    layoutImage = findViewById(images[id_of_playing_note]);
                    layoutImage.setPadding(0, 0, 0, 0);
                    layoutImage.setImageResource(note_image_res);
                    if (!is_sound_enable){ setFrequency(2, note_frequency); }
                    note_layout.setVisibility(View.VISIBLE);
                    startTranslation(note_layout);
                }

                is_star_happen = true;
                note_playing_counter++;
            }

            if (note_playing_counter < musicNoteList.size() && !out_is_clicked){ timer();
            }else {
                delay();
                note_playing_counter = 0;
            }

    }

    public void startTranslation(ImageView img){

        Animation anim = new TranslateAnimation(0,getScreenLength(),0,0);
        anim.setDuration(1000 * 10);
        anim.setInterpolator(this,android.R.anim.linear_interpolator);
        anim.setFillAfter(true);
        img.startAnimation(anim);
    }

    public void startTranslation(RelativeLayout layout){

        Animation anim = new TranslateAnimation(0,getScreenLength(),0,0);
        anim.setDuration(1000 * 10);
        anim.setInterpolator(this,android.R.anim.linear_interpolator);
        anim.setFillAfter(true);
        layout.startAnimation(anim);
    }

    public int getScreenLength(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width;
    }

    public void setFrequency(int duration , double freq){
        final int sampleRate = 22050;
        final int numSamples = duration * sampleRate;
        final double samples[] = new double[numSamples];
        final short buffer[] = new short[numSamples];

        for (int i = 0; i < numSamples; ++i)
        {
            samples[i] = Math.sin(2 * Math.PI * i / (sampleRate / freq)); // Sine wave
            buffer[i] = (short) (samples[i] * Short.MAX_VALUE);  // Higher amplitude increases volume
        }

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, buffer.length,
                AudioTrack.MODE_STATIC);

        audioTrack.write(buffer, 0, buffer.length);
        audioTrack.play();

    }

    public void findNotes(){


        /**                                              _________________________
         * this function connect between Buttons array ( |do|re|mi|fa|sol|la|si| )
         * to frequency. more over this function in charge of give the user feedback
         * when he or she press the note button
         * */

        time_tv.setText(sum + "/" + amount);
        amount = musicNoteList.size();
        for (int i = 0; i < 7; i++){
            int temp = 0;
            if (key){  temp = i + 2 ;}
            else{temp = i + 4 ;}  // because do or C is the 4th note on the freq table
            final int button_freq_index = temp ;

            notes_button_on_screen_array[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFrequency(2, freq[button_freq_index]);

                    if (is_star_happen) {
                        if (button_freq_index - 7 == id_of_playing_note || button_freq_index == id_of_playing_note || button_freq_index + 7 == id_of_playing_note || button_freq_index + 14 == id_of_playing_note) {

                            is_star_happen = false;
                            sum++;
                            percent = (sum/amount)*100;
                            time_tv.setText(sum + "/" + amount);

                            if (16 > id_of_playing_note && id_of_playing_note > 4) {
                                note_image.setImageResource(R.drawable.star_yellow);
                            } else {
                                //layoutImage.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
                                layoutImage.setPadding(0, 100, 0, 100);
                                layoutImage.setImageResource(R.drawable.star_yellow);
                            }
                        }
                    }
                }
            });
        }
    }


    public void timer(){
        new CountDownTimer(note_rate,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //time.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                if (16 > id_of_playing_note && id_of_playing_note > 4){
                    note_image = findViewById(images[id_of_playing_note]);
                    note_image.setVisibility(View.INVISIBLE);
                    note_image.setImageResource(note_image_res);
                }else {
                    note_layout = findViewById(layout_images[id_of_playing_note]);
                    layoutImage = findViewById(images[id_of_playing_note]);
                    note_layout.setVisibility(View.INVISIBLE);
                }


                start_notes();
            }
        }.start();
    }

    public void delay(int millis){
        new CountDownTimer(millis,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //time.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                doReMi_buttons_on_play_screen_layout.setVisibility(View.VISIBLE);
                start_notes();
            }
        }.start();


    }

    public void alertDialog ( String next_btn){
        alertDialog = new AlertDialog.Builder(PlayActivity.this).create();
        alertDialog.setView(v);
        saveData();

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, next_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameSequensIndex++;
                //gameSequens = convertStringToIntArray(NOTES_SEQUENCE[gameSequensIndex][game_stage]);

                start_play_button.setVisibility(View.GONE);
                note_playing_counter = 0;
                time_tv.setText(0 + "/" + amount);

                permission();
                tuner();

                findNotes();
                game_stage++;
                musicNoteSetup(game_stage);

                delay(1000);
                time_tv.setVisibility(View.VISIBLE);
                graph_index++;

                dialog.dismiss();
                doReMi_buttons_on_play_screen_layout.setVisibility(View.GONE);


            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Previous", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                doReMi_buttons_on_play_screen_layout.setVisibility(View.GONE);
            }
        });

        alertDialog.show();
    }


    public int bitToMs(int bitRate){
       return (60000/(bitRate + 1)) ;
    }

    public void getData(){

        Intent intent = getIntent();
        is_screen_on_enable = intent.getBooleanExtra("is_screen_on_enable", false);
        is_night_mode_enable = intent.getBooleanExtra("is_night_mode_enable", false);
        is_sound_enable = intent.getBooleanExtra("is_sound_enable", false);
        game_level = intent.getIntExtra("game_level",1); // easy hard master...
        game_stage = intent.getIntExtra("stage",1) +1;     // in the level part, stage num(+1 because start from 1 not 0)
       // gameSequens = convertStringToIntArray(NOTES_SEQUENCE[gameSequensIndex][game_stage]); // sec of notes
      //  amount = gameSequens.length;

     }

    public void setNightMode(){
        if (is_night_mode_enable) {
            play_general_layout.setBackgroundColor(Color.parseColor("#585858"));
            for (int i = 0; i < 7; i++) {
                notes_button_on_screen_array[i].setBackgroundColor(Color.parseColor("#585858"));
            }
        }else{
            play_general_layout.setBackgroundColor(Color.parseColor("#ffffff"));
            for (int i = 0; i < 7; i++) {
                notes_button_on_screen_array[i].setBackgroundColor(Color.parseColor("#ffffff"));
            }
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


    public void setButtonsName( String s0 ,String s1, String s2, String s3, String s4, String s5, String s6 ){
        notes_button_on_screen_array[0].setText(s0);
        notes_button_on_screen_array[1].setText(s1);
        notes_button_on_screen_array[2].setText(s2);
        notes_button_on_screen_array[3].setText(s3);
        notes_button_on_screen_array[4].setText(s4);
        notes_button_on_screen_array[5].setText(s5);
        notes_button_on_screen_array[6].setText(s6);

    }

    public void setKey(){

        if(key) {
            image_key.setPadding(-100, 110, 110, 110);
            image_key.setImageResource(R.drawable.f_clef);
            setButtonsName( "do", "re", "mi", "fa", "sul", "la", "si");
            freq = freq_Clef_F;
        }
        else {
            image_key.setPadding(0, -14, 0 , 20);
            switch (s1) {
                case "A":
                    image_key.setImageResource(R.drawable.key_sharp_3);
                    setButtonsName("do #", "re", "mi", "fa #", "sul #", "la", "si");
                    freq = freq_A;
                    break;

                case "B":
                    image_key.setImageResource(R.drawable.key_sharp_5);
                    setButtonsName("do #", "re #", "mi", "fa #", "sul #", "la #", "si");
                    freq = freq_B;
                    break;

                case "C":
                    image_key.setImageResource(R.drawable.key_0);
                    setButtonsName("do", "re", "mi", "fa", "sul", "la", "si");
                    freq = freq_C;
                    break;

                case "D":
                    image_key.setImageResource(R.drawable.key_sharp_2);
                    setButtonsName("do #", "re", "mi", "fa #", "sul", "la", "si");
                    freq = freq_D;
                    break;

                case "E":
                    image_key.setImageResource(R.drawable.key_sharp_4);
                    setButtonsName("do #", "re #", "mi", "fa #", "sul #", "la", "si");
                    freq = freq_E;
                    break;

                case "F":
                    image_key.setImageResource(R.drawable.key_flat_1);
                    setButtonsName("do", "re", "mi", "fa", "sul", "la", "si");
                    freq = freq_F;
                    break;

                case "G":
                    image_key.setImageResource(R.drawable.key_sharp_1);
                    setButtonsName("do", "re", "mi", "fa #", "sul", "la", "si");
                    freq = freq_G;
                    break;

            }
        }
    }

    public void passData(){
        Intent intent = new Intent(this,StartActivity.class);
        intent.putExtra("play_acknowledge",true);
        startActivity(intent);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(points);
        editor.putString(GRAPH_SCORE_ARRY,json);
        editor.putInt(GRAPH_INDEX, graph_index);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, MODE_PRIVATE);
        graph_index = sharedPreferences.getInt(GRAPH_INDEX,1);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(GRAPH_SCORE_ARRY,null);
        Type type = new TypeToken<DataPoint[]>(){}.getType();
        points = gson.fromJson(json,type);
        if (points == null){
            points = new DataPoint[100];
            for (int i = 0; i < points.length; i++) {
                points[i] = new DataPoint(i, 0);
            }
        }
//          Log.d(TAG, "loadData: " + graph_index + "+++++++++++++" + points[0] + points[1] + points[2] + points[3] + points[4] + points[5] );

    }


    public void delay(){
        new CountDownTimer(note_rate,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //time.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                LayoutInflater inflater = getLayoutInflater();
                v = inflater.inflate(R.layout.graph_layout,null);




                if(sum > 0){  percent  = ( sum  * 100 / amount ) ; }
                else{ percent = 0;}

                Log.d(TAG, "start_notes:          " + sum + "/" + amount + "   " + percent + "%" );
                if (!out_is_clicked) {
                    setGraph();
                    String s = "Excellent!";
                    if (percent == 0 ){ s = " You can do better " ;}
                    if (percent > 0 && percent < 10 ){ s = " It is ok, Try again " ;}
                    if (percent > 10 && percent <= 20 ){ s = " Fine " ;}
                    if (percent > 20 && percent <= 30 ){ s = " Good " ; }
                    if (percent > 30 && percent <= 40 ){ s = " Ok " ;}
                    if (percent > 40 && percent <= 50 ){ s = " Tip top " ; }
                    if (percent > 50 && percent <= 60 ){ s = " Outstanding " ; }
                    if (percent > 60 && percent <= 70 ){ s = " Splendid! " ; }
                    if (percent > 70 && percent <= 80 ){ s = " Tip terrific! " ; }
                    if (percent > 80 && percent <= 90 ){ s = " Thumbs up! " ; }
                    if (percent > 93 && percent <= 95 ){ s = " Swell! " ; }
                    if (percent > 95 && percent <= 97 ){ s = " Smashing! " ; }
                    if (percent > 97 && percent <= 100 ){ s = " Excellent! " ; }


                    alertDialog( "NEXT");
                    graphTitle = v.findViewById(R.id.graph_title);
                    graphTitle.setText(s);

                    stageNum = v.findViewById(R.id.stage_num);
                    stageNum.setText("STAGE - " + game_stage);

                    performance = v.findViewById(R.id.performance);
                    performance.setText(sum + "/" + amount + " " + percent + "%");

                    setStarsRate();

                    start_play_button.setVisibility(View.VISIBLE);
                    title_tv.setVisibility(View.VISIBLE);
                    doReMi_buttons_on_play_screen_layout.setVisibility(View.GONE);


                }
                sum = 0;
            }
        }.start();
    }


    public void setGraph(){


        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.graph_layout,null);

        GraphView graph = (GraphView) v.findViewById(R.id.graph);



        for (int i = graph_index; i < points.length; i++) {
            points[i] = new DataPoint(i, 0);
        }
        points[graph_index] = new DataPoint(graph_index, percent);

        Log.d(TAG, "loadData: " + graph_index + "+++++++++++++" + points[0] + points[1] + points[2] + points[3] + points[4] + points[5] );

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(102);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(graph_index);


        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);


        graph.addSeries(series);

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        if (graph_index >= 999){
            graph_index = 0;
            saveData();
        }

    }

    public void setStarsRate(){

        star1 = v.findViewById(R.id.star_1);
        star2 = v.findViewById(R.id.star_2);
        star3 = v.findViewById(R.id.star_3);

        if(percent > 98){
            star1.setImageResource(R.drawable.star_full);
            star2.setImageResource(R.drawable.star_full);
            star3.setImageResource(R.drawable.star_full);
        } else if(percent > 78){
            star1.setImageResource(R.drawable.star_full);
            star2.setImageResource(R.drawable.star_full);
            star3.setImageResource(R.drawable.star_half);
        } else if(percent > 66){
            star1.setImageResource(R.drawable.star_full);
            star2.setImageResource(R.drawable.star_full);
            star3.setImageResource(R.drawable.star_empty);
        } else if(percent > 48){
            star1.setImageResource(R.drawable.star_full);
            star2.setImageResource(R.drawable.star_half);
            star3.setImageResource(R.drawable.star_empty);
        }else if(percent > 33){
            star1.setImageResource(R.drawable.star_full);
            star2.setImageResource(R.drawable.star_empty);
            star3.setImageResource(R.drawable.star_empty);
        }else{
            star1.setImageResource(R.drawable.star_half);
            star2.setImageResource(R.drawable.star_empty);
            star3.setImageResource(R.drawable.star_empty);
        }


    }

    public void back(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out_is_clicked = true;
                passData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        out_is_clicked = true;
        passData();
         super.onBackPressed();

    }

    public void permission(){
        ActivityCompat.requestPermissions(PlayActivity.this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(TAG, "onRequestPermissionsResult:  --------------happened!---------------");
                    if(checkIfAlreadyhavePermission()){
                        AudioDispatcher dispatcher =
                                AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

                        PitchDetectionHandler pdh = new PitchDetectionHandler() {
                            @Override
                            public void handlePitch(PitchDetectionResult res, AudioEvent e){
                                final float pitchInHz = res.getPitch();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        processPitch(pitchInHz);
                                    }
                                });
                            }
                        };
                        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
                        dispatcher.addAudioProcessor(pitchProcessor);

                        Thread audioThread = new Thread(dispatcher, "Audio Thread");
                        audioThread.start();
                    }                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(PlayActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void processPitch(float pitchInHz ) {
        title_tv.setText("" + pitchInHz);

            if (!VOICE_FLAGE) {
                if (pitchInHz - 3 < note_frequency && pitchInHz + 3 > note_frequency) {
                    if (16 > id_of_playing_note && id_of_playing_note > 4) {
                        note_image.setImageResource(R.drawable.star_yellow);
                    } else {
                        //layoutImage.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
                        layoutImage.setPadding(0, 100, 0, 100);
                        layoutImage.setImageResource(R.drawable.star_yellow);
                    }
                    VOICE_FLAGE = true;
                    sum++;
                    time_tv.setText(sum + "/" + amount);

                }
            }

    }


    public void tuner(){
        if(checkIfAlreadyhavePermission()){
            AudioDispatcher dispatcher =
                    AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

            PitchDetectionHandler pdh = new PitchDetectionHandler() {
                @Override
                public void handlePitch(PitchDetectionResult res, AudioEvent e){
                    final float pitchInHz = res.getPitch();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processPitch(pitchInHz);
                        }
                    });
                }

            };
            AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
            dispatcher.addAudioProcessor(pitchProcessor);

            Thread audioThread = new Thread(dispatcher, "Audio Thread");
            audioThread.start();
        }
    }

    public int[] convertStringToIntArray(String s){

        String sArr[] = s.split(" ");
        int iArr[] = new int[sArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = (Integer.parseInt(sArr[i]));
        }

        return iArr;
    }

    public void musicNoteSetup(int stage) {

        if (stage < musicNotesData.length) {
            musicNoteList = new ArrayList<>();
            String note_splits_data[] = musicNotesData[stage].split(", ");

            for (int i = 0; i < note_splits_data.length; i++) {
                String sub_temp_arr[] = note_splits_data[i].split("-");
                MusicNote note = new MusicNote(
                        sub_temp_arr[0],
                        Double.parseDouble(sub_temp_arr[1]),
                        Integer.parseInt(sub_temp_arr[2])
                );
                musicNoteList.add(note);
            }

            id_of_playing_note = musicNoteList.get(note_playing_counter).getNote_display_id();
            note_image_res = musicNoteList.get(note_playing_counter).getNote_image();
            note_duration = musicNoteList.get(note_playing_counter).getNote_duration();
            note_frequency = musicNoteList.get(note_playing_counter).getNote_frequency();

            START_SOUND_DETECT = true; // it is mean start processPitch function- start to sample freq
        } else {
            //level error- the level data is not exist please restart the app
            Toast.makeText(this, "Hi we some problem it will take us some time to fix please try again soon.", Toast.LENGTH_LONG).show();
            start_play_button.setVisibility(View.INVISIBLE);
        }
    }

    public void set_redundancy_image_array(int note_id){

        switch(redundancy_usage[note_id]) {
            case 0:
                images = images_redundancy_0;
                redundancy_usage[note_id] = 1;
                break;
            case 1:
                images = images_redundancy_1;
                redundancy_usage[note_id] = 2;
                break;
            case 2:
                images = images_redundancy_2;
                redundancy_usage[note_id] = 3;
                break;
            case 3:
                images = images_redundancy_3;
                redundancy_usage[note_id] = 0;
                break;
            case 4:
                images = images_redundancy_4;
                redundancy_usage[note_id] = 0;
                break;

        }

    }

    public void set_redundancy_image_layout_array(int note_id){

        switch(redundancy_usage[note_id]) {
            case 0:
                layout_images = layout_images_redundancy_0;
                images = images_redundancy_0;
                redundancy_usage[note_id] = 1;
                break;
            case 1:
                layout_images = layout_images_redundancy_1;
                images = images_redundancy_1;
                redundancy_usage[note_id] = 2;
                break;
            case 2:
                layout_images = layout_images_redundancy_2;
                images = images_redundancy_2;
                redundancy_usage[note_id] = 3;
                break;
            case 3:
                layout_images = layout_images_redundancy_3;
                images = images_redundancy_3;
                redundancy_usage[note_id] = 4;
                break;
            case 4:
                layout_images = layout_images_redundancy_4;
                images = images_redundancy_4;
                redundancy_usage[note_id] = 0;
                break;

        }


    }
}
