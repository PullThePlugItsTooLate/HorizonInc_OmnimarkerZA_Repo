package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//Widget imports
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailId, passwordValue;
    private String email, password;
    private Button btnSignIn;
    private TextView tvSignUp;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById(R.id.etEmail);
        passwordValue = findViewById(R.id.etPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailId.getText().toString().trim();
                password = passwordValue.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please fill in the blank fields", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "Login for " + mFirebaseAuth.getCurrentUser().getEmail() + " successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "User Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish(); // this method closes the previous activity to reduce memory usage
            }
        });
    }
}