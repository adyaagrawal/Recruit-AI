package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recruitai.Model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Interview extends AppCompatActivity {
    TextView q,l;
    Button start;
    String jobid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference det;
    HashMap<String, String> langmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        q=findViewById(R.id.textView8);
        l=findViewById(R.id.textView17);
        start=findViewById(R.id.startint);

        ArrayList<String> langname = new ArrayList<>();
        langmap = new HashMap<>();
        langname.add("English (United States)");
        langmap.put("en-US","English (United States)");
        langname.add("English (India)");
        langmap.put("en-IN","English (India)");
        langname.add("Hindi (India)");
        langmap.put("hi-IN","Hindi (India)");
        langname.add("Tamil (India)");
        langmap.put("ta-IN","Tamil (India)");
        langname.add("Telugu (India)");
        langmap.put("te-IN","Telugu (India)");

        if (getIntent() != null) {
            jobid = getIntent().getStringExtra("JobID");
        }

        firebaseDatabase=FirebaseDatabase.getInstance();
        det=firebaseDatabase.getReference("Jobs");
        det.child(jobid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Job job=snapshot.getValue(Job.class);
                q.setText(job.getIQ());
                String lid=job.getLanguage();
                String Lname=langmap.get(lid);
                l.setText(Lname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Interview.this,MainActivity2.class);
                intent.putExtra("JobID",jobid);
                startActivity(intent);
            }
        });
    }
}