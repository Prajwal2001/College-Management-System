package com.example.collegemanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ViewDocuments extends AppCompatActivity {

    RecyclerDocumentAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
    String collection;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documents);

        Intent intent = getIntent();
        collection = intent.getStringExtra("collection");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddCollection.class);
                i.putExtra("collection",collection);
                startActivity(i);
            }
        });
        db.collection(collection)
                .addSnapshotListener((documentSnapshots, e) -> {
                    if (e != null)
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    list.clear();
                    if (documentSnapshots != null) {
                        for (DocumentSnapshot doc: documentSnapshots) {
                            Map<String, Object> data = doc.getData();
                            String item = "";
                            for(Map.Entry<String, Object> entry: Objects.requireNonNull(data).entrySet())
                                item = item.concat(entry.getKey() + ": " + entry.getValue() + "\n");

                            list.add(new AbstractMap.SimpleEntry<>(doc.getId(), item));
                        }
                    }
                    adapter = new RecyclerDocumentAdapter(this, list);
                    recyclerView.setAdapter(adapter);
                });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                int pos = viewHolder.getAdapterPosition();
                                Toast.makeText(ViewDocuments.this, "Deleted:"+ pos, Toast.LENGTH_SHORT).show();
                                db.collection(collection).document(adapter.getAdapterId(pos)).delete().addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext().getApplicationContext(), " Data deleted successfully ", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(getApplicationContext().getApplicationContext(), " Error: "+e.getMessage(), Toast.LENGTH_LONG).show());
                                adapter.notifyItemRemoved(pos);
                            })
                            .setNegativeButton("No", (dialog, which) -> adapter.notifyItemChanged(viewHolder.getAdapterPosition()))
                            .create()
                            .show();
                    break;

                case ItemTouchHelper.RIGHT:
//                    Toast.makeText(ViewDocuments.this, collection + ": "+ adapter.getAdapterId(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Intent update = new Intent(getApplicationContext(), UpdateDocument.class);
                    update.putExtra("collection", collection);
                    update.putExtra("docId", adapter.getAdapterId(viewHolder.getAdapterPosition()));
                    startActivity(update);
                    break;
            }
        }
    };
}