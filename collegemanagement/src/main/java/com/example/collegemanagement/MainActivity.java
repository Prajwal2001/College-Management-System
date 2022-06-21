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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            final String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            final String passwordRegEx = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";

            Pattern pattern = Pattern.compile(emailRegEx);
            boolean emailValidated = pattern.matcher(email.getText().toString()).matches();

            pattern = Pattern.compile(passwordRegEx);
            boolean passwordValidated = pattern.matcher(pwd.getText().toString()).matches();

            if (emailValidated && passwordValidated) Toast.makeText(this, email.getText().toString() + " Logged in Successfully", Toast.LENGTH_SHORT).show();
            else if (!emailValidated) Toast.makeText(this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
        });
    }
}