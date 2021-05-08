package com.techdevs.practis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    Button mMenuButton;
    ImageView mProfileImage;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    Button  timeButton, helpButton, logoutButton;
    TextView notifTime;
    RelativeLayout notificationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mProfileImage = findViewById(R.id.profileImage);
        mMenuButton = findViewById(R.id.menuButton);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        notificationsButton = findViewById(R.id.notifButton);
        timeButton = findViewById(R.id.timeSpentBtn);
        helpButton = findViewById(R.id.helpBtn);
        logoutButton = findViewById(R.id.logoutBtn);
        notifTime=(TextView)findViewById(R.id.notifTime);
        notifTime.bringToFront();
        //notifications
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotifDialog();
            }
        });
        //time
        
        //help
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),HelpActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open menu
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
        setupDrawerContent(nvDrawer);
    }


    public void openNotifDialog(){
        NotificationsDialog notificationsDialog = new NotificationsDialog(this);
        notificationsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        notificationsDialog.show();
        Spinner dropdown;
        dropdown=notificationsDialog.findViewById(R.id.dropDown);
        Button ok = notificationsDialog.findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationsDialog.dismiss();
            }
        });
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(position!=0)
                    notifTime.setText(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
}