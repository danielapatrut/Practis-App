package com.techdevs.practis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TmrUTRecyclerAdapter extends RecyclerView.Adapter<TmrUTRecyclerAdapter.TomorrowTaskViewHolder> {
    private List<Task> tasks;
    public TmrUTRecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public TmrUTRecyclerAdapter.TomorrowTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.urgent_task_item, parent,false);
        return new TmrUTRecyclerAdapter.TomorrowTaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TmrUTRecyclerAdapter.TomorrowTaskViewHolder viewHolder, int position) {
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

    public class TomorrowTaskViewHolder extends RecyclerView.ViewHolder{

        private View mView;
public CheckBox mTask;
        public TomorrowTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTaskName(String title){
            mTask = mView.findViewById(R.id.utask);
            mTask.setText(title);

        }

    }
}
