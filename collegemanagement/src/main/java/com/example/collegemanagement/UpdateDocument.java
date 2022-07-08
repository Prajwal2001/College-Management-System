package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class UpdateDocument extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView updateListView;
    ArrayList<Map.Entry<String, String>> updateViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_document);
        Intent update = getIntent();
        String collection = update.getStringExtra("collection");
        String docId = update.getStringExtra("docId");

        updateListView = findViewById(R.id.updateList);
        updateViewList.clear();
        db.collection(collection).document(docId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                Map<String, Object> data = doc.getData();
                assert data != null;
                for (Map.Entry<String, Object> entry: data.entrySet())
                    updateViewList.add(new AbstractMap.SimpleEntry<>(entry.getValue().toString(), entry.getKey()));
                Toast.makeText(getApplicationContext(), doc.toString(), Toast.LENGTH_SHORT).show();
//                    ArrayAdapter updateListAdapter = new ArrayAdapter(UpdateDocument.this, updateViewList);
//                    updateListView.setAdapter(updateListAdapter);
            }
            else {
                Log.d("GetDoc", "Get Failed with ", task.getException());
            }
        });
    }
}