package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SafetyInfoActivity extends AppCompatActivity {

    Button btnGeneralInfo, btnPhoneNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_info);

        btnGeneralInfo = findViewById(R.id.btnGeneralInfo);
        btnPhoneNumbers = findViewById(R.id.btnImportantPhoneNumber);

        btnGeneralInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens pop up
                Intent i = new Intent(getApplicationContext(), SafetyInfoGeneralPopActivity.class );
                startActivity(i);
            }
        });

        btnPhoneNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens pop up
                Intent i = new Intent(getApplicationContext(), SafetyInfoNumbersPopActivity.class );
                startActivity(i);
            }
        });
    }
}