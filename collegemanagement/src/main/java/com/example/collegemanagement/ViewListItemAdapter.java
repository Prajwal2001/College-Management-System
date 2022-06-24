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

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


class ViewListItem {
    // Entry
    private String mViewListItemName;
    // Number of ViewListItems
    private String mViewListItemId;

    // * Create a new ViewListItem object.
    // * @param vName is the name of the ViewListItem
    // * @param vNumber is the corresponding number of ViewListItems

    public ViewListItem(String vName, String vNumber) {
        mViewListItemName = vName;
        mViewListItemId = vNumber;
    }

    // Get the name of the ViewListItem
    public String getViewListItemName() {
        return mViewListItemName;
    }
    //Get the  number of ViewListItems
    public String getViewListItemNumber() {
        return mViewListItemId;
    }
}

public class ViewListItemAdapter extends ArrayAdapter<ViewListItem> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String LOG_TAG = ViewListItemAdapter.class.getSimpleName();
    private String collection;
//      This is our own custom constructor (it doesn't mirror a superclass constructor).
//      The context is used to inflate the layout file, and the list is the data we want
//      to populate into the lists.
//      @param context        The current context. Used to inflate the layout file.
//     * @param ViewListItems A List of ViewListItem objects to display in a list


    public ViewListItemAdapter(Activity context, ArrayList<ViewListItem> ViewListItems, String collection) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, ViewListItems);
        this.collection = collection;
    }

//      Provides a view for an AdapterView (ListView, GridView, etc.)
//      @param position The position in the list of data that should be displayed in the list item view.
//     * @param convertView The recycled view to populate.
//     * @param parent The parent ViewGroup that is used for inflation.
//     * @return The View for the position in the AdapterView.

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
        // Get the version name from the current ViewListItem object and
        // set this text on the name TextView
        nameTextView.setText(currentViewListItem.getViewListItemName());

        Button deleteBtn = (Button) listItemView.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(view -> {
            Log.d("Deletion", "id: "+currentViewListItem.getViewListItemNumber() + " " + LOG_TAG);
            db.collection(collection).document(currentViewListItem.getViewListItemNumber()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext().getApplicationContext(), " Data deleted successfully ", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext().getApplicationContext(), " Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        // Find the TextView in the list_item.xml layout with the ID version_number
        //TextView numberTextView = (TextView) listItemView.findViewById(R.id.ViewListItem_number);
        // Get the version number from the current ViewListItem object and
        // set this text on the number TextView
        //numberTextView.setText(String.valueOf(currentViewListItem.getViewListItemNumber()));
        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        //ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current ViewListItem object and
        // set the image to iconView
        //iconView.setImageResource(currentViewListItem.getImageResourceId());
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}

