package com.example.recruitai.Model;

public class InterviewResponse {
    String id;
    String name;

    public InterviewResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public InterviewResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

