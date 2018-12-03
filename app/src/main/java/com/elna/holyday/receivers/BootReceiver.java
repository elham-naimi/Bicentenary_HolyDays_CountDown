
package com.elna.holyday.receivers;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.elna.holyday.services.UpdateService;
import com.elna.holyday.util.AlarmUtil;

/**
 * Created by elhamnaimi on 5/12/18.
 */

public class BootReceiver extends BroadcastReceiver {

    private  static String TAG = BootReceiver.class.getSimpleName();
    static int i = 0;
    static ImageView image  = null;
    int a = 1;


    @Override
    public void onReceive(Context context, Intent intent) {


        image = new ImageView(context);
        i = a;
        Log.i(TAG,"BootReceiver onReceive");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] bicentenaryIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BicentenaryProvider.class));
        int[] holyDayIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, HolyDayWidgetProvider.class));

        if (bicentenaryIds.length > 0 || holyDayIds.length >0) {
            AlarmUtil.scheduleUpdate(context);
        }

        if(intent.getAction().contentEquals(Intent.ACTION_TIME_CHANGED) ||
                intent.getAction().contentEquals(Intent.ACTION_TIMEZONE_CHANGED)) {
          /*  Intent serviceIntent = new Intent(context, UpdateService.class);
            context.startService(serviceIntent);*/
          Log.i(TAG,"on time change calling update service");
            UpdateService.enqueueWork(context, intent);

        }

        new Button(context).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}