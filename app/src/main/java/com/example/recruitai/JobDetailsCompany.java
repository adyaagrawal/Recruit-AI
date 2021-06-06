package com.example.recruitai;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitai.Model.UserClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobDetailsCompany extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dat;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<UserClass, CompanyUserViewHolder> userAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_company);

        database = FirebaseDatabase.getInstance();
        dat = database.getReference("Jobs");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.rv3);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadUsers();
    }

    private void loadUsers() {
        userAdapter = new FirebaseRecyclerAdapter<UserClass, CompanyUserViewHolder>(UserClass.class,
                R.layout.applicantcard,
                CompanyUserViewHolder.class,
                dat.child(uid).child("Juser").orderByKey()
        ) {
            @Override
            protected void populateViewHolder(CompanyUserViewHolder viewholder, UserClass user, int i) {
                viewholder.uname.setText(user.getName());
                viewholder.status.setText(user.getStatus());

                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(JobDetailsCompany.this, user.getStatus(), Toast.LENGTH_SHORT).show();
                        if (user.getStatus().equals("Resume Processed")) {
                            Intent jobdet = new Intent(JobDetailsCompany.this, UserAnalysis.class);
                            jobdet.putExtra("UserID", userAdapter.getRef(position).getKey());
                            startActivity(jobdet);
                        } else if (user.getStatus().equals("Interview phase")) {
                            Toast.makeText(JobDetailsCompany.this, "User is yet to give interview", Toast.LENGTH_SHORT).show();
                        } else if (user.getStatus().equals("Interview Submitted")) {
                            Toast.makeText(JobDetailsCompany.this, "Interview is being processed", Toast.LENGTH_SHORT).show();
                        } else if (user.getStatus().equals("Interview Processed")) {
                            Intent jobdet = new Intent(JobDetailsCompany.this, FinalAnalysis.class);
                            jobdet.putExtra("UserID", userAdapter.getRef(position).getKey());
                            startActivity(jobdet);
                        }

                    }
                });
            }
        };
        recyclerView.setAdapter(userAdapter);
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
            Intent signOutIntent = new Intent(JobDetailsCompany.this, Signup.class);
            startActivity(signOutIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}