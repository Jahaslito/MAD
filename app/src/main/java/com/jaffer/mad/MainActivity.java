package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //Inflate the toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Create instance of the tab layout
        TabLayout tabLayout= findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.mental_health));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.addiction_recovery));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.previous_activities));
        //set the tab to fill the layout
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(View ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
        //Use the pager adapter to manage screens
        //create an instance of the view pager
        final ViewPager viewPager = findViewById(R.id.view_pager);
        //create an instance of the PagerAdapter
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        //set the adapter to the viewpager
        viewPager.setAdapter(pagerAdapter);
        //set listener for Clicks
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }
    public void test(View view){
        Intent mIntent= new Intent(this, NewReminderActivity.class);
        startActivity(mIntent);
    }
    public void test2(View view){
        Intent mIntent= new Intent(this, RemindersActivity.class);
        startActivity(mIntent);
    }
}