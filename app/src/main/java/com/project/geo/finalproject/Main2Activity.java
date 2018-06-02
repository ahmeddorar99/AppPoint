package com.project.geo.finalproject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    private SectionsPageAdapter msectionsPageAdapter;
    private ViewPager mViewPager;
    private String Email, Fname, Lname, Gender, Birthdate, ProfileImage;
    public static FloatingActionButton FAB;
    public static TabLayout tabLayout;
    public Double aLong, lati;
    private GpsTracker gpsTracker;
    GetPath Get;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
           Get = new GetPath();

        Email = getIntent().getStringExtra("Username");
        Fname = getIntent().getStringExtra("Fname");
        Lname = getIntent().getStringExtra("Lname");
        Gender = getIntent().getStringExtra("Gender");
        ProfileImage = getIntent().getStringExtra("ProfileImage");
        Birthdate = getIntent().getStringExtra("Birthdte");
        Log.d(TAG, "onCreate:Starting.");

        msectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

        if (Get.readFromFile(Main2Activity.this).isEmpty() == false) {
            changeTabsFont();
        }


        FAB = findViewById(R.id.fab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                int selectedTabPosition = tabLayout.getSelectedTabPosition();

                if (selectedTabPosition == 0) {
                    //first tab selected
                    FAB.setVisibility(View.VISIBLE);

                    gpsTracker = new GpsTracker(Main2Activity.this);
                    if (gpsTracker.canGetLocation()) {
                        lati = gpsTracker.getLatitude();
                        aLong = gpsTracker.getLongitude();

                    } else {
                        gpsTracker.showSettingsAlert();
                    }


                } else if (selectedTabPosition == 1) {

                    FAB.setVisibility(View.GONE);
                } else if (selectedTabPosition == 2) {

                    FAB.setVisibility(View.GONE);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Main2Activity.this,
                        PostActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("Username", Email);
                intent2.putExtra("Fname", Fname);
                intent2.putExtra("Lname", Lname);
                intent2.putExtra("Gender", Gender);
                intent2.putExtra("ProfileImage", ProfileImage);
                intent2.putExtra("Birthdte", Birthdate);
                startActivity(intent2);
            }
        });


    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("Username", Email);
        bundle.putString("Fname", Fname);
        bundle.putString("Lname", Lname);
        bundle.putString("Gender", Gender);
        bundle.putString("ProfileImage", ProfileImage);
        bundle.putString("Birthdte", Birthdate);

        tab1 t1 = new tab1();
        t1.setArguments(bundle);
        adapter.AddFragment(t1, "Main");
        adapter.AddFragment(new tab2(), "Notification");
        tab3 t3 = new tab3();
        t3.setArguments(bundle);
        adapter.AddFragment(t3, "Profile");

        viewPager.setAdapter(adapter);

    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    AssetManager mgr = Main2Activity.this.getAssets();
                    Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(Main2Activity.this));
                    ((TextView) tabViewChild).setTypeface(face);
                }
            }
        }
    }


}
