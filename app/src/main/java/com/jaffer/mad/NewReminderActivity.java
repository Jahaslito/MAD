package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class NewReminderActivity extends AppCompatActivity {

    private TextInputLayout mTime;
    private TextInputLayout mMessage;
    private TextInputLayout mTitle;
    private Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        mTime = findViewById(R.id.time);
        mMessage= findViewById(R.id.message);
        mTitle= findViewById(R.id.title);
        selectTime();
    }

    private void selectTime() {
        final int[] hour = new int[1];
        int mMinute=0;
        mTime.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.isFocused()){
                    TimePickerDialog timePickerDialog = new TimePickerDialog(NewReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mCalendar= Calendar.getInstance();
                            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            mCalendar.set(Calendar.MINUTE, minute);
                            mCalendar.set(Calendar.SECOND, 0);
                            mTime.getEditText().setText(DateFormat.format("hh:mm aa",mCalendar));

//                            mMinute= minute;
                        }
                    },12,0,false);
                    //Show the previous selected time
                    timePickerDialog.updateTime(hour[0],mMinute);
                    timePickerDialog.show();
                }
            }
        });
    }



    public void setAlarm(View view) {
        String time=  mTime.getEditText().getText().toString();
        String message=  mMessage.getEditText().getText().toString();
        String title= mTitle.getEditText().getText().toString();

        int requestCode=-1;
        DatabaseHelper db= new DatabaseHelper(this);
        Cursor data= db.getListContents();
        if (data.getCount()==0){
            requestCode=1;
        }else{
            data= db.getLastRow();
            while(data.moveToNext()){
              requestCode= data.getInt(0);
            }
        }
        startAlarm(requestCode,message,title);

        boolean result= db.addData(time,message,title);
        if(result){
            Intent intent = new Intent(this, RemindersActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Notification Added Successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public void startAlarm(int requestCode, String message, String title){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("message",message);
        intent.putExtra("title",title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
       // Log.d("message", String.valueOf(mCalendar.getTimeInMillis()));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(View view) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

}