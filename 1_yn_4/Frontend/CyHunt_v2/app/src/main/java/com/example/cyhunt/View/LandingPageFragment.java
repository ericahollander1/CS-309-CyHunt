package com.example.cyhunt.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cyhunt.R;

/**
 * Handles any Android related aspect of the Landing page
 * A simple {@link Fragment} subclass.
 * Use the {@link LandingPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Lexi
 */
public class LandingPageFragment extends Fragment implements  View.OnClickListener{

    Button findCyButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor
     */
    public LandingPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LandingPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LandingPageFragment newInstance(String param1, String param2) {
        LandingPageFragment fragment = new LandingPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_landing_page, container, false);

        findCyButton = (Button)view.findViewById(R.id.find_cy_button);
        findCyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IMainView) getActivity()).onFindCyButtonClicked();
            }
        });


        return view;
    }

    /**
     * Included for the button onClickListener
     * @param v a View
     */
    @Override
    public void onClick(View v) {}
}