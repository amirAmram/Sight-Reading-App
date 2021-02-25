package com.sparkling.sightreading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.Random;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class PracticeActivity extends AppCompatActivity {
    private static final String TAG = "PracticeActivity";

    public static final String SHARED_PREFES = "sharedPrefs";
    public static final String GRAPH_SCORE_ARRY = "graph_score";
    public static final String GRAPH_INDEX = "spinnerint";


    String gameSeq;
    int[] gameSequens;
    int gameSequensIndex = 0;
    int[] freq;
    Button[] Notes = new Button[7];

    LinearLayout doReMi;
    int doReMiFlage = -1;

    SeekBar seek;
    TextView seekText;
    int seekInt;

    TextView title;
    TextView time;
    ImageView note;
    RelativeLayout noteLayout;
    ImageView layoutImage;
    Button start;
    ImageView back;

    Random r = new Random();
    final int[] images = new int[]{
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

    final int[] layoutImages = new int[]{
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

    final int[] freq_F = new int[]{
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
    final int[] freq_Clef_F = new int[]{
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
     * 174,  //f0
     * 185,
     * 196,  //g0
     * 207,
     * 220,  //a0
     * 233,
     * 246,  //b0
     * 261,  //c1
     * 277,
     * 293,  //d1
     * 311,
     * 330,  //e1
     * 350,  //f1
     * 369,
     * 392,  //g1
     * 415,
     * 440,  //a1
     * 466,
     * 494,  //b1
     * 523,  //c2
     * 544,
     * 587,  //d2
     * 622,
     * 659,  //e2
     * ,  //f2
     * ***********             740,
     * 784,  //g2
     * 830,
     * 880,  //a2
     * 932,
     * 987,  //b2
     * 1047, //c3
     * 1108,
     * 1175, //d3
     * 1244,
     * 1319, //e3
     */
    final int[] freq_C = new int[]{
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

    final int[] freq_G = new int[]{
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

    final int[] freq_D = new int[]{
            185,  //f#0
            196,  //g0
            220,  //a0
            246,  //b0
            277,  //c#1
            293,  //d1
            330,  //e1
            369,  //f#1
            392,  //g1
            440,  //a1
            494,  //b1
            544,  //c#2
            587,  //d2
            659,  //e2
            740,  //f2
            784,  //g2
            880,  //a2
            987,  //b2
            1047, //c#3
            1175, //d3
            1319, //e3
    };

    final int[] freq_A = new int[]{
            185,  //f#0
            207,  //g#0
            220,  //a0
            246,  //b0
            277,  //c#1
            293,  //d1
            330,  //e1
            369,  //f#1
            415,  //g#1
            440,  //a1
            494,  //b1
            544,  //c#2
            587,  //d2
            659,  //e2
            740,  //f2
            830,  //g#2
            880,  //a2
            987,  //b2
            1047, //c#3
            1175, //d3
            1319, //e3
    };

    final int[] freq_E = new int[]{
            185,  //f#0
            207,  //g#0
            220,  //a0
            246,  //b0
            277,  //c#1
            311,  //d#1
            330,  //e1
            369,  //f#1
            415,  //g#1
            440,  //a1
            494,  //b1
            544,  //c#2
            622,  //d#2
            659,  //e2
            740,  //f2
            830,  //g#2
            880,  //a2
            987,  //b2
            1047, //c#3
            1244, //d#3
            1319, //e3
    };

    final int[] freq_B = new int[]{
            185,  //f#0
            207,  //g#0
            233,  //a#0
            246,  //b0
            277,  //c#1
            311,  //d#1
            330,  //e1
            369,  //f#1
            415,  //g#1
            466,  //a#1
            494,  //b1
            544,  //c#2
            622,  //d#2
            659,  //e2
            740,  //f2
            830,  //g#2
            932,  //a#2
            987,  //b2
            1047, //c#3
            1244, //d#3
            1319, //e3
    };

    /**
     * 174,  //f0
     * 185,
     * 196,  //g0
     * 207,
     * 220,  //a0
     * 233,
     * 246,  //b0
     * 261,  //c1
     * 277,
     * 293,  //d1
     * 311,
     * 330,  //e1
     * 350,  //f1
     * 369,
     * 392,  //g1
     * 415,
     * 440,  //a1
     * 466,
     * 494,  //b1
     * 523,  //c2
     * 544,
     * 587,  //d2
     * 622,
     * 659,  //e2
     * 698,  //f2
     * 740,
     * 784,  //g2
     * 830,
     * 880,  //a2
     * 932,
     * 987,  //b2
     * 1047, //c3
     * 1108,
     * 1175, //d3
     * 1244,
     * 1319, //e3
     */


    int cnt = 0;
    int sum = 0;
    boolean isStarClicked = false;

    RelativeLayout general;
    boolean b1;
    boolean b2;
    boolean b3;
    boolean b4;
    boolean b5;

    int i1;
    String s1 = "C";

    int amount;
    int percent;

    int HighNoteControl = 15;
    int LowNoteControl = 5;

    ImageView image_key;

    int rate = 2500;

    DataPoint[] points = new DataPoint[100];
    int index_ = -1;
    View v;

    boolean outIsClicked = false;

    int current_freq = -10;
    //     ^
    // I put -10 because the default is -1 and I wanna include
    //      range of 3 both side --> ( -1 + 3, -1 - 3 ) so it could be -5 but I took extra

    boolean VOICE_FLAGE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Log.d(TAG, "PracticeActivity STARTED ");

        find();
        back();
        getData();
        loadData();
        setSeekBar();
        setNightMode();
        setScreenOn();
        setKey();

        findNotes();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //title.setVisibility(View.INVISIBLE);
                seek.setVisibility(View.GONE);
                time.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);
                cnt = 0;
                time.setText(0 + "/" + amount);

                permission();
                tuner();

                delay(1000);
                time.setVisibility(View.VISIBLE);
                index_++;
            }
        });
    }

    public void find() {
        general = findViewById(R.id.play_general);

        doReMi = findViewById(R.id.do_re_mi);

        back = findViewById(R.id.back);

        Notes[5] = findViewById(R.id.text_la);
        Notes[6] = findViewById(R.id.text_si);
        Notes[0] = findViewById(R.id.text_do);
        Notes[1] = findViewById(R.id.text_re);
        Notes[2] = findViewById(R.id.text_mi);
        Notes[3] = findViewById(R.id.text_fa);
        Notes[4] = findViewById(R.id.text_sul);

        image_key = findViewById(R.id.g_key);

        title = findViewById(R.id.title);
        time = findViewById(R.id.time_left);
        start = findViewById(R.id.btn);

    }

    public void start_notes_() {
        // max value 20
        if (!outIsClicked) {

            int num = r.nextInt(20);
            if (HighNoteControl > LowNoteControl) {
                num = r.nextInt(HighNoteControl - LowNoteControl + 1) + LowNoteControl;
            }

            VOICE_FLAGE = false;
            doReMiFlage = num; // for the click compare


            if (16 > num && num > 4) {
                note = findViewById(images[num]);
                setFrequency(2, freq[num]);
                current_freq = freq[num];
                note.setVisibility(View.VISIBLE);
                startTranslation(note);
            } else {
                noteLayout = findViewById(images[num]);
                layoutImage = findViewById(layoutImages[num]);
                layoutImage.setImageResource(R.drawable.quarter_note);
                layoutImage.setPadding(0, 0, 0, 0);
                setFrequency(2, freq[num]);
                current_freq = freq[num];
                noteLayout.setVisibility(View.VISIBLE);
                startTranslation(noteLayout);
            }

            isStarClicked = true;
            cnt++;
        }
        if (cnt < amount && !outIsClicked) {
            timer();
        } else {
            delay();
        }
    }

    public void start_notes() {
        if (!outIsClicked) {
            int num = gameSequens[gameSequensIndex];
            VOICE_FLAGE = false;
            doReMiFlage = num; // for the click compare

            if (16 > num && num > 4) {
                note = findViewById(images[num]);
                setFrequency(2, freq[num]);
                current_freq = freq[num];
                note.setVisibility(View.VISIBLE);
                startTranslation(note);

            } else {

                noteLayout = findViewById(images[num]);
                layoutImage = findViewById(layoutImages[num]);
                layoutImage.setImageResource(R.drawable.quarter_note);
                layoutImage.setPadding(0, 0, 0, 0);
                setFrequency(2, freq[num]);
                current_freq = freq[num];
                noteLayout.setVisibility(View.VISIBLE);
                startTranslation(noteLayout);
            }
            isStarClicked = true;

            gameSequensIndex++;

        }
        if (gameSequensIndex < gameSequens.length && !outIsClicked) {
            timer();

        } else {
            delay();
            gameSequensIndex = 0;
        }

    }


    public void startTranslation(ImageView img) {

        Animation anim = new TranslateAnimation(0, getScreenLength(), 0, 0);
        anim.setDuration(1000 * 10);
        anim.setInterpolator(this, android.R.anim.linear_interpolator);
        anim.setFillAfter(true);
        img.startAnimation(anim);
    }

    public void startTranslation(RelativeLayout layout) {

        Animation anim = new TranslateAnimation(0, getScreenLength(), 0, 0);
        anim.setDuration(1000 * 10);
        anim.setInterpolator(this, android.R.anim.linear_interpolator);
        anim.setFillAfter(true);
        layout.startAnimation(anim);
    }

    public int getScreenLength() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);
        return width;
    }

    public void setFrequency(int duration, int freq) {
        final int sampleRate = 22050;
        final int numSamples = duration * sampleRate;
        final double samples[] = new double[numSamples];
        final short buffer[] = new short[numSamples];

        for (int i = 0; i < numSamples; ++i) {
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


    public void findNotes() {
        time.setText(sum + "/" + amount);

        for (int i = 0; i < 7; i++) {
            int temp = 0;
            if (b5) {
                temp = i + 2;
            } else {
                temp = i + 4;
            }
            final int index = temp;


            Notes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    setFrequency(2, freq[index]);
                    Log.d(TAG, "onClick:                " + doReMiFlage + "            " + index);
                    //time.setText(sum + "/" + amount);

                    if (isStarClicked) {
                        if (index - 7 == doReMiFlage || index == doReMiFlage || index + 7 == doReMiFlage || index + 14 == doReMiFlage) {

                            Log.d(TAG, "onClick:                +1            ");
                            isStarClicked = false;
                            sum++;
                            time.setText(sum + "/" + amount);

                            if (16 > doReMiFlage && doReMiFlage > 4) {
                                note.setImageResource(R.drawable.star_yelllow);
                            } else {
                                //layoutImage.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
                                layoutImage.setPadding(0, 100, 0, 100);

                                layoutImage.setImageResource(R.drawable.star_yelllow);
                            }
                        }
                    }


                }
            });
        }
    }

    public void setSeekBar() {
        rate = bitToMs(i1);
        seekInt = i1;
        seek = findViewById(R.id.seekBar);
        seekText = findViewById(R.id.seekText);
        doReMi.setVisibility(View.GONE);
        if (b4) {
            seek.setVisibility(View.VISIBLE);
            seek.setProgress(i1);
            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekText.setText("" + progress);
                    seekInt = progress;
                    rate = bitToMs(seekInt);

                    if (seekInt < 15) {
                        seekText.setTextColor(Color.parseColor("#F0F70909"));
                    }
                    if (seekInt > 15 && seekInt < 25) {
                        seekText.setTextColor(Color.parseColor("#FFD81B60"));
                    }
                    if (seekInt > 25 && seekInt < 35) {
                        seekText.setTextColor(Color.parseColor("#FF8E24AA"));
                    }
                    if (seekInt > 35 && seekInt < 45) {
                        seekText.setTextColor(Color.parseColor("#FF5E35B1"));
                    }
                    if (seekInt > 45 && seekInt < 55) {
                        seekText.setTextColor(Color.parseColor("#FF3949AB"));
                    }
                    if (seekInt > 55 && seekInt < 65) {
                        seekText.setTextColor(Color.parseColor("#FF039BE5"));
                    }
                    if (seekInt > 65 && seekInt < 75) {
                        seekText.setTextColor(Color.parseColor("#FF00ACC1"));
                    }
                    if (seekInt > 75 && seekInt < 85) {
                        seekText.setTextColor(Color.parseColor("#FF00897B"));
                    }
                    if (seekInt > 85 && seekInt < 95) {
                        seekText.setTextColor(Color.parseColor("#FF43A047"));
                    }
                    if (seekInt > 95 && seekInt < 100) {
                        seekText.setTextColor(Color.parseColor("#FFC0CA33"));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    seekText.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seekText.setVisibility(View.INVISIBLE);
                }
            });
            Log.d(TAG, "                    step2 ");


        } else {
            seek.setVisibility(View.GONE);
        }
    }


    public void timer() {
        new CountDownTimer(rate, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //time.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                if (16 > doReMiFlage && doReMiFlage > 4) {
                    note = findViewById(images[doReMiFlage]);
                    note.setVisibility(View.INVISIBLE);
                    note.setImageResource(R.drawable.quarter_note);
                } else {
                    noteLayout = findViewById(images[doReMiFlage]);
                    noteLayout.setVisibility(View.INVISIBLE);
                }


                start_notes();
            }
        }.start();
    }

    public void delay(int millis) {
        new CountDownTimer(millis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //time.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                doReMi.setVisibility(View.VISIBLE);
                start_notes();
            }
        }.start();


    }

    public void alertDialog(String title, String message, String button_text) {
        AlertDialog alertDialog = new AlertDialog.Builder(PracticeActivity.this).create();
        alertDialog.setView(v);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        saveData();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doReMi.setVisibility(View.GONE);
                seek.setVisibility(View.VISIBLE);

            }
        });
        alertDialog.show();
    }


    public int bitToMs(int bitRate) {
        return (60000 / (bitRate + 1));
    }


    public void getData() {

        Intent intent = getIntent();
        b1 = intent.getBooleanExtra("b1", false);
        b2 = intent.getBooleanExtra("b2", false);
        b3 = intent.getBooleanExtra("b3", false);
        b4 = intent.getBooleanExtra("b4", false);
        b5 = intent.getBooleanExtra("b5", false);
        i1 = intent.getIntExtra("i1", 60);
        s1 = intent.getStringExtra("s1");
        HighNoteControl = intent.getIntExtra("i2", 0);
        LowNoteControl = intent.getIntExtra("i3", 20);
        amount = intent.getIntExtra("i4", 15);
//            gameSeq = intent.getStringExtra("NOTES_SEQUENCE");
//            gameSequens = convertStringToIntArray(gameSeq);
        Log.d(TAG, "getData:  ***********" + " b1: " + b1 + " b2: " + b2 + " b3: " + b3 + " b4: "
                + b4 + " i1: " + i1 + " s1: " + s1
                + "  " + "HighNoteControl: " + HighNoteControl + LowNoteControl + " i4: " + amount + " clef: " + b5);


    }

    public void setNightMode() {
        if (b2) {
            general.setBackgroundColor(Color.parseColor("#585858"));
            for (int i = 0; i < 7; i++) {
                Notes[i].setBackgroundColor(Color.parseColor("#585858"));
            }
        } else {
            general.setBackgroundColor(Color.parseColor("#ffffff"));
            for (int i = 0; i < 7; i++) {
                Notes[i].setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }

    public void setScreenOn() {
        if (b1) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }


    public void setButtonsName(String s0, String s1, String s2, String s3, String s4, String s5, String s6) {
        Notes[0].setText(s0);
        Notes[1].setText(s1);
        Notes[2].setText(s2);
        Notes[3].setText(s3);
        Notes[4].setText(s4);
        Notes[5].setText(s5);
        Notes[6].setText(s6);

    }

    public void setKey() {
        int a = 110;
        if (b5) {
            image_key.setPadding(-100, a, a, a);
            image_key.setImageResource(R.drawable.f_clef);
            setButtonsName("do", "re", "mi", "fa", "sul", "la", "si");
            freq = freq_Clef_F;
        } else {
            image_key.setPadding(0, -14, 0, 20);
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

    public void passData() {
        Log.d(TAG, "passData: 000000000000000000");

        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("play_acknowledge", true);
        intent.putExtra("seekInt", seekInt);
        Log.d(TAG, "passData:                  " + seekInt);
        Log.d(TAG, "passData:                  " + b4);

        startActivity(intent);

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(points);
        editor.putString(GRAPH_SCORE_ARRY, json);
        editor.putInt(GRAPH_INDEX, index_);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, MODE_PRIVATE);
        index_ = sharedPreferences.getInt(GRAPH_INDEX, 1);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(GRAPH_SCORE_ARRY, null);
        Type type = new TypeToken<DataPoint[]>() {
        }.getType();
        points = gson.fromJson(json, type);
        if (points == null) {
            points = new DataPoint[100];
            for (int i = 0; i < points.length; i++) {
                points[i] = new DataPoint(i, 0);
            }
        }
        Log.d(TAG, "loadData: " + index_ + "+++++++++++++" + points[0] + points[1] + points[2] + points[3] + points[4] + points[5]);

    }


    public void delay() {
        new CountDownTimer(rate, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //time.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                if (sum > 0) {
                    percent = (sum * 100 / amount);
                } else {
                    percent = 0;
                }

                Log.d(TAG, "start_notes:          " + sum + "/" + amount + "   " + percent + "%");
                if (!outIsClicked) {
                    setGraph();
                    String s = "Excellent!";
                    if (percent == 0) {
                        s = " You can do better ";
                    }
                    if (percent > 0 && percent < 10) {
                        s = " It is ok, Try again ";
                    }
                    if (percent > 10 && percent <= 20) {
                        s = " Fine ";
                    }
                    if (percent > 20 && percent <= 30) {
                        s = " Good ";
                    }
                    if (percent > 30 && percent <= 40) {
                        s = " Ok ";
                    }
                    if (percent > 40 && percent <= 50) {
                        s = " Tip top ";
                    }
                    if (percent > 50 && percent <= 60) {
                        s = " Outstanding ";
                    }
                    if (percent > 60 && percent <= 70) {
                        s = " Splendid! ";
                    }
                    if (percent > 70 && percent <= 80) {
                        s = " Tip terrific! ";
                    }
                    if (percent > 80 && percent <= 90) {
                        s = " Thumbs up! ";
                    }
                    if (percent > 93 && percent <= 95) {
                        s = " Swell! ";
                    }
                    if (percent > 95 && percent <= 97) {
                        s = " Smashing! ";
                    }
                    if (percent > 97 && percent <= 100) {
                        s = " Excellent! ";
                    }

                    alertDialog( /*title*/ s, sum + "/" + amount + "   " + percent + "%", "ok");
                    start.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    doReMi.setVisibility(View.GONE);
                    if (b4) {
                        seek.setVisibility(View.VISIBLE);
                    }

                }
                sum = 0;
            }
        }.start();
    }


    public void setGraph() {
        LayoutInflater inflater = getLayoutInflater();
        v = inflater.inflate(R.layout.graph_layout, null);

        GraphView graph = (GraphView) v.findViewById(R.id.graph);


        for (int i = index_; i < points.length; i++) {
            points[i] = new DataPoint(i, 0);
        }
        points[index_] = new DataPoint(index_, percent);

        Log.d(TAG, "loadData: " + index_ + "+++++++++++++" + points[0] + points[1] + points[2] + points[3] + points[4] + points[5]);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(102);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(index_);


        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);


        graph.addSeries(series);

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        if (index_ == 9999) {
            index_ = 0;
            saveData();
        }

    }

    public void back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outIsClicked = true;
                passData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        outIsClicked = true;
        passData();
        super.onBackPressed();

    }

    public void permission() {
        ActivityCompat.requestPermissions(PracticeActivity.this,
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
                    if (checkIfAlreadyhavePermission()) {
                        AudioDispatcher dispatcher =
                                AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

                        PitchDetectionHandler pdh = new PitchDetectionHandler() {
                            @Override
                            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
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
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(PracticeActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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

    public void processPitch(float pitchInHz) {

        title.setText("" + pitchInHz);

        //Log.d(TAG, "processPitch: " + (int)pitchInHz + " ---- " + current_freq);

        if (!VOICE_FLAGE) {
            if (pitchInHz - 3 < current_freq && pitchInHz + 3 > current_freq) {
                if (16 > doReMiFlage && doReMiFlage > 4) {
                    note.setImageResource(R.drawable.star_yelllow);
                } else {
                    //layoutImage.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
                    layoutImage.setPadding(0, 100, 0, 100);
                    layoutImage.setImageResource(R.drawable.star_yelllow);
                }
                VOICE_FLAGE = true;
                sum++;
                time.setText(sum + "/" + amount);

            }
        }
    }


    public void tuner() {
        if (checkIfAlreadyhavePermission()) {
            AudioDispatcher dispatcher =
                    AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

            PitchDetectionHandler pdh = new PitchDetectionHandler() {
                @Override
                public void handlePitch(PitchDetectionResult res, AudioEvent e) {
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

    public int[] convertStringToIntArray(String s) {
        int arr[] = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr[i] = ((int) s.charAt(i));
            Log.d(TAG, "convertStringToIntArray: " + arr[i]);
        }
        return arr;
    }


}

