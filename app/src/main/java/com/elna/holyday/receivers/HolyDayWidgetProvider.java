package com.elna.holyday.receivers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.elna.holyday.services.WidgetService;
import com.elna.holyday.ui.HolyDayListActivity;
import com.elna.holyday.util.AlarmUtil;
import com.elna.holyday.R;

public class HolyDayWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_UPDATE = "action.update";
    private static String TAG =   HolyDayWidgetProvider.class.getSimpleName();
    Context mContext;
    Handler handler = new Handler();
    private PendingIntent pendingIntent;
    RemoteViews view;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }
    @Override
    public void onEnabled(Context context) {
        Log.i(TAG,"--------Provider::onEnabled::START---------");
        // Enter relevant functionality for when the first widget is created
        AlarmUtil.scheduleUpdate(context);
        // Start the first one
        Log.i(TAG,"--------Provider::onEnabled::END---------");

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        AlarmUtil.clearUpdate(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // UpdateService.enqueueWork(context, new Intent());


          Log.i(TAG,"onUpdate HolyDayWIdgetProvider");
            int[] realAppWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, HolyDayWidgetProvider.class));
            view = new RemoteViews(context.getPackageName(), R.layout.widget_holyday_list);
            Intent intent = new Intent(context, WidgetService.class);
            view.setRemoteAdapter(R.id.list, intent);

          intent = new Intent(context, HolyDayListActivity.class);
          PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
          view.setPendingIntentTemplate(R.id.list, pendingIntent);

            appWidgetManager.notifyAppWidgetViewDataChanged(realAppWidgetIds,R.id.list);
            appWidgetManager.updateAppWidget(realAppWidgetIds, view);

        }

    }

