package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recruitai.Model.Job;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostJob extends AppCompatActivity {
    EditText cname,position,location;
    String cnames,positions,locations;
    Button post;
    ImageView upload;
    Uri imageuri = null;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    String url;
    FirebaseDatabase mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid=user.getUid();

        mdatabase=FirebaseDatabase.getInstance();

        cname=findViewById(R.id.companyName);
        position=findViewById(R.id.position);
        location=findViewById(R.id.location);
        post=findViewById(R.id.postjob);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job job = new Job(positions,uid,cnames,locations,url,"Resume Phase");
                mdatabase.getReference().child("Jobs").child(uid).setValue(job).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PostJob.this,"Job Posted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PostJob.this,Company.class));
                    }
                });
            }

            });


        upload=findViewById(R.id.imageView3);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnames=cname.getText().toString().trim();
                positions=position.getText().toString().trim();
                locations=location.getText().toString().trim();
                if(cnames.isEmpty()){
                    cname.setError("Company name is required");
                    cname.requestFocus();
                    return;
                }
                if(positions.isEmpty()){
                    position.setError("Position is required");
                    position.requestFocus();
                    return;
                }
                if(locations.isEmpty()){
                    location.setError("Location is required");
                    location.requestFocus();
                    return;
                }
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

    }

    ProgressDialog dialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading to Firebase");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = uid;
            Toast.makeText(PostJob.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child("company/" + messagePushID + "/" + "job_desc.pdf");
            Toast.makeText(PostJob.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        url=myurl;
                        Toast.makeText(PostJob.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        Toast.makeText(PostJob.this, myurl, Toast.LENGTH_SHORT).show();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(PostJob.this, "Uploaded Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
