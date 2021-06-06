package com.example.recruitai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitai.Model.Job;
import com.example.recruitai.Model.UserClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class User extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dat;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Job, JobUserViewHolder> jobAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.d("snapshot", "activity opened");
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("Jobs");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Log.d("snapshot", "load jobs");
        loadJobs();
    }

    private void loadJobs() {
        Log.d("snapshot", "load jobs called");
        jobAdapter = new FirebaseRecyclerAdapter<Job, JobUserViewHolder>(Job.class,
                R.layout.jobusercard,
                JobUserViewHolder.class,
                dat.orderByChild("jname")
        ) {
            @Override
            protected void populateViewHolder(JobUserViewHolder viewholder, Job job, int i) {
                Log.d("snapshot", "populate viewholder");
                viewholder.cname.setText(job.getCname());
                viewholder.pos.setText(job.getJname());
                viewholder.loc.setText(job.getJloc());
                Log.d("snapshot", "populate viewholder 2");
                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        dat.child(jobAdapter.getRef(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Log.d("snapshot", "On data changed call");
                                if (snapshot.hasChild("Juser") && snapshot.child("Juser").hasChild(uid)) {
                                    Log.d("snapshot", "pls bhagwan");
                                    HashMap<String, UserClass> list = (HashMap<String, UserClass>) job.getJuser();
                                    Log.d("snapshot", "pls bhagwan pls");
                                    Log.d("snapshot", list.get(uid).getName());
                                    UserClass user = list.get(uid);
                                    Toast.makeText(User.this, user.getStatus(), Toast.LENGTH_SHORT).show();
                                    Log.d("snapshot", user.getName());
                                    if (user.getStatus().equals("Resume Processed")) {
                                        Toast.makeText(User.this, "Waiting for company to view profile and start interview", Toast.LENGTH_SHORT).show();
                                    } else if (user.getStatus().equals("Interview Approved")) {
                                        Intent jobdet = new Intent(User.this, Interview.class);
                                        jobdet.putExtra("JobID", jobAdapter.getRef(position).getKey());
                                        startActivity(jobdet);
                                    }
                                } else {
                                    Intent jobdet = new Intent(User.this, UserApply.class);
                                    jobdet.putExtra("JobID", jobAdapter.getRef(position).getKey());
                                    startActivity(jobdet);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("snapshot", "On cancelled");
                            }
                        });

                    }
                });
            }
        };
        Log.d("snapshot", "not set adapter");
        recyclerView.setAdapter(jobAdapter);
        Log.d("snapshot", "set adapter");
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
            Intent signOutIntent = new Intent(User.this, Signup.class);
            startActivity(signOutIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}