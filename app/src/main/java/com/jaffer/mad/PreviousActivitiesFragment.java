package com.jaffer.mad;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link PreviousActivitiesFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class PreviousActivitiesFragment extends Fragment {

    private RecyclerView activitiesRecyclerView;
    private ArrayList<PrevActivity> prevActivities;
    private ArrayList<String> activityID;
    private DailyActivitiesAdapter dailyActivitiesAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FloatingActionButton fab;


    //    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//

//    private String mParam1;
//    private String mParam2;
//
//    public PreviousActivitiesFragment() {
//        // Required empty public constructor
//    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PreviousActivitiesFragment.
//     */
//    //
//    public static PreviousActivitiesFragment newInstance(String param1, String param2) {
//        PreviousActivitiesFragment fragment = new PreviousActivitiesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_activities, container, false);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        fab = view.findViewById(R.id.fab);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PreviousActivities");
        activitiesRecyclerView = view.findViewById(R.id.recycler_previous_activities);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        prevActivities = new ArrayList<>();
        activityID = new ArrayList<>();
        dailyActivitiesAdapter = new DailyActivitiesAdapter(prevActivities, activityID ,getContext());
        activitiesRecyclerView.setAdapter(dailyActivitiesAdapter);
        initializePreviousActivitiesData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daily = new Intent(getContext(),DailyActivity.class);
                startActivity(daily);
            }
        });
        return view;
    }

    private void initializePreviousActivitiesData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prevActivities.clear();
                activityID.clear();
                Iterator<DataSnapshot> a = snapshot.getChildren().iterator();
                while (a.hasNext()){
                    DataSnapshot Previous = a.next();
                    if(Previous.child("userID").getValue().equals(mCurrentUser.getUid())){
                        prevActivities.add(Previous.getValue(PrevActivity.class));
                        activityID.add(Previous.getKey());
                    }else{
                        Log.d("Data si zake", Previous.getValue().toString());
                    }

                }
                Collections.reverse(prevActivities);
                Collections.reverse(activityID);
                dailyActivitiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}