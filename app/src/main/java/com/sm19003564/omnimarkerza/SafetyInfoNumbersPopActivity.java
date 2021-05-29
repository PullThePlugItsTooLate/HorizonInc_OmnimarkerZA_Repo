package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import static android.content.Intent.ACTION_DIAL;
import static android.provider.LiveFolders.INTENT;

public class SafetyInfoNumbersPopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_info_numbers_pop);

        TextView number1, number2, number3;
        String text1, text2, text3;

        number1 = (TextView) findViewById(R.id.numbers1);
        number2 = (TextView) findViewById(R.id.numbers2);
        number3 = (TextView) findViewById(R.id.numbers3);
        text1 = "Nationwide Police Emergency Response: 10111 ";
        text2 = "Ambulance or Fire Department: 10177 ";
        text3 = "Any Emergency: 112 ";

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


        SpannableString ss1 = new SpannableString(text1);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(ACTION_DIAL);
                intent.setData(Uri.parse("tel:10111"));
                startActivity(intent);
            }
        };
        ss1.setSpan(clickableSpan1, 38, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );


        SpannableString ss2 = new SpannableString(text2);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(ACTION_DIAL);
                intent.setData(Uri.parse("tel:10177"));
                startActivity(intent);
            }
        };
        ss2.setSpan(clickableSpan2, 30, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        SpannableString ss3 = new SpannableString(text3);
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(ACTION_DIAL);
                intent.setData(Uri.parse("tel:112"));
                startActivity(intent);

            }
        };
        ss3.setSpan(clickableSpan3, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        number1.setText(ss1);
        number1.setMovementMethod(LinkMovementMethod.getInstance());
        number2.setText(ss2);
        number2.setMovementMethod(LinkMovementMethod.getInstance());
        number3.setText(ss3);
        number3.setMovementMethod(LinkMovementMethod.getInstance());

    }
}