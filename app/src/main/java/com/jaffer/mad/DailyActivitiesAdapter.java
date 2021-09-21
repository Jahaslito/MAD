package com.jaffer.mad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DailyActivitiesAdapter extends RecyclerView.Adapter<DailyActivitiesAdapter.ViewHolder> {
    private ArrayList<PrevActivity> activitiesData;
    private Context myContext;
    private ArrayList<String> prevActivityID;

    DailyActivitiesAdapter(ArrayList<PrevActivity> mActivitiesData,ArrayList<String> prevActivityID, Context context) {
        this.activitiesData = mActivitiesData;
        this.myContext = context;
        this.prevActivityID = prevActivityID;

    }
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public DailyActivitiesAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(myContext).inflate(R.layout.previous_daily_activity_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull DailyActivitiesAdapter.ViewHolder holder, int position) {
        PrevActivity dailyActivities = activitiesData.get(position);
        String currentPrevActivityID = prevActivityID.get(position);
        holder.bindTo(dailyActivities,currentPrevActivityID);
    }

    @Override
    public int getItemCount() {
        return activitiesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mActivity1,mActivity2,mActivity3,mActivity4,mActivity5,mProgress;
        private TextView mDate;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.daily_activity_date);
            mActivity1 = itemView.findViewById(R.id.activity_1);
            mActivity2 = itemView.findViewById(R.id.activity_2);
            mActivity3 = itemView.findViewById(R.id.activity_3);
            mActivity4 = itemView.findViewById(R.id.activity_4);
            mActivity5 = itemView.findViewById(R.id.activity_5);
            mProgress = itemView.findViewById(R.id.progress_circular);
        }

        public void bindTo(PrevActivity prevActivity,String currentPrevActivityID) {
            mDate.setText(prevActivity.getDate());
            mActivity1.setText(prevActivity.getActivity1());
            mActivity2.setText(prevActivity.getActivity2());
            mActivity3.setText(prevActivity.getActivity3());
            mActivity4.setText(prevActivity.getActivity4());
            mActivity5.setText(prevActivity.getActivity5());
            mProgress.setText(prevActivity.getProgress());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] s_progress = prevActivity.getProgress().split("%");

                    Intent intent1 = new Intent(myContext, ViewDailyActivity.class);
                    intent1.putExtra("PrevKey", currentPrevActivityID);
                    intent1.putExtra("Date", prevActivity.getDate());
                    intent1.putExtra("Activity1", prevActivity.getActivity1());
                    intent1.putExtra("Activity2", prevActivity.getActivity2());
                    intent1.putExtra("Activity3", prevActivity.getActivity3());
                    intent1.putExtra("Activity4", prevActivity.getActivity4());
                    intent1.putExtra("Activity5", prevActivity.getActivity5());
                    intent1.putExtra("Progress", s_progress[0]);
                    myContext.startActivity(intent1);
                }
            });
        }
    }
}
