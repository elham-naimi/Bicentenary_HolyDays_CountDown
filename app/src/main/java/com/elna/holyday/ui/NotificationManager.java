package com.elna.holyday.ui;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.elna.holyday.model.HolyDay;
import com.elna.holyday.util.Util;
import com.elna.holyday.R;

import org.threeten.bp.LocalDateTime;

public class NotificationManager {




    private static String TAG = NotificationManager.class.getSimpleName();
    public static void sendNotification(Context context, HolyDay holyday) {


        Log.i(TAG,"sendNotification");
        createNotificationChannel(context);

          LocalDateTime date = holyday.date.minusDays(1);
         Intent intent = new Intent(context,HolyDayListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context.getApplicationContext());

            boolean isLollipop = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        int smallIcon = context.getResources().getIdentifier("notification_small_icon", "drawable", context.getPackageName());



        if (smallIcon <= 0 || !isLollipop) {
            smallIcon = context.getApplicationInfo().icon;
        }



        String message = "Begins evening of "+ Util.formatCase(date.getDayOfWeek().toString())+
                ", "+Util.formatCase(date.getMonth().toString())+" "+(date.getDayOfMonth());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"1");

        builder.addAction(smallIcon,holyday.desc,pendingIntent);
        builder.setContentTitle(holyday.desc)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, builder.build());
        Log.i(TAG,"createNotificationChannel check 3");


    }

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Log.i(TAG,"createNotificationChannel check 1");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG,"createNotificationChannel check 2");

            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
