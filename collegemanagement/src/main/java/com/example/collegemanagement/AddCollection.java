package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCollection extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        FirebaseAuth mAuth2 = FirebaseAuth.getInstance();

        Intent i = getIntent();
        String collection = i.getStringExtra("collection");

        db.collection(collection).addSnapshotListener((documentSnapshots, e) -> {
            if (e != null)
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
}