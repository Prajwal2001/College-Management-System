package com.example.collegemanagement;

import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class AddCollection extends AppCompatActivity {

    String collection;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        collection = getIntent().getStringExtra("collection");
        String heading =  "Add " + collection.substring(0, 1).toUpperCase()+collection.substring(1);
        ((TextView) findViewById(R.id.addCollectionHead)).setText(heading);
        HashMap<String, Integer> drawables = new HashMap<>();
        drawables.put("student", R.drawable.student);
        drawables.put("teacher", R.drawable.teacher);
        drawables.put("marks", R.drawable.marks);
        drawables.put("subject", R.drawable.subject);
        drawables.put("attendance",R.drawable.attendance);

        LinearLayout addLayout = findViewById(R.id.addColLayout);
        LinkedList<Map.Entry <String, EditText>> editTexts = new LinkedList<>();

        //noinspection ConstantConditions
        ((ImageView)findViewById(R.id.viewDocImage)).setImageResource(drawables.get(collection));
        db.collection("metaData").document("schema").addSnapshotListener((doc, e) -> {
            if(e!=null)
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            else if(doc!=null){
                try {
                    @SuppressWarnings("ConstantConditions") JSONObject json = new JSONObject((doc.getData().get(collection).toString()).replaceAll("=", "=\"\""));
                    for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                        String key = it.next();

                        TextView textView = new TextView(this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setTextSize(20f);
                        textView.setText(key);
                        addLayout.addView(textView);

                        EditText editText = new EditText(this);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setTextSize(20f);
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
//                        editText.setBackground(Drawable.createFromPath("@drawable/round_corner"));
                        editTexts.add(new AbstractMap.SimpleEntry<>(key, editText));
                        addLayout.addView(editText);
                    }
                    Button submitBtn = (findViewById(R.id.submitBtn));
                    submitBtn.setOnClickListener(view -> {
                        Map<String, Object> data = new HashMap<>();
                        for (Map.Entry<String, EditText> entry:editTexts) {
                            data.put(entry.getKey(), entry.getValue().getEditableText().toString());
                        }
                        db.collection(collection)
                                .add(data)
                                .addOnSuccessListener(documentReference -> {
//                                        Log.d("Add Data", "DocumentSnapshot written to: " + collection);
                                    Toast.makeText(getApplicationContext(), "Document Added to " + collection, Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e1 -> {
//                                        Log.w("Add Data", "Error adding document", e);
                                    Toast.makeText(getApplicationContext(), "Error: " + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                        finish();
                    });
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                    finish();
                }
            }
        });
    }
}