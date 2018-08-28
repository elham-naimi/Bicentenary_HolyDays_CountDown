package com.elna.bicentenary.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.elna.bicentenary.constant.HolyDaysData;
import com.elna.bicentenary.util.Util;
import com.elna.elhamnaimi.bicentenary.R;

public class BicentenaryActivity extends Activity {
    IntentFilter filter = new IntentFilter();

      Handler handler;
    private static String TAG = BicentenaryActivity.class.getSimpleName();

    TextView title1;
    TextView txtBicentenaryDate;
    TextView txtdaysleft;
    TextView txtCountDown = null;
    // TextView title2;
    TextView title3;
    TextView note;
    TextView txtDemo;
    TextView title4;
    long days;
    BroadcastReceiver uiUpdateReceiver;
    Typeface font_allura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        HolyDaysData.init();
        font_allura = Typeface.createFromAsset(getAssets(), "allura-regular.ttf");
        Typeface harmattan_regular = Typeface.createFromAsset(getAssets(), "harmattan_regular.ttf");
        Typeface montserrat = Typeface.createFromAsset(getAssets(), "montserrat_regular.ttf");
        Typeface sanspro_bold = Typeface.createFromAsset(getAssets(), "sanspro_bold.ttf");

        Typeface sanspro_semibold = Typeface.createFromAsset(getAssets(), "sanspro_semibold.ttf");


        setContentView(R.layout.bicentenary_main_layout);

        filter.addAction("com.hello.action");
        txtCountDown = findViewById(R.id.debugtext);
        title1 = findViewById(R.id.title1);
        txtBicentenaryDate = findViewById(R.id.txtdate);
        txtdaysleft = findViewById(R.id.daysleft);
        title3 = findViewById(R.id.title3);
        title4 = findViewById(R.id.title4);
        note = findViewById(R.id.note);
        txtDemo = findViewById(R.id.demotext);
        title1.setTypeface(montserrat);
        title3.setTypeface(font_allura);
        title4.setTypeface(sanspro_bold);
        txtBicentenaryDate.setTypeface(sanspro_semibold);
        txtdaysleft.setTypeface(sanspro_semibold);
        txtDemo.setTypeface(harmattan_regular);
        note.setTypeface(sanspro_semibold);


        SpannableString text = new SpannableString("Please tap here for a demo.");
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BicentenaryActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        }, 0, text.length(), 0);
        txtDemo.setMovementMethod(LinkMovementMethod.getInstance());

        txtDemo.setText(text);

        int daysleft = Util.getDaysLeftToBicentenary();
        if(daysleft ==0){

            txtCountDown.setText("");
            title3.setText("");
            txtdaysleft.setText("");
            txtCountDown.setText("Happy Bicentenary Celebration");
            txtCountDown.setTypeface(font_allura);
        }
        else{
            if(daysleft == 1)
               txtdaysleft.setText(" day");
            else
                txtdaysleft.setText(" days");
            title3.setText("in");
            txtCountDown.setText("" + daysleft);


        }
         txtBicentenaryDate.setText(Html.fromHtml("Tuesday, 29<sup><small>th</small></sup> Oct 2019"));


        };

    @Override
    protected void onResume() {
        super.onResume();
        int daysleft = Util.getDaysLeftToBicentenary();
        if(daysleft ==0){

            txtCountDown.setText("");
            title3.setText("");
            txtdaysleft.setText("");
            txtCountDown.setText("Happy Bicentenary Celebrations");
            txtCountDown.setTypeface(font_allura);
        }
        else{
            if(daysleft == 1)
                txtdaysleft.setText(" day");
            else
                txtdaysleft.setText(" days");
            title3.setText("in");
            txtCountDown.setText("" + daysleft);
        }
    }
}


