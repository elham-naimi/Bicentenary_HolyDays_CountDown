package com.elna.holyday.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.elna.holyday.constant.HolyDaysData;
import com.elna.holyday.model.HolyDay;
import com.elna.holyday.util.Util;
import com.elna.holyday.R;

import java.util.ArrayList;


public class HolyDayListActivity extends Activity {



    Typeface montserrat;
    Typeface harmattan_regular;
    Typeface sanspro_bold;

    TextView txtDemo;
    TextView note;
    ListView listview;
    View demoView;
    int number = 11;
    private static  String TAG = HolyDayListActivity.class.getSimpleName();
    HolyDaysData holyDaysData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG,"OnCreate()");
        setContentView(R.layout.holidaylist);
        listview = (ListView) findViewById(R.id.listview);
        montserrat = Typeface.createFromAsset(getAssets(), "montserrat_regular.ttf");
        sanspro_bold = Typeface.createFromAsset(getAssets(), "sanspro_bold.ttf");
        harmattan_regular = Typeface.createFromAsset(getAssets(), "harmattan_regular.ttf");

        Typeface sanspro_semibold = Typeface.createFromAsset(getAssets(), "sanspro_semibold.ttf");

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.holidaylist_header, listview, false);
        ((TextView) header.findViewById(R.id.listheadertitle)).setTypeface(sanspro_semibold);
        listview.addHeaderView(header, null, false);
        listview.setAdapter(new HolyDayListAdapter(this, Util.getUpcomingHolyDays(number)));

        listview.setBackgroundColor(getResources().getColor(R.color.appbgcolor));
         TextView demoTextView = ((TextView) findViewById(R.id.demotext));

        SpannableString text = new SpannableString("Please tap here for a demo.");
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HolyDayListActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        }, 0, text.length(), 0);
        demoTextView.setMovementMethod(LinkMovementMethod.getInstance());
        demoTextView.setTypeface(harmattan_regular);
        demoTextView.setText(text);
     }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume()");
        listview.setAdapter(new HolyDayListAdapter(this, Util.getUpcomingHolyDays(number)));



    }

    public class HolyDayListAdapter extends ArrayAdapter<HolyDay> {
        public HolyDayListAdapter(Context context, ArrayList<HolyDay> users) {
            super(context, 0, users);

        }

        int textcolor;
        int defaultcolor = 1979711488;
         @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position


            HolyDay holyDay = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.holydays_list_item, parent, false);
            }


            // Lookup view for data population
            TextView text_feast_name = (TextView) convertView.findViewById(R.id.text_feast_name);
            text_feast_name.setTextColor(text_feast_name.getCurrentTextColor());
            TextView text_month_date = (TextView) convertView.findViewById(R.id.text_feast_month_date);
            TextView text_count = (TextView) convertView.findViewById(R.id.imgcount);
            text_count.setTypeface(montserrat);
            text_feast_name.setTypeface(montserrat);
            text_month_date.setTypeface(harmattan_regular);
            textcolor = text_feast_name.getCurrentTextColor();

            // resting to default color
            text_feast_name.setTextColor(defaultcolor);
            text_month_date.setTextColor(defaultcolor);
            text_count.setTextColor(defaultcolor);

            if (holyDay.desc.contains("&")) {
                text_feast_name.setText(holyDay.desc.substring(0, holyDay.desc.indexOf("&")));
                text_month_date.setText(Util.getWhenString(holyDay)
                        + "\nat " + holyDay.desc.substring(holyDay.desc.indexOf("&") + 1, holyDay.desc.length()));

            } else {
                text_feast_name.setText(holyDay.desc);
                text_month_date.setText(Util.getWhenString(holyDay));

            }

            if (holyDay.desc.contains("Bicentenary")) {
                text_feast_name.setTextColor(getResources().getColor(R.color.highlightcolor));
                text_month_date.setTextColor(getResources().getColor(R.color.highlightcolor));
            }
            long count = Util.getDaysLeft(holyDay.date.minusDays(1));
            text_count.setText("" + count);


            if (count < 2)
                text_count.setTextColor(Color.RED);

            return convertView;
        }
    }
}

