package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        mFirebaseAuth = FirebaseAuth.getInstance();
        btnAbout = findViewById(R.id.btnAbout);
        btnHelp = findViewById(R.id.btnHelp);
        rbMetric = findViewById(R.id.rbMetric);
        rbImperial = findViewById(R.id.rbImperial);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        settings = new Settings(true, false);

        settingsRef.child("SettingsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataInfo: snapshot.getChildren()){
                    settings = dataInfo.getValue(Settings.class);
                }
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
                Toast.makeText(SettingsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbMetric.isChecked()){
                    settings = new Settings(true, false);
                } else {
                    if (rbImperial.isChecked()){
                        settings = new Settings(false, true);
                    }
                }

                settingsRef.child("SettingsData").push().setValue(settings);
                Toast.makeText(SettingsActivity.this, "Your settings have been saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}