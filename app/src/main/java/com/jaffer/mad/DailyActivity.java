package com.jaffer.mad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DailyActivity extends AppCompatActivity {

    private CheckBox mActivity1,mActivity2,mActivity3,mActivity4;
    private TextInputLayout mMemo;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference activitiesDatabaseReference;
    private String dailyActivityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Daily Activity");
        mActivity1 = findViewById(R.id.daily_activity1);
        mActivity2 = findViewById(R.id.daily_activity2);
        mActivity3 = findViewById(R.id.daily_activity3);
        mActivity4 = findViewById(R.id.daily_activity4);
        mMemo = findViewById(R.id.memo);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PreviousActivities");
        Log.d("data: ",databaseReference.toString());
        checkDailyActivityExists();
    }
    private boolean validateMemo(){
        String routes = mMemo.getEditText().getText().toString().trim();
        if(routes.isEmpty()){
            mMemo.setError("Field is empty");
            return false;
        }
        else {
            mMemo.setError(null);
            return true;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void saveMemo(View view) {
        if (!validateMemo()) {
            return;
        }
        databaseReference = firebaseDatabase.getReference("PreviousActivities");
        Log.d("hey ",mMemo.getEditText().getText().toString());
        databaseReference.child(dailyActivityId).child("activity5").setValue(mMemo.getEditText().getText().toString());
    }
    public boolean checkDailyActivityExists (){
        boolean returnValue;
    databaseReference.addValueEventListener(new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override

        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            PrevActivity prevActivity = snapshot.getValue(PrevActivity.class);
            boolean executed= false;
            LocalDateTime now = LocalDateTime.now();
           String today= now.getDayOfWeek() + ", " + now.getDayOfMonth() + " " + now.getMonth()+ " " + now.getYear();
            Iterator<DataSnapshot> a = snapshot.getChildren().iterator();
            while (a.hasNext()){
                DataSnapshot Previous = a.next();
                Log.d("CurrentId: ",mCurrentUser.getUid());
                Log.d("SavedId: ",Previous.child("userID").getValue().toString());
                Log.d("Middle: ",String.valueOf(Previous.child("userID").getValue().equals(mCurrentUser.getUid())));
                if(Previous.child("userID").getValue().equals(mCurrentUser.getUid())){
                    if (Previous.child("date").getValue().toString().equals(today)){
                        PrevActivity prevActivity = Previous.getValue(PrevActivity.class);
                        executed=true;
                        Log.d("Call 1A: ","Call 1A");
                        dailyActivityId= Previous.getKey();
                        populateDailyActivity(prevActivity);
                        Log.d("Call 1B: ","Call 1B");
                        break;
                    }
                }
            }
            if (!executed){
                Log.d("Call 3A: ","Call 3A");
                createDailyActivity();
                Toast.makeText(getApplicationContext(),"New User",Toast.LENGTH_SHORT);
                Log.d("Call 3B: ","Call 3B");
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
        return true;
    }

    private void populateDailyActivity(PrevActivity prevActivity) {
        Intent mIntent= new Intent(this,DailyActivity.class);
        mActivity1.setText(prevActivity.getActivity1());
        mActivity2.setText(prevActivity.getActivity2());
        mActivity3.setText(prevActivity.getActivity3());
        mActivity4.setText(prevActivity.getActivity4());
        List<String> completedActivities= new ArrayList<String>();
        completedActivities= prevActivity.getCompletedActivities();
        for (String activity:completedActivities){
            if (activity.equals("Activity1")){
                mActivity1.setChecked(true);
            }else if (activity.equals("Activity2")){
                mActivity2.setChecked(true);
            }else if (activity.equals("Activity3")){
                mActivity3.setChecked(true);
            }else if (activity.equals("Activity4")){
                mActivity4.setChecked(true);
            }
        }
        mMemo.getEditText().setText(prevActivity.getActivity5());
    }

    public void test3(View view) {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createDailyActivity(){
        activitiesDatabaseReference = firebaseDatabase.getReference("Activities");
        List<Integer> selectedActivities=new ArrayList<Integer>();
        List<String> dbSelectedActivities=new ArrayList<String>();
        selectedActivities.add(1);selectedActivities.add(2);selectedActivities.add(3);selectedActivities.add(4);

        activitiesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> a = snapshot.getChildren().iterator();
                int counter=0;
                while (a.hasNext() && counter<4){
                    DataSnapshot Previous = a.next();
                    if(Previous.getKey().toString().equals(selectedActivities.get(counter).toString())){
                        dbSelectedActivities.add(Previous.getValue().toString());
                        counter++;
                    }
//                    Log.d("hey",(Previous.getKey()));
//                    Log.d("hey",(Previous.child(selectedActivities.get(counter).toString())).toString());
                }
                saveData(dbSelectedActivities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveData(List<String> dbSelectedActivities){
        LocalDateTime now = LocalDateTime.now();
        String today= now.getDayOfWeek() + ", " + now.getDayOfMonth() + " " + now.getMonth()+ " " + now.getYear();
        PrevActivity prevActivity= new PrevActivity();
        prevActivity.setActivity1(dbSelectedActivities.get(0));
        prevActivity.setActivity2(dbSelectedActivities.get(1));
        prevActivity.setActivity3(dbSelectedActivities.get(2));
        prevActivity.setActivity4(dbSelectedActivities.get(3));
        prevActivity.setActivity5("");
        prevActivity.setDate(today);
        prevActivity.setProgress("0%");
        prevActivity.setUserID(mCurrentUser.getUid());
        List<String> temporaryList= new ArrayList<String>();
        temporaryList.add("none");
        prevActivity.setCompletedActivities(temporaryList);
        String key = databaseReference.push().getKey();
        dailyActivityId= key;
        databaseReference.child(key).setValue(prevActivity);
    }

    public List<Integer> generateRandomNumbers(int range, List<Integer> selected){
        boolean found= false;
        int int_random;
            int counter= 1;
            Random rand = new Random(); //instance of random class
            int_random = rand.nextInt(range);
            selected.add(int_random);
            while(counter<5){
                if (int_random==range){
                    int_random=1;
                }
                selected.add(int_random);
                int_random+=1;
            }

       return selected;
    }

    public void checkBoxClicked(View view) {
        List<String> completedActivities= new ArrayList<String>();
        if (mActivity1.isChecked()){
            completedActivities.add("Activity1");
        }
        if (mActivity2.isChecked()){
            completedActivities.add("Activity2");
        }
        if (mActivity3.isChecked()){
            completedActivities.add("Activity3");
        }
        if (mActivity4.isChecked()){
            completedActivities.add("Activity4");
        }
        int progress;
        progress= completedActivities.size();
        if (mMemo.getEditText().getText().toString()!=""){
            progress+=1;
        }
        progress= (progress*100/5);

        databaseReference = firebaseDatabase.getReference("PreviousActivities");
        databaseReference.child(dailyActivityId).child("progress").setValue(progress+"%");
        databaseReference.child(dailyActivityId).child("completedActivities").setValue(completedActivities);
    }
}