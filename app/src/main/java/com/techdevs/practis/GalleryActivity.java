package com.techdevs.practis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class GalleryActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    Button mMenuButton;
    ImageView mProfileImage;
    private GalleryFragment mGalleryFragment;
    private FrameLayout mFrameLayout;
    FloatingActionButton mAddImageButton;
    private Uri filePath;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mMenuButton = findViewById(R.id.menuButton);
        mProfileImage = findViewById(R.id.profileImage);
        mFrameLayout = findViewById(R.id.galleryContainer);
        mAddImageButton = findViewById(R.id.newImage);
        mGalleryFragment = new GalleryFragment();
        replaceFragment(mGalleryFragment);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.galleryContainer,fragment);
        fragmentTransaction.commit();
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
            StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
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
                                        firebaseFirestore.collection("images")
                                                .orderBy("imageID", Query.Direction.DESCENDING)
                                                .limit(1)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        int pageID = Integer.parseInt(document.getString("imageID"));
                                                        pageID++;
                                                        mImage.setImageID(String.valueOf(pageID));
                                                        //save in db
                                                        firebaseFirestore.collection("images").document(String.valueOf(pageID)).set(mImage);
                                                        startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
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