package com.example.collegemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

class UpdateDocAdapter extends ArrayAdapter<Map.Entry<String, String>> {

    public UpdateDocAdapter(Activity context, ArrayList<Map.Entry<String, String>> ViewListItems) {
        super(context, 0, ViewListItems);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map.Entry<String, String> entry = (Map.Entry<String, String>) getItem(position);

        try {
            TextView fieldTextView = (TextView) convertView.findViewById(R.id.update_list_text);
            EditText fieldEditText = (EditText) convertView.findViewById(R.id.update_list_edit);


            fieldTextView.setText(entry.getKey());
            fieldEditText.setText(entry.getValue());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return convertView;
    }
}

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

        updateListView = (ListView) findViewById(R.id.updateList);
        //updateViewList.clear();

        db.collection(collection).document(docId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                Map<String, Object> data = doc.getData();
                assert data != null;
                for (Map.Entry<String, Object> entry: data.entrySet()) {
                    updateViewList.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()));
                }
                UpdateDocAdapter updateListAdapter = new UpdateDocAdapter(UpdateDocument.this, updateViewList);
                updateListView.setAdapter(updateListAdapter);
            }
            else {
                Log.d("GetDoc", "Get Failed with ", task.getException());
            }
        });
    }
}