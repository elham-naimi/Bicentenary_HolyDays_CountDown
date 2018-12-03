package com.elna.holyday.receivers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.elna.holyday.ui.BicentenaryActivity;
import com.elna.holyday.util.AlarmUtil;
import com.elna.holyday.util.Util;
import com.elna.holyday.R;

public class BicentenaryProvider extends AppWidgetProvider {

    public static final String ACTION_UPDATE = "action.update";
    private static String TAG =   BicentenaryProvider.class.getSimpleName();
    Context mContext;
    Handler handler = new Handler();
    private PendingIntent pendingIntent;
    RemoteViews view;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"In OnReceive"+intent.toString());
        Log.i(TAG,"In OnReceive"+intent.getExtras());

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
      Log.i("TAG", "--------Provider::onUpdate::START---------");
      //  TimingLogger timings = new TimingLogger("TAG", "");

     // timings.addSplit("forloop");

        Log.i(TAG, "length:" + appWidgetIds.length);
        int daysleft = Util.getDaysLeftToBicentenary();
        // daysleft = 0;
      //  timings.addSplit("daysleft");

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if (daysleft == 0){ view = new RemoteViews(context.getPackageName(), R.layout.widget_layout_celebrations);
          //  timings.addSplit("getview");

    }
         else {
            view = new RemoteViews(context.getPackageName(), R.layout.widget_bicentenary_layout);
            view.setTextViewText(R.id.count, daysleft + "");
          //  timings.addSplit("getview");

        }


        int[] realAppWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, BicentenaryProvider.class));

        Intent intent = new Intent(context, BicentenaryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
// Get the layout for the App Widget and attach an on-click listener to the button
        view.setOnClickPendingIntent(R.id.widget_bicentenary_layout, pendingIntent);

        manager.updateAppWidget(realAppWidgetIds, view);
     //   timings.addSplit("udpateAppWidget");

        Log.i(TAG,"--------Provider::onUpdate::END---------");


      //  timings.dumpToLog();

    }

}
