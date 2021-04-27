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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        if (user != null) {
            // User is signed in
            //change welcome label text
            DocumentReference docRef = firestore.collection("users").document(user.getUid());
            firestore.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    mWelcomeLabel.setText("Welcome, "+currentUser.getUserName());
                }
            });
        }
        //change profile picture
        //mProfileImage.setImageURI();

        mMainPageFragment = new MainPageFragment();
        replaceFragment(mMainPageFragment);

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open menu
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
            }
        });
        mNewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPageIntent=new Intent(getApplicationContext(),NewPageActivity.class);
                //go to new page
                startActivity(newPageIntent);

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