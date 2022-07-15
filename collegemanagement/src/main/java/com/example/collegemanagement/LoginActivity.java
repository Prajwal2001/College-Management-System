package com.example.collegemanagement;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    public static String USER_TYPE;

    Button login;
    ProgressBar circularPB;
    EditText inputEmail, inputPass;
    TextView forgotPass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    final String emailRegEx = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    final String passwordRegEx = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateSignIn(currentUser);
    }

    private void updateSignIn(FirebaseUser user) {
        if (user != null)
            db.collection("admin")
                    .document(user.getUid())
                    .get()
                    .addOnCompleteListener(t -> {
                        if (t.getResult().exists()) {
                            Intent i = new Intent(LoginActivity.this, AdminPage.class);
                            startActivity(i);
                        }
                        else
                            db.collection("teacher")
                                    .addSnapshotListener((documentSnapshots, e) -> {
                                        if (documentSnapshots != null) {
                                            for (DocumentSnapshot doc: documentSnapshots) {
                                                if (Objects.requireNonNull(doc.getString("email")).equalsIgnoreCase(user.getEmail())){
                                                    Map<String, Object> data = doc.getData();
                                                    for(Map.Entry<String, Object> entry: Objects.requireNonNull(data).entrySet()) {
                                                        if(entry.getValue().toString().equals(user.getUid())){
                                                            Intent i = new Intent(LoginActivity.this, TeacherPage.class);
                                                            startActivity(i);
                                                        }
                                                        else {
                                                            db.collection("teacher").document(doc.getId()).update("uid", user.getUid());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_SHORT).show();
                                    });
                    });
    }

    private void checkForUser(String emailString, String passwordString) {
        db.collection("teacher")
                .addSnapshotListener((documentSnapshots, e) -> {
                    if (documentSnapshots != null) {
                        for (DocumentSnapshot doc: documentSnapshots) {
                            Map<String, Object> data = doc.getData();
                            String item = "";
                            for(Map.Entry<String, Object> entry: Objects.requireNonNull(data).entrySet()) {
                                item = item.concat(entry.getKey() + ": " + entry.getValue() + "\n");
                                if(item.contains("email: " + emailString)){
                                    userRegister(emailString, passwordString, doc.getId());
                                }
                            }
                        }
                    }
                });

    }
    private void userRegister(String emailString, String passwordString, String docId) {
        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        db.collection("teacher")
                                .document(docId)
                                .update("uid", user.getUid());
                        Intent i = new Intent(LoginActivity.this, TeacherPage.class);
                        startActivity(i);
                        finish();
                    }
                });
    }

    private boolean validation(String string, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(string).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.emailId);
        inputPass = findViewById(R.id.password);
        forgotPass = findViewById(R.id.forgot_pwd);
        login = findViewById(R.id.login_btn);
        circularPB = findViewById(R.id.progressBar);

        forgotPass.setOnClickListener(v -> {
            if (validation(inputEmail.getText().toString(), emailRegEx))
                mAuth.sendPasswordResetEmail(inputEmail.getText().toString());
            else
                Toast.makeText(this, "Enter a valid Email_id", Toast.LENGTH_SHORT).show();
        });


        login.setOnClickListener(view -> {
            boolean emailValidated = validation(inputEmail.getText().toString(), emailRegEx);
            boolean passwordValidated = validation(inputPass.getText().toString(), passwordRegEx);

            if (emailValidated && passwordValidated) {
                circularPB.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPass.getText().toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateSignIn(user);
                            } else {
                                checkForUser(inputEmail.getText().toString(), inputPass.getText().toString());
                                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (!emailValidated) Toast.makeText(this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
        });
    }
}