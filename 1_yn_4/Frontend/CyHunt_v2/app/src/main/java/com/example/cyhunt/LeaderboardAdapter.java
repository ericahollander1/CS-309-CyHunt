package com.example.cyhunt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyhunt.R;

import java.util.ArrayList;

/**
 * this is an adapter for the recyclerView used in the adapter
 */
public class LeaderboardAdapter extends
        RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private ArrayList<String> entries;

    public LeaderboardAdapter(ArrayList<String> l)
    {
        entries = l;
    }

    @NonNull
    @Override
    //get the necessary context for the operation and connect the layout file
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_string_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    //fills the entries of the RecyclerView with the contents of entries
    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        TextView textView = holder.leaderboardEntry;
        textView.setText(entries.get(position));
        //Log.d("L_ADAPTER", (entries.get(position) + "\n"));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    /**Provide a direct reference to each of the views within a data item
    * Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView leaderboardEntry;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            leaderboardEntry = (TextView) itemView.findViewById(R.id.leaderboardEntry);
        }
    }
}