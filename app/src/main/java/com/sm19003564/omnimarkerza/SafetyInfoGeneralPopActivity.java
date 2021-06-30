package com.sm19003564.omnimarkerza;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

public class SafetyInfoGeneralPopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_info_general_pop);

        // Edit layout
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }
}

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