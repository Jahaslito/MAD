package com.jaffer.mad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private ArrayList<Reminder> reminderData;
    private Context myContext;


    ReminderAdapter(ArrayList<Reminder> reminderData, Context context){
        this.myContext = context;
        this.reminderData = reminderData;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.remainder_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {
        Reminder currentReminder = reminderData.get(position);
        holder.bindTo(currentReminder);
    }

    @Override
    public int getItemCount() {
        return reminderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView title;
        public TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            message= itemView.findViewById(R.id.message);
            time= itemView.findViewById(R.id.time_taken);
        }

        public void bindTo(Reminder currentReminder) {
            title.setText(currentReminder.getTitle());
            message.setText(currentReminder.getMessage());
            time.setText(currentReminder.getTime());
        }
    }
}
