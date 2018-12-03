package com.elna.holyday.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.elna.holyday.constant.HolyDaysData;
import com.elna.holyday.util.Util;
import com.elna.holyday.R;

public class BicentenaryActivity extends Activity {
    IntentFilter filter = new IntentFilter();

      Handler handler;
    private static String TAG = BicentenaryActivity.class.getSimpleName();

    TextView title1;
    TextView txtDate1;
    TextView txtDate2;
    TextView txtDate3;
    TextView txtDate4;

    TextView txtdaysleft;
    TextView txtCountDown = null;
    TextView title3;
    TextView title4;
    long days;
    BroadcastReceiver uiUpdateReceiver;
    Typeface font_allura;
    HolyDaysData holyDaysData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        font_allura = Typeface.createFromAsset(getAssets(), "allura_regular.ttf");
        Typeface montserrat = Typeface.createFromAsset(getAssets(), "montserrat_regular.ttf");
        Typeface sanspro_bold = Typeface.createFromAsset(getAssets(), "sanspro_bold.ttf");
        Typeface sanspro_semibold = Typeface.createFromAsset(getAssets(), "sanspro_semibold.ttf");
        setContentView(R.layout.bicentenary_main_layout);

        filter.addAction("com.hello.action");
        txtCountDown = findViewById(R.id.debugtext);
        title1 = findViewById(R.id.title1);
        txtDate1 = findViewById(R.id.txtdate1);
        txtDate2 = findViewById(R.id.txtdate2);
        txtDate3 =  findViewById(R.id.txtdate3);
        txtDate4 = findViewById(R.id.txtdate4);
        title3 = findViewById(R.id.title3);
        title4 = findViewById(R.id.title4);

        title1.setTypeface(montserrat);
        title3.setTypeface(sanspro_semibold);
        title4.setTypeface(sanspro_bold);
        txtDate2.setTypeface(sanspro_semibold);
        txtDate1.setTypeface(sanspro_semibold);
        txtDate3.setTypeface(sanspro_semibold);
        txtDate4.setTypeface(sanspro_semibold);


        SpannableString text = new SpannableString("Please tap here for a demo.");
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BicentenaryActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        }, 0, text.length(), 0);

        int daysleft = Util.getDaysLeftToBicentenary();
        if(daysleft ==0){

            txtCountDown.setText("");
            title3.setText("");
            txtCountDown.setText("Happy Bicentenary Celebration");
            txtCountDown.setTypeface(font_allura);
        }
        else{

            title3.setText("days left");
            txtCountDown.setText("" + daysleft);


        }

        txtDate1.setText("Begins at sunset, on");
        txtDate1.setTypeface(font_allura);
        txtDate2.setText("Monday, 28 October 2019");

        };

    @Override
    protected void onResume() {
        super.onResume();
        int daysleft = Util.getDaysLeftToBicentenary();
        txtCountDown.setText("" + daysleft);
        }
    }



