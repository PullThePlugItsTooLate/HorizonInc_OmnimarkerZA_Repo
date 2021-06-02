package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileChangePasswordPopActivity extends Activity {

    //declare
    Button changePasswordButton;
    EditText currentPasswordET;
    EditText newPasswordET;
    String currentPassword, newPassword;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change_password_pop);

        //Initialize
        changePasswordButton = (Button) findViewById(R.id.buttonChangePassword);
        currentPasswordET = (EditText) findViewById(R.id.editTextTextPasswordOld);
        newPasswordET = (EditText) findViewById(R.id.editTextTextPasswordNew);
        mFirebaseAuth = FirebaseAuth.getInstance();


        //Layout settings
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.6), (int) (height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);


        //when the button is clicked
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if text boxes are empty
                if ((newPasswordET.getText().toString().trim().length() == 0) || (currentPasswordET.getText().toString().trim().length() == 0)) {
                    Toast.makeText(getApplicationContext(), "Text boxes cannot be empty ", Toast.LENGTH_SHORT).show();
                    return;
                }
                newPassword = newPasswordET.getText().toString();
                currentPassword = currentPasswordET.getText().toString();


                String currentEmail = mFirebaseAuth.getCurrentUser().getEmail();
                //get user
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(currentEmail, currentPassword);
                //re-authenticate user
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //update password
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "Password changed successfully" , Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "ERROR: please try again" , Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                        }
                        else {
                            //handle exception
                            Toast.makeText(getApplicationContext(), "Incorrect Password" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}