package com.elna.bicentenary.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;

import com.elna.bicentenary.receivers.WidgetService;
import com.elna.elhamnaimi.bicentenary.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationManager {

    private static String TAG = NotificationManager.class.getSimpleName();
    public static void sendNotification(Context context, String holyday) {
         Intent intent = new Intent(context,HolyDayListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context.getApplicationContext());

            boolean isLollipop = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        int smallIcon = context.getResources().getIdentifier("notification_small_icon", "drawable", context.getPackageName());

        if (smallIcon <= 0 || !isLollipop) {
            smallIcon = context.getApplicationInfo().icon;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"1");
        builder.addAction(smallIcon,holyday,pendingIntent);
        builder.setContentTitle(holyday+"Tomorrow!")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)

                .setContentIntent(pendingIntent);


        notificationManager.notify(1, builder.build());
    }
}
