package com.example.collegemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
    private static final String LOG_TAG = UpdateDocAdapter.class.getSimpleName();

    public UpdateDocAdapter(Activity context, ArrayList<Map.Entry<String, String>> ViewListItems) {
        super(context, 0, ViewListItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map.Entry<String, String> entry = getItem(position);

        TextView fieldTextView = (TextView) convertView.findViewById(R.id.update_list_text);
        EditText fieldEditText = (EditText) convertView.findViewById(R.id.update_list_edit);
        Toast.makeText(getContext(), entry.toString(), Toast.LENGTH_SHORT).show();
        fieldTextView.setText(entry.getKey());
        fieldEditText.setText(entry.getValue());
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
                    updateViewList.add(new AbstractMap.SimpleEntry<String, String>(entry.getKey(), entry.getValue().toString()));
                    Log.d("UpdtVal", "getView: "+ entry.getKey() + " " + entry.getValue());
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