
package com.elna.bicentenary.receivers;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elna.bicentenary.services.UpdateService;
import com.elna.bicentenary.util.AlarmUtil;

/**
 * Created by elhamnaimi on 5/12/18.
 */

public class BootReceiver extends BroadcastReceiver {

    private  static String TAG = BootReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"BootReceiver onReceive");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, BicentenaryProvider.class));
        if (ids.length > 0) {
            AlarmUtil.scheduleUpdate(context);
        }

        if(intent.getAction().contentEquals(Intent.ACTION_TIME_CHANGED) ||
                intent.getAction().contentEquals(Intent.ACTION_TIMEZONE_CHANGED)) {
          /*  Intent serviceIntent = new Intent(context, UpdateService.class);
            context.startService(serviceIntent);*/
            UpdateService.enqueueWork(context, intent);

        }
    }

}