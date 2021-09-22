package com.jaffer.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class RemindersActivity extends AppCompatActivity {
    private RecyclerView rvReminders;
    private ArrayList<Reminder> reminderData;
    private ReminderAdapter reminderAdapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        // Lookup the recyclerview in activity layout
        rvReminders = (RecyclerView) findViewById(R.id.recycler_reminders);
        rvReminders.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.tool_bar_reminders);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reminders");

        reminderData = new ArrayList<>();
        reminderAdapter = new ReminderAdapter(reminderData, this);
        rvReminders.setAdapter(reminderAdapter);
        InitializeData();
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = viewHolder.getAdapterPosition();
                Collections.swap(reminderData, from, to);
                reminderAdapter.notifyItemMoved(from, to);

                return true;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                reminderData.remove(viewHolder.getAdapterPosition());
                reminderAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(rvReminders);
    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    private void InitializeData() {
        reminderData.clear();
        DatabaseHelper db= new DatabaseHelper(this);
        Cursor data = db.getListContents();
        if (data.getCount()==0){
            Toast.makeText(this, "No Reminder yet!", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                Reminder mReminder = new Reminder(
                        data.getInt(0),
                        data.getString(3),
                        data.getString(2),
                        data.getString(1)
                );
                reminderData.add(mReminder);
            }
        }
        reminderAdapter.notifyDataSetChanged();
    }

    public void addReminder(View view) {
        Intent mIntent= new Intent(this, NewReminderActivity.class);
        startActivity(mIntent);
    }
}