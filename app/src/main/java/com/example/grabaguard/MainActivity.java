package com.example.grabaguard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewPager mySlideViewpager;
    LinearLayout myDotLayout;
    Button backbtn, nextbtn, skipbtn;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;


    private boolean keep = true;
    private final int DELAY = 1250;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        String FirstTime = preferences.getString("FirstTimeInstall", "");
        if(FirstTime.equals("Yes")){
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
            finish();
        }
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
        backbtn = (Button) findViewById(R.id.backbtn);
        nextbtn = (Button) findViewById(R.id.nextbtn);
        skipbtn = (Button) findViewById(R.id.skipButton);
        splashScreen.setKeepOnScreenCondition(() -> keep);
        Handler handler = new Handler();
        handler.postDelayed(()-> keep = false, DELAY);

        backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (getitem(0) > 0){

                    mySlideViewpager.setCurrentItem(getitem(-1),true);

                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (getitem(0) < 3)
                    mySlideViewpager.setCurrentItem(getitem(1),true);
                else {

                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(i);
                    finish();

                }
            }
        });
        skipbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
                finish();

            }
        });

        mySlideViewpager = (ViewPager) findViewById(R.id.slideViewPager);
        myDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mySlideViewpager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mySlideViewpager.addOnPageChangeListener(viewListener);

    }

    public void setUpindicator(int position){

        dots = new TextView[4];
        myDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));

            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            myDotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0){

                backbtn.setVisibility(View.VISIBLE);

            }else {

                backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mySlideViewpager.getCurrentItem() + i;
    }
}