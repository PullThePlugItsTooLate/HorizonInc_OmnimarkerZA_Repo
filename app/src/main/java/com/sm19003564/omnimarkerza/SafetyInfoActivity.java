package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class SafetyInfoActivity extends AppCompatActivity {

    Button btnGeneralInfo, btnPhoneNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_info);

        btnGeneralInfo = findViewById(R.id.btnGeneralInfo);
        btnPhoneNumbers = findViewById(R.id.btnImportantPhoneNumber);


    }
}