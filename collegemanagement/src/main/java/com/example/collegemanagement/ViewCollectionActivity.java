package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewCollectionActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView listView;
    List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection);
        Intent intent = getIntent();
        String collection = intent.getStringExtra("collection");

        listView = findViewById(R.id.lv);

        db.collection(collection).addSnapshotListener((documentSnapshots, e) -> {
            if (e != null)
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            list.clear();
            for (DocumentSnapshot doc : documentSnapshots) {
                Map<String, Object> data = doc.getData();
                String item = "";
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    item = item.concat(entry.getKey()+": "+entry.getValue()+"\n");
                }
                list.add(item);
            }
            Log.d("List View", "onEvent: " + list.toString());

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ViewCollectionActivity.this,
                    R.layout.view_list_item,
                    list);
            listView.setAdapter(arrayAdapter);

        });
    }
}