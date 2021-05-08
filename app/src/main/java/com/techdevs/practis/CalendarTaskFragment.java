package com.techdevs.practis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CalendarTaskFragment extends Fragment {

    private RecyclerView mTaskListView;
    private List<Task> tasks;
    private CalendarTaskRecyclerAdapter mRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public CalendarTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_task, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        tasks = new ArrayList<>();
        mTaskListView = getActivity().findViewById(R.id.tasksListView);
        mRecyclerAdapter = new CalendarTaskRecyclerAdapter(tasks);
        mTaskListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTaskListView.setAdapter(mRecyclerAdapter);

        //retrieve from db
        String pattern = "d/M/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        firebaseFirestore.collection("tasks")
                .whereEqualTo("userID", firebaseAuth.getUid())
                .whereEqualTo("dueDate",date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document!=null) {
                                    Task currentTask;
                                    currentTask = document.toObject(Task.class);
                                    tasks.add(currentTask);
                                    mRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            System.out.println("error retrieving tasks");
                        }
                    }
                });

        return view;
    }
    public void updatedRecyclerView(){
        mRecyclerAdapter.notifyDataSetChanged();
    }
}