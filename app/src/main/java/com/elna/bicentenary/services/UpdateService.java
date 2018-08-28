package com.elna.bicentenary.services;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.RemoteViews;

import com.elna.bicentenary.constant.HolyDaysData;
import com.elna.bicentenary.model.HolyDay;
import com.elna.bicentenary.receivers.BicentenaryProvider;
import com.elna.bicentenary.receivers.HolyDayWidgetProvider;
import com.elna.bicentenary.receivers.WidgetListAdapter;
import com.elna.bicentenary.receivers.WidgetService;
import com.elna.bicentenary.ui.NotificationManager;
import com.elna.bicentenary.util.Util;
import com.elna.elhamnaimi.bicentenary.R;


/**
 * Created by elhamnaimi on 4/7/18.
 */

public class UpdateService extends JobIntentService {
    private static String TAG = UpdateService.class.getSimpleName();
    private static Context context = null;
    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;

    public static void enqueueWork(Context context, Intent intent) {
        Log.i(TAG,"Service called enque work");
        UpdateService.context = context ;
        enqueueWork(context, UpdateService.class, JOB_ID, intent);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        {
            HolyDaysData.init();
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            RemoteViews view;
            ComponentName bicentenaryWidget = new ComponentName(this, BicentenaryProvider.class);
            int[] appBicentenaryWidgetIds = manager.getAppWidgetIds(bicentenaryWidget);

            ComponentName holydayWidget = new ComponentName(this, HolyDayWidgetProvider.class);
            int[] appHolyDayWidgetIds = manager.getAppWidgetIds(holydayWidget);

            if(appBicentenaryWidgetIds.length > 0){
                int daysleft = Util.getDaysLeftToBicentenary();
                if(daysleft == 0) {
                    view = new RemoteViews(getPackageName(), R.layout.widget_layout_celebrations);
                }
                else {
                    view = new RemoteViews(getPackageName(), R.layout.widget_bicentenary_layout);
                    view.setTextViewText(R.id.count, daysleft + "");

                }
                manager.updateAppWidget(appBicentenaryWidgetIds,view);
            }

            if(appHolyDayWidgetIds.length > 0) {


                manager.notifyAppWidgetViewDataChanged(appHolyDayWidgetIds,R.id.list);


            }
        }

    }





}
