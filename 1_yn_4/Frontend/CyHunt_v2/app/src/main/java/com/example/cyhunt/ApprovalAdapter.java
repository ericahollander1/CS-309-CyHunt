package com.example.cyhunt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyhunt.R;
import com.example.cyhunt.View.ApproveDisapproveTriviaFragment;

import java.util.ArrayList;

/**
 * this is an adapter for the recyclerView used in the adapter
 */
public class ApprovalAdapter extends
        RecyclerView.Adapter<ApprovalAdapter.ViewHolder> {

    private ArrayList<triviaData> entries;

    public ApprovalAdapter(ArrayList<triviaData> e)
    {
        entries = e;
    }

    @NonNull
    @Override
    //get the necessary context for the operation and connect the layout file
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_approval_entry, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }



    //fills the entries of the RecyclerView with the contents of entries
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView authorHint = holder.authorHint;
        TextView questionHint = holder.questionHint;
        TextView triviaID = holder.triviaID;
        Button evaluate = holder.evaluate;
        authorHint.setText("Author: " + entries.get(position).author);
        questionHint.setText("Question: " + entries.get(position).question);
        triviaID.setText("ID: " + entries.get(position).ID);
        int i = position;
        evaluate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickEvaluate(v, i);
            }
        });

        //Log.d("L_ADAPTER", (entries.get(position) + "\n"));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    private void onClickEvaluate(View v, int i)
    {
        Log.d("ID IS ", entries.get(i).ID + "");
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Fragment myFragment = new ApproveDisapproveTriviaFragment(entries.get(i).ID, v.getContext());
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
    }

    /**Provide a direct reference to each of the views within a data item
     * Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView authorHint;
        public TextView questionHint;
        public TextView triviaID;
        public Button evaluate;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            authorHint = (TextView) itemView.findViewById(R.id.authorHint);
            questionHint = (TextView) itemView.findViewById(R.id.questionHint);
            triviaID = (TextView) itemView.findViewById(R.id.triviaID);
            evaluate = (Button) itemView.findViewById(R.id.evaluateTrivia);
        }

    }
}