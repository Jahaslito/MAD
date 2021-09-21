package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DailyActivity extends AppCompatActivity {

    private CheckBox mActivity1,mActivity2,mActivity3,mActivity4;
    private TextInputLayout mMemo;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Daily Activity");
        mActivity1 = findViewById(R.id.daily_activity1);
        mActivity2 = findViewById(R.id.daily_activity2);
        mActivity3 = findViewById(R.id.daily_activity3);
        mActivity4 = findViewById(R.id.daily_activity4);
        mMemo = findViewById(R.id.memo);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

    }
    private boolean validateMemo(){
        String routes = mMemo.getEditText().getText().toString().trim();
        if(routes.isEmpty()){
            mMemo.setError("Field is empty");
            return false;
        }
        else {
            mMemo.setError(null);
            return true;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void saveMemo(View view) {
        if (!validateMemo()) {
            return;
        }
    }
}