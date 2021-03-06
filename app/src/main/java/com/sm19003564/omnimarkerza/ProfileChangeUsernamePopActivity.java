package com.sm19003564.omnimarkerza;

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

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileChangeUsernamePopActivity extends Activity {
    // Declaration
    Button changeEmailButton;
    TextView currentEmailTV;
    EditText newEmailET;
    String currentEmail, newEmail;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change_username_pop);

        // Initialize
        changeEmailButton = (Button) findViewById(R.id.buttonChangeEmail);
        currentEmailTV = (TextView) findViewById(R.id.textViewCurrentEmail);
        newEmailET = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Layout settings
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

        // Get email
        currentEmail = mFirebaseAuth.getCurrentUser().getEmail();

        // Set textview
        currentEmailTV.setText(currentEmail);

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if text boxes are empty
                if ((newEmailET.getText().toString().trim().length() == 0) ) {
                    Toast.makeText(getApplicationContext(), "Text boxes cannot be empty ", Toast.LENGTH_SHORT).show();
                    return;
                }
                newEmail = newEmailET.getText().toString();

                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                // Update email
                user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Email changed to " + newEmail, Toast.LENGTH_SHORT).show();
                            // Get email from firebase
                            currentEmail = mFirebaseAuth.getCurrentUser().getEmail();

                            // Set textview to new email
                            currentEmailTV.setText(currentEmail);
                        }
                    }
                });
            }
        });
    }
}

/**
 *
 *
 * -----------------------------CODE-ATTRIBUTION---------------------------------
 *  Resource Type: Firebase Docs
 *  Available at: https://firebase.google.com/docs/auth/web/manage-users
 *  Author: Google
 *  Year: N/A
 *  Year used: 2021
 *  Date used: 20/05
 * -------------------------------------------------------------------------------
 */
/**
 *
 *
 * -----------------------------CODE-ATTRIBUTION---------------------------------
 *  Resource Type: Youtube Video
 *  Available at: https://www.youtube.com/watch?v=eX-TdY6bLdg
 *  Author: Angga Risky
 *  Year: 2017
 *  Year used: 2021
 *  Date used: 30/05
 * -------------------------------------------------------------------------------
 */