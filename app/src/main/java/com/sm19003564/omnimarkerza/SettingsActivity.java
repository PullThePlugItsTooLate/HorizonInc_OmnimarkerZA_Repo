package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
// Widget imports
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
// Firebase imports
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    // Object declaration
    Button btnAbout;
    Button btnHelp;
    FirebaseAuth mFirebaseAuth;
    Button btnSaveChanges;
    RadioButton rbMetric;
    RadioButton rbImperial;
    Settings settings;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference settingsRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialization of objects
        mFirebaseAuth = FirebaseAuth.getInstance();
        btnAbout = findViewById(R.id.btnAbout);
        btnHelp = findViewById(R.id.btnHelp);
        rbMetric = findViewById(R.id.rbMetric);
        rbImperial = findViewById(R.id.rbImperial);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        settings = new Settings(true, false);

        // Getting settings data from firebase
        settingsRef.child("SettingsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataInfo: snapshot.getChildren()){
                    // Retrieves the latest user setting
                    settings = dataInfo.getValue(Settings.class);
                }

                // Presets the setting control so that the user knows what setting is currently active
                if (settings.isMetric()){
                    rbMetric.setChecked(true);
                } else {
                    if (settings.isImperial()){
                        rbImperial.setChecked(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // Displays an error in case of database error
                Toast.makeText(SettingsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Saves the users selected setting to firebase
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Extracts the user's chosen setting
                if (rbMetric.isChecked()){
                    settings = new Settings(true, false);
                } else {
                    if (rbImperial.isChecked()){
                        settings = new Settings(false, true);
                    }
                }

                // Pushes the user's setting to firebase
                settingsRef.child("SettingsData").push().setValue(settings);
                Toast.makeText(SettingsActivity.this, "Your settings have been saved", Toast.LENGTH_SHORT).show();
            }
        });

        // Displays a simple toast message
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "This Landmark Android Application has been created by Horizon Inc. which is composed of: Shai-lin Lalu, Zahra Carrim, Rayhaan Nakooda, Suvashin Moodliar. OmnimarkerZA was created for OPSC7312 POE 2021", Toast.LENGTH_LONG).show();
            }
        });

        // Opens the help activity when the user clicks on the help button
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });
    }
}