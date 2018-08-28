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

public class MainActivity extends Activity {


    TextView title1;
    TextView note;
    TextView txtDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Typeface harmattan_regular = Typeface.createFromAsset(getAssets(), "harmattan_regular.ttf");
        Typeface montserrat = Typeface.createFromAsset(getAssets(), "montserrat_regular.ttf");
        Typeface sanspro_bold = Typeface.createFromAsset(getAssets(), "sanspro_bold.ttf");

        Typeface sanspro_semibold = Typeface.createFromAsset(getAssets(), "sanspro_semibold.ttf");

      title1 = findViewById(R.id.title1);
        note = findViewById(R.id.note);
        txtDemo = findViewById(R.id.demotext);
        title1.setTypeface(montserrat);
        txtDemo.setTypeface(harmattan_regular);
        note.setTypeface(sanspro_semibold);


        SpannableString text = new SpannableString("Please tap here for a demo.");
        text.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        }, 0, text.length(), 0);
        txtDemo.setMovementMethod(LinkMovementMethod.getInstance());

        txtDemo.setText(text);



    };

}


