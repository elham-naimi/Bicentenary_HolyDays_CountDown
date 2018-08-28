package com.elna.bicentenary.receivers;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.elna.bicentenary.constant.HolyDaysData;
import com.elna.bicentenary.model.HolyDay;
import com.elna.bicentenary.services.UpdateService;
import com.elna.bicentenary.ui.NotificationManager;
import com.elna.bicentenary.util.Util;
import com.elna.elhamnaimi.bicentenary.R;

import java.util.ArrayList;

public class WidgetListAdapter implements RemoteViewsService.RemoteViewsFactory {
   Context context;
   private static String TAG = WidgetListAdapter.class.getSimpleName();
   ArrayList<HolyDay> holyDays = new ArrayList<>();
    public WidgetListAdapter(Context context){
        this.context = context;
        Log.i(TAG,"Constructor");
    }
    @Override
    public void onCreate() {

        HolyDaysData.init();
        holyDays = Util.getUpcomingHolyDays(2);
        Log.i(TAG,"Created holydays");

    }

    @Override
    public void onDataSetChanged() {
        if(holyDays.isEmpty())
            HolyDaysData.init();
        holyDays = Util.getUpcomingHolyDays(2);

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
        {
            // Get the data item for this position


            HolyDay holyDay = holyDays.get(position);
            RemoteViews  view = new RemoteViews(context.getPackageName(), R.layout.widget_holyday_list_item);
            view.setTextViewText(R.id.imgholydayname,holyDay.desc);
            view.setTextViewText(R.id.imgdaysleft,"days left");
            view.setTextViewText(R.id.imgwhen,Util.getWhenString(holyDay));
            if(holyDay.desc.contains("&")){
                view.setTextViewText(R.id.imgholydayname,holyDay.desc.substring(0,holyDay.desc.indexOf("&")));
                view.setTextViewText(R.id.imgwhen, Util.getWhenString(holyDay)+" "+holyDay.date.getYear()
                        + "\nat " + holyDay.desc.substring(holyDay.desc.indexOf("&")+1, holyDay.desc.length()));

            }
            else{
                view.setTextViewText(R.id.imgholydayname,holyDay.desc);
                view.setTextViewText(R.id.imgwhen,Util.getWhenString(holyDay)+" "+holyDay.date.getYear());

            }


            long count =  Util.getDaysLeft(holyDay.date.minusDays(1));
            view.setTextViewText(R.id.imgcount,""+count);
            view.setTextColor(R.id.imgdaysleft,context.getResources().getColor(R.color.appbgcolor));
            view.setTextColor(R.id.imgwhen,context.getResources().getColor(R.color.gray));
            view.setTextColor(R.id.imgholydayname,context.getResources().getColor(R.color.black));
            view.setTextColor(R.id.imgcount,context.getResources().getColor(R.color.appbgcolor));
            if(count == 1 ) {
                NotificationManager.sendNotification(context,holyDay.desc);
                view.setTextColor(R.id.imgcount,context.getResources().getColor(R.color.red));

            }

            return view;



        }
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
