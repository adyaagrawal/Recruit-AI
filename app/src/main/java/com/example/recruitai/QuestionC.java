package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionC extends AppCompatActivity {
    EditText q;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    HashMap<String, String> langmap;
    String jobid;
    Spinner languageSpinner;
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

        languageSpinner = findViewById(R.id.spinner2);
        ArrayList<String> langname = new ArrayList<>();
        langmap = new HashMap<>();
        langname.add("English (United States)");
        langmap.put("English (United States)","en-US");
        langname.add("English (India)");
        langmap.put("English (India)","en-IN");
        langname.add("Hindi (India)");
        langmap.put("Hindi (India)","hi-IN");
        langname.add("Tamil (India)");
        langmap.put("Tamil (India)","ta-IN");
        langname.add("Telugu (India)");
        langmap.put("Telugu (India)","te-IN");

        ArrayAdapter<String> catnameadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, langname);
        catnameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*langspinner.setAdapter(catnameadapter);
        spinner.setVisibility(View.INVISIBLE);
        catnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catnamecurrent= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Category.this,catnamecurrent,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Category.this,"Nothing selected",Toast.LENGTH_SHORT).show();
            }
        });*/

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