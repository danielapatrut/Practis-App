package com.techdevs.practis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewPageActivity extends AppCompatActivity {

    Button mMenuButton,mSaveButton;
    ImageButton mMakeCheckBox,mMakeTextBold,mMakeTextItalic,mUnderlineText,mMakeTextDifferentColor,mAddImageToolbar;
    FloatingActionButton mAddImage;
    TextView mPageTitle;
    ConstraintLayout mButtonContainer;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private PageFragment mPageFragment;
    private Page mPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        //load the page if it's old
        mMenuButton = findViewById(R.id.menuButton);
        mAddImage = findViewById(R.id.addImageInNewPage);
        mSaveButton = findViewById(R.id.saveButton);
        mAddImageToolbar=findViewById(R.id.addImage);
        mMakeCheckBox=findViewById(R.id.makeCheckBox);
        mMakeTextBold=findViewById(R.id.makeTextBold);
        mMakeTextItalic=findViewById(R.id.makeTextItalic);
        mMakeTextDifferentColor=findViewById(R.id.changeTextColor);
        mUnderlineText = findViewById(R.id.makeUnderlined);
        mPageTitle= findViewById(R.id.pageUpperTitle);
        mButtonContainer=findViewById(R.id.buttonContainer);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mPage=new Page();

        mPage.setUserID(firebaseAuth.getUid());

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

                mPage.setTitle(mPageFragment.getTitle());
                mPage.setContent(mPageFragment.getContent());
                MainActivity.incrementPages();
                //save content to new page and add to list of tiles
                //if it's new add to list
                if(mPage.isNewPage()) {
                    //save in db
                    mPage.setNewPage(false);
                    firebaseFirestore.collection("pages").document(String.valueOf(MainActivity.getNumberOfPagesCreated())).set(mPage);
                }else {
                    //if it's old update

                }

                //go back to main page
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open image dialog
                //change image for page
                mPage.setHasCoverImage(false); //until then, it's false
            }
        });

        //text editor buttons
        mAddImageToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open image dialog
            }
        });

        mMakeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert checkbox
            }
        });

        mMakeTextDifferentColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open color dialog
                openColorDialog();
            }
        });

        mMakeTextBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFragment.makeTextBold();
            }
        });

        mMakeTextItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mPageFragment.makeTextItalic();
            }
        });

        mUnderlineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //underline text
            }
        });

        mPageFragment = new PageFragment();
        replaceFragment(mPageFragment);
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.newPageContainer,fragment);
        fragmentTransaction.commit();
    }
    public void setPageTitle(CharSequence string){
        String s=string.toString();
        mPageTitle.setText(s);
    }
    private void openColorDialog() {
        ColorDialog dialog = new ColorDialog(this);
        dialog.show();
        //Window window = dialog.getWindow();
        //window.setLayout(40,70);
        int[] location = new int[2];
        mButtonContainer.getLocationOnScreen(location);
        int x = location[0];
        mMakeTextDifferentColor.getLocationOnScreen(location);
        int y = location[1];
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.x = x;
        wlp.y=y;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        Button redColor = (Button)dialog.findViewById(R.id.red);
        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("red button");
            }
        });
        Button blueColor = (Button)dialog.findViewById(R.id.blue);
        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("blue button");
            }
        });
        Button greenColor = (Button)dialog.findViewById(R.id.green);
        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("green button");
            }
        });
        Button orangeColor = (Button)dialog.findViewById(R.id.orange);
        orangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("orange button");
            }
        });
        Button purpleColor = (Button)dialog.findViewById(R.id.purple);
        purpleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("purple button");
            }
        });
        Button blackColor = (Button)dialog.findViewById(R.id.black);
        blackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("black button");
            }
        });

    }
}