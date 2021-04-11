package com.techdevs.practis;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class ColorDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity mActivity;
    Button mRed, mBlue, mGreen, mOrange, mPurple, mBlack;

    public ColorDialog() {
        super(null);
    }
    public ColorDialog(Activity activity){
        super(activity);
        mActivity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.color_dialog);

        mRed=findViewById(R.id.red);
        mBlue=findViewById(R.id.blue);
        mGreen=findViewById(R.id.green);
        mOrange=findViewById(R.id.orange);
        mPurple=findViewById(R.id.purple);
        mBlack=findViewById(R.id.black);
        //make text different color

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default: break;
        }
        dismiss();
    }
}
