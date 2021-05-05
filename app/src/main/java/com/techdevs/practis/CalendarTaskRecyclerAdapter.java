package com.techdevs.practis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarTaskRecyclerAdapter extends RecyclerView.Adapter<CalendarTaskRecyclerAdapter.CalendarTaskViewHolder> {
    private List<Task> tasks;

    public CalendarTaskRecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public CalendarTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent,false);
        return new CalendarTaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CalendarTaskViewHolder viewHolder, int position) {
        viewHolder.setTaskName(tasks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class CalendarTaskViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CheckBox mTask;
        public CalendarTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        //set image to tile
        public void setTaskName(String title){
            mTask = mView.findViewById(R.id.task);
            mTask.setText(title);
        }
    }
}
