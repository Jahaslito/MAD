package com.jaffer.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class BookmarkActivity extends AppCompatActivity {

    private ArrayList<Article> articleArrayList;
    private RecyclerView recyclerView;
    private BookmarkAdapter bookmarkAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        articleArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bookmarkAdapter = new BookmarkAdapter(articleArrayList, getApplicationContext());
        recyclerView.setAdapter(bookmarkAdapter);

        initializeData();
    }

    private void initializeData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                articleArrayList.clear();
                Iterator<DataSnapshot> bookmarks = snapshot.getChildren().iterator();
                while (bookmarks.hasNext()){
                    DataSnapshot bookmark = bookmarks.next();
                    //Log.d("Routes", route.toString());
//                    if(route.child("availability").getValue().equals("active") &&
//                            route.child("status").getValue().equals("enabled")){
//                        routesList.add(route.child("routes").getValue().toString());
//                    }

                }
                bookmarkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}