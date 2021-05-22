package com.example.recruitai.Model;

public class ResumeResponse {
    String id;
    String name;

    public ResumeResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ResumeResponse() {
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

