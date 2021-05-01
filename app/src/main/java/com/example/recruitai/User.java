package com.example.recruitai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recruitai.Model.Job;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                final Job local=job;
                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent jobdet=new Intent(User.this,UserApply.class);
                        jobdet.putExtra("JobID",jobAdapter.getRef(position).getKey());
                        startActivity(jobdet);

                    }
                });
            }
        };
        recyclerView.setAdapter(jobAdapter);
    }

}