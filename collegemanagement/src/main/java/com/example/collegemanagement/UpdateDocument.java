package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UpdateDocument extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_document);
        Intent update = getIntent();
        String collection = update.getStringExtra("collection");
        String docId = update.getStringExtra("docId");
        LinearLayout updateDocList = findViewById(R.id.updateDocList);
        db.collection(collection).document(docId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                Map<String, Object> data = doc.getData();
                if (data != null) {
                    for (Map.Entry<String, Object> entry: data.entrySet()) {
                        TextView textView = new TextView(this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setTextSize(20f);
                        textView.setText(entry.getKey());
                        updateDocList.addView(textView);

                        EditText editText = new EditText(this);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setTextSize(20f);
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        editText.setText(entry.getValue().toString());
                        updateDocList.addView(editText);
                    }
                }
            }
            else {
                Log.d("GetDoc", "Get Failed with ", task.getException());
            }
        });

        Button updateBtn= findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(v -> {
        });
    }
}