package com.empire.sitpoly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.empire.sitpoly.DepartmentActivity;
import com.empire.sitpoly.R;
import com.empire.sitpoly.SettingActivity;
import com.empire.sitpoly.ui.about.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationMenuView;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);


        toolbar = findViewById(R.id.toolbar_news);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SIT POLY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationMenuView = findViewById(R.id.navigation_view);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationMenuView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_developers:
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_video:
           startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            break;
            case R.id.navigation_ebook:
                startActivity(new Intent(MainActivity.this, PdfActivity.class));
                break;
            case R.id.department:
                startActivity(new Intent(MainActivity.this, DepartmentActivity.class));
                break;
            case R.id.logout:

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Log Out")
                        .setMessage("Are you sure wants to Logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.cancel();
                    }
                });
                dialog = builder.create();
                dialog.show();

                break;

            case R.id.navigation_rate:

                final String appPackageName = getApplicationContext().getPackageName();
                String strAppLink = "";

                try
                {
                    strAppLink = "market://details?id=" + appPackageName;
                }
                catch (android.content.ActivityNotFoundException anfe)
                {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(strAppLink));
                startActivity(intent);

                break;
            case R.id.navigation_website:

                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("https://www.sitpolytechnic.ac.in/"));
                startActivity(intent1);
                break;

            case R.id.navigation_share:

                String body = "Hey, Install SIT Polytechnic Application to get more information about SIT POly and get extra features towards your study  " +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_SUBJECT,"SIT Polytechnic");
                intent2.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent2,"Share Via..."));

                break;

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.profile_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)){
            return true;
        }

        if (item.getItemId() == R.id.profile_menu){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
        return true;

    }

    @Override
    public void onBackPressed() {


        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
}