package com.elna.holyday.services;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.RemoteViews;

import com.elna.holyday.R;
import com.elna.holyday.constant.HolyDaysData;
import com.elna.holyday.model.HolyDay;
import com.elna.holyday.receivers.BicentenaryProvider;
import com.elna.holyday.receivers.HolyDayWidgetProvider;
import com.elna.holyday.ui.BicentenaryActivity;
import com.elna.holyday.ui.NotificationManager;
import com.elna.holyday.util.Util;

import java.util.ArrayList;


/**
 * Created by elhamnaimi on 4/7/18.
 */

public class UpdateService extends JobIntentService {
    private static String TAG = UpdateService.class.getSimpleName();
    private static Context context = null;
    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;
    HolyDaysData holyDaysData;

    public static void enqueueWork(Context context, Intent intent) {
        Log.i(TAG,"Service called enque work");
        UpdateService.context = context ;
        enqueueWork(context, UpdateService.class, JOB_ID, intent);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        {
            //holyDaysData = new HolyDaysData();
           // holyDaysData.init();
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            RemoteViews view;
            ComponentName bicentenaryWidget = new ComponentName(this, BicentenaryProvider.class);
            int[] appBicentenaryWidgetIds = manager.getAppWidgetIds(bicentenaryWidget);

            ComponentName holydayWidget = new ComponentName(this, HolyDayWidgetProvider.class);
            int[] appHolyDayWidgetIds = manager.getAppWidgetIds(holydayWidget);

            if(appBicentenaryWidgetIds.length > 0){
                int daysleft = Util.getDaysLeftToBicentenary();
                if(daysleft == 0) {
                    view = new RemoteViews(context.getPackageName(), R.layout.widget_layout_celebrations);
                }
                else {
                    view = new RemoteViews(context.getPackageName(), R.layout.widget_bicentenary_layout);
                    view.setTextViewText(R.id.count, daysleft + "");
                    Intent  onClickIntent = new Intent(context, BicentenaryActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, onClickIntent, 0);
                    // Get the layout for the App Widget and attach an on-click listener to the button
                    view.setOnClickPendingIntent(R.id.widget_bicentenary_layout, pendingIntent);

                }



                manager.updateAppWidget(appBicentenaryWidgetIds,view);
            }

            if(appHolyDayWidgetIds.length > 0) {
                Log.i(TAG,"Send notification");
                sendNotification();
                manager.notifyAppWidgetViewDataChanged(appHolyDayWidgetIds,R.id.list);

            }
        }

    }

    private void sendNotification() {
        ArrayList<HolyDay> holyDays = Util.getUpcomingHolyDays(3);
        for (HolyDay holyday:holyDays
                ) {
            long count =  Util.getDaysLeft(holyday.date.minusDays(1));
            if(count == 1) {
                NotificationManager.sendNotification(context, holyday);
                break;
            }

        }
    }


}
