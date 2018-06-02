package com.project.geo.finalproject;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    TextView TV1, TV2, TV3, TV4, TV5, TV6, TV7, TV8, TV9, TV10, TV11, TV12, TV13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TV1 = (TextView) findViewById(R.id.textView24);
        TV2 = (TextView) findViewById(R.id.textView25);
        TV3 = (TextView) findViewById(R.id.textView28);
        TV4 = (TextView) findViewById(R.id.textView27);
        TV5 = (TextView) findViewById(R.id.textView30);
        TV6 = (TextView) findViewById(R.id.textView26);
        TV7 = (TextView) findViewById(R.id.textView29);
        TV8 = (TextView) findViewById(R.id.textView31);
        TV9 = (TextView) findViewById(R.id.textView39);
        TV10 = (TextView) findViewById(R.id.textView40);
        TV11 = (TextView) findViewById(R.id.textView41);
        TV12 = (TextView) findViewById(R.id.textView42);
        TV13 = (TextView) findViewById(R.id.textView43);


        GetPath Get = new GetPath();
        if (Get.readFromFile(AboutUs.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(AboutUs.this));
            TV1.setTypeface(face);
            TV2.setTypeface(face);
            TV3.setTypeface(face);
            TV4.setTypeface(face);
            TV5.setTypeface(face);
            TV6.setTypeface(face);
            TV7.setTypeface(face);
            TV8.setTypeface(face);
            TV9.setTypeface(face);
            TV10.setTypeface(face);
            TV11.setTypeface(face);
            TV12.setTypeface(face);
            TV13.setTypeface(face);

        }

        if (Get.readFromFile2(AboutUs.this).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(AboutUs.this));
            TV1.setTextSize(size);
            TV2.setTextSize(size);
            TV3.setTextSize(size);
            TV4.setTextSize(size);
            TV5.setTextSize(size);
            TV6.setTextSize(size);
            TV7.setTextSize(size);
            TV8.setTextSize(size);
            TV9.setTextSize(size);
            TV10.setTextSize(size);
            TV11.setTextSize(size);
            TV12.setTextSize(size);
            TV13.setTextSize(size);
        }
        // nav


    }
}
