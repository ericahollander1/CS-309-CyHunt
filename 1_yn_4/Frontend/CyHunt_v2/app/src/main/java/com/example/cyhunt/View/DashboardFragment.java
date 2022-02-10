package com.example.cyhunt.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cyhunt.R;

/**
 * Handles any Android related aspect of the Dashboard page
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Lexi
 */
public class DashboardFragment extends Fragment implements IDashboardView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferences;
    private TextView user_name;
    private TextView user_score;

    /**
     * Required empty public constructor
     */
    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a fragment
     * @param savedInstanceState a savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Creates the view to be inflated
     * @param inflater a layout inflator
     * @param container a container
     * @param savedInstanceState a savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        sharedPreferences = getActivity().getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);

        user_name = (TextView) view.findViewById(R.id.dashboard_user_name);
        user_score = (TextView) view.findViewById(R.id.dashboard_score);

        setName(sharedPreferences.getString("USERNAME", "Guest"));
        setScore();

        return view;
    }

    private void setName(String s) {
        s = "Hello, " + s;
        user_name.setText(s);
    }

    private void setScore() {
        String s = "Score: " + String.valueOf(sharedPreferences.getInt("USER_SCORE", -1));
        user_score.setText(s);
    }
}