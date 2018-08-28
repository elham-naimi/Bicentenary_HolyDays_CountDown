package com.elna.bicentenary.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elna.bicentenary.services.UpdateService;

public class JobIntentReceiver extends BroadcastReceiver {
    private static String TAG = JobIntentReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"JobIntentReceiver onReceive called");
        UpdateService.enqueueWork(context, intent);
    }
}
