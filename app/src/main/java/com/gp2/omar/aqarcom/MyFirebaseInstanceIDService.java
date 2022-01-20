package com.gp2.omar.aqarcom;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Omar on 10/4/2017.
 */

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService
{
    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh()
    {
        String recent_token= FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("FCM_TOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FCM_TOKEN",recent_token);
        editor.apply();
        Log.d(REG_TOKEN,recent_token);
    }
}
