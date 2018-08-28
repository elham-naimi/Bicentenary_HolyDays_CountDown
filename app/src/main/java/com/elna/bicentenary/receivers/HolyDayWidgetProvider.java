package com.elna.bicentenary.receivers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.elna.bicentenary.constant.HolyDaysData;
import com.elna.bicentenary.model.HolyDay;
import com.elna.bicentenary.ui.HolyDayListActivity;
import com.elna.bicentenary.util.AlarmUtil;
import com.elna.bicentenary.util.Util;
import com.elna.elhamnaimi.bicentenary.R;

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
        HolyDaysData.init();
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        view = new RemoteViews(context.getPackageName(), R.layout.widget_holyday_list);

        Intent intent = new Intent(context,WidgetService.class);
        view.setRemoteAdapter(R.id.list,intent);
        Log.i(TAG,"setting remote adapter");
      /* view.setTextViewText(R.id.imgcount,  daysleft+"" );
        view.setTextViewText(R.id.imgdaysleft,  "days left");

          if(nextHolyDay.desc.contains("&")){
              view.setTextViewText(R.id.imgholydayname,nextHolyDay.desc.substring(0,nextHolyDay.desc.indexOf("&")));
            view.setTextViewText(R.id.imgwhen, Util.getWhenString(nextHolyDay)
                    + " at " + nextHolyDay.desc.substring(nextHolyDay.desc.indexOf("&")+1, nextHolyDay.desc.length()));
        }
        else {
            view.setTextViewText(R.id.imgholydayname,nextHolyDay.desc);
            view.setTextViewText(R.id.imgwhen, Util.getWhenString(nextHolyDay));
        }
        if(daysleft == 0){
            view.setTextViewText(R.id.imgcount,   "" );
            view.setTextViewText(R.id.imgdaysleft,  "");
        }*/
        intent = new Intent(context, HolyDayListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        view.setOnClickPendingIntent(R.id.holyday_widget_layout, pendingIntent);
        manager.updateAppWidget(appWidgetIds, view);
    }

}
