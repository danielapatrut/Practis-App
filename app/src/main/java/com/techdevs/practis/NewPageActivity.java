package com.techdevs.practis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import io.noties.markwon.Markwon;

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
    private boolean fromList=false;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

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
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        final Markwon markwon = Markwon.create(this);

        mPage=new Page();

        if(getIntent().hasExtra("PAGE_TITLE")) {
            mPage.setNewPage(getIntent().getBooleanExtra("IS_NEW",true));
            mPage.setTitle(getIntent().getStringExtra("PAGE_TITLE"));
            mPage.setContent(getIntent().getStringExtra("PAGE_CONTENT"));
            mPage.setPageID(getIntent().getIntExtra("PAGE_ID",0));
            mPage.setHasCoverImage(getIntent().getBooleanExtra("HAS_PHOTO", false));
            fromList=true;
        }

        mPage.setUserID(firebaseAuth.getUid());

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open menu
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage.setTitle(mPageFragment.getTitle());
                mPage.setContent(mPageFragment.getContent());
                //save content to new page and add to list of tiles
                if(mPage.isNewPage()) {
                    Query q = firebaseFirestore.collection("pages").orderBy("pageID", Query.Direction.DESCENDING).limit(1);
                    q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Long pageID = (Long) document.get("pageID");
                                    int pgID = pageID.intValue();
                                    pgID++;

                                    mPage.setPageID(pgID);
                                    if(!mPage.getTitle().equals("") && !mPage.getContent().equals("")) {
                                        //save in db
                                        mPage.setNewPage(false);
                                        firebaseFirestore.collection("pages").document(String.valueOf(pgID)).set(mPage);
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }else{
                                            openSavingDialog();
                                        }

                                }
                            }
                        }
                    });
                }else {
                    //if it's old update
                    firebaseFirestore.collection("pages").document(String.valueOf(mPage.getPageID())).set(mPage);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });

        //add image from gallery
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open image dialog
                //change image for page
                mPage.setHasCoverImage(true); //until then, it's false
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
                mPageFragment.makeTextBold(markwon);
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
        mPageFragment.setmPage(mPage);
        replaceFragment(mPageFragment);
        setupDrawerContent(nvDrawer);
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
                mPageFragment.changeTextColor();
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

    private void openSavingDialog() {
        SavingDialog savingDialog = new SavingDialog(this);
        savingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        savingDialog.show();

        Button continueEditing = (Button)savingDialog.findViewById(R.id.continueEditing);
        continueEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingDialog.dismiss();
            }
        });
        Button goHome = (Button)savingDialog.findViewById(R.id.goMain);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    public boolean isFromList() {
        return fromList;
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = MainActivity.class;
                break;
            /*case R.id.nav_calendar:
                fragmentClass = CalendarActivity.class;
                break;
            case R.id.nav_urgent_task:
                fragmentClass = UrgentTasksActivity.class;
                break;*/
            case R.id.nav_gallery:
                fragmentClass = GalleryActivity.class;
                break;
            case R.id.nav_profile:
                fragmentClass = MyProfileActivity.class;
                break;
            /*case R.id.nav_settings:
                fragmentClass = SettingsActivity.class;
                break;*/
            case R.id.nav_logout:
                fragmentClass=Login.class;
                logout();
                break;
            default:
                fragmentClass = MainActivity.class;
        }

        startActivity(new Intent(getApplicationContext(),fragmentClass));

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}