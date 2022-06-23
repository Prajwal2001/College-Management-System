package com.example.collegemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity {

    Button signOutBtn;
    TextView studentBtn, teacherBtn, subjectBtn, marksBtn;
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

        Toast.makeText(this, "" + LoginActivity.USER_TYPE, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();

        signOutBtn = findViewById(R.id.signOutBtn);

        studentBtn = findViewById(R.id.studentBtn);
        teacherBtn = findViewById(R.id.teacherBtn);
        subjectBtn = findViewById(R.id.subjectBtn);
        marksBtn = findViewById(R.id.marksBtn);

        studentBtn.setOnClickListener(view -> activityManager("student", ViewCollection.class));
        teacherBtn.setOnClickListener(view -> activityManager("teacher", ViewCollection.class));
        subjectBtn.setOnClickListener(view -> activityManager("subject", ViewCollection.class));
        marksBtn.setOnClickListener(view -> activityManager("marks", ViewCollection.class));

        signOutBtn.setOnClickListener(view -> {
            Intent i = new Intent(AdminPage.this, MainActivity.class);
            mAuth.signOut();
            LoginActivity.USER_TYPE = "";
            startActivity(i);
        });
    }
}