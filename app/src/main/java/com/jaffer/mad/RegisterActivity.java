package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView goToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void viewLoginClicked(View view){
        goToLogin = findViewById(R.id.goToLogin);
        Intent openLogin = new Intent(this, LoginActivity.class);
        startActivity(openLogin);

    }
}
