package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;

public class ViewDailyActivity extends AppCompatActivity {

    private TextView mViewDate,mActivity_1,mActivity_2,mActivity_3,mActivity_4,mActivity_5;

    private DonutProgress donutProgress;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mViewDate = findViewById(R.id.view_daily_activity_date);
        mActivity_1 = findViewById(R.id.view_activity_1);
        mActivity_2 = findViewById(R.id.view_activity_2);
        mActivity_3 = findViewById(R.id.view_activity_3);
        mActivity_4 = findViewById(R.id.view_activity_4);
        mActivity_5 = findViewById(R.id.view_activity_5);


        donutProgress = findViewById(R.id.percentage_completion);

        progress = Integer.parseInt(getIntent().getStringExtra("Progress"));
        mViewDate.setText(getIntent().getStringExtra("Date"));
        mActivity_1.setText(getIntent().getStringExtra("Activity1"));
        mActivity_2.setText(getIntent().getStringExtra("Activity2"));
        mActivity_3.setText(getIntent().getStringExtra("Activity3"));
        mActivity_4.setText(getIntent().getStringExtra("Activity4"));
        mActivity_5.setText(getIntent().getStringExtra("Activity5"));

        updateProgressBar();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void updateProgressBar() {
        donutProgress.setProgress(progress);
    }
}