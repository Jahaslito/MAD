package com.jaffer.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Toolbar toolbar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        toolbar = findViewById(R.id.tool_bar_bookmark);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bookmarks");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference("Bookmarks").child(currentUser.getUid());


        articleArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bookmarkAdapter = new BookmarkAdapter(articleArrayList, getApplicationContext());
        recyclerView.setAdapter(bookmarkAdapter);

        initializeData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initializeData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                articleArrayList.clear();
                Iterator<DataSnapshot> bookmarks = snapshot.getChildren().iterator();
                while (bookmarks.hasNext()){
                    DataSnapshot bookmark = bookmarks.next();
                    articleArrayList.add(bookmark.getValue(Article.class));

                }
                bookmarkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}