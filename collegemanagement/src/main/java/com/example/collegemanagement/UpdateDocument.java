package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class UpdateDocument extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_document);
        Intent update = getIntent();
        String collection = update.getStringExtra("collection");
        String docId = update.getStringExtra("docId");

        String heading = "Update " + collection.substring(0, 1).toUpperCase()+collection.substring(1);
        ((TextView)findViewById(R.id.viewDocHead)).setText(heading);
        HashMap<String, Integer> drawables = new HashMap<>();
        drawables.put("student", R.drawable.student);
        drawables.put("teacher", R.drawable.teacher);
        drawables.put("marks", R.drawable.marks);
        drawables.put("subject", R.drawable.subject);
        drawables.put("attendance",R.drawable.attendance);
        ((ImageView)findViewById(R.id.updateDocImage)).setImageResource((drawables.get(collection)));
        LinearLayout updateDocList = findViewById(R.id.updateDocList);
        LinkedList<Map.Entry <String, EditText>> editTexts = new LinkedList<>();

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
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setTextSize(20f);
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        editText.setText(entry.getValue().toString());
                        editTexts.add(new AbstractMap.SimpleEntry<>(entry.getKey(), editText));
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
            for (Map.Entry<String, EditText> entry : editTexts)
                db.collection(collection).document(docId).update(entry.getKey(), entry.getValue().getEditableText().toString());
            Toast.makeText(UpdateDocument.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}