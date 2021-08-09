package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView goToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void viewRegisterClicked(View view) {
        goToRegister = findViewById(R.id.goToRegister);
        Intent openRegister = new Intent(this, RegisterActivity.class);
        startActivity(openRegister);
    }

    public void viewForgotPassword(View view){
        Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show();
    }
}