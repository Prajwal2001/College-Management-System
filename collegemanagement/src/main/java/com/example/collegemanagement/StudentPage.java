package com.example.collegemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView userName;
    CardView signOutBtn, marksBtn, attendanceBtn, subjectsBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        mAuth = FirebaseAuth.getInstance();
        signOutBtn = findViewById(R.id.signOutBtn);

        String uid = mAuth.getCurrentUser().getUid();
        db.collection("student").document(uid).get().addOnCompleteListener(task -> {
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
            startActivity(new Intent(StudentPage.this, LoginActivity.class));
        });
    }

    private void activityManager(String collection) {
        Intent intent = new Intent(StudentPage.this, ViewDocuments.class);
        intent.putExtra("collection", collection);
        startActivity(intent);
    }
}