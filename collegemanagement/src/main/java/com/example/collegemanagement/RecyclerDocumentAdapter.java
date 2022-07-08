package com.example.collegemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerDocumentAdapter extends RecyclerView.Adapter<RecyclerDocumentAdapter.ViewHolder> {

    Context context;
    ArrayList<Map.Entry<String, String>> list;

    public RecyclerDocumentAdapter(Context context, ArrayList<Map.Entry<String, String>> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public String getAdapterId(int position){
        return list.get(position).getKey();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.list_item);
        }

    }

}
