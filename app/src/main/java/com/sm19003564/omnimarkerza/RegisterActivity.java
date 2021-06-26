package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;

public class RegisterActivity extends AppCompatActivity {

    EditText emailId, passwordValue;
    String email, password;
    Button btnSignUp;
    TextView tvSignIn;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById(R.id.etEmail);
        passwordValue = findViewById(R.id.etPassword);

        tvSignIn = findViewById(R.id.tvSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailId.getText().toString().trim();
                password = passwordValue.getText().toString().trim();
                //confirmPassword = confirmPasswordvalue.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Please fill in the blank fields", Toast.LENGTH_SHORT).show();
                } else
                    {
                        mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                            {

                                                Toast.makeText(RegisterActivity.this, "Login for " + mFirebaseAuth.getCurrentUser().getEmail() + " successful", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivity(i);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(RegisterActivity.this, "User Login Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //check if user email exists
                                mFirebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<SignInMethodQueryResult> task) {

                                        if (task.getResult().getSignInMethods().size() == 0){
                                            Toast.makeText(RegisterActivity.this, "User Registration Failed: Unknown Error", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "User Registration Failed: A User With This Email Already Exists ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        });
                    }



            }
        });



        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish(); // this method closes the previous activity to reduce memory usage
            }
        });


    }
}