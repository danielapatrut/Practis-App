package com.techdevs.practis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;

public class CalendarActivity extends AppCompatActivity {

    Button mMenuButton;
    ImageView mProfileImage;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private CalendarTaskFragment calendarTaskFragment;
    FloatingActionButton addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mProfileImage = findViewById(R.id.profileImage);
        mMenuButton = findViewById(R.id.menuButton);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        addTask = findViewById(R.id.addTask);
        calendarTaskFragment = new CalendarTaskFragment();
        replaceFragment(calendarTaskFragment);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskDialog();
            }
        });
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
        setupDrawerContent(nvDrawer);
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
            /*case R.id.nav_urgent_task:
                fragmentClass = UrgentTasksActivity.class;
                break;*/
            case R.id.nav_gallery:
                fragmentClass = GalleryActivity.class;
                break;
            /*case R.id.nav_profile:
                fragmentClass = ProfileActivity.class;
                break;*/
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
        fragmentTransaction.replace(R.id.taskListContainer,fragment);
        fragmentTransaction.commit();
    }
    public void openTaskDialog(){
        TaskCreationDialog taskDialog = new TaskCreationDialog(this);
        taskDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        taskDialog.show();
        Spinner imp;
        Task newTask= new Task();
        EditText taskTitle = taskDialog.findViewById(R.id.taskTitleEdit);
        imp=taskDialog.findViewById(R.id.importanceSpinner);
        Button ok=taskDialog.findViewById(R.id.okTask);
        TextView due = taskDialog.findViewById(R.id.due_date);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTask.setName(taskTitle.getText().toString());
                newTask.setDone(false);
                switch (imp.getSelectedItemPosition()){
                    case 0: newTask.setUrgent(true);
                        break;
                    case 1: newTask.setUrgent(false);
                        break;
                }
                newTask.setUserID(firebaseAuth.getCurrentUser().getUid());
                newTask.setDueDate(due.getText().toString());
                //save in db
                Query q = firebaseFirestore.collection("tasks").orderBy("taskID", Query.Direction.DESCENDING).limit(1);
                q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Long taskID = (Long) document.get("taskID");
                                int tskID = taskID.intValue();
                                tskID++;
                                newTask.setTaskID(tskID);
                                firebaseFirestore.collection("tasks").document(String.valueOf(tskID)).set(newTask);
                            }
                        }
                    }
                });
                taskDialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });
        imp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                System.out.println(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}