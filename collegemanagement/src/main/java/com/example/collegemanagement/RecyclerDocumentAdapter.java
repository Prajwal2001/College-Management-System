package com.example.collegemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class RecyclerDocumentAdapter extends RecyclerView.Adapter<RecyclerDocumentAdapter.ViewHolder> {

    Context context;
    ArrayList<Map.Entry<String, String>> list;

    int[] males = new int[]{R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7, R.drawable.m8, R.drawable.m9};
    int[] females = new int[]{R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7, R.drawable.f8, R.drawable.f9};


    public RecyclerDocumentAdapter(Context context, ArrayList<Map.Entry<String, String>> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getValue());
        holder.imgView.setImageResource(males[new Random().nextInt(9)]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public String getAdapterId(int position) {
        return list.get(position).getKey();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_item);
            imgView = itemView.findViewById(R.id.profile_pic);
        }

    }

}
