package com.elna.bicentenary.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.elna.bicentenary.constant.HolyDaysData;
import com.elna.bicentenary.receivers.JobIntentReceiver;

import java.util.Calendar;


public class AlarmUtil {

    private static String TAG = AlarmUtil.class.getSimpleName();

    public static void scheduleUpdate(Context context) {
        Calendar upcomingUpdateTime =  getNextUpdateTime();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = getAlarmIntent(context);
        am.cancel(pi);
        am.setRepeating(AlarmManager.RTC,  upcomingUpdateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

    }

    public static Calendar getNextUpdateTime(){
        Calendar nextUpdateTime = Calendar.getInstance();
        nextUpdateTime.set(Calendar.HOUR_OF_DAY, HolyDaysData.NEXT_UPDATE_HOUR);
        nextUpdateTime.set(Calendar.MINUTE, HolyDaysData.NEXT_UPDATE_MINUTE);
        nextUpdateTime.set(Calendar.SECOND, HolyDaysData.NEXT_UPDATE_SECOND);
        if(getCurrentTime().after(nextUpdateTime )){
            nextUpdateTime.add(Calendar.DATE,1);
        }
        return  nextUpdateTime;

    }

    private static Calendar getCurrentTime(){ return Calendar.getInstance();}

    private static PendingIntent getAlarmIntent(Context context) {
        Intent alarmIntent = new Intent(context, JobIntentReceiver.class);
        return PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

    }

    public static void clearUpdate(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getAlarmIntent(context));
    }


}
