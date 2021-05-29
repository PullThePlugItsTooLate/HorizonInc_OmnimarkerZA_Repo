package com.sm19003564.omnimarkerza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class SafetyInfoGeneralPopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_info_general_pop);
        TextView info = (TextView) findViewById(R.id.info);
        String safetyInfo = "Airport Safety: \n" +
                "\u2022 Do not leave your luggage unattended.\n" +
                "\u2022 Only ask for help and information from marked information stands.\n" +
                "\u2022 Take notice of your surroundings and the marked emergency exits.\n" +
                "Accommodation Safety: \n" +
                "\u2022 Keep your room door locked at all times.\n" +
                "\u2022 Never leave valuables unattended.\n" +
                "\u2022 Do not open the door to unexpected visitors/staff.\n" +
                "Street Safety: \n" +
                "\u2022 Do not carry large amounts of cash with you\n" +
                "\u2022 Walk in a group when possible\n" +
                "\u2022 Be aware of your surroundings";



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