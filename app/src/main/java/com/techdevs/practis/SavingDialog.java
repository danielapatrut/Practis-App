package com.techdevs.practis;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SavingDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity mActivity;
    Button continueEditing, goHome;

    public SavingDialog() {
        super(null);
    }
    public SavingDialog(Activity activity){
        super(activity);
        mActivity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.saving_dialog);
        continueEditing = findViewById(R.id.continueEditing);
        goHome = findViewById(R.id.goMain);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default: break;
        }
        dismiss();
    }
}
