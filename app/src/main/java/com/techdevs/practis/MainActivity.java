package com.techdevs.practis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {

    Button mMenuButton;
    TextView mWelcomeLabel;
    FloatingActionButton mNewPageButton;
    ImageView mProfileImage;

    private MainPageFragment mMainPageFragment;
    private FrameLayout mFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewPageButton = findViewById(R.id.newPageButton);
        mMenuButton = findViewById(R.id.menuButton);
        mWelcomeLabel = findViewById(R.id.welcomeLabel);
        mProfileImage = findViewById(R.id.profileImage);
        mFrameLayout = findViewById(R.id.mainContainer);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //current logged in user
        if (user != null) {
            // User is signed in
            //change welcome label text
            for (UserInfo profile : user.getProviderData()) {

                // UID specific to the provider
                String uid = profile.getUid();
                String email = profile.getEmail();
                //mWelcomeLabel.setText("Welcome, "+email); //just user?
            }
        }
        //change profile picture
        //mProfileImage.setImageURI();

        mMainPageFragment = new MainPageFragment();
        replaceFragment(mMainPageFragment);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open menu
                //startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            }
        });
        mNewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to new page

                startActivity(new Intent(getApplicationContext(),NewPageActivity.class));

            }
        });

    }

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer,fragment);
        fragmentTransaction.commit();
    }

}