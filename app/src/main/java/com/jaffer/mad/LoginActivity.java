package com.jaffer.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
   private TextView goToRegister;
   private TextInputEditText emailEditText;
   private TextInputEditText passwordEditText;
   private Button loginBtn;
   private FirebaseAuth mAuth;
   private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.LoginButton);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        loginBtn.setOnClickListener(view -> {
            loginUser();
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailEditText.setError("Please provide an email address");
            emailEditText.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            passwordEditText.setError("Please provide a password");
            passwordEditText.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Oops! Something went wrong " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void viewRegisterClicked(View view) {
        goToRegister = findViewById(R.id.goToRegister);
        Intent openRegister = new Intent(this, RegisterActivity.class);
        startActivity(openRegister);
    }

    public void viewForgotPassword(View view){
        Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show();
    }
    public void onStart(){
        super.onStart();
        if(mCurrentUser!= null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
}