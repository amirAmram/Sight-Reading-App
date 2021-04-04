package com.sparkling.sightreading;

public class MusicNote {

    private String note_name = "F_0"; // Name range f0 to e3.
    private int note_height = 0; // There is 22 notes on the board, the range will be between 0 - 21.
    private double note_duration = 0.25; // Quoter note is 0.25, half note is 0.5.
    private double note_frequency = 174.0;

    private boolean note_isStaccato = false; // short note
    private boolean note_isLegato = false; // loge note
    private boolean note_isForte = false; // loud note
    private boolean note_isPiano = false; // quite note
    private int note_forte = 0;
    private int note_piano = 0;


    /** constructor **/
    public MusicNote(String note_name, int note_height, double note_duration, double note_frequency ){
        this.note_name = note_name;
        this.note_height = note_height;
        this.note_duration = note_duration;
        this.note_frequency = note_frequency;
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


    public boolean isStaccato() {
        return note_isStaccato;
    }

    public void setStaccato(boolean note_isStaccato) {
        this.note_isStaccato = note_isStaccato;
    }

    public boolean isLegato() {
        return note_isLegato;
    }

    public void setLegato(boolean note_isLegato) {
        this.note_isLegato = note_isLegato;
    }

    public boolean isForte() {
        return note_isForte;
    }

    public void setForte(boolean note_isForte) {
        this.note_isForte = note_isForte;
    }

    public boolean isPiano() {
        return note_isPiano;
    }

    public void setPiano(boolean note_isPiano) {
        this.note_isPiano = note_isPiano;
    }

    public int getForte() {
        return note_forte;
    }

    public void setForte(int note_forte) {
        this.note_forte = note_forte;
    }

    public int getPiano() {
        return note_piano;
    }

    public void sePiano(int note_piano) {
        this.note_piano = note_piano;
    }
}
