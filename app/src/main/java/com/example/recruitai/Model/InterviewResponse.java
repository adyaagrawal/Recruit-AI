package com.example.recruitai.Model;

public class InterviewResponse {
    String id;
    String status;

    public InterviewResponse() {
    }

    public InterviewResponse(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

