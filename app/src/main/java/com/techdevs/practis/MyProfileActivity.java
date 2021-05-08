package com.techdevs.practis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyProfileActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    EditText   mEditName;
    TextView mWelcomeLabel,mEditEmail,mEditPassword;
    ImageView mEditNameButton, mAddProfileImage;
    Button mMenuButton;
    FirebaseAuth fAuth;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    FirebaseFirestore fStore;
    String userID;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mEditEmail = findViewById(R.id.editTextEmail);
        mEditName = findViewById(R.id.editTextName);
        mEditPassword = findViewById(R.id.editTextPassword);
        mWelcomeLabel = findViewById(R.id.welcomeLabel);
        mEditNameButton = findViewById(R.id.editNameButton);
        mMenuButton = findViewById(R.id.menuButton);
        mAddProfileImage = findViewById(R.id.addProfileImageButton);
        fAuth = FirebaseAuth.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);

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
                    //mWelcomeLabel.setText("Welcome, "+currentUser.getUserName());
                    mEditEmail.setText(currentUser.getEmail());
                    mEditName.setText(currentUser.getUserName());
                    mEditPassword.setText(currentUser.getPassword());
                }
            });
        }
        mAddProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open menu
                //startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
        setupDrawerContent(nvDrawer);
    }

    public void buttonClickedEditName(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_name, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Update your Username");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                userID = fAuth.getCurrentUser().getUid();
                String name = etUsername.getText().toString();
                String email = mEditEmail.getText().toString();
                String password = mEditPassword.getText().toString();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                User userinformation = new User(name,email,password);
                //documentReference.child(user.getUid()).setValue(userinformation);
               // databaseReference.child(user.getUid()).setValue(userinformation);
                documentReference.set(userinformation);
                documentReference.set(userinformation);
                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
                mEditName.setText(" " + name);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
        }
    }

    private void uploadImageToFirebase(Uri imguri){
        if(imguri!=null){
            Image mImage = new Image();
            String fileName= UUID.randomUUID().toString()+".png";
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference ref = storageRef.child("profileimages/"+ UUID.randomUUID().toString());
            ref.putFile(imguri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mImage.setUri(uri.toString());
                                        mImage.setUserID(firebaseAuth.getCurrentUser().getUid());
                                        firebaseFirestore.collection("profileimages")
                                                .orderBy("imageID", Query.Direction.DESCENDING)
                                                .limit(1)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        int imageID = Integer.parseInt(document.getString("imageID"));
                                                        imageID++;
                                                        mImage.setImageID(String.valueOf(imageID));
                                                        //save in db
                                                        firebaseFirestore.collection("profileimages").document(String.valueOf(imageID)).set(mImage);
                                                        startActivity(new Intent(getApplicationContext(),MyProfileActivity.class));
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