package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionC extends AppCompatActivity {
    EditText q;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String jobid;
    DatabaseReference job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_c);
        q=findViewById(R.id.question);
        submit=findViewById(R.id.questionsubmit);
        firebaseDatabase=FirebaseDatabase.getInstance();
        job=firebaseDatabase.getReference("Jobs");

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        jobid=user.getUid();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q1=q.getText().toString();
                job.child(jobid).child("Juser").child("0").child("status").setValue("Interview Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(QuestionC.this,"Status updated",Toast.LENGTH_SHORT).show();
                    }
                });
                job.child(jobid).child("IQ").setValue(q1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(QuestionC.this,"Question uploaded",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(QuestionC.this,JobDetailsCompany.class));
                    }
                });

            }
        });



    }
}