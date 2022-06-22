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
    TextView studentBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        Toast.makeText(this, "" + MainActivity.USER_TYPE, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        signOutBtn = findViewById(R.id.signOutBtn);
        studentBtn = findViewById(R.id.studentBtn);

        studentBtn.setOnClickListener(view-> {
            Intent intent = new Intent(AdminPage.this, ViewCollectionActivity.class);
            intent.putExtra("collection", "user");
            startActivity(intent);
        });

        signOutBtn.setOnClickListener(view -> {
            Intent i = new Intent(AdminPage.this, MainActivity.class);
            mAuth.signOut();
            MainActivity.USER_TYPE = "";
            startActivity(i);
        });
    }
}