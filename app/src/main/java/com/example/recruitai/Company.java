package com.example.recruitai;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitai.Model.Job;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Company extends AppCompatActivity {
    Button post;
    FirebaseDatabase database;
    DatabaseReference dat;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Job, JobCompanyViewHolder> jobAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        database = FirebaseDatabase.getInstance();
        dat = database.getReference("Jobs");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadCJobs();

        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Company.this, PostJob.class));
            }
        });
    }

    private void loadCJobs() {
        jobAdapter = new FirebaseRecyclerAdapter<Job, JobCompanyViewHolder>(Job.class,
                R.layout.companyjobcard,
                JobCompanyViewHolder.class,
                dat.orderByKey().equalTo(uid)
        ) {
            @Override
            protected void populateViewHolder(JobCompanyViewHolder viewholder, Job job, int i) {
                viewholder.jname.setText(job.getJname());
                viewholder.status.setText(job.getJphase());
                if (job.getJuser() != null) {
                    String s = "Number of Applicants:" + job.getJuser().size();
                    viewholder.num.setText(s);
                }

                final Job local = job;
                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent jobdet = new Intent(Company.this, JobDetailsCompany.class);
                        jobdet.putExtra("JobID", jobAdapter.getRef(position).getKey());
                        startActivity(jobdet);
                    }
                });
            }
        };
        recyclerView.setAdapter(jobAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            firebaseAuth.signOut();
            Intent signOutIntent = new Intent(Company.this, Signup.class);
            startActivity(signOutIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}