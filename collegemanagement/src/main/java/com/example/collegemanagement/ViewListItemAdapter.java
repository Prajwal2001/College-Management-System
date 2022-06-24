package com.example.collegemanagement;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


class ViewListItem {
    private final String mViewListItemName;
    private final String mViewListItemId;

    public ViewListItem(String itemName, String itemDocId) {
        mViewListItemName = itemName;
        mViewListItemId = itemDocId;
    }

    // Get the name of the ViewListItem
    public String getViewListItemName() {
        return mViewListItemName;
    }
    //Get the  number of ViewListItems
    public String getViewListItemDocId() {
        return mViewListItemId;
    }
}

public class ViewListItemAdapter extends ArrayAdapter<ViewListItem> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String LOG_TAG = ViewListItemAdapter.class.getSimpleName();
    private final String collection;

    public ViewListItemAdapter(Activity context, ArrayList<ViewListItem> ViewListItems, String collection) {
        super(context, 0, ViewListItems);
        this.collection = collection;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.view_list_item, parent, false);
        }

        // Get the {@link ViewListItem} object located at this position in the list
        ViewListItem currentViewListItem = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.viewListItemText);
        nameTextView.setText(currentViewListItem.getViewListItemName());

        Button deleteBtn = (Button) listItemView.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(view -> {
            Log.d("Deletion", "id: "+currentViewListItem.getViewListItemDocId() + " " + LOG_TAG);
            db.collection(collection).document(currentViewListItem.getViewListItemDocId()).delete().addOnSuccessListener(aVoid -> Toast.makeText(getContext().getApplicationContext(), " Data deleted successfully ", Toast.LENGTH_SHORT).show())
            .addOnFailureListener(e -> Toast.makeText(getContext().getApplicationContext(), " Error: "+e.getMessage(), Toast.LENGTH_LONG).show());
        });

        return listItemView;
    }
}

