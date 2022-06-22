package com.example.collegemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText inputEmail, inputPass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailId);
        pwd = findViewById(R.id.password);

        inputEmail = findViewById(R.id.emailId);
        inputPass = findViewById(R.id.password);

        login = findViewById(R.id.login_btn);

        login.setOnClickListener(view -> {
            final String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            final String passwordRegEx = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";

            Pattern pattern = Pattern.compile(emailRegEx);
            boolean emailValidated = pattern.matcher(inputEmail.getText().toString()).matches();

            pattern = Pattern.compile(passwordRegEx);
            boolean passwordValidated = pattern.matcher(inputPass.getText().toString()).matches();

            if (emailValidated && passwordValidated) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            db.collection("user").document(user.getUid()).get().addOnCompleteListener(t -> {
                                if (t.getResult().exists())
                                        Toast.makeText(this, "Logged in Successfully: " + t.getResult().getData(), Toast.LENGTH_SHORT).show();
                            });
                        } else Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    });

                Toast.makeText(this, inputEmail.getText().toString() + " Logged in Successfully", Toast.LENGTH_SHORT).show();

            }
            else if (!emailValidated) Toast.makeText(this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
        });
    }
}