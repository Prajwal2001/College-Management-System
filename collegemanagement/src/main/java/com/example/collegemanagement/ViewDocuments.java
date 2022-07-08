package com.example.collegemanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class ViewDocuments extends AppCompatActivity {

    RecyclerDocumentAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Map.Entry<String, String>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_documents);

        Intent intent = getIntent();
        String collection = intent.getStringExtra("collection");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.collection(collection)
                .addSnapshotListener((documentSnapshots, e) -> {
                    if (e != null)
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    list.clear();
                    if (documentSnapshots != null) {
                        for (DocumentSnapshot doc: documentSnapshots) {
                            Map<String, Object> data = doc.getData();
                            String item = "";
                            for(Map.Entry<String, Object> entry: data.entrySet())
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
            ConstraintLayout constraintLayout = findViewById(R.id.activity_view_documents);
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                int pos = viewHolder.getAdapterPosition();
                                Toast.makeText(ViewDocuments.this, adapter.getAdapterId(pos), Toast.LENGTH_SHORT).show();
                                list.remove(pos);
                                Log.d("Deletion", "id: "+ adapter.getAdapterId(pos) + " ");
                                adapter.notifyItemRemoved(pos);
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            })
                            .create()
                            .show();
//                    Snackbar snackbar = Snackbar
//                            .make(constraintLayout, "Removed", Snackbar.LENGTH_LONG);
//                    snackbar.setAction("UNDO", new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(ViewDocuments.this, "UNDO", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    snackbar.setActionTextColor(Color.YELLOW);
//                    snackbar.show();
                    break;

                case ItemTouchHelper.RIGHT:
                    Toast.makeText(ViewDocuments.this, "swiped right", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}