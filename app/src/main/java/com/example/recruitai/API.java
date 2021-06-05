package com.example.recruitai;

import com.example.recruitai.Model.InterviewResponse;
import com.example.recruitai.Model.ResumeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    //https://recruitai-resume.herokuapp.com/analyseresume/?userid=IlGqWBBHVoQ45S04v0H3jF4h9AC2&companyid=vrxXYhd0SqWt96PaE1UpIulmazV2
    @GET("analyseresume")
    Call<ResumeResponse> resume(@Query("userid") String uid, @Query("companyid") String cid);

    //http://165.22.217.172/interviewprocessing/?userid=IlGqWBBHVoQ45S04v0H3jF4h9AC2&companyid=vrxXYhd0SqWt96PaE1UpIulmazV2
    @GET("interviewprocessing")
    Call<InterviewResponse> interview(@Query("userid") String uid,@Query("companyid")String cid);

}
