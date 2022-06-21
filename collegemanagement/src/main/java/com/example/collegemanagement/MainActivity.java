package com.example.collegemanagement;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email, pwd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailId);
        pwd = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(view -> {
            System.out.println(email.getText().toString());
            Toast.makeText(this, email.getText().toString(), Toast.LENGTH_SHORT).show();

            // Create a new user with a first and last name
//            Map<String, String> user = new HashMap<>();
//            user.put("email", email.getText().toString());
//            user.put("password", pwd.getText().toString());
//
//
//            // Add a new document with a generated ID
//            db.collection("users")
//                    .get()
//                    .addOnSuccessListener(documentReference -> System.out.println("DocumentSnapshot added with ID: " + documentReference.getId()))
//                    .addOnFailureListener(e -> Log.d("jo_mama", e.toString()));

            db.collection("users").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult())
                        Log.d("DATA", document.getId() + " \n\n\n\n=> " + document.getData());
                } else Log.d("Get failed: ", "" + task.getException());
            });
        });
    }
}