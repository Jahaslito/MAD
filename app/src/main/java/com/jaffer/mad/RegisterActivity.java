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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    TextView goToLogin;
    Button registerBtn;
    TextInputEditText nameEditText;
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    TextInputEditText confirmPasswordEditText;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerBtn = findViewById(R.id.register_button);
        nameEditText = findViewById(R.id.register_name);
        emailEditText = findViewById(R.id.register_email);
        passwordEditText = findViewById(R.id.register_password);
        confirmPasswordEditText = findViewById(R.id.register_confirm);
        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(view -> {
            createUser();
        });
    }

    private void createUser() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(name)){
            nameEditText.setError("Please provide your name");
            nameEditText.requestFocus();
        }else if (TextUtils.isEmpty(email)){
            emailEditText.setError("Please provide an email address");
            emailEditText.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            passwordEditText.setError("Please set a password");
            passwordEditText.requestFocus();
        }else if (TextUtils.isEmpty(confirmPassword)){
            confirmPasswordEditText.setError("Please confirm your password");
            confirmPasswordEditText.requestFocus();
        }else if (!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Passwords do not match!");
            confirmPasswordEditText.requestFocus();
        }else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        UserHelperClass userHelperClass = new UserHelperClass(name, email);
                        FirebaseDatabase.getInstance().getReference("user")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(userHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                }
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(RegisterActivity.this, "Oops! Something went wrong " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void viewLoginClicked(View view){
        goToLogin = findViewById(R.id.goToLogin);
        Intent openLogin = new Intent(this, LoginActivity.class);
        startActivity(openLogin);

    }
}
