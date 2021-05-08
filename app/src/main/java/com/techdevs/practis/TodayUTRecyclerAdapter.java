package com.techdevs.practis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TodayUTRecyclerAdapter extends RecyclerView.Adapter<TodayUTRecyclerAdapter.TodayTaskViewHolder> {

    private List<Task> tasks;
    public TodayUTRecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public TodayUTRecyclerAdapter.TodayTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.urgent_task_item, parent,false);
        return new TodayUTRecyclerAdapter.TodayTaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TodayUTRecyclerAdapter.TodayTaskViewHolder viewHolder, int position) {
        viewHolder.setTaskName(tasks.get(position).getName());
        viewHolder.mTask.setOnCheckedChangeListener(null);
        viewHolder.mTask.setChecked(tasks.get(position).isDone());
        viewHolder.mTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tasks.get(position).setDone(isChecked);
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("tasks").document(String.valueOf(tasks.get(position).getTaskID())).update("done", isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public class TodayTaskViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        public CheckBox mTask;
        public TodayTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTaskName(String title){
            mTask = mView.findViewById(R.id.utask);
            mTask.setText(title);

        }

    }
}
