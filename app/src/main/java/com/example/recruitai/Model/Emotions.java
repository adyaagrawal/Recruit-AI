package com.example.recruitai.Model;

public class Emotions {
    String angry;
    String disgusted;
    String fearful;
    String happy;
    String neutral;
    String sad;
    String surprised;

    public Emotions() {
    }

    public Emotions(String angry, String disgusted, String fearful, String happy, String neutral, String sad, String surprised) {
        this.angry = angry;
        this.disgusted = disgusted;
        this.fearful = fearful;
        this.happy = happy;
        this.neutral = neutral;
        this.sad = sad;
        this.surprised = surprised;
    }

    public String getAngry() {
        return angry;
    }

    public void setAngry(String angry) {
        this.angry = angry;
    }

    public String getDisgusted() {
        return disgusted;
    }

    public void setDisgusted(String disgusted) {
        this.disgusted = disgusted;
    }

    public String getFearful() {
        return fearful;
    }

    public void setFearful(String fearful) {
        this.fearful = fearful;
    }

    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public String getNeutral() {
        return neutral;
    }

    public void setNeutral(String neutral) {
        this.neutral = neutral;
    }

    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }

    public String getSurprised() {
        return surprised;
    }

    public void setSurprised(String surprised) {
        this.surprised = surprised;
    }
}
