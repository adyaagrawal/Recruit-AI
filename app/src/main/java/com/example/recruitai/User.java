package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recruitai.Model.Job;
import com.example.recruitai.Model.UserClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class User extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dat;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Job,JobUserViewHolder> jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        database=FirebaseDatabase.getInstance();
        dat=database.getReference("Jobs");

        recyclerView=(RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadJobs();
    }

    private void loadJobs() {
        jobAdapter=new FirebaseRecyclerAdapter<Job, JobUserViewHolder>(Job.class,
                R.layout.jobusercard,
                JobUserViewHolder.class,
                dat.orderByChild("jname")
        ) {
            @Override
            protected void populateViewHolder(JobUserViewHolder viewholder, Job job, int i) {
                viewholder.cname.setText(job.getCname());
                viewholder.pos.setText(job.getJname());
                viewholder.loc.setText(job.getJloc());

                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        dat.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.hasChild("Juser")) {
                                    List<UserClass> list=job.getJuser();
                                    UserClass user=list.get(0);
                                    Toast.makeText(User.this,user.getStatus(),Toast.LENGTH_SHORT).show();
                                    if (user.getStatus().equals("Resume Processed")){
                                        Toast.makeText(User.this,"Waiting for company to view profile and start interview",Toast.LENGTH_SHORT).show();
                                    }
                                    else if (user.getStatus().equals("Interview Approved")){
                                        Intent jobdet=new Intent(User.this,Interview.class);
                                        jobdet.putExtra("JobID",jobAdapter.getRef(position).getKey());
                                        startActivity(jobdet);
                                    }
                                }
                                else{
                                    Intent jobdet=new Intent(User.this,UserApply.class);
                                    jobdet.putExtra("JobID",jobAdapter.getRef(position).getKey());
                                    startActivity(jobdet);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                }
            });
    }
    };
        recyclerView.setAdapter(jobAdapter);
}}