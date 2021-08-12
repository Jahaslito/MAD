package com.jaffer.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button logoutBtn;
    private TextView hello;
    private TextView userEmail;
    private FirebaseAuth mAuth;
    private String userId;
    FirebaseUser user;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutBtn = findViewById(R.id.logoutButton);
        hello = findViewById(R.id.Hello);
        userEmail = findViewById(R.id.user_email);
        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
//        if (user != null) {
//            userId = user.getUid();
//            dbReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    UserHelperClass userHelperClass = snapshot.getValue(UserHelperClass.class);
//
//                    if (userHelperClass != null) {
//                        String name = userHelperClass.getName();
//                        String email = user.getEmail();
//                        hello.setText(name);
//                        userEmail.setText(email);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(MainActivity.this,"Something Went Wrong. Try Again",Toast.LENGTH_LONG).show();
//                }
//            });
//
//        }
        logoutBtn.setOnClickListener(View -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
        user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else {

            String name = user.getDisplayName();
            String email = user.getEmail();

            hello.setText(name);
            userEmail.setText(email);
        }


    }
}