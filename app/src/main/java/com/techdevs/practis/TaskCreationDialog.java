package com.techdevs.practis;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class TaskCreationDialog extends Dialog implements android.view.View.OnClickListener{

    public Activity mActivity;
    Spinner importance;
    TextView due_date;
    EditText taskTitle;
    Button ok;

    public TaskCreationDialog() {
        super(null);
    }
    public TaskCreationDialog(Activity activity){
        super(activity);
        mActivity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_creation_dialog);
        taskTitle = findViewById(R.id.taskTitleEdit);
        ok=findViewById(R.id.okTask);
        importance = (Spinner) findViewById(R.id.importanceSpinner);
        due_date=findViewById(R.id.due_date);
        String[] items = new String[]{"High Priority","Lower Priority"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importance.setAdapter(adapter);
        due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                due_date.setText(day + "/" + (month+1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default: break;
        }
        dismiss();
    }
}
