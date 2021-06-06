package com.example.recruitai.Model;

import java.util.HashMap;

public class UserClass {
    String name;
    String phone;
    String current;
    String status;
    Double resume_score;
    String resumepdf;
    String interviewmp4;
    String audio_text;
    String confidence_score;
    HashMap<String, String> emotions;

    public UserClass() {
    }

    public UserClass(String name, String phone, String current, String status, String resumepdf) {
        this.name = name;
        this.phone = phone;
        this.current = current;
        this.status = status;
        this.resumepdf = resumepdf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getResume_score() {
        return resume_score;
    }

    public void setResume_score(Double resume_score) {
        this.resume_score = resume_score;
    }

    public String getResumepdf() {
        return resumepdf;
    }

    public void setResumepdf(String resumepdf) {
        this.resumepdf = resumepdf;
    }

    public String getInterviewmp4() {
        return interviewmp4;
    }

    public void setInterviewmp4(String interviewmp4) {
        this.interviewmp4 = interviewmp4;
    }

    public String getAudio_text() {
        return audio_text;
    }

    public void setAudio_text(String audio_text) {
        this.audio_text = audio_text;
    }

    public String getConfidence_score() {
        return confidence_score;
    }

    public void setConfidence_score(String confidence_score) {
        this.confidence_score = confidence_score;
    }

    public HashMap<String, String> getEmotions() {
        return emotions;
    }

    public void setEmotions(HashMap<String, String> emotions) {
        this.emotions = emotions;
    }
}
