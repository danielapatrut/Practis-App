package com.techdevs.practis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewPageActivity extends AppCompatActivity {

    Button mMenuButton,mSaveButton;
    ImageButton mMakeCheckBox,mMakeTextBold,mMakeTextItalic,mUnderlineText,mMakeTextDifferentColor,mAddImageToolbar;
    FloatingActionButton mAddImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        mMenuButton = findViewById(R.id.menuButton);
        mAddImage = findViewById(R.id.addImageInNewPage);
        mSaveButton = findViewById(R.id.saveButton);
        mAddImageToolbar=findViewById(R.id.addImage);
        mMakeCheckBox=findViewById(R.id.makeCheckBox);
        mMakeTextBold=findViewById(R.id.makeTextBold);
        mMakeTextItalic=findViewById(R.id.makeTextItalic);
        mMakeTextDifferentColor=findViewById(R.id.changeTextColor);
        mUnderlineText = findViewById(R.id.makeUnderlined);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open menu
                //startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to main page
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}