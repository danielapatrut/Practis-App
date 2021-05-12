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
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import io.noties.markwon.Markwon;

public class NewPageActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
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
    private Uri filePath;
    ImageView coverImage;

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
        coverImage=findViewById(R.id.coverImage);

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
        if(getIntent().hasExtra("PAGE_URI")){
            mPage.setUri(getIntent().getStringExtra("PAGE_URI"));
            Glide.with(getApplicationContext()).load(Uri.parse(mPage.getUri())).into(coverImage);
        }else{
            coverImage.requestLayout();
            coverImage.getLayoutParams().height=0;
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
                chooseImage();
                mPage.setHasCoverImage(true); //until then, it's false
                if(!mPage.isNewPage()) updateInFirestore(mPage.getPageID());
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
                mPageFragment.editor.insertTodo();
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
                //mPageFragment.makeTextBold(markwon);
                mPageFragment.editor.setBold();
            }
        });


        mMakeTextItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPageFragment.makeTextItalic();
                mPageFragment.editor.setItalic();
            }
        });

        mUnderlineText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //underline text
                mPageFragment.editor.setUnderline();
            }
        });

        mPageFragment = new PageFragment();
        mPageFragment.setmPage(mPage);
        replaceFragment(mPageFragment);
        setupDrawerContent(nvDrawer);

    }
    private void updateInFirestore(int id){
        FirebaseFirestore.getInstance().collection("pages").document(String.valueOf(id)).update("hasCoverImage",true);
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            uploadImageToFirebase(filePath);
            //Glide.with(getApplicationContext()).load(filePath).into(coverImage);
        }
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
            case R.id.nav_calendar:
                fragmentClass = CalendarActivity.class;
                break;
            case R.id.nav_urgent_task:
                fragmentClass = UrgentTasksActivity.class;
                break;
            case R.id.nav_gallery:
                fragmentClass = GalleryActivity.class;
                break;
            case R.id.nav_profile:
                fragmentClass = MyProfileActivity.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsActivity.class;
                break;
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
                //mPageFragment.changeTextColor();
                mPageFragment.editor.setTextColor(0xffff4444);
            }
        });
        Button blueColor = (Button)dialog.findViewById(R.id.blue);
        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFragment.editor.setTextColor(0xff33b5e5);
            }
        });
        Button greenColor = (Button)dialog.findViewById(R.id.green);
        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFragment.editor.setTextColor(0xff99cc00);
            }
        });
        Button orangeColor = (Button)dialog.findViewById(R.id.orange);
        orangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFragment.editor.setTextColor(0xffffbb33);
            }
        });
        Button purpleColor = (Button)dialog.findViewById(R.id.purple);
        purpleColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFragment.editor.setTextColor(0xFFBB86FC);
            }
        });
        Button blackColor = (Button)dialog.findViewById(R.id.black);
        blackColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFragment.editor.setTextColor(Color.BLACK);
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
    private void uploadImageToFirebase(Uri imguri) {
        if (imguri != null) {
            CoverImage mImage = new CoverImage();
            if(mPage.getPageID()!=0){
                mImage.setPageID(String.valueOf(mPage.getPageID()));
            }
            String fileName = UUID.randomUUID().toString() + ".png";
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference ref = storageRef.child("coverimages/" + UUID.randomUUID().toString());
            ref.putFile(imguri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mImage.setUri(uri.toString());
                                        mImage.setUserID(firebaseAuth.getCurrentUser().getUid());
                                        firebaseFirestore.collection("pages")
                                                .whereEqualTo("pageID",mPage.getPageID())
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        mPage.setUri(String.valueOf(mImage.getUri()));
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });


        }
    }
}