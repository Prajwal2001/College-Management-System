package com.example.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewCollection extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView listView;
    List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection);
        Intent intent = getIntent();
        String collection = intent.getStringExtra("collection");

        TextView addBtn = findViewById(R.id.addDocument);
        listView = findViewById(R.id.lv);

        addBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddCollection.class);
            i.putExtra("collection",collection);
            startActivity(i);
        });

        db.collection(collection).addSnapshotListener((documentSnapshots, e) -> {
            if (e != null)
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            list.clear();
            if (documentSnapshots != null) {
                for (DocumentSnapshot doc : documentSnapshots) {
                    Map<String, Object> data = doc.getData();
                    String item = "";
                    assert data != null;
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        item = item.concat(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                    list.add(item);
                }
            }
            Log.d("List View", "onEvent: " + list.toString());

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ViewCollection.this,
                    R.layout.view_list_item,
                    list);
            listView.setAdapter(arrayAdapter);
        });
    }
}
/*

public class ViewListItem {
    // Name of the desert
    private String mViewListItemName;
    // Number of ViewListItems
    private int mViewListItemNumber;
    // Drawable resource ID
    private int mImageResourceId;
    */
/*
     * Create a new ViewListItem object.
     * @param vName is the name of the ViewListItem
     * @param vNumber is the corresponding number of ViewListItems
     * @param image is drawable reference ID that corresponds to the ViewListItem
     * *//*

    public ViewListItem(String vName, int vNumber, int imageResourceId) {
        mViewListItemName = vName;
        mViewListItemNumber = vNumber;
        mImageResourceId = imageResourceId;
    }
    */
/*
     * Get the name of the ViewListItem
     *//*

    public String getViewListItemName() {
        return mViewListItemName;
    }

    */
/*
      Get the  number of ViewListItems
     *//*

    public int getViewListItemNumber() {
        return mViewListItemNumber;
    }

    */
/*
      Get the image resource ID
     *//*

    public int getImageResourceId() {
        return mImageResourceId;

public class ViewListItemAdapter extends ArrayAdapter<ViewListItem> {

    private static final String LOG_TAG = ViewListItemAdapter.class.getSimpleName();

    */
/*
      This is our own custom constructor (it doesn't mirror a superclass constructor).
      The context is used to inflate the layout file, and the list is the data we want
      to populate into the lists.

      @param context        The current context. Used to inflate the layout file.
     * @param ViewListItems A List of ViewListItem objects to display in a list
     *//*

    public ViewListItemAdapter(Activity context, ArrayList<ViewListItem> ViewListItems) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, ViewListItems);
    }

    */
/*
      Provides a view for an AdapterView (ListView, GridView, etc.)

      @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     *//*

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link ViewListItem} object located at this position in the list
        ViewListItem currentViewListItem = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.ViewListItem_name);
        // Get the version name from the current ViewListItem object and
        // set this text on the name TextView
        nameTextView.setText(currentViewListItem.getViewListItemName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.ViewListItem_number);
        // Get the version number from the current ViewListItem object and
        // set this text on the number TextView
        numberTextView.setText(String.valueOf(currentViewListItem.getViewListItemNumber()));

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current ViewListItem object and
        // set the image to iconView
        iconView.setImageResource(currentViewListItem.getImageResourceId());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}*/
