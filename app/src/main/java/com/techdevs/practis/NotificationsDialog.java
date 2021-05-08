package com.techdevs.practis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class NotificationsDialog extends Dialog implements android.view.View.OnClickListener{

    public Activity mActivity;
    Spinner dropdown;
    Button okButton;

    public NotificationsDialog() {
        super(null);
    }
    public NotificationsDialog(Activity activity){
        super(activity);
        mActivity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.notifications_dialog);
        okButton = findViewById(R.id.okButton);
        dropdown = (Spinner) findViewById(R.id.dropDown);
        String[] items = new String[]{"Select a time","30 mins before", "1 hr before", "2 hrs before", "5 hrs before", "12 hrs before"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default: break;
        }
        dismiss();
    }
}
