package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignup extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        email=findViewById(R.id.emailsignup);
        pass=findViewById(R.id.passsignup);
        Button signup=findViewById(R.id.signupuser);
        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emails=email.getText().toString().trim();
                final String passs=pass.getText().toString().trim();

                if(emails.isEmpty()){
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                    email.setError("Enter valid email");
                    email.requestFocus();
                    return;
                }
                if(passs.isEmpty()){
                    pass.setError("Email is required");
                    pass.requestFocus();
                    return;
                }
                if(passs.length()<6){
                    pass.setError("Password length should be a minimum of 6");
                    pass.requestFocus();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(emails,passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserSignup.this,"Sign up successful. Login with credentials",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserSignup.this,UserLogin.class));
                        }
                        else{
                            Toast.makeText(UserSignup.this,"User registration failed. Try again!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}