package com.example.recruitai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CompanyLogin extends AppCompatActivity {
    TextView signup;
    EditText entpass;
    EditText entemail;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        entpass=findViewById(R.id.passcomlogin);
        entemail=findViewById(R.id.emailcomlogin);
        firebaseAuth=FirebaseAuth.getInstance();

        Button login=findViewById(R.id.logincom);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=entemail.getText().toString().trim();
                final String pass=entpass.getText().toString().trim();

                if(email.isEmpty()){
                    entemail.setError("Email is required");
                    entemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    entemail.setError("Enter valid company email");
                    entemail.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    entpass.setError("Email is required");
                    entpass.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    entpass.setError("Password length should be a minimum of 6");
                    entpass.requestFocus();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CompanyLogin.this,"Sign in successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CompanyLogin.this,Company.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(CompanyLogin.this,"Failed to log in. Please check your credentials.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        signup=findViewById(R.id.textView);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyLogin.this,CompanySignup.class));
            }
        });
    }


}