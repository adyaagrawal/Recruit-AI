package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recruitai.Model.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAnalysis extends AppCompatActivity {
    Button interview;
    TextView name,current,phone,score;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dat;
    String userid;
    String uid;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    UserClass currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analysis);

        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid=user.getUid();

        interview=findViewById(R.id.interview);
        name=findViewById(R.id.ananame);
        current=findViewById(R.id.anacurrent);
        phone=findViewById(R.id.anaphone);
        score=findViewById(R.id.textView10);

        if (getIntent() != null) {
            userid = getIntent().getStringExtra("UserID");
        }

        firebaseDatabase=FirebaseDatabase.getInstance();
        dat=firebaseDatabase.getReference("Jobs");
        dat.child(uid).child("Juser").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentuser = snapshot.getValue(UserClass.class);
                name.setText(currentuser.getName());
                current.setText(currentuser.getCurrent());
                phone.setText(currentuser.getPhone());
                score.setText(currentuser.getResume_score().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inter = new Intent(UserAnalysis.this,QuestionC.class);
                inter.putExtra("UserID", userid);
                startActivity(inter);
            }
        });

    }
}