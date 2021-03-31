package com.sparkling.sightreading;

import android.util.Log;
import android.widget.ImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MusicNote {



    final int NUMBER_OF_NOTES = 12;
    int note_display_ids[][] = {
            {-1, -1, -1, -1, -1, 0, 0, 1, 1, 2, 2, 3 },
            { 4, 4, 5, 5, 6, 7, 7, 8, 8, 9, 9, 10} //+ 7 every time
    };
    String note_names_flat[] = {"Do", "Re♭", "Re", "Mi♭", "Mi", "Fa", "Sol♭", "Sol", "La♭", "La", "Si♭", "Si"};
    String note_names_sharp[] = {"Do","Do#", "Re", "Re#", "Mi", "Fa", "Fa#", "Sol", "Sol#", "La", "La#", "Si"};
    double note_frequencies[] = {16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87}; // C
    double note_durations[] = {0.75, 0.5, 0.375, 0.25, 0.125, 0.0625, 0.03125};
    int note_images[] = {
            R.drawable.dotted_half_note, //0.75
            R.drawable.half_note, //0.5
            R.drawable.dotted_quarter_note, //0.375
            R.drawable.quarter_note, //0.25
            R.drawable.eighth_note, //0.125
            R.drawable.sixteenth_note, //0.0625
            R.drawable.thirty_second_note //0.03125
    };

    int rest_images[] = {
            R.drawable.rest_whole,
            R.drawable.rest_half,
            R.drawable.rest_quarter,
            R.drawable.rest_eighth,
            R.drawable.rest_sixteenth
    };

    private String note_name = "Fa";
    private int octave_num = 3;
    private int note_height = 0;
    private double note_duration = 0.25;
    private double note_frequency = 175.0;
    private int note_image = R.drawable.quarter_note;
    private int note_display_id = -1;


    public MusicNote(String note_name, double note_duration, int octave_num){
       this.note_name = note_name;
       this.note_duration = note_duration;
       this.octave_num = octave_num;
       setup();
    }

    private void setup(){
        for (int i = 0; i < NUMBER_OF_NOTES; i++) {

            if (note_name.equals(note_names_sharp[i])){
                note_frequency = Math.pow(2, octave_num) * note_frequencies[i]; //set the frequency,increase x2 from the previous frequency

                if (octave_num == 3 && i > 4){
                    note_display_id = note_display_ids[0][i];
                }else {
                    if (octave_num > 3) {
                        Log.d(TAG, "setup: values:\n\t index: " + i + " \n\t note_display_ids[1][i]" +note_display_ids[1][i]);
                        note_display_id = note_display_ids[1][i] + (7 * (octave_num - 4)); //because minimal the octave num for the displays notes is 3 and this one abouve
                    }
                }break;

            }
        }

        for (int i = 0; i < note_durations.length; i++) {
            if (note_duration == note_durations[i]){
                note_image = note_images[i];
                break;

            }else if(note_name.equals("Rest")) {
                if (note_duration == 1){
                    note_image =  R.drawable.rest_whole;
                    break;
                }else if (note_duration == 0.5){
                    note_image =  R.drawable.rest_half;
                    break;
                }else if (note_duration == 0.25){
                    note_image =  R.drawable.rest_quarter;
                    break;
                }else if (note_duration == 125){
                    note_image =  R.drawable.sixteenth_note;
                    break;
                }
            }
        }

    }

    public int getNote_octave_num(){ return octave_num; }

    public void setNote_display_id(int note_display_id) {
        this.note_display_id = note_display_id;
    }

    public int getNote_display_id(){
        return note_display_id;
    }

    public int getNote_image(){
        return note_image;
    }

    public String getNote_name() {
        return note_name;
    }

    public void setNote_name(String note_name) {
        this.note_name = note_name;
    }

    public int getNote_height() {
        return note_height;
    }

    public void setNote_height(int note_height) {
        this.note_height = note_height;
    }

    public double getNote_duration() {
        return note_duration;
    }

    public void setNote_duration(double note_duration) {
        this.note_duration = note_duration;
    }

    public double getNote_frequency() {
        return note_frequency;
    }

    public void setNote_frequency(double note_frequency) {
        this.note_frequency = note_frequency;
    }

    @Override
    public String toString() {
        return "MusicNote{" +
                "octave_num=" + octave_num +
                ", note_duration=" + note_duration +
                ", note_frequency=" + note_frequency +
                ", note_image=" + note_image +
                ", note_display_id=" + note_display_id +
                '}' + "\n\n";
    }












}
