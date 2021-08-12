package com.jaffer.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class NewReminderActivity extends AppCompatActivity {

    private TextInputLayout mTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        mTime = findViewById(R.id.time);
        selectTime();
    }

    private void selectTime() {
        final int[] hour = new int[1];
        int minute = 0;
        mTime.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.isFocused()){
                    TimePickerDialog timePickerDialog = new TimePickerDialog(NewReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hour[0] = hourOfDay;
                            minute = minute;

                            Calendar calendar1 = Calendar.getInstance();
                            //setting hour and minute
                            calendar1.set(0,0,0, hour[0],minute);
                            mTime.getEditText().setText(DateFormat.format("hh:mm aa",calendar1));
                        }
                    },12,0,false);
                    //Show the previous selected time
                    timePickerDialog.updateTime(hour[0],minute);
                    timePickerDialog.show();
                }
            }
        });
    }
}