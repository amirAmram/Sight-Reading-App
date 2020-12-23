package com.sparkling.sightreading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class Tuner extends AppCompatActivity {
    private static final String TAG = "Tuner";

    TextView noteText;
    TextView pitchText;

    /**
     * this activity represent tuner for user instrument
     * */

    private int RECORD_PERMISSON_CODE = 1;
    boolean FLAGE = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);

        noteText = findViewById(R.id.note_text);
        pitchText = findViewById(R.id.pitch_text);

        permission();
        tuner();
        Log.d(TAG, "onCreate: 0000000000000000\n00000   " + FLAGE + " 0000000000");





    }


    public void processPitch(float pitchInHz) {

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

        pitchText.setText("" + pitchInHz);

        freq_to_note(pitchInHz,174,185,"F1");
        freq_to_note(pitchInHz,185,196,"F#");
        freq_to_note(pitchInHz,196,207,"G1");
        freq_to_note(pitchInHz,207,220,"G#1");
        freq_to_note(pitchInHz,220,233,"A1");
        freq_to_note(pitchInHz,233,246,"A#1");
        freq_to_note(pitchInHz,246,261,"B1");
        freq_to_note(pitchInHz,261,277,"C2");
        freq_to_note(pitchInHz,277,293,"C#2");
        freq_to_note(pitchInHz,293,311,"D2");
        freq_to_note(pitchInHz,311,330,"D#2");
        freq_to_note(pitchInHz,330,350,"E2");
        freq_to_note(pitchInHz,350,369,"F2");
        freq_to_note(pitchInHz,369,392,"F#2");
        freq_to_note(pitchInHz,392,415,"G2");
        freq_to_note(pitchInHz,415,440,"G#2");
        freq_to_note(pitchInHz,440,466,"A2");
        freq_to_note(pitchInHz,466,494,"A#2");
        freq_to_note(pitchInHz,494,523,"B2");
        freq_to_note(pitchInHz,523,544,"C3");
        freq_to_note(pitchInHz,544,587,"C#3");
        freq_to_note(pitchInHz,587,622,"D3");
        freq_to_note(pitchInHz,622,659,"D#3");
        freq_to_note(pitchInHz,659,698,"E3");
        freq_to_note(pitchInHz,698,740,"F3");
        freq_to_note(pitchInHz,740,784,"F#3");
        freq_to_note(pitchInHz,784,830,"G3");
        freq_to_note(pitchInHz,830,880,"G#3");
        freq_to_note(pitchInHz,880,932,"A3");
        freq_to_note(pitchInHz,932,987,"A#3");
        freq_to_note(pitchInHz,987,1047,"B3");
        freq_to_note(pitchInHz,1047,1108,"C4");
        freq_to_note(pitchInHz,1108,1175,"C#4");
        freq_to_note(pitchInHz,1175,1244,"D4");
        freq_to_note(pitchInHz,1244,1319,"D#4");


    }

    public void permission(){
        ActivityCompat.requestPermissions(Tuner.this,
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
                    Toast.makeText(Tuner.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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

    public void freq_to_note(float pitchInHz ,int freq1, int freq2, String s){
        if(pitchInHz >= freq1 && pitchInHz < freq2) {
            noteText.setText(s);


            if (pitchInHz >= freq1 - 5 && pitchInHz < freq1 + 5) {
                noteText.setTextColor(Color.parseColor("#FF41C90F"));
                FLAGE = false;
            } else if (pitchInHz >= freq1 - 10 && pitchInHz < freq1 + 10) {
                noteText.setTextColor(Color.parseColor("#FF8F00"));
            } else if (pitchInHz >= freq1 - 15 && pitchInHz < freq1 + 15){
                noteText.setTextColor(Color.parseColor("#706368"));
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
}
