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

public class Interview extends AppCompatActivity {
    TextView q;
    Button start;
    String jobid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        q=findViewById(R.id.textView8);
        start=findViewById(R.id.startint);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Interview.this,MainActivity2.class));
            }
        });
    }
}