package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView userName;
    CardView signOutBtn, marksBtn, attendanceBtn, subjectsBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);

        mAuth = FirebaseAuth.getInstance();
        signOutBtn = findViewById(R.id.signOutBtn);
        attendanceBtn = findViewById(R.id.attendanceBtn);
        subjectsBtn = findViewById(R.id.subjectsBtn);
        marksBtn = findViewById(R.id.marksBtn);

        String uid = mAuth.getCurrentUser().getUid();
        db.collection("teacher").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String fname = document.get("fname").toString();
                    userName.setText(userName.getText().toString().concat(fname));
                    //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });

        subjectsBtn.setOnClickListener(view -> activityManager("subject"));
        attendanceBtn.setOnClickListener(view -> activityManager("attendance"));
        marksBtn.setOnClickListener(view -> activityManager("marks"));

        signOutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(TeacherPage.this, LoginActivity.class));
        });
    }

    private void activityManager(String collection) {
        Intent intent = new Intent(TeacherPage.this, ViewDocuments.class);
        intent.putExtra("collection", collection);
        intent.putExtra("userType", "teacher");
        startActivity(intent);
    }
}