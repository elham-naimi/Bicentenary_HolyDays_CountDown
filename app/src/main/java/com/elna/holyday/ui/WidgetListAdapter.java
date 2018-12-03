package com.elna.holyday.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.elna.holyday.constant.HolyDaysData;
import com.elna.holyday.model.HolyDay;
import com.elna.holyday.util.Util;
import com.elna.holyday.R;

import java.util.ArrayList;

public class WidgetListAdapter implements RemoteViewsService.RemoteViewsFactory {
   Context context;
   private static String TAG = WidgetListAdapter.class.getSimpleName();
   ArrayList<HolyDay> holyDays = new ArrayList<>();
    public WidgetListAdapter(Context context){
        this.context = context;
    }
    HolyDaysData holyDaysData;
    @Override
    public void onCreate() {
         holyDays = Util.getUpcomingHolyDays(3);
    }

    @Override
    public void onDataSetChanged() {

        holyDaysData = new HolyDaysData();
        holyDays = Util.getUpcomingHolyDays(3);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return holyDays.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {


        Log.i(TAG,"getViewAt WidgetListAdapter position "+position);

            HolyDay holyDay = holyDays.get(position);


            RemoteViews  view = new RemoteViews(context.getPackageName(), R.layout.widget_holyday_list_item);
            view.setTextViewText(R.id.imgdaysleft,"days left");
            if(holyDay.desc.contains("&")){
                view.setTextViewText(R.id.imgwhen,
                         Util.getWhenString(holyDay) + "\nat "
                                + holyDay.desc.substring(holyDay.desc.indexOf("&")+1, holyDay.desc.length()));
                view.setTextViewText
                        (R.id.imgholydayname,
                               holyDay.desc.substring(0,holyDay.desc.indexOf("&")));
            }
            else{
                view.setTextViewText(R.id.imgwhen,Util.getWhenString(holyDay));
                view.setTextViewText(R.id.imgholydayname, holyDay.desc);
            }
            long count =  Util.getDaysLeft(holyDay.date.minusDays(1));
            view.setTextViewText(R.id.imgcount,""+count);
            view.setTextColor(R.id.imgdaysleft,context.getResources().getColor(R.color.gray));
            view.setTextColor(R.id.imgwhen,context.getResources().getColor(R.color.gray));
            view.setTextColor(R.id.imgholydayname,context.getResources().getColor(R.color.colorPrimary));
            view.setTextColor(R.id.imgcount,context.getResources().getColor(R.color.colorPrimary));

            if(count <2 ) {
                view.setTextColor(R.id.imgcount,context.getResources().getColor(R.color.red));
            }
        Intent intent = new Intent(context, HolyDayListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        view.setPendingIntentTemplate(R.id.list, pendingIntent);
        view.setOnClickFillInIntent(R.id.holyday_widget_layout, intent);
        Log.i(TAG,"Count "+count);
       /* if (count==1) {
            NotificationManager.sendNotification(context, holyDay);
            Log.i(TAG,"calling notification");
        }*/
        return view;

        }



    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
