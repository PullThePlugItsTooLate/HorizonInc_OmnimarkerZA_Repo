package com.sm19003564.omnimarkerza;

import android.content.Context;
import android.content.Intent;

public class IntentHelper {
    public static void openIntent(Context context, Class passTo){
        Intent i = new Intent(context, passTo);
        context.startActivity(i);
    }
}
