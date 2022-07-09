package com.example.collegemanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity {

    public static String USER;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button signOutBtn;
    CardView studentBtn, teacherBtn, subjectBtn, marksBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null)
            activityManager(null, MainActivity.class);
    }

    private void activityManager(String collection, Class<?> className) {
        Intent intent = new Intent(AdminPage.this, className);
        intent.putExtra("collection", collection);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

//        Toast.makeText(this, "" + LoginActivity.USER_TYPE, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();

//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.navigation_view);
//        toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


        signOutBtn = findViewById(R.id.signOutBtn);

        studentBtn = findViewById(R.id.studentBtn);
        teacherBtn = findViewById(R.id.teacherBtn);
        subjectBtn = findViewById(R.id.subjectsBtn);
        marksBtn = findViewById(R.id.marksBtn);

        studentBtn.setOnClickListener(view -> activityManager("student", ViewDocuments.class));
        teacherBtn.setOnClickListener(view -> activityManager("teacher", ViewDocuments.class));
        subjectBtn.setOnClickListener(view -> activityManager("subject", ViewDocuments.class));
        marksBtn.setOnClickListener(view -> activityManager("marks", ViewDocuments.class));

        signOutBtn.setOnClickListener(view -> new AlertDialog.Builder(AdminPage.this)
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mAuth.signOut();
                    Intent i = new Intent(AdminPage.this, MainActivity.class);
                    LoginActivity.USER_TYPE = "";
                    startActivity(i);
                })
                .setNegativeButton("No", (dialog, which) -> {})
                .create()
                .show());
    }
}