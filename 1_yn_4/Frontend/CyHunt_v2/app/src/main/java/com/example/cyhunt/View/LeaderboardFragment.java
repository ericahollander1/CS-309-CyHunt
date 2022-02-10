package com.example.cyhunt.View;

import static java.lang.Math.min;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.LeaderboardFunctionalityInterface;
import com.example.cyhunt.Presenter.LeaderboardFunctionality;
import com.example.cyhunt.Model.CustomVolleyListenerArrayList;
import com.example.cyhunt.LeaderboardAdapter;
import com.example.cyhunt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Screen that allows the user to view a leaderboard of the top users
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment implements CustomVolleyListenerArrayList {
    private RecyclerView leaderboard;
    private ArrayList l = new ArrayList();
    private ArrayAdapter adapter;
    private LeaderboardFunctionalityInterface c;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c = new LeaderboardFunctionality(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        leaderboard = (RecyclerView) view.findViewById(R.id.leaderboard_list);
        LeaderboardAdapter a = new LeaderboardAdapter(l);
        leaderboard.setAdapter(a);
        //leaderboard.setHasFixedSize(true);
        leaderboard.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //leaderboard.setAdapter(a);
        l = c.getUsersForDisplay(this);
        return view;
    }

    /**
     * Handles the successful completion of a JSON request
     * @param response the arrayList returned by the JSON request
     */
    @Override
    public void onSuccess(ArrayList<String> response) {
        LeaderboardAdapter a = new LeaderboardAdapter(l);
        leaderboard.setAdapter(a);

    }

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    @Override
    public void onError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("TAG", responseBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("TAG", e.toString());
        }


    }
}
