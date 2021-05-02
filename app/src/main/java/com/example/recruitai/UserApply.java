package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recruitai.Model.ResumeResponse;
import com.example.recruitai.Model.UserClass;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApply extends AppCompatActivity {
    EditText uname,uphone,ucurrent;
    ImageView download;
    ImageView upload;
    String unames,uphones,ucurrents;
    Button apply;
    Uri imageuri = null;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    String url;
    String JobID;
    FirebaseDatabase mdatabase;
    DatabaseReference det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apply);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid=user.getUid();

        mdatabase=FirebaseDatabase.getInstance();
        det=mdatabase.getReference();

        uname=findViewById(R.id.userName);
        uphone=findViewById(R.id.phone);
        ucurrent=findViewById(R.id.currentemployment);

        if (getIntent() != null) {
            JobID = getIntent().getStringExtra("JobID");
        }

        download=findViewById(R.id.imageView4);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        apply=findViewById(R.id.applyjob);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserClass user=new UserClass(unames,uphones,ucurrents,"Resume Submitted",url);
                List<UserClass> list= new ArrayList<UserClass>();
                list.add(user);
                mdatabase.getReference().child("Jobs").child(JobID).child("Juser").setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UserApply.this,"Job Applied",Toast.LENGTH_SHORT).show();
                        apicall();
                    }

                });

            }
        });

        upload=findViewById(R.id.imageView30);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unames=uname.getText().toString().trim();
                ucurrents=ucurrent.getText().toString().trim();
                uphones=uphone.getText().toString().trim();
                if(unames.isEmpty()){
                    uname.setError("Name is required");
                    uname.requestFocus();
                    return;
                }
                if(ucurrents.isEmpty()){
                    ucurrent.setError("Current Employment is required");
                    ucurrent.requestFocus();
                    return;
                }
                if(uphones.isEmpty()){
                    uphone.setError("Phone is required");
                    uphone.requestFocus();
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

    private void apicall() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://recruitai.herokuapp.com/resumeanalysis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api=retrofit.create(API.class);
        Call<List<ResumeResponse>> call=api.resume(uid,JobID);
        call.enqueue(new Callback<List<ResumeResponse>>() {
            @Override
            public void onResponse(Call<List<ResumeResponse>> call, Response<List<ResumeResponse>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(UserApply.this,"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(UserApply.this,"Resume Processed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserApply.this,User.class));
            }
            @Override
            public void onFailure(Call<List<ResumeResponse>> call, Throwable t) {
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
            Toast.makeText(UserApply.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child("users/" + messagePushID + "/" + "user_resume.pdf");
            Toast.makeText(UserApply.this, filepath.getName(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UserApply.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        Toast.makeText(UserApply.this, myurl, Toast.LENGTH_SHORT).show();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(UserApply.this, "Uploaded Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    }
