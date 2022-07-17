package com.example.collegeManagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegemanagement.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddUser extends AppCompatActivity {

    private EditText dateOfBirth;
    Button submitBtn;
    EditText firstName, lastName, address, salary, mail;
    String fName, lName, dob, add, sal, email;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        dateOfBirth = findViewById(R.id.dob);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateOfBirth.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (datePicker, year1, month1, day1) -> {
                month1 = month1 +1;
                String date = day1 +"-"+ month1 +"-"+ year1;
                dateOfBirth.setText(date);
            },year,month,day);
            datePickerDialog.show();
        });

        db.collection("teacher").addSnapshotListener((documentSnapshots, e) -> {
            if (e != null)
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(view -> {
            firstName = findViewById(R.id.fName);
            fName = firstName.getText().toString();
            lastName = findViewById(R.id.lName);
            lName = lastName.getText().toString();
            address = findViewById(R.id.address);
            add = address.getText().toString();
            dob = dateOfBirth.getText().toString();
            salary = findViewById(R.id.salary);
            sal = salary.getText().toString();
            mail = findViewById(R.id.mail);
            email = mail.getText().toString();

            Map<String, Object> teacher = new HashMap<>();
            teacher.put("address", add);
            teacher.put("dob", dob);
            teacher.put("fname", fName);
            teacher.put("lname", lName);
            teacher.put("salary", sal);
            teacher.put("email", email);
            teacher.put("uid","");


            db.collection("teacher")
                    .add(teacher)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error adding", Toast.LENGTH_SHORT).show());
        });
    }
}