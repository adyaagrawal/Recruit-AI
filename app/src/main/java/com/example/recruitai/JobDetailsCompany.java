package com.example.recruitai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recruitai.Model.Job;
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
    FirebaseRecyclerAdapter<UserClass,CompanyUserViewHolder> userAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_company);

        database=FirebaseDatabase.getInstance();
        dat=database.getReference("Jobs");
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid=user.getUid();

        recyclerView=(RecyclerView)findViewById(R.id.rv3);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadUsers();
    }

    private void loadUsers() {
        userAdapter=new FirebaseRecyclerAdapter<UserClass, CompanyUserViewHolder>(UserClass.class,
                R.layout.applicantcard,
                CompanyUserViewHolder.class,
                dat.child(uid).child("Juser").orderByKey().equalTo("0")
                //dat.orderByKey().equalTo(uid).orderByChild("Juser").orderByKey().equalTo("0")
        ) {
            @Override
            protected void populateViewHolder(CompanyUserViewHolder viewholder, UserClass user, int i) {
                viewholder.uname.setText(user.getName());
                viewholder.status.setText(user.getStatus());

                final UserClass local=user;
                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent jobdet = new Intent(JobDetailsCompany.this, UserAnalysis.class);
                        jobdet.putExtra("UserID", userAdapter.getRef(position).getKey());
                        startActivity(jobdet);
                    }
                });
            }
        };
        recyclerView.setAdapter(userAdapter);

    }
}