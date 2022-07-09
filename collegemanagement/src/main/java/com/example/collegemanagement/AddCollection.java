package com.example.collegemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCollection extends AppCompatActivity {

    private EditText dob;
    Button submitBtn;
    EditText firstName, lastName, dateOfBirth, address, salary, pword, mail;
    String fName, lName, dOb, add, sal, email, pwd;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        dob = findViewById(R.id.dob);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"-"+month+"-"+year;
                        dob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        Intent i = getIntent();
        String collection = i.getStringExtra("collection");

        db.collection(collection).addSnapshotListener((documentSnapshots, e) -> {
            if (e != null)
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });


        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = findViewById(R.id.fName);
                fName = firstName.getText().toString();
                lastName = findViewById(R.id.lName);
                lName = lastName.getText().toString();
                address = findViewById(R.id.address);
                add = address.getText().toString();
                dateOfBirth = findViewById(R.id.dob);
                dOb = dateOfBirth.getText().toString();
                salary = findViewById(R.id.salary);
                sal = salary.getText().toString();
                mail = findViewById(R.id.mail);
                email = mail.getText().toString();
                pword = findViewById(R.id.pwd);
                pwd = pword.getText().toString();

                Map<String, Object> teacher = new HashMap<>();
                teacher.put("address", add);
                teacher.put("dob", dOb);
                teacher.put("fname", fName);
                teacher.put("lname", lName);
                teacher.put("salary", sal);
                teacher.put("email", email);
                teacher.put("password", pwd);
                teacher.put("uid","");


                db.collection("teacher")
                        .add(teacher)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error adding", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}