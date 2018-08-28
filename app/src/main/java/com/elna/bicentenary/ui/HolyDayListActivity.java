package com.elna.bicentenary.ui;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.elna.bicentenary.constant.HolyDaysData;
import com.elna.bicentenary.model.HolyDay;
import com.elna.bicentenary.util.Util;
import com.elna.elhamnaimi.bicentenary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HolyDayListActivity extends ListActivity {

    ArrayList<HolyDay> currentYearHolyDays;
    ArrayList<HolyDay> nextYearHolyDays;
    Typeface montserrat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HolyDaysData.init();
        montserrat = Typeface.createFromAsset(getAssets(), "montserrat_regular.ttf");

        setListAdapter(new HolyDayListAdapter(this,Util.getUpcomingHolyDays(12)));
        getListView().setBackgroundColor(getResources().getColor(R.color.appbgcolor));
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.holidaylist_header, getListView(), false);
        ((TextView) header.findViewById(R.id.listheadertitle)).setTypeface(montserrat);
        getListView().addHeaderView(header, null, false);
    }



    @Override
    protected void onResume() {
        super.onResume();

       // HolyDaysData.init();
        //ArrayList<HolyDay> holyDayArrayList = getUpcomingHolyDays();
        setListAdapter(new HolyDayListAdapter(this,Util.getUpcomingHolyDays(12)));

    }

    public class HolyDayListAdapter extends ArrayAdapter<HolyDay> {
        public HolyDayListAdapter(Context context, ArrayList<HolyDay> users) {
            super(context, 0, users);

        }


        int textcolor;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position


            HolyDay holyDay = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.holydays_list_item, parent, false);
            }



           

            //View view = super.getView(position, convertView, parent);
           /* if (position % 2 == 1) {
                convertView.setBackgroundColor(Color.BLUE);
            } else {
                convertView.setBackgroundColor(Color.CYAN);
            }*/

            // Lookup view for data population
            TextView text_feast_name = (TextView) convertView.findViewById(R.id.text_feast_name);

            text_feast_name.setTextColor(text_feast_name.getCurrentTextColor());
            TextView text_month_date = (TextView) convertView.findViewById(R.id.text_feast_month_date);

            TextView text_count = (TextView) convertView.findViewById(R.id.imgcount);
            text_count.setTypeface(montserrat);
            text_feast_name.setTypeface(montserrat);
            text_month_date.setTypeface(montserrat);
            textcolor = text_feast_name.getCurrentTextColor();
            Log.i(TAG,"Textcolor"+textcolor);



            text_feast_name.setTextColor(1979711488);
            text_month_date.setTextColor(1979711488);
            text_count.setTextColor(1979711488);

            if(holyDay.desc.contains("&")){
                text_feast_name.setText(holyDay.desc.substring(0,holyDay.desc.indexOf("&")));
                text_month_date.setText ( Util.getWhenString(holyDay)+" "+holyDay.date.getYear()
                        + "\nat " + holyDay.desc.substring(holyDay.desc.indexOf("&")+1, holyDay.desc.length()));

            }
            else{
                text_feast_name.setText(holyDay.desc);
                text_month_date.setText(Util.getWhenString(holyDay)+" "+holyDay.date.getYear());

            }

            if(holyDay.desc.contains("Bicentenary")){
                text_feast_name.setTextColor(getResources().getColor(R.color.highlightcolor));
                text_month_date.setTextColor(getResources().getColor(R.color.highlightcolor));
            }
            long count =  Util.getDaysLeft(holyDay.date.minusDays(1));
            text_count.setText(""+count);

            if(count <2)
                text_count.setTextColor(Color.RED);

            return convertView;
        }
    }
}

